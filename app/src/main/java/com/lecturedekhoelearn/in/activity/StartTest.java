package com.lecturedekhoelearn.in.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.FirstTimeTestModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartTest extends AppCompatActivity {
    TextView txt_test_name, txt_test_noQ, txt_total_time, txt_total_marks;
    Button btn_start_test;
    String total_time, user_id, test_id, test_name, noQ, test_series_id;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView textInst1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        sharedPreferences = getApplication().getSharedPreferences(Constant.myprif, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
                toolbar.setNavigationIcon(d);
            }
        });

        test_name = sharedPreferences.getString("testName", "");
        noQ = sharedPreferences.getString("noQ", "");
        total_time = sharedPreferences.getString("testduration", "");
        test_id = sharedPreferences.getString("test_id", "");
        user_id = sharedPreferences.getString("studentId", "");
        test_series_id = sharedPreferences.getString("test_series_id", "");

        txt_test_name = findViewById(R.id.text_name);
        txt_test_noQ = findViewById(R.id.noq);
        txt_total_time = findViewById(R.id.duration);
        btn_start_test = findViewById(R.id.btnsrtets);
        txt_test_name.setText(test_name);
        txt_test_noQ.setText(noQ);
        txt_total_time.setText(total_time);
        textInst1 = findViewById(R.id.instruction);

        textInst1.setText(" 1. This test has" + " " + noQ + " " + "MCQs only");

        btn_start_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<FirstTimeTestModel> firstTimeTestModelCall = Api_Client.getInstance().sendStartTestAnalysis(test_series_id, test_id, user_id, noQ);
                firstTimeTestModelCall.enqueue(new Callback<FirstTimeTestModel>() {
                    @Override
                    public void onResponse(Call<FirstTimeTestModel> call, Response<FirstTimeTestModel> response) {

                        if (response.body().getStatus().equals(1)) {
                            FirstTimeTestModel firstTimeTestModel = response.body();
                            editor.putString("session_id", firstTimeTestModel.getSession_id()).apply();
                            Intent intent = new Intent(StartTest.this, TestStart.class);
                            intent.putExtra("test_id",test_series_id);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<FirstTimeTestModel> call, Throwable t) {

                    }
                });
            }
        });
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