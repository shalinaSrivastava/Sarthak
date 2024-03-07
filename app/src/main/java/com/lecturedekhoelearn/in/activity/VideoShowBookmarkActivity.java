package com.lecturedekhoelearn.in.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.ShowVideoBookmarkAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.VideoDataBookModel;
import com.lecturedekhoelearn.in.model.VideoDeailsBookModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoShowBookmarkActivity extends AppCompatActivity {

    RecyclerView rv_videos_lect;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private ProgressDialog pDialog;
    String StudentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_show_bookmark);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

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


        rv_videos_lect = findViewById(R.id.rv_videos_lect);

        preferences = getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();

        StudentId = preferences.getString("studentId", "");

        getVideoLect();


    }


    public void getVideoLect() {
        pDialog.setTitle("Loading Video Lecture...");
        showDialog();

        Call<VideoDeailsBookModel> videoLectModelCall = Api_Client.getInstance().getBookmarkVideoList(StudentId);
        videoLectModelCall.enqueue(new Callback<VideoDeailsBookModel>() {
            @Override
            public void onResponse(Call<VideoDeailsBookModel> call, Response<VideoDeailsBookModel> response) {
                if (response.body().getStatus().equals(1)) {
                    hideDialog();
                    VideoDeailsBookModel video1 = response.body();
                    ArrayList<VideoDataBookModel> videoDataBookModels = (ArrayList<VideoDataBookModel>) video1.getVideoDataBookModels();
                    if (videoDataBookModels.isEmpty()) {
                        Toast.makeText(VideoShowBookmarkActivity.this, "You have not purchased any videos", Toast.LENGTH_SHORT).show();
                    } else {
                        ShowVideoBookmarkAdapter adapter = new ShowVideoBookmarkAdapter(getApplication(), videoDataBookModels);
                        rv_videos_lect.setAdapter(adapter);
                        rv_videos_lect.setHasFixedSize(true);
                        rv_videos_lect.setLayoutManager(new LinearLayoutManager(getApplication(), RecyclerView.VERTICAL, false));

                    }
                } else {
                    hideDialog();
                    Toast.makeText(VideoShowBookmarkActivity.this, "You have not purchased any videos", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<VideoDeailsBookModel> call, Throwable t) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                return true;

            case R.id.action_test_bookmark:
                Intent i = new Intent(VideoShowBookmarkActivity.this, MyBookmarkActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                return true;

            default:
                return super.onContextItemSelected(item);
        }

    }

}