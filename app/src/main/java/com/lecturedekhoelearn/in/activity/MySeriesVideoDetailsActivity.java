package com.lecturedekhoelearn.in.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telecom.VideoProfile;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.parentActivity.Video_player;
import com.lecturedekhoelearn.in.adapter.TestSeriesInsideVideoAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.TestInsideVideoDetailsModel;
import com.lecturedekhoelearn.in.model.TestInsideVideoModel;
import com.lecturedekhoelearn.in.model.VideoBookmarkModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.biubiubiu.justifytext.library.JustifyTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lecturedekhoelearn.in.Constant.packageVideothumbnail;

public class MySeriesVideoDetailsActivity extends AppCompatActivity {

    TextView tv_pac_name, tv_test_count;
    String packagename, pacDescription, pacimage, testCount;
    ImageView imagepackage;
    JustifyTextView tv_description;
    String StudentId;
    String VideoID,VideoIDKEY;
    ImageView ic_bookmmark;
    RecyclerView rv_test;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_series_video_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        preferences = getApplicationContext().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        StudentId = preferences.getString("studentId", "");
        Intent intent = getIntent();
        packagename = intent.getStringExtra("title");
        pacDescription = intent.getStringExtra("description");
        pacimage = intent.getStringExtra("thumbnail");
        testCount = intent.getStringExtra("testCount");
        VideoID = intent.getStringExtra("videoID");
        VideoIDKEY = intent.getStringExtra("video_id");
        editor.putString("topic_name", packagename).apply();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
                toolbar.setNavigationIcon(d);
            }
        });


        tv_description = findViewById(R.id.tv_description);
        tv_test_count = findViewById(R.id.tv_test_count);
        tv_pac_name = findViewById(R.id.tv_pac_name);
        imagepackage = findViewById(R.id.image);
        rv_test = findViewById(R.id.rv_test);
        ic_bookmmark = findViewById(R.id.ic_bookmmark);

        Picasso.get().load(packageVideothumbnail + pacimage).into(imagepackage);


        tv_pac_name.setText(packagename);
        tv_description.setText("Description: " + Html.fromHtml(pacDescription));


        imagepackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MySeriesVideoDetailsActivity.this, Video_player.class);
                intent.putExtra("video_id", VideoIDKEY);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
        if (testCount == null) {

            tv_test_count.setText("Test Count:-" + "N/A");
        } else {

            tv_test_count.setText("Test Count: " + testCount);
        }


        getTestList();


        ic_bookmmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<VideoBookmarkModel> videoBookmarkModelCall = Api_Client.getInstance().insetVideoBookmark(StudentId, VideoID);
                videoBookmarkModelCall.enqueue(new Callback<VideoBookmarkModel>() {
                    @Override
                    public void onResponse(Call<VideoBookmarkModel> call, Response<VideoBookmarkModel> response) {
                        if (response.body().getStatus().equals(1)) {

                            ic_bookmmark.setColorFilter(ic_bookmmark.getContext().getResources().getColor(R.color.tab_checked), PorterDuff.Mode.SRC_ATOP);
                            Toast.makeText(MySeriesVideoDetailsActivity.this, "Bookmarked", Toast.LENGTH_SHORT).show();

                        } else {

                            ic_bookmmark.setColorFilter(ic_bookmmark.getContext().getResources().getColor(R.color.tab_unchecked), PorterDuff.Mode.SRC_ATOP);

                            Toast.makeText(MySeriesVideoDetailsActivity.this, "UnBookmarked", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<VideoBookmarkModel> call, Throwable t) {

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
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void getTestList() {
        Call<TestInsideVideoModel> testInsideVideoModelCall = Api_Client.getInstance().getTestWithVideoID(VideoID);
        testInsideVideoModelCall.enqueue(new Callback<TestInsideVideoModel>() {
            @Override
            public void onResponse(Call<TestInsideVideoModel> call, Response<TestInsideVideoModel> response) {
                if (response.body().getStatus().equals(1)) {
                    TestInsideVideoModel TestInsideVideoModel = response.body();
                    ArrayList<TestInsideVideoDetailsModel> testInsideVideoDetailsModels = (ArrayList<TestInsideVideoDetailsModel>) TestInsideVideoModel.getTestInsideVideoDetailsModels();
                    if (testInsideVideoDetailsModels.size() > 0) {
                        TestSeriesInsideVideoAdapter allTestSeriesAdapter = new TestSeriesInsideVideoAdapter(MySeriesVideoDetailsActivity.this, testInsideVideoDetailsModels);
                        rv_test.setHasFixedSize(true);
                        rv_test.setLayoutManager(new LinearLayoutManager(MySeriesVideoDetailsActivity.this, RecyclerView.VERTICAL, false));
                        allTestSeriesAdapter.notifyDataSetChanged();
                        rv_test.setAdapter(allTestSeriesAdapter);
                    }
                } else {
                    Toast.makeText(MySeriesVideoDetailsActivity.this, "Test not found inside video", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TestInsideVideoModel> call, Throwable t) {

            }
        });
    }


}