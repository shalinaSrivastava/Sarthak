package com.lecturedekhoelearn.in.activity.bookmark;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.DatabaseHandler;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.MyBookmarkActivity;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.BookmarkDetailsModel;
import com.lecturedekhoelearn.in.model.ShowBookMarkModelDetails;
import com.lecturedekhoelearn.in.model.TestJDetail;
import com.lecturedekhoelearn.in.preferences.AppPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.lecturedekhoelearn.in.util.StaticData.KEY_ID;
import static com.lecturedekhoelearn.in.util.StaticData.KEY_Q_ID;
import static com.lecturedekhoelearn.in.util.StaticData.KEY_REDIRECT;
import static com.lecturedekhoelearn.in.util.StaticData.KEY_TEST_LOADED;


public class BookmarkTestSeries extends Fragment {

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private Context context;

    @SuppressLint("CommitPrefEdits")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.activity_my_bookmark, container, false);
        context=getActivity();
        ButterKnife.bind(Objects.requireNonNull(getActivity()));
        tvQuestion=view.findViewById(R.id.tvQuestion);
        tvQuestion_image=view.findViewById(R.id.tvQuestion_image);
        radioGroup=view.findViewById(R.id.radioGroup);
        tvAnswered=view.findViewById(R.id.tvAnswered);
        tvTotal=view.findViewById(R.id.tvTotal);
        linear=view.findViewById(R.id.linearQstn);
        weboption1=view.findViewById(R.id.option1);
        weboption2=view.findViewById(R.id.option2);
        weboption3=view.findViewById(R.id.option3);
        weboption4=view.findViewById(R.id.option4);
        btn_save=view.findViewById(R.id.btn_save);
        ll_button=view.findViewById(R.id.ll_button);
        linear1=view.findViewById(R.id.linear1);
        scroll_no_data=view.findViewById(R.id.scroll_no_data);
        scroll_view_data=view.findViewById(R.id.scroll_view_data);



        preferences = getActivity().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        mqid = preferences.getString("test_id", "");
        user_id = preferences.getString("studentId", "");
        total_time = preferences.getString("testduration", "");

        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);


        dbHelper = new DatabaseHandler(context);

        id = getActivity().getIntent().getStringExtra(KEY_ID);


        if (getActivity().getIntent().getBooleanExtra(KEY_REDIRECT, false)) {
            testPaperData = (ArrayList<ShowBookMarkModelDetails>) dbHelper.getAllBookMark();
            if (questionsList.size() > 0) {
                quid = getActivity().getIntent().getIntExtra(KEY_Q_ID, 0) - 1;
                currentQuestions = testPaperData.get(quid);
                setQuestionView();
                tvAnswered.setText(quid + 1 + "/");
                tvTotal.setText(testPaperData.size() + "");
            } else {
                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
            }
        }

        dbHelper.resetDatabse();
        callTestPaperAPi();
        return view;
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

            Toast.makeText(context, "Question ID not found", Toast.LENGTH_SHORT).show();
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
                        AppPreferences.saveBollen(context, KEY_TEST_LOADED, true);

                    } else {
                        scroll_no_data.setVisibility(View.VISIBLE);
                        ll_button.setVisibility(View.GONE);
                        linear1.setVisibility(View.GONE);
                        scroll_view_data.setVisibility(View.GONE);
                        Toast.makeText(context, "Question Not found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    hideDialog();
                  //  Toast.makeText(context, "Your Bookmark List is empty ", Toast.LENGTH_SHORT).show();
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

}
