package com.lecturedekhoelearn.in.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.ChangedMobileNumber;
import com.rilixtech.CountryCodePicker;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import in.androidhunt.otp.AutoDetectOTP;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildOtpActivity extends AppCompatActivity {

    AutoDetectOTP autoDetectOTP;
    CountryCodePicker countryCodePicker;
    AppCompatEditText edtPhoneNumber;
    public static String str_phone;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String auto_hash="";
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_otp_child);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        preferences = getApplication().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        auto_hash=AutoDetectOTP.getHashCode(this);
        progressBar=findViewById(R.id.progress_bar);
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

                str_phone = Objects.requireNonNull(edtPhoneNumber.getText()).toString().trim();
                if (str_phone.length() != 10) {
                    edtPhoneNumber.setError("Please enter your phone number");
                } else {
                    progressBar.setVisibility(View.VISIBLE);
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
        Call<ChangedMobileNumber> topicsCall = Api_Client.getInstance().getchild(str_phone);
        topicsCall.enqueue(new Callback<ChangedMobileNumber>() {
            @Override
            public void onResponse(@NotNull Call<ChangedMobileNumber> call, @NotNull Response<ChangedMobileNumber> response) {
                    progressBar.setVisibility(View.GONE);
                try {
                    if (response.body().getStatus().equals(1)) {
                        editor.putString("otpNumber", response.body().getOtp()).apply();
                        Intent intent = new Intent(ChildOtpActivity.this, OtpVerifyforChild.class);
                        intent.putExtra("NO", countryCodePicker.getFullNumberWithPlus());
                        startActivity(intent);
                    } else {
                        Toast.makeText(ChildOtpActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(ChildOtpActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ChangedMobileNumber> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ChildOtpActivity.this, "network failure: Please check your Internet connection", Toast.LENGTH_SHORT).show();

            }
        });
    }


}

















/*extends AppCompatActivity {


    Button btn_get_otp;
    EditText phone_number_edt;
    public static String str_phone;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private ProgressDialog pDialog;
    String Mssagetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_otp);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        preferences = getApplication().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();

        phone_number_edt = findViewById(R.id.phone_number_edt);
        btn_get_otp = findViewById(R.id.btn_get_otp);


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        btn_get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_phone = phone_number_edt.getText().toString().trim();
                if (str_phone != null && str_phone.length() != 10) {
                    phone_number_edt.setError("Please enter your phone number");

                } else {

                    getOTP();

                }
            }
        });


    }


    public void getOTP() {

        pDialog.setMessage("Sending OTP...");
        showDialog();
        Call<ChangedMobileNumber> userCall = Api_Client.getInstance().getOtp(str_phone);
        userCall.enqueue(new Callback<ChangedMobileNumber>() {
            @Override
            public void onResponse(Call<ChangedMobileNumber> call, Response<ChangedMobileNumber> response) {
                if (response.body().getStatus().equals(1)) {
                    hideDialog();
                    editor.putString("otpNumber", response.body().getOtp()).apply();
                    Intent i = new Intent(SignupOtpActivity.this, OtpVerifyforSignup.class);
                    startActivity(i);
                    finish();

                } else {
                    hideDialog();
                    Toast.makeText(SignupOtpActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChangedMobileNumber> call, Throwable t) {
                hideDialog();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
*/