package com.lecturedekhoelearn.in.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.IntroScreen;
import com.lecturedekhoelearn.in.activity.MainActivity;
import com.lecturedekhoelearn.in.activity.parentActivity.ParentDashboard;
import com.lecturedekhoelearn.in.activity.teacherSection.TeacherDashboard;

public class SplashScreenActivity extends AppCompatActivity {


    private static int SPLASH_TIME_OUT = 1500;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String strEmail, LoginType;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        preferences = getApplication().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        progressBar = findViewById(R.id.progressBar);

        strEmail = preferences.getString("email", "");
        LoginType = preferences.getString("loginType", "");
        trasparentNoti();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                progressBar.setVisibility(View.VISIBLE);

                if (LoginType.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    Intent i = new Intent(SplashScreenActivity.this, IntroScreen.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

                } else if (LoginType.equals("Student")) {
                    progressBar.setVisibility(View.GONE);
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

                }

                else if (LoginType.equals("Teacher")) {
                    progressBar.setVisibility(View.GONE);
                    Intent i = new Intent(SplashScreenActivity.this, TeacherDashboard.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

                }


                else if (LoginType.equals("Parent")) {
                    progressBar.setVisibility(View.GONE);
                    Intent i = new Intent(SplashScreenActivity.this, ParentDashboard.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

                }

                finish();
            }
        }, SPLASH_TIME_OUT);

    }

    public void trasparentNoti() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}