package com.lecturedekhoelearn.in.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.DatabaseHandler;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.BookmarkDetailsModel;
import com.lecturedekhoelearn.in.model.ShowBookMarkModelDetails;
import com.lecturedekhoelearn.in.model.TestJDetail;
import com.lecturedekhoelearn.in.preferences.AppPreferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lecturedekhoelearn.in.util.StaticData.KEY_ID;
import static com.lecturedekhoelearn.in.util.StaticData.KEY_Q_ID;
import static com.lecturedekhoelearn.in.util.StaticData.KEY_REDIRECT;
import static com.lecturedekhoelearn.in.util.StaticData.KEY_TEST_LOADED;

public class MyBookmarkActivity extends AppCompatActivity {
    List<TestJDetail> questionsList;
    int quid = 0;
    ArrayList<ShowBookMarkModelDetails> testPaperData;
    ShowBookMarkModelDetails currentQuestions;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String Tag = "BookMarkedActivity";
    @BindView(R.id.tvQuestion)
    TextView tvQuestion;
    @BindView(R.id.tvQuestion_image)
    WebView tvQuestion_image;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.tvAnswered)
    TextView tvAnswered;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.linearQstn)
    LinearLayout linear;
    DatabaseHandler dbHelper;
    String total_time;
    String id;
    String mqid;
    String user_id;
    private ProgressDialog pDialog;
    String opt1, opt2, opt3, opt4;
    @BindView(R.id.option1)
    WebView weboption1;
    @BindView(R.id.option2)
    WebView weboption2;
    @BindView(R.id.option3)
    WebView weboption3;
    @BindView(R.id.option4)
    WebView weboption4;

    @BindView(R.id.btn_save)
    Button btn_save;


    @BindView(R.id.ll_button)
    LinearLayout ll_button;

    @BindView(R.id.linear1)
    LinearLayout linear1;

    @BindView(R.id.scroll_no_data)
    ScrollView scroll_no_data;

    @BindView(R.id.scroll_view_data)
    ScrollView scroll_view_data;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookmark);

       getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
                toolbar.setNavigationIcon(d);
            }
        });

        ButterKnife.bind(this);

        preferences = getApplication().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        mqid = preferences.getString("test_id", "");
        user_id = preferences.getString("studentId", "");
        total_time = preferences.getString("testduration", "");

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        dbHelper = new DatabaseHandler(this);

        id = getIntent().getStringExtra(KEY_ID);


        if (getIntent().getBooleanExtra(KEY_REDIRECT, false)) {
            testPaperData = (ArrayList<ShowBookMarkModelDetails>) dbHelper.getAllBookMark();
            if (questionsList.size() > 0) {
                quid = getIntent().getIntExtra(KEY_Q_ID, 0) - 1;
                currentQuestions = testPaperData.get(quid);
                setQuestionView();
                tvAnswered.setText(quid + 1 + "/");
                tvTotal.setText(testPaperData.size() + "");
            } else {
                Toast.makeText(MyBookmarkActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                finish();

            }
        }

        dbHelper.resetDatabse();
        callTestPaperAPi();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setQuestionView() {

        if (currentQuestions.getId() != null) {

            tvQuestion.setVisibility(View.GONE);

            String html = currentQuestions.getQuestion();
            tvQuestion_image.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", "");
            opt1 = currentQuestions.getA();
            opt2 = currentQuestions.getB();
            opt3 = currentQuestions.getC();
            opt4 = currentQuestions.getD();

            weboption1.loadDataWithBaseURL("file:///android_asset/", opt1, "text/html", "utf-8", "");
            weboption2.loadDataWithBaseURL("file:///android_asset/", opt2, "text/html", "utf-8", "");
            weboption3.loadDataWithBaseURL("file:///android_asset/", opt3, "text/html", "utf-8", "");
            weboption4.loadDataWithBaseURL("file:///android_asset/", opt4, "text/html", "utf-8", "");

            tvAnswered.setText(quid + 1 + "/");
            tvTotal.setText(testPaperData.size() + "");


            if (quid == testPaperData.size())
                quid = testPaperData.size() - 1;//2

            Log.e("qstId", quid + "");

        } else {

            Toast.makeText(this, "Question ID not found", Toast.LENGTH_SHORT).show();
        }

    }


    private void callTestPaperAPi() {
        pDialog.setMessage("Loading your Test..");
        showDialog();
        Call<BookmarkDetailsModel> call = Api_Client.getInstance().getAllBookmarkApi(user_id);
        call.enqueue(new Callback<BookmarkDetailsModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<BookmarkDetailsModel> call, Response<BookmarkDetailsModel> response) {
                if (response.body().getStatus().equals(1)) {
                    hideDialog();
                    BookmarkDetailsModel testPaper = response.body();
                    testPaperData = (ArrayList<ShowBookMarkModelDetails>) testPaper.getShowBookMarkModelDetails();
                    if (testPaperData != null && !testPaperData.isEmpty() && testPaperData.size()>0) {
                        scroll_no_data.setVisibility(View.GONE);
                        ll_button.setVisibility(View.VISIBLE);
                        linear1.setVisibility(View.VISIBLE);
                        scroll_view_data.setVisibility(View.VISIBLE);

                        for (ShowBookMarkModelDetails testDetails : testPaperData) {
                            id = String.valueOf(testDetails.getId());
                        }
                        for (int i = 0; i < testPaper.getShowBookMarkModelDetails().size(); i++) {
                            System.out.println("detailj " + testPaper.getShowBookMarkModelDetails());
                            dbHelper.addBookQuestion(testPaper.getShowBookMarkModelDetails().get(i), i);
                        }
                        testPaperData = (ArrayList<ShowBookMarkModelDetails>) dbHelper.getAllBookMark();
                        System.out.println("dataj " + testPaperData);
                        currentQuestions = testPaperData.get(quid);
                        setQuestionView();

                        tvAnswered.setText(quid + 1 + "/");
                        tvTotal.setText(testPaperData.size() + "");
                        AppPreferences.saveBollen(MyBookmarkActivity.this, KEY_TEST_LOADED, true);

                    } else {
                        scroll_no_data.setVisibility(View.VISIBLE);
                        ll_button.setVisibility(View.GONE);
                        linear1.setVisibility(View.GONE);
                        scroll_view_data.setVisibility(View.GONE);
                        Toast.makeText(MyBookmarkActivity.this, "Question Not found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    hideDialog();
                    Toast.makeText(MyBookmarkActivity.this, "Your Bookmark List is empty ", Toast.LENGTH_SHORT).show();
                    scroll_no_data.setVisibility(View.VISIBLE);
                    ll_button.setVisibility(View.GONE);
                    linear1.setVisibility(View.GONE);
                    scroll_view_data.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<BookmarkDetailsModel> call, Throwable t) {
                call.cancel();
                hideDialog();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.btn_save)
    public void bookmark(View view) {
        quid++;
        if (quid < testPaperData.size()) {
            currentQuestions = testPaperData.get(quid);
            setQuestionView();
        } else {
            quid = testPaperData.size() - 1;
            btn_save.setVisibility(View.GONE);
        }
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                return true;

            case R.id.action_video_bookmark:
                Intent i = new Intent(MyBookmarkActivity.this, VideoShowBookmarkActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                return true;

            default:
                return super.onContextItemSelected(item);
        }

    }

}