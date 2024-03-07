package com.lecturedekhoelearn.in.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.MyTestSeriesVideoAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.PackageVideoDetailsModel;
import com.lecturedekhoelearn.in.model.PackageVideoModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTestSeriesVideoActivity extends AppCompatActivity {

    RecyclerView rv_video;
    String packageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_test_series_video);

       getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        packageID = getIntent().getStringExtra("pid");
        rv_video = findViewById(R.id.rv_video);

        getTestSeriesVideos();

    }


    private void getTestSeriesVideos() {
        Call<PackageVideoModel> packageVideoModelCall = Api_Client.getInstance().getVideoInsidePackage(packageID);
        packageVideoModelCall.enqueue(new Callback<PackageVideoModel>() {
            @Override
            public void onResponse(Call<PackageVideoModel> call, Response<PackageVideoModel> response) {

                PackageVideoModel packageVideoModel = response.body();

                ArrayList<PackageVideoDetailsModel> packageVideoDetailsModels = (ArrayList<PackageVideoDetailsModel>) packageVideoModel.getPackageVideoDetailsModels();

                if (packageVideoDetailsModels.size() > 0) {

                    MyTestSeriesVideoAdapter analysisAdapter = new MyTestSeriesVideoAdapter(getApplication(), packageVideoDetailsModels);
                    rv_video.setAdapter(analysisAdapter);
                    rv_video.setHasFixedSize(true);
                    rv_video.setLayoutManager(new LinearLayoutManager(getApplication(), RecyclerView.VERTICAL, false));

                } else {

                    Toast.makeText(MyTestSeriesVideoActivity.this, "No videos found in Series", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<PackageVideoModel> call, Throwable t) {

            }
        });


    }
}