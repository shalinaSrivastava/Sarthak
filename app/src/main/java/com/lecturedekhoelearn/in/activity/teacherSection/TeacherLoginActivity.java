package com.lecturedekhoelearn.in.activity.teacherSection;

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
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.login.LoginActivity;
import com.lecturedekhoelearn.in.model.TeacherModel.TeacherLoginModel;
import com.lecturedekhoelearn.in.model.TeacherModel.TeacherLoginModelDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherLoginActivity extends AppCompatActivity {

    TextView tv_forgot, tv_new_account;
    EditText editemail, editpass;
    Button LogInBtn;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
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

        LogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkDataEntered();
            }
        });

        tv_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TeacherLoginActivity.this, LoginActivity.class);
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
        Call<TeacherLoginModel> loginUserModelCall = Api_Client.getInstance().getTeacherUser(editemail.getText().toString().trim(), editpass.getText().toString().trim());
        loginUserModelCall.enqueue(new Callback<TeacherLoginModel>() {
            @Override
            public void onResponse(Call<TeacherLoginModel> call, Response<TeacherLoginModel> response) {
                if (response.body().getStatus().equals(1)) {
                    hideDialog();
                    TeacherLoginModel teacherLoginModel = response.body();
                    TeacherLoginModelDetails teacherLoginModelDetails = teacherLoginModel.getTeacherLoginModelDetails();

                    editor.putString("studentId", teacherLoginModelDetails.getId()).apply();
                    editor.putString("name", teacherLoginModelDetails.getName()).apply();
                    editor.putString("email", teacherLoginModelDetails.getEmail()).apply();
                    editor.putString("class_id", teacherLoginModelDetails.getClass_id()).apply();
                    editor.putString("profilePic",teacherLoginModelDetails.getProfile_pic());
                    editor.putString("loginType","Teacher").apply();

                    Intent i = new Intent(TeacherLoginActivity.this, TeacherDashboard.class);
                    startActivity(i);
                    finish();
                } else {
                    hideDialog();
                    Toast.makeText(TeacherLoginActivity.this, response.body().getMsg().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TeacherLoginModel> call, Throwable t) {
                hideDialog();

            }
        });

    }

    public void checkDataEntered() {
        if (isEmail(editemail) == false) {
            editemail.setError("Please enter valid email!");
        } else if (isEmpty(editpass)) {
            Toast.makeText(TeacherLoginActivity.this, "Please enter your password!", Toast.LENGTH_SHORT).show();

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
