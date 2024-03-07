package com.lecturedekhoelearn.in.activity;

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
import com.lecturedekhoelearn.in.adapter.TeacherListAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.TeacherListDetailsModel;
import com.lecturedekhoelearn.in.model.TeacherListModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTeachersActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String ClassId, studentId;
    RecyclerView rv_teacher_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_teachers);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        preferences = getApplication().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();

        rv_teacher_list = findViewById(R.id.rv_teacher_list);
        ClassId = preferences.getString("class_id", "");
        studentId = preferences.getString("studentId", "");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(new Runnable() {

            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
                toolbar.setNavigationIcon(d);
            }
        });


        getTeacherListdetails();

    }

    private void getTeacherListdetails() {
        Call<TeacherListModel> call = Api_Client.getInstance().getTeacherList("10");
        call.enqueue(new Callback<TeacherListModel>() {
            @Override
            public void onResponse(Call<TeacherListModel> call, Response<TeacherListModel> response) {
                if (response.body().getStatus().equals(1)) {
                    TeacherListModel motivationalVideos = response.body();
                    ArrayList<TeacherListDetailsModel> teacherListDetailsModels = (ArrayList<TeacherListDetailsModel>) motivationalVideos.getTeacherListDetailsModels();
                    if (teacherListDetailsModels.size() > 0) {
                        TeacherListAdapter teacherListAdapter = new TeacherListAdapter(MyTeachersActivity.this, teacherListDetailsModels);
                        rv_teacher_list.setAdapter(teacherListAdapter);
                        rv_teacher_list.setHasFixedSize(true);
                        rv_teacher_list.setLayoutManager(new LinearLayoutManager(MyTeachersActivity.this, LinearLayoutManager.VERTICAL, false));
                    }
                } else {
                    Toast.makeText(MyTeachersActivity.this, "Teachers not available in your class", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<TeacherListModel> call, Throwable t) {
                Toast.makeText(MyTeachersActivity.this, "network failure: Please check your Internet connection", Toast.LENGTH_SHORT).show();

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
