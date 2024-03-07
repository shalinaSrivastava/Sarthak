package com.lecturedekhoelearn.in.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.DatabaseHandler;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.BookMarkModel;
import com.lecturedekhoelearn.in.model.ResultSubmitModel;
import com.lecturedekhoelearn.in.model.StudentAnalysisModel;
import com.lecturedekhoelearn.in.model.TestJDetail;
import com.lecturedekhoelearn.in.model.Testj;
import com.lecturedekhoelearn.in.preferences.AppPreferences;
import com.lecturedekhoelearn.in.util.BroadcastService;
import com.lecturedekhoelearn.in.util.StaticData;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestStart extends AppCompatActivity implements StaticData {

    @BindView(R.id.timer)
    TextView tvTimer;
    String timeFormatted;
    List<TestJDetail> questionsList;
    int score = 0;
    int quid = 0;
    ArrayList<TestJDetail> testPaperData;
    TestJDetail currentQuestions;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String Tag = "TestStart";
    @BindView(R.id.tvQuestion)
    TextView tvQuestion;
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
    @BindView(R.id.tvAnswered)
    TextView tvAnswered;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.imgQstn)
    ImageView imQstn;
    @BindView(R.id.linearQstn)
    LinearLayout linear;
    int UpdateId = 0;
    DatabaseHandler dbHelper;
    List<String> reviewList;
    String total_time;
    boolean updateTimer = false;
    String id;
    String mqid;
    String user_id;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;
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

    public String ChValueOne;
    public String ChValueTwo;
    public String ChValueThree;
    public String ChValueFour;
    public String sessionID;
    String test_series_id;
    private long START_TIME_IN_MILLIS;

    String[] values;
    String hour, min, Second, totaltimetaken, StrPerQuestionTime;
    long hours, mint, sec, t, result, time_taken;

    @BindView(R.id.ic_bookmmark)
    ImageView ic_bookmmark;

    String strstarttime, strendTime;

    String diffTime;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_start);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }

        trasparentNoti();
        ButterKnife.bind(this);

        preferences = getApplication().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        mqid = preferences.getString("test_id", "");
        user_id = preferences.getString("studentId", "");
        total_time = preferences.getString("testduration", "");
        test_series_id = preferences.getString("test_series_id", "");
        sessionID = preferences.getString("session_id", "");

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        dbHelper = new DatabaseHandler(this);

        START_TIME_IN_MILLIS = Long.valueOf(total_time) * 60000;
        mTimeLeftInMillis = Long.valueOf(total_time) * 60000;

        id = getIntent().getStringExtra(KEY_ID);


        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    //   Toast.makeText(TestStart.this, "time Formatted" + timeFormatted, Toast.LENGTH_SHORT).show();

                    DateFormat df = new java.text.SimpleDateFormat("hh:mm:ss");
                    strendTime = df.format(new Date());
                    //  Toast.makeText(TestStart.this, "startDate:" + strendTime, Toast.LENGTH_SHORT).show();
                    getDifferenceTime();

                    values = timeFormatted.split(":");

                    hour = values[0];
                    min = values[1];
                    Second = values[2];

                    hours = Integer.parseInt(hour);
                    mint = Integer.parseInt(min);
                    sec = Integer.parseInt(Second);

                    t = (hours * 3600L) + (mint * 60L) + sec;

                    result = TimeUnit.SECONDS.toMillis(t);

                    time_taken = ((START_TIME_IN_MILLIS - result));

                    totaltimetaken = String.format(Locale.getDefault(), "%02d:%02d:%02d",
                            TimeUnit.MILLISECONDS.toHours(time_taken) % 60,
                            TimeUnit.MILLISECONDS.toMinutes(time_taken) % 60,
                            TimeUnit.MILLISECONDS.toSeconds(time_taken) % 60);




                    ResultSendData();


                } else {

                    DateFormat df = new java.text.SimpleDateFormat("hh:mm:ss");
                    strendTime = df.format(new Date());
                    //     Toast.makeText(TestStart.this, "startDate:" + strendTime, Toast.LENGTH_SHORT).show();

                    values = timeFormatted.split(":");
                    hour = values[0];
                    min = values[1];
                    Second = values[2];

                    hours = Integer.parseInt(hour);
                    mint = Integer.parseInt(min);
                    sec = Integer.parseInt(Second);

                    t = (hours * 3600L) + (mint * 60L) + sec;

                    result = TimeUnit.SECONDS.toMillis(t);

                    time_taken = ((START_TIME_IN_MILLIS - result));

                    totaltimetaken = String.format(Locale.getDefault(), "%02d:%02d:%02d",
                            TimeUnit.MILLISECONDS.toHours(time_taken) % 60,
                            TimeUnit.MILLISECONDS.toMinutes(time_taken) % 60,
                            TimeUnit.MILLISECONDS.toSeconds(time_taken) % 60);


                    getDifferenceTime();

                    ResultSendData();
                }

            }
        });


        if (getIntent().getBooleanExtra(KEY_REDIRECT, false)) {
            testPaperData = (ArrayList<TestJDetail>) dbHelper.getAllTestQuestions();
            if (questionsList.size() > 0) {
                quid = getIntent().getIntExtra(KEY_Q_ID, 0) - 1;
                currentQuestions = testPaperData.get(quid);

                setQuestionView();
                tvAnswered.setText(quid + 1 + "/");
                tvTotal.setText(testPaperData.size() + "");
            } else {
                Toast.makeText(TestStart.this, "No Data Found", Toast.LENGTH_SHORT).show();
                finish();

            }
        }

        dbHelper.resetDatabse();
        callTestPaperAPi();
    }

    public void trasparentNoti() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
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

            DateFormat df = new java.text.SimpleDateFormat("hh:mm:ss");
            strstarttime = df.format(new Date());
            //  System.out.println("strEndTime: " + strstarttime);
            //  Toast.makeText(this, "startDate:" + strstarttime, Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(this, "Question ID not found", Toast.LENGTH_SHORT).show();
        }

    }



    private void countDownStart(long millis) {
        mCountDownTimer = new CountDownTimer(millis, 1000) {
            public void onTick(long millisUntilFinished) {
                timeFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                tvTimer.setText(timeFormatted);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                tvTimer.setText("done!");
                finish();
            }

        }.start();
        mTimerRunning = true;
    }

    private void callTestPaperAPi() {
        pDialog.setMessage("Loading your Test..");
        showDialog();
        Call<Testj> call = Api_Client.getInstance().getQuestionLoad(mqid.trim());
        call.enqueue(new Callback<Testj>() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onResponse(Call<Testj> call, Response<Testj> response) {
                if (response.body().getStatus().equals(1)) {
                    hideDialog();

                    Testj testPaper = response.body();
                    testPaperData = (ArrayList<TestJDetail>) testPaper.getGetTestDetail();
                    if (testPaperData != null) {

                        for (TestJDetail testDetails : testPaperData) {
                            id = String.valueOf(testDetails.getId());
                        }
                        AppPreferences.saveString(TestStart.this, "testduration", total_time);
                        Intent intent = new Intent(TestStart.this, BroadcastService.class);
                        startService(intent);
                        countDownStart(mTimeLeftInMillis);
                        for (int i = 0; i < testPaper.getGetTestDetail().size(); i++) {
                            System.out.println("detailj " + testPaper.getGetTestDetail());
                            dbHelper.addQuestion(testPaper.getGetTestDetail().get(i), i);
                        }
                        testPaperData = (ArrayList<TestJDetail>) dbHelper.getAllTestQuestions();
                        System.out.println("dataj " + testPaperData);
                        currentQuestions = testPaperData.get(quid);
                        setQuestionView();

                        tvAnswered.setText(quid + 1 + "/");
                        tvTotal.setText(testPaperData.size() + "");
                        AppPreferences.saveBollen(TestStart.this, KEY_TEST_LOADED, true);
                        updateTimer = true;
                    } else {
                        Toast.makeText(TestStart.this, "Question Not found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    updateTimer = false;
                    hideDialog();
                    finish();
                }

            }

            @Override
            public void onFailure(Call<Testj> call, Throwable t) {
                call.cancel();
                hideDialog();
            }
        });
    }

    @OnClick(R.id.btnFinish)
    public void finishTest(View view) {
        showConfirmationPopup();
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
        AppPreferences.saveString(TestStart.this, "timer_value", tvTimer.getText().toString());
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
            countDownStart(millisUntilFinished);
            Log.i("TAG", "Countdown seconds remaining: " + millisUntilFinished / 1000);
        }
    }


    public void showConfirmationPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TestStart.this);
        LayoutInflater layoutInflater = LayoutInflater.from(TestStart.this);
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
        view.findViewById(R.id.alertYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();

                Call<StudentAnalysisModel> studentAnalysisModelCall = Api_Client.getInstance().getStudentAnalysis(test_series_id, mqid, user_id);
                studentAnalysisModelCall.enqueue(new Callback<StudentAnalysisModel>() {
                    @Override
                    public void onResponse(Call<StudentAnalysisModel> call, Response<StudentAnalysisModel> response) {

                        if (response.body().getStatus().equals(1)) {

                            StudentAnalysisModel studentAnalysisModel = response.body();

                            Toast.makeText(TestStart.this, "Submitting your Test", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(TestStart.this, StudentAnalysisActivity.class);
                            i.putExtra("Result", (Serializable) studentAnalysisModel);
                            startActivity(i);
                            finish();

                        }


                    }

                    @Override
                    public void onFailure(Call<StudentAnalysisModel> call, Throwable t) {
                    }
                });


            }
        });
        alert.show();
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

        //  long days = TimeUnit.MILLISECONDS.toDays(diff);
        // long remainingHoursInMillis = diff - TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(diff);
        long remainingMinutesInMillis = diff - TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(remainingMinutesInMillis);
        long remainingSecondsInMillis = remainingMinutesInMillis - TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(remainingSecondsInMillis);


        diffTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }


    public void ResultSendData() {

        Call<ResultSubmitModel> resultSubmitModelCall = Api_Client.getInstance().submitTestResult(sessionID, user_id, test_series_id, mqid, String.valueOf(currentQuestions.getId()), ChValueOne, ChValueTwo, ChValueThree, ChValueFour, totaltimetaken, diffTime);
        resultSubmitModelCall.enqueue(new Callback<ResultSubmitModel>() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onResponse(Call<ResultSubmitModel> call, Response<ResultSubmitModel> response) {

                quid++;
                if (quid < testPaperData.size()) {

                    time_taken = 0;
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
                    btnsave.setVisibility(View.GONE);
                    showConfirmationPopup();
                }
            }

            @Override
            public void onFailure(Call<ResultSubmitModel> call, Throwable t) {


            }
        });


    }


    @OnClick(R.id.ic_bookmmark)
    public void bookmark(View view) {
        Call<BookMarkModel> bookMarkModelCall = Api_Client.getInstance().insertBookMark(user_id, mqid,String.valueOf(currentQuestions.getId()));
        bookMarkModelCall.enqueue(new Callback<BookMarkModel>() {
            @Override
            public void onResponse(Call<BookMarkModel> call, Response<BookMarkModel> response) {
                if (response.body().getStatus().equals(1)) {

                    ic_bookmmark.setColorFilter(ic_bookmmark.getContext().getResources().getColor(R.color.tab_checked), PorterDuff.Mode.SRC_ATOP);
                    Toast.makeText(TestStart.this, "Bookmarked", Toast.LENGTH_SHORT).show();

                } else {

                    ic_bookmmark.setColorFilter(ic_bookmmark.getContext().getResources().getColor(R.color.tab_unchecked), PorterDuff.Mode.SRC_ATOP);

                    Toast.makeText(TestStart.this, "UnBookmarked", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<BookMarkModel> call, Throwable t) {

            }
        });

    }


}