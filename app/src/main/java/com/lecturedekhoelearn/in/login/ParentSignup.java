package com.lecturedekhoelearn.in.login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
import androidx.appcompat.widget.AppCompatEditText;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.parentActivity.ParentDashboard;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.ChangedMobileNumber;
import com.lecturedekhoelearn.in.model.RegisterModelDetails;
import com.lecturedekhoelearn.in.model.parentModel.ParentRegModel;
import com.lecturedekhoelearn.in.model.parentModel.ParentRegModelDetails;
import com.rilixtech.CountryCodePicker;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ParentSignup extends AppCompatActivity {

    TextView already_account;
    EditText editName, editstuemail, editphone, editparent_email, editpass;
    Button button;
    String str_levelId, str_type_id, str_name, str_student_email, str_parent_email, str_password,str_phone;
    String Mssage;
    private ProgressDialog pDialog;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_signup);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        preferences = getApplicationContext().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();


        str_levelId = preferences.getString("level_id", "");
        str_type_id = preferences.getString("category_id", "");
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        already_account = findViewById(R.id.already_account);
        editName = (EditText) findViewById(R.id.editName);
        editstuemail = (EditText) findViewById(R.id.editstuemail);
        editphone = (EditText) findViewById(R.id.editphone);
        editparent_email = (EditText) findViewById(R.id.editparent_email);
        editpass = (EditText) findViewById(R.id.editpass);

        button = (Button) findViewById(R.id.btn_register);


        already_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ParentSignup.this, LoginActivity.class);
                startActivity(i);
            }
        });

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


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_student_email = getIntent().getStringExtra("mobile_no");
                str_student_email=str_student_email.substring(3);
                str_parent_email = editparent_email.getText().toString().trim();
                str_password = editpass.getText().toString().trim();
                str_name = editName.getText().toString().trim();
                str_phone = editphone.getText().toString().trim();
                if (isEmail(editparent_email) == false) {
                    editstuemail.setError("Please enter your email");

                } else if (str_password.length() <= 6 && str_password != null) {
                    editpass.setError("Password must be 7 digit");

                } else if (str_name.trim().equals("")) {
                    editName.setError("Please enter your name");

                }
                else if (str_phone != null && str_phone.length() != 10) {
                    editphone.setError("Please enter your phone number");

                }
                else {
                    getParentRegister();
                }
            }


        });

    }

    public void getParentRegister() {
        pDialog.setMessage("Registering...");
        showDialog();
        Call<ParentRegModel> userCall = Api_Client.getInstance().getParentRegister(str_levelId, str_type_id, str_student_email,str_name, str_parent_email,str_phone, str_password);
        userCall.enqueue(new Callback<ParentRegModel>() {
            @Override
            public void onResponse(Call<ParentRegModel> call, Response<ParentRegModel> response) {

                if (response.body().getStatus().equals(0)) {
                    hideDialog();
                    Mssage = response.body().getMsg();
                    showRegisterUnsucess();
                } else if (response.body().getStatus().equals(1)) {
                    hideDialog();
                    Mssage = response.body().getMsg();
                    showRegisterUnsucess();
                } else if (response.body().getStatus().equals(2)) {
                    hideDialog();
                    Mssage = response.body().getMsg();
                    userId = String.valueOf(response.body().getId());
                    ParentRegModelDetails parentRegModelDetails = response.body().getParentRegModelDetails();
                    editor.putString("studentId", parentRegModelDetails.getStudent_id()).apply();
                    editor.putString("parentId", userId).apply();
                    editor.putString("class_id", parentRegModelDetails.getClass_id()).apply();
                    editor.putString("name", parentRegModelDetails.getName()).apply();
                    editor.putString("email", parentRegModelDetails.getEmail()).apply();
                    editor.putString("mobile", parentRegModelDetails.getMobile()).apply();
                    editor.putString("loginType", "Parent").apply();
                    showRegister();

                } else {
                    hideDialog();
                    Mssage = response.body().getMsg();
                    showRegisterUnsucess();

                }

            }

            @Override
            public void onFailure(Call<ParentRegModel> call, Throwable t) {
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

    boolean isEmail(EditText editText) {
        CharSequence email = editText.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }


    private void showRegister() {
        final android.app.AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new android.app.AlertDialog.Builder(ParentSignup.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new android.app.AlertDialog.Builder(ParentSignup.this);
        }
        builder.setTitle("Register Successful")
                .setMessage(Mssage)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent logoutIntent = new Intent(ParentSignup.this, ParentDashboard.class);
                        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(logoutIntent);
                        overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();

                    }
                })
                .setIcon(R.drawable.ic_done_all_black_24dp)
                .show();
    }


    private void showRegisterUnsucess() {
        final android.app.AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new android.app.AlertDialog.Builder(ParentSignup.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new android.app.AlertDialog.Builder(ParentSignup.this);
        }
        builder.setTitle("Register Unsuccessful ")
                .setMessage(Mssage)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


}