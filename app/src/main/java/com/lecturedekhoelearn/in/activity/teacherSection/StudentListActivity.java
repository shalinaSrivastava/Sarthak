package com.lecturedekhoelearn.in.activity.teacherSection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.StudentListAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.TeacherModel.StudentListDetailsModel;
import com.lecturedekhoelearn.in.model.TeacherModel.StudentListModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentListActivity extends AppCompatActivity {


    RecyclerView rv_student_list;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String classId;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        preferences = getApplication().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("please wait...");
        classId = preferences.getString("class_id", "");


        rv_student_list = findViewById(R.id.rv_student_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
                toolbar.setNavigationIcon(d);
            }
        });


        getAllStudentList();



    }


    public void getAllStudentList() {
        showDialog();
        Call<StudentListModel> call = Api_Client.getInstance().getStudentList(classId);
        call.enqueue(new Callback<StudentListModel>() {
            @Override
            public void onResponse(Call<StudentListModel> call, Response<StudentListModel> response) {
                hideDialog();
                if (response.body().getStatus().equals(1)) {
                    StudentListModel studentListModel = response.body();
                    ArrayList<StudentListDetailsModel> studentListDetailsModels = (ArrayList<StudentListDetailsModel>) studentListModel.getStudentListDetailsModels();
                    if (studentListDetailsModels != null && !studentListDetailsModels.isEmpty() && studentListDetailsModels.size() > 0) {

                        StudentListAdapter allTestSeriesAdapter = new StudentListAdapter(StudentListActivity.this, studentListDetailsModels);
                        rv_student_list.setHasFixedSize(true);
                        rv_student_list.setLayoutManager(new LinearLayoutManager(StudentListActivity.this, RecyclerView.VERTICAL, false));
                        allTestSeriesAdapter.notifyDataSetChanged();
                        rv_student_list.setAdapter(allTestSeriesAdapter);



                    } else {

                        Toast.makeText(StudentListActivity.this, "No Buy Test series found ", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<StudentListModel> call, Throwable t) {
            hideDialog();
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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}