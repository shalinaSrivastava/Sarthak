package com.lecturedekhoelearn.in.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.MainActivity;
import com.lecturedekhoelearn.in.activity.parentActivity.ParentDashboard;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.parentModel.ParentLoginDetails;
import com.lecturedekhoelearn.in.model.parentModel.ParentLoginModel;
import com.lecturedekhoelearn.in.util.AppPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginAsParent extends AppCompatActivity {

    TextView tv_forgot, tv_new_account;
    EditText editemail, editpass;
    Button LogInBtn;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_as_parent);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        preferences = getApplicationContext().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();


        tv_forgot = findViewById(R.id.tv_forgot);
        editpass = findViewById(R.id.editpass);
        editemail = findViewById(R.id.editemail);
        tv_new_account = findViewById(R.id.tv_new_account);
        LogInBtn = findViewById(R.id.LogInBtn);


        editpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editpass.getTransformationMethod().getClass().getSimpleName().equals("PasswordTransformationMethod")) {
                    editpass.setTransformationMethod(new SingleLineTransformationMethod());
                } else {
                    editpass.setTransformationMethod(new PasswordTransformationMethod());
                }

                editpass.setSelection(editpass.getText().length());
            }
        });

        tv_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(LoginAsParent.this, ForgotPassword.class);
                startActivity(i);
            }
        });


        LogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkDataEntered();
            }
        });


        tv_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginAsParent.this, LoginActivity.class);
                startActivity(i);

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

    public void getUserLogin() {
        pDialog.setMessage("login...");
        showDialog();
        Call<ParentLoginModel> loginUserModelCall = Api_Client.getInstance().getParentUser(editemail.getText().toString().trim(), editpass.getText().toString().trim());
        loginUserModelCall.enqueue(new Callback<ParentLoginModel>() {
            @Override
            public void onResponse(Call<ParentLoginModel> call, Response<ParentLoginModel> response) {
                if (response.body().getStatus().equals("1")) {
                    hideDialog();
                    ParentLoginModel parentLoginModel = response.body();
                    ParentLoginDetails parentLoginDetails = parentLoginModel.getParentLoginDetails();
                    editor.putString("studentId", parentLoginDetails.getStudent_id()).apply();
                    editor.putString("parentId", parentLoginDetails.getId()).apply();
                    editor.putString("name", parentLoginDetails.getName()).apply();
                    editor.putString("email", parentLoginDetails.getEmail()).apply();
                    editor.putString("class_id", parentLoginDetails.getClass_id()).apply();
                    editor.putString("mobile", parentLoginDetails.getMobile()).apply();
                    editor.putString("loginType", "Parent").apply();
                    AppPreferences.setUserid(getApplicationContext(),editpass.getText().toString());
                    AppPreferences.setP_email(getApplicationContext(),editemail.getText().toString());
                    Intent i = new Intent(LoginAsParent.this, ParentDashboard.class);
                    startActivity(i);
                    finish();
                } else {
                    hideDialog();
                    Toast.makeText(LoginAsParent.this, response.body().getMsg().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ParentLoginModel> call, Throwable t) {
                hideDialog();

            }
        });

    }


    public void checkDataEntered() {
        if (isEmail(editemail) == false) {
            editemail.setError("Please enter valid email!");
        } else if (isEmpty(editpass)) {
            Toast.makeText(LoginAsParent.this, "Please enter your password!", Toast.LENGTH_SHORT).show();

        } else {
            getUserLogin();
        }

    }


    boolean isEmail(EditText editText) {
        CharSequence email = editText.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText editText) {
        CharSequence str = editText.getText().toString().trim();

        return TextUtils.isEmpty(str);
    }


}