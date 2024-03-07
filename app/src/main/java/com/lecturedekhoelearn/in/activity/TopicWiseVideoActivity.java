package com.lecturedekhoelearn.in.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.PremiumVideoAdapter;
import com.lecturedekhoelearn.in.adapter.TopicWiseVideosADapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.PreemiemVideosDetails;
import com.lecturedekhoelearn.in.model.TopicWiseVideoDetailsModel;
import com.lecturedekhoelearn.in.model.TopicWiseVideoModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicWiseVideoActivity extends AppCompatActivity {


    RecyclerView rv_video,rv_video_premiem;
    String TopicId, StudentId, ClassId, SubjectId;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private ProgressDialog pDialog;
    TextView tv_paid, tv_free;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_wise_video);

       getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        preferences = getApplicationContext().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        StudentId = preferences.getString("studentId", "");
        ClassId = preferences.getString("class_id", "");
        SubjectId = preferences.getString("subject_Id", "");
        TopicId = getIntent().getStringExtra("topic_id");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
                toolbar.setNavigationIcon(d);
            }
        });


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        rv_video = findViewById(R.id.rv_video);
       // tv_free = findViewById(R.id.tv_free);
      //  tv_paid = findViewById(R.id.tv_paid);
        rv_video_premiem = findViewById(R.id.rv_video_premiem);

        getTestSeriesVideos();

    }

    public void getTestSeriesVideos() {

        pDialog.setTitle("Loading All Videos...");
        showDialog();
        Call<TopicWiseVideoModel> packageVideoModelCall = Api_Client.getInstance().getTopicWiseVideos(ClassId, TopicId, SubjectId,StudentId);
        packageVideoModelCall.enqueue(new Callback<TopicWiseVideoModel>() {
            @Override
            public void onResponse(Call<TopicWiseVideoModel> call, Response<TopicWiseVideoModel> response) {

                if (response.body().getStatus().equals(1)) {
                    hideDialog();
                    TopicWiseVideoModel topicWiseVideoModel = response.body();
                    ArrayList<TopicWiseVideoDetailsModel> topicWiseVideoDetailsModels = (ArrayList<TopicWiseVideoDetailsModel>) topicWiseVideoModel.getTopicWiseVideoDetailsModels();
                    ArrayList<PreemiemVideosDetails> preemiemVideosDetails = (ArrayList<PreemiemVideosDetails>) topicWiseVideoModel.getPreemiemVideosDetails();

                    if (!topicWiseVideoDetailsModels.isEmpty() && topicWiseVideoDetailsModels.size() > 0) {
                      //  tv_free.setVisibility(View.VISIBLE);
                        TopicWiseVideosADapter analysisAdapter = new TopicWiseVideosADapter(getApplication(), topicWiseVideoDetailsModels);
                        rv_video.setAdapter(analysisAdapter);
                        rv_video.setHasFixedSize(true);
                        rv_video.setLayoutManager(new LinearLayoutManager(getApplication(), RecyclerView.VERTICAL, false));

                    } else {
                      //  tv_free.setVisibility(View.GONE);
                        Toast.makeText(TopicWiseVideoActivity.this, "No videos found in Topics", Toast.LENGTH_SHORT).show();
                    }
                   /* if (!preemiemVideosDetails.isEmpty() && preemiemVideosDetails.size() > 0) {
                     //   tv_paid.setVisibility(View.VISIBLE);
                        PremiumVideoAdapter analysisAdapter = new PremiumVideoAdapter(getApplication(), preemiemVideosDetails);
                        rv_video_premiem.setAdapter(analysisAdapter);
                        rv_video_premiem.setHasFixedSize(true);
                        rv_video_premiem.setLayoutManager(new LinearLayoutManager(getApplication(), RecyclerView.VERTICAL, false));

                    } else {

                     //   tv_paid.setVisibility(View.GONE);
                       // Toast.makeText(TopicWiseVideoActivity.this, "No videos found in Topics", Toast.LENGTH_SHORT).show();
                    }*/
                }

                else {
                    hideDialog();
                //    Toast.makeText(TopicWiseVideoActivity.this, "No videos found in Topics", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<TopicWiseVideoModel> call, Throwable t) {

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