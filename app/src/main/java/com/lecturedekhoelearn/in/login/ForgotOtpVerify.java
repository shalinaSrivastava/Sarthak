package com.lecturedekhoelearn.in.login;

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

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import in.androidhunt.otp.AutoDetectOTP;

public class ForgotOtpVerify extends AppCompatActivity implements
        SMSReceiver.OTPReceiveListener{
    private String otpnN0="";
    TextView timer;
    AutoDetectOTP autoDetectOTP;
    Pinview pinview1;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String preOtp="";
    public static String no;
    private SMSReceiver smsReceiver;
    CountDownTimer countDownTimer = new CountDownTimer(250000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timer.setText(millisecondsToTime(millisUntilFinished));
        }

        @Override
        public void onFinish() {
            timer.setText("");
        }
    };
    Handler handler = new Handler();
    Runnable runnable = () -> {
        if (otpnN0 != null && otpnN0.equals("1234")) {
            ///  otpTextView.showSuccess();
            countDownTimer.cancel();
        } else {
            // otpTextView.showError();
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_otp_verify);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        autoDetectOTP = new AutoDetectOTP(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView phoneview = findViewById(R.id.phone_);
        timer = findViewById(R.id.timer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(null);

        preferences = getApplicationContext().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        preOtp = preferences.getString("otpNumber", "");
       // System.out.println("amclass:" + preOtp);

        AppBarLayout app = findViewById(R.id.appbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            app.setOutlineProvider(null);
        }
        findViewById(R.id.fab_previos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        no = getIntent().getStringExtra("NO");
        if (no != null) {
            phoneview.append(no);
        }


        pinview1 = findViewById(R.id.otp_view);

        pinview1.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {

                otpnN0 = pinview.getValue();
                handler.postDelayed(runnable, 100);
            }
        });

        countDownTimer.start();
        startSMSListener();
        autoDetectOTP.startSmsRetriver(new AutoDetectOTP.SmsCallback() {
            @Override
            public void connectionfailed() {
               // Toast.makeText(ForgotOtpVerify.this, "Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void connectionSuccess(Void aVoid) {

               // Toast.makeText(ForgotOtpVerify.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void smsCallback(String sms) {

                /*if (sms.contains(":") && sms.contains(".")) {
                    String otp = sms.substring(sms.indexOf(":") + 1, sms.indexOf(".")).trim();
                    Toast.makeText(ForgotOtpVerify.this, "The OTP is " + otp, Toast.LENGTH_SHORT).show();
                }*/


            }


        });


        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (otpnN0.equals(preOtp)) {
                    Toast.makeText(ForgotOtpVerify.this, "OTP is Correct", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ForgotOtpVerify.this, ChangePasswordActivity.class);
                    startActivity(i);
                    finish();

                } else {

                    Toast.makeText(ForgotOtpVerify.this, "OTP is Incorrect", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void startSMSListener() {
        try {
            smsReceiver = new SMSReceiver();
            smsReceiver.setOTPListener(ForgotOtpVerify.this);

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
            this.registerReceiver(smsReceiver, intentFilter);

            SmsRetrieverClient client = SmsRetriever.getClient(this);

            Task<Void> task = client.startSmsRetriever();
            task.addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                   // showToast("start service");
                }
            });

            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                  //  showToast(" service not started");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onOTPReceived(String otp) {
       /* //String [] value=otp.split(" ");
        String value=otp.substring(32,35);
        pinview1.setValue(otp.substring(32,35));
        //pinview1.setValue(value);*/
        showToast(otp.substring(32,37).trim());
        if (smsReceiver != null) {
            unregisterReceiver(smsReceiver);
            smsReceiver = null;
        }
    }

    @Override
    public void onOTPTimeOut() {
        showToast("OTP Time out");
    }

    @Override
    public void onOTPReceivedError(String error) {
        showToast(error);
    }




    private void showToast(String msg) {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (msg.equals(preOtp)) {
                    Intent i = new Intent(ForgotOtpVerify.this, ChangePasswordActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 2000);
        pinview1.setValue(msg);
       // Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onDestroy() {

        if (countDownTimer != null) {
            countDownTimer.onFinish();
            countDownTimer.cancel();
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        autoDetectOTP.stopSmsReciever();
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