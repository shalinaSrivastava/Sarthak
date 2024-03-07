package com.lecturedekhoelearn.in.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.ChangedMobileNumber;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lecturedekhoelearn.in.login.ForgotPassword.str_phone;

public class ChangePasswordActivity extends AppCompatActivity {
    Button btn_change_password;
    EditText et_pass, et_phone;
    String str_password;
    private ProgressDialog pDialog;
    String Mssagetext;

    String deviceid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        btn_change_password = findViewById(R.id.btn_change_password);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        et_pass = (EditText) findViewById(R.id.editpass);
        et_phone = (EditText) findViewById(R.id.phone_number_edt);
        et_phone.setText(str_phone);
        et_phone.setEnabled(false);

        deviceid = Settings.Secure.getString(getApplication().getContentResolver(), Settings.Secure.ANDROID_ID);

        btn_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_password = et_pass.getText().toString().trim();
                if (str_password.length() <= 6 && str_password != null) {
                    et_pass.setError("Password must be 7 digit");

                } else {
                    updatePassword();
                }
            }
        });

        et_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_pass.getTransformationMethod().getClass().getSimpleName().equals("PasswordTransformationMethod")) {
                    et_pass.setTransformationMethod(new SingleLineTransformationMethod());
                } else {
                    et_pass.setTransformationMethod(new PasswordTransformationMethod());
                }

                et_pass.setSelection(et_pass.getText().length());
            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
                toolbar.setNavigationIcon(d);
            }
        });


    }

    public void updatePassword() {
        pDialog.setMessage("Updating your Password...");
        showDialog();
        Call<ChangedMobileNumber> userCall = Api_Client.getInstance().updateMobileNumber(str_phone, str_password,deviceid);
        userCall.enqueue(new Callback<ChangedMobileNumber>() {
            @Override
            public void onResponse(Call<ChangedMobileNumber> call, Response<ChangedMobileNumber> response) {
                if (response.body().getStatus().equals(1)) {
                    hideDialog();
                    Mssagetext = response.body().getMsg();
                    Toast.makeText(ChangePasswordActivity.this, Mssagetext, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();

                } else {
                    hideDialog();
                    Mssagetext = response.body().getMsg();
                    Toast.makeText(ChangePasswordActivity.this, Mssagetext, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ChangePasswordActivity.this, ForgotPassword.class);
                    startActivity(i);
                    finish();
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