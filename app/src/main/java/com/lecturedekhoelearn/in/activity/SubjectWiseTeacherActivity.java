package com.lecturedekhoelearn.in.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.SubjectWiseTeacherAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.SubjectTeacherDetailsModel;
import com.lecturedekhoelearn.in.model.SubjectTeacherModel;
import com.lecturedekhoelearn.in.model.TeacherListStudent;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubjectWiseTeacherActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    RecyclerView rv_subject_teacher;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String subjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_wise_teacher);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        rv_subject_teacher = findViewById(R.id.rv_subject_teacher);

        preferences = getApplicationContext().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();

        subjectId = preferences.getString("subject_Id", "");
        //Toast.makeText(this, subjectId, Toast.LENGTH_SHORT).show();


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
                toolbar.setNavigationIcon(d);
            }
        });

        getSubjectTeacher();
    }


    public void getSubjectTeacher() {
        pDialog.setTitle("Loading All Teacher...");
        showDialog();
        Call<SubjectTeacherModel> topicsCall = Api_Client.getInstance().getSubjectTeacherList(subjectId);
        topicsCall.enqueue(new Callback<SubjectTeacherModel>() {

            @Override
            public void onResponse(Call<SubjectTeacherModel> call, Response<SubjectTeacherModel> response) {

                if (response.body().getStatus().equals(1)) {
                    hideDialog();
                    SubjectTeacherModel  topics = response.body();
                    TeacherListStudent teacherListStudent=topics.getTeacherModel();
                    ArrayList<SubjectTeacherDetailsModel> topics_details = (ArrayList<SubjectTeacherDetailsModel>) teacherListStudent.getSubjectTeacherDetailsModels();

                    if (!topics_details.isEmpty() && topics_details.size() > 0) {
                        SubjectWiseTeacherAdapter topic_adapter = new SubjectWiseTeacherAdapter(getApplication(), topics_details);
                        rv_subject_teacher.setAdapter(topic_adapter);
                        rv_subject_teacher.setHasFixedSize(true);
                        rv_subject_teacher.setLayoutManager(new LinearLayoutManager(getApplication(), RecyclerView.VERTICAL, false));
                    } else {
                        Toast.makeText(SubjectWiseTeacherActivity.this, "Teachers Not Found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    hideDialog();
                    Toast.makeText(SubjectWiseTeacherActivity.this, "Teachers Not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SubjectTeacherModel> call, Throwable t) {
                hideDialog();
                Toast.makeText(SubjectWiseTeacherActivity.this, "network failure: Please check your Internet connection", Toast.LENGTH_SHORT).show();

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

