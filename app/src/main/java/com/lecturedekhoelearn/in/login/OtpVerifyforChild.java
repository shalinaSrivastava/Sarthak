package com.lecturedekhoelearn.in.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.goodiebag.pinview.Pinview;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.SMSReceiver;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import in.androidhunt.otp.AutoDetectOTP;

public class OtpVerifyforChild extends AppCompatActivity {

    private String otpnN0="";
    TextView timer;
    Pinview pinview1;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String preOtp;
    public static String no;
    CountDownTimer countDownTimer;
    Handler handler;
    public static final String TAG = OtpVerifyforChild.class.getSimpleName();

    private SMSReceiver smsReceiver;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verifyfor_signup);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        countDownTimer = new CountDownTimer(250000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(millisecondsToTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                timer.setText("");
            }
        };
         handler = new Handler();
        Runnable runnable = () -> {
            if (otpnN0 != null && otpnN0.equals("1234")) {
                // otpTextView.showSuccess();
                countDownTimer.cancel();
            }
        };

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView phoneview = findViewById(R.id.phone_);
        timer = findViewById(R.id.timer);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(null);

        preferences = getApplicationContext().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        preOtp = preferences.getString("otpNumber", "");
        AppBarLayout app = findViewById(R.id.appbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            app.setOutlineProvider(null);
        }
        findViewById(R.id.fab_previos).setOnClickListener(v -> onBackPressed());
        no = getIntent().getStringExtra("NO");
        if (no != null) {
            phoneview.append(no);
        }

        pinview1 = findViewById(R.id.otp_view);
        pinview1.setPinViewEventListener((pinview, fromUser) -> {
            otpnN0 = pinview.getValue();
            handler.postDelayed(runnable, 100);
        });

        countDownTimer.start();

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (otpnN0.equals(preOtp)) {
                    Toast.makeText(OtpVerifyforChild.this, "OTP is Correct", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(OtpVerifyforChild.this, ParentSignup.class);
                    i.putExtra("mobile_no",no);
                    startActivity(i);
                    finish();

                } else {
                    Toast.makeText(OtpVerifyforChild.this, "OTP is Incorrect", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    @Override
    protected void onDestroy() {
        if (smsReceiver != null) {
            unregisterReceiver(smsReceiver);
        }
        if (countDownTimer != null) {
            countDownTimer.onFinish();
            countDownTimer.cancel();
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (countDownTimer != null) {
            countDownTimer.onFinish();
            countDownTimer.cancel();
        }
    }

    private String millisecondsToTime(long milliseconds) {

        return "Time remaining " + String.format("%d : %d ",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
    }
}