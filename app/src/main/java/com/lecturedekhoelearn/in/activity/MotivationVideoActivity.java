package com.lecturedekhoelearn.in.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.MotivationalVideoAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.MotivationalVideos;
import com.lecturedekhoelearn.in.model.MotivationalVideosDetails;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MotivationVideoActivity extends AppCompatActivity {


    RecyclerView rv_motivational;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivation_video);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        rv_motivational = findViewById(R.id.rv_motivational);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
                toolbar.setNavigationIcon(d);
            }
        });

        getMotivationalVideos();
    }


    public void getMotivationalVideos() {
        Call<MotivationalVideos> call = Api_Client.getInstance().getMotivationalVideo();
        call.enqueue(new Callback<MotivationalVideos>() {
            @Override
            public void onResponse(Call<MotivationalVideos> call, Response<MotivationalVideos> response) {
                if (response.body().getStatus().equals(1)) {
                    MotivationalVideos motivationalVideos = response.body();
                    ArrayList<MotivationalVideosDetails> subVideos = (ArrayList<MotivationalVideosDetails>) motivationalVideos.getMotivationalVideosDetails();
                    if (!subVideos.isEmpty() && subVideos.size() > 0) {
                        MotivationalVideoAdapter adapter = new MotivationalVideoAdapter(MotivationVideoActivity.this, subVideos);
                        rv_motivational.setAdapter(adapter);
                        rv_motivational.setHasFixedSize(true);
                        rv_motivational.setLayoutManager(new LinearLayoutManager(MotivationVideoActivity.this, LinearLayoutManager.VERTICAL, false));

                    } else {

                        Toast.makeText(MotivationVideoActivity.this, "No videos found", Toast.LENGTH_SHORT).show();

                    }

                } else {

                    Toast.makeText(MotivationVideoActivity.this, "No videos found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<MotivationalVideos> call, Throwable t) {
                Toast.makeText(MotivationVideoActivity.this, "network failure: Please check your Internet connection", Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                //overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}