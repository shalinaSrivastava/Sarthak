package com.lecturedekhoelearn.in.activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.DatabaseHandler;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.DailyQuizModel;
import com.lecturedekhoelearn.in.model.DailyQuizModelDetails;
import com.lecturedekhoelearn.in.model.DailyQuizSubmitModel;
import com.lecturedekhoelearn.in.preferences.AppPreferences;
import com.lecturedekhoelearn.in.util.BroadcastService;
import com.squareup.picasso.Picasso;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.lecturedekhoelearn.in.Constant.userProfilePath;
import static com.lecturedekhoelearn.in.util.StaticData.KEY_ID;
import static com.lecturedekhoelearn.in.util.StaticData.KEY_Q_ID;
import static com.lecturedekhoelearn.in.util.StaticData.KEY_REDIRECT;
import static com.lecturedekhoelearn.in.util.StaticData.KEY_TEST_LOADED;

public class DailyQuizActivity extends AppCompatActivity {

    List<DailyQuizModelDetails> questionsList;
    int quid = 0;
    ArrayList<DailyQuizModelDetails> testPaperData;
    DailyQuizModelDetails currentQuestions;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String Tag = "DailyQuizActivity";

    @BindView(R.id.tvQuestion)
    TextView tvQuestion;
    @BindView(R.id.ques)
    TextView ques;
    @BindView(R.id.tv_show_test)
    TextView tv_show_test;
    @BindView(R.id.tvQuestion_image)
    WebView tvQuestion_image;
    @BindView(R.id.rbOption1)
    CheckBox rbOption1;
    @BindView(R.id.rbOption2)
    CheckBox rbOption2;
    @BindView(R.id.rbOption3)
    CheckBox rbOption3;
    @BindView(R.id.rbOption4)
    CheckBox rbOption4;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.linearQstn)
    LinearLayout linear;


    @BindView(R.id.ll_btn)
    LinearLayout ll_btn;
    @BindView(R.id.scrollQuestion)
    ScrollView scrollQuestion;

    @BindView(R.id.scrollNodata)
    ScrollView scrollNodata;

    @BindView(R.id.img_student)
    CircleImageView img_student;


    @BindView(R.id.tv_student_name)
    TextView tv_student_name;

    @BindView(R.id.scroll_test_result)
    ScrollView scroll_test_result;

    @BindView(R.id.scroll_test_submiited)
    ScrollView scroll_test_submiited;

    DatabaseHandler dbHelper;
    boolean updateTimer = false;
    String id;
    String mqid;
    String user_id;

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateGUI(intent); // or whatever method used to update your GUI fields
        }
    };
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
    Button btnsave;

    @BindView(R.id.btn_go_to_dashboard)
    Button btn_go_to_dashboard;

    @BindView(R.id.btn_go_to_dashboard_s)
    Button btn_go_to_dashboard_s;

    public String ChValueOne;
    public String ChValueTwo;
    public String ChValueThree;
    public String ChValueFour;
    private long START_TIME_IN_MILLIS;
    CountDownTimer countDownTimer;
    String strstarttime, strendTime;
    String diffTime;

    String formattedDate;
    TextView time_q;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_quiz);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

   /*     if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }
*/
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
        mqid = preferences.getString("class_id", "");
        user_id = preferences.getString("studentId", "");
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        dbHelper = new DatabaseHandler(this);
        id = getIntent().getStringExtra(KEY_ID);
        time_q=findViewById(R.id.time_q);
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data_save();
            }

        });

        if (getIntent().getBooleanExtra(KEY_REDIRECT, false)) {
            testPaperData = (ArrayList<DailyQuizModelDetails>) dbHelper.getAllQuestions();
            if (questionsList.size() > 0) {
                quid = getIntent().getIntExtra(KEY_Q_ID, 0) - 1;
                currentQuestions = testPaperData.get(quid);
                setQuestionView();
            } else {
                Toast.makeText(DailyQuizActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        dbHelper.resetDatabse();
        callTestPaperAPi(formattedDate);

        btn_go_to_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(DailyQuizActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        btn_go_to_dashboard_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(DailyQuizActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    public void data_save(){
        dbHelper.updateAnswerAssignemnt(testPaperData.get(quid).getId(), "1", "1", "1", "1");
        String answer1 = testPaperData.get(quid).getAnswer_a();
        String answer2 = testPaperData.get(quid).getAnswer_b();
        String answer3 = testPaperData.get(quid).getAnswer_c();
        String answer4 = testPaperData.get(quid).getAnswer_d();

        if (rbOption1.isChecked()) {
            ChValueOne = "1";

        } else {
            ChValueOne = "0";

        }
        if (rbOption2.isChecked()) {
            ChValueTwo = "1";

        } else {
            ChValueTwo = "0";

        }
        if (rbOption3.isChecked()) {
            ChValueThree = "1";

        } else {
            ChValueThree = "0";
        }
        if (rbOption4.isChecked()) {
            ChValueFour = "1";
        } else {

            ChValueFour = "0";
        }

        if (answer1.equals(ChValueOne) && answer2.equals(ChValueTwo) && answer3.equals(ChValueThree) && answer4.equals(ChValueFour)) {
            DateFormat df = new java.text.SimpleDateFormat("hh:mm:ss");
            strendTime = df.format(new Date());
            getDifferenceTime();
            ResultSendData();
        } else {
            DateFormat df = new java.text.SimpleDateFormat("hh:mm:ss");
            strendTime = df.format(new Date());
            getDifferenceTime();
            ResultSendData();
        }
    }

    public void start_timer(){
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
      countDownTimer =new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                time_q.setText("Time remaining: " + millisUntilFinished / 1000);
            }
            public void onFinish() {
                time_q.setText("done!");
                data_save();
            }
        }.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setQuestionView() {
        tvQuestion.setVisibility(View.GONE);
        start_timer();
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

        if (quid == testPaperData.size())
            quid = testPaperData.size() - 1;//2

        int currnt_ques=quid+1;
        ques.setText("Question "+currnt_ques+" / "+testPaperData.size());
        Log.e("qstId", quid + "");

        DateFormat df = new java.text.SimpleDateFormat("hh:mm:ss");
        strstarttime = df.format(new Date());
    }

    private void callTestPaperAPi(String formattedDate) {
        pDialog.setMessage("Loading your Daily Quiz...");
        showDialog();
        Call<DailyQuizModel> call = Api_Client.getInstance().getDailyQuiz(mqid, user_id, formattedDate);
        call.enqueue(new Callback<DailyQuizModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<DailyQuizModel> call, Response<DailyQuizModel> response) {
                if (response.body().getStatus().equals(1)) {
                    hideDialog();
                    DailyQuizModel testPaper = response.body();
                    testPaperData = (ArrayList<DailyQuizModelDetails>) testPaper.getDailyQuizModelDetails();
                    if (testPaperData != null && !testPaperData.isEmpty() && testPaperData.size() > 0) {

                        scrollNodata.setVisibility(View.GONE);
                        scrollQuestion.setVisibility(View.VISIBLE);
                        ll_btn.setVisibility(View.VISIBLE);
                        for (DailyQuizModelDetails testDetails : testPaperData) {
                            id = String.valueOf(testDetails.getId());
                        }

                        Intent intent = new Intent(DailyQuizActivity.this, BroadcastService.class);
                        startService(intent);

                        for (int i = 0; i < testPaper.getDailyQuizModelDetails().size(); i++) {
                            System.out.println("detailj " + testPaper.getDailyQuizModelDetails());
                            dbHelper.addQuestion(testPaper.getDailyQuizModelDetails().get(i), i);
                        }
                        testPaperData = (ArrayList<DailyQuizModelDetails>) dbHelper.getAllQuestions();
                        System.out.println("dataj " + testPaperData);
                        currentQuestions = testPaperData.get(quid);
                        setQuestionView();

                        AppPreferences.saveBollen(DailyQuizActivity.this, KEY_TEST_LOADED, true);
                        updateTimer = true;
                    } else {
                        ll_btn.setVisibility(View.GONE);
                        scrollNodata.setVisibility(View.VISIBLE);
                        scrollQuestion.setVisibility(View.GONE);
                        Toast.makeText(DailyQuizActivity.this, "Quiz Not found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    hideDialog();
                    Calendar c = Calendar.getInstance();
                    int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

                    if (timeOfDay >= 0 && timeOfDay < 12) {

                        scroll_test_submiited.setVisibility(View.VISIBLE);

                    } else if (timeOfDay >= 12 && timeOfDay < 16) {

                        scroll_test_submiited.setVisibility(View.VISIBLE);
                    } else if (timeOfDay >= 16 && timeOfDay < 21) {

                        scroll_test_submiited.setVisibility(View.VISIBLE);

                    } else if (timeOfDay >= 21 && timeOfDay < 22){
                        DailyQuizModel testPaper = response.body();
                        testPaperData = (ArrayList<DailyQuizModelDetails>) testPaper.getDailyQuizModelDetails();
                        if (testPaperData != null && !testPaperData.isEmpty()) {
                            scroll_test_result.setVisibility(View.VISIBLE);
                            tv_student_name.setText("Today's Winner:- " + testPaperData.get(0).getStudent_name());
                            Picasso.get().load(userProfilePath + testPaperData.get(0).getProfile_pic()).placeholder(R.drawable.profile_pic_place_holder)// Place holder image from drawable folder
                                    .error(R.drawable.profile_pic_place_holder)
                                    .into(img_student);
                        } else {
                            scroll_test_result.setVisibility(View.VISIBLE);
                            Toast.makeText(DailyQuizActivity.this, "Your Answer is incorrect", Toast.LENGTH_SHORT).show();
                        }

                    }else if(timeOfDay>=22 && timeOfDay <24){
                        scroll_test_submiited.setVisibility(View.VISIBLE);
                        tv_show_test.setText("Quiz will start at 7 am. \n Till then keep learning. \n Thank you !");
                    }
                }
            }
            @Override
            public void onFailure(Call<DailyQuizModel> call, Throwable t) {
                call.cancel();
                hideDialog();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(br, new IntentFilter(BroadcastService.COUNTDOWN_BR));
        Log.i("tag", "Registered broacast receiver");
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(br);
        //    AppPreferences.saveString(DailyQuizActivity.this, "timer_value", tvTimer.getText().toString());
        Log.i("tag", "Unregistered broacast receiver");
    }

    @Override
    public void onStop() {
        try {
            unregisterReceiver(br);
        } catch (Exception e) {
            // Receiver was probably already stopped in onPause()
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, BroadcastService.class));
        Log.i("Tag", "Stopped service");
        super.onDestroy();
    }

    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            long millisUntilFinished = intent.getLongExtra("countdown", 0);

            Log.i("TAG", "Countdown seconds remaining: " + millisUntilFinished / 1000);
        }
    }


    public void showConfirmationPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DailyQuizActivity.this);
        LayoutInflater layoutInflater = LayoutInflater.from(DailyQuizActivity.this);
        View view = layoutInflater.inflate(R.layout.confirmation_popup, null);
        builder.setView(view);
        builder.setCancelable(true);
        final AlertDialog alert = builder.create();
        view.findViewById(R.id.alertNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    public void getDifferenceTime() {
        // Custom date format
        java.text.SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(strstarttime);
            d2 = format.parse(strendTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Get msec from each, and subtract.
        long diff = d2.getTime() - d1.getTime();
        long hours = TimeUnit.MILLISECONDS.toHours(diff);
        long remainingMinutesInMillis = diff - TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(remainingMinutesInMillis);
        long remainingSecondsInMillis = remainingMinutesInMillis - TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(remainingSecondsInMillis);
        diffTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }


    public void ResultSendData() {
        Call<DailyQuizSubmitModel> resultSubmitModelCall = Api_Client.getInstance().DailyQuizSubmit(user_id, String.valueOf(currentQuestions.getId()), ChValueOne, ChValueTwo, ChValueThree, ChValueFour, diffTime);
        resultSubmitModelCall.enqueue(new Callback<DailyQuizSubmitModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<DailyQuizSubmitModel> call, Response<DailyQuizSubmitModel> response) {
                if (response.body().getStatus().equals(1)) {
                    quid++;
                    if (quid < testPaperData.size()) {
                        radioGroup.clearCheck();
                        rbOption1.setChecked(false);
                        rbOption2.setChecked(false);
                        rbOption3.setChecked(false);
                        rbOption4.setChecked(false);
                        currentQuestions = testPaperData.get(quid);
                        btnsave.setVisibility(View.VISIBLE);
                        setQuestionView();
                    } else {
                        quid = testPaperData.size() - 1;
                        scrollQuestion.setVisibility(View.GONE);
                        ll_btn.setVisibility(View.GONE);
                        scroll_test_submiited.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onFailure(Call<DailyQuizSubmitModel> call, Throwable t) {
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}