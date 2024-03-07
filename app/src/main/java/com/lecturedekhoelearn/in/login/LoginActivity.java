package com.lecturedekhoelearn.in.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.MainActivity;
import com.lecturedekhoelearn.in.activity.teacherSection.TeacherLoginActivity;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.LoginUserModel;
import com.lecturedekhoelearn.in.model.LoginUserModelDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    TextView tv_forgot, tv_new_account;
    EditText editemail, editpass;
    Button LogInBtn;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private ProgressDialog pDialog;

    RadioGroup rbGroup;
    RadioButton rad_student, rad_parent;

    String deviceid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        preferences = getApplication().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();

        rad_student = findViewById(R.id.rad_student);
        rbGroup = findViewById(R.id.rbGroup);
        rad_parent = findViewById(R.id.rad_parent);

        tv_forgot = findViewById(R.id.tv_forgot);
        editpass = findViewById(R.id.editpass);
        editemail = findViewById(R.id.editemail);
        tv_new_account = findViewById(R.id.tv_new_account);
        LogInBtn = findViewById(R.id.LogInBtn);

        deviceid = Settings.Secure.getString(getApplication().getContentResolver(), Settings.Secure.ANDROID_ID);
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


                Intent i = new Intent(LoginActivity.this, ForgotPassword.class);
                //   Intent i = new Intent(LoginActivity.this, ForgotOtpVerify.class);
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

                Intent i = new Intent(LoginActivity.this, CategoryActivity.class);
                startActivity(i);
            }
        });


        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rad_student.isChecked()) {
                    GradientDrawable backgroundGradient = (GradientDrawable) rad_student.getBackground();
                    backgroundGradient.setColor(getResources().getColor(R.color.colorPrimary));
                    rad_student.setTextColor(Color.WHITE);
                    Intent i = new Intent(LoginActivity.this, TeacherLoginActivity.class);
                    startActivity(i);


                } else {
                    GradientDrawable backgroundGradient = (GradientDrawable) rad_student.getBackground();
                    backgroundGradient.setColor(getResources().getColor(R.color.colorWhite));
                    rad_student.setTextColor(Color.BLACK);
                }

                if (rad_parent.isChecked()) {
                    GradientDrawable backgroundGradient = (GradientDrawable) rad_parent.getBackground();
                    backgroundGradient.setColor(getResources().getColor(R.color.colorPrimary));
                    rad_parent.setTextColor(Color.WHITE);
                    Intent i = new Intent(LoginActivity.this, LoginAsParent.class);
                    startActivity(i);


                } else {

                    GradientDrawable backgroundGradient = (GradientDrawable) rad_parent.getBackground();
                    backgroundGradient.setColor(getResources().getColor(R.color.colorWhite));
                    rad_parent.setTextColor(Color.BLACK);
                }
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
        Call<LoginUserModel> loginUserModelCall = Api_Client.getInstance().getLoginUser(editemail.getText().toString().trim(), editpass.getText().toString().trim(), deviceid);
        loginUserModelCall.enqueue(new Callback<LoginUserModel>() {
            //a74564f9d7c595f8
            @Override
            public void onResponse(Call<LoginUserModel> call, Response<LoginUserModel> response) {
                if (response.body().getStatus().equals(1)) {
                    hideDialog();
                    LoginUserModel loginUserModel = response.body();
                    LoginUserModelDetails loginUserModelDetails = loginUserModel.getLoginUserModelDetails();

                    editor.putString("studentId", loginUserModelDetails.getId()).apply();
                    editor.putString("name", loginUserModelDetails.getName()).apply();
                    editor.putString("email", loginUserModelDetails.getEmail()).apply();
                    editor.putString("class_id", loginUserModelDetails.getClass_id()).apply();
                    editor.putString("refer", loginUserModelDetails.getRefer_code()).apply();
                    editor.putString("mobile", loginUserModelDetails.getMobile()).apply();
                    editor.putString("loginType", "Student").apply();

                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    hideDialog();
                    Toast.makeText(LoginActivity.this, response.body().getMsg().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginUserModel> call, Throwable t) {
                hideDialog();

            }
        });

    }


    public void checkDataEntered() {
        if (isEmail(editemail) == false) {
            editemail.setError("Please enter valid email!");
        } else if (isEmpty(editpass)) {
            Toast.makeText(LoginActivity.this, "Please enter your password!", Toast.LENGTH_SHORT).show();

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









/*extends AppCompatActivity {


    Button btn_new_user;
    Button btn_sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_new_user = findViewById(R.id.btn_new_user);
        btn_sign_in = findViewById(R.id.btn_sign_in);


        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

        btn_new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }
}
*/