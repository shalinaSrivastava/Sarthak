package com.lecturedekhoelearn.in.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.ChangedMobileNumber;
import com.rilixtech.CountryCodePicker;

import in.androidhunt.otp.AutoDetectOTP;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {
    AutoDetectOTP autoDetectOTP;
    CountryCodePicker countryCodePicker;
    AppCompatEditText edtPhoneNumber;
    public static String str_phone;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String auto_hash="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        preferences = getApplication().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        auto_hash=AutoDetectOTP.getHashCode(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        countryCodePicker = findViewById(R.id.ccp);
        edtPhoneNumber = findViewById(R.id.phone_number_edt);
        countryCodePicker.registerPhoneNumberTextView(edtPhoneNumber);
        autoDetectOTP = new AutoDetectOTP(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_phone = edtPhoneNumber.getText().toString().trim();
                if (str_phone != null && str_phone.length() != 10) {
                    edtPhoneNumber.setError("Please enter your phone number");
                } else {
                    getMobileNumber();
                }

            }
        });
        autoDetectOTP.requestPhoneNoHint();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AutoDetectOTP.RC_HINT) {
            if (resultCode == RESULT_OK) {
                countryCodePicker.setFullNumber(autoDetectOTP.getPhoneNo(data));

                Snackbar.make(findViewById(R.id.root_view), autoDetectOTP.getPhoneNo(data), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {

            }
        }
    }

    public void getMobileNumber() {
        Call<ChangedMobileNumber> topicsCall = Api_Client.getInstance().getForgotOtp(str_phone,auto_hash);
        topicsCall.enqueue(new Callback<ChangedMobileNumber>() {

            @Override
            public void onResponse(Call<ChangedMobileNumber> call, Response<ChangedMobileNumber> response) {

                try {
                    if (response.body().getStatus().equals(1)) {

                        editor.putString("otpNumber", response.body().getOtp()).apply();

                        Intent intent = new Intent(ForgotPassword.this, ForgotOtpVerify.class);
                        intent.putExtra("NO", countryCodePicker.getFullNumberWithPlus());
                        startActivity(intent);

                    } else {

                        Toast.makeText(ForgotPassword.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(ForgotPassword.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChangedMobileNumber> call, Throwable t) {

                Toast.makeText(ForgotPassword.this, "network failure: Please check your Internet connection", Toast.LENGTH_SHORT).show();

            }
        });
    }


}


