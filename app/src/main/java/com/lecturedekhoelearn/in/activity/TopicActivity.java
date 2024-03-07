package com.lecturedekhoelearn.in.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.lecturedekhoelearn.in.adapter.TopicAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.TopicModel;
import com.lecturedekhoelearn.in.model.TopicModelDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lecturedekhoelearn.in.Constant.subjectthumbnails;

public class TopicActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    RecyclerView rvTopics;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String subjectId;
    ImageView subject_image;
    String ClassID;
    TextView subject_name;
    String images,subName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        preferences = getApplicationContext().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        subjectId = preferences.getString("subject_Id", "");
        ClassID = preferences.getString("class_id", "");
        images = preferences.getString("subject_imge", "");
        subName = preferences.getString("subject_Name", "");

        subject_image = findViewById(R.id.subject_image);
        rvTopics = findViewById(R.id.recycler_by_topic);
        subject_name = findViewById(R.id.subject_name);
        subject_name.setText(subName);

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


        Picasso.get()
                .load(subjectthumbnails + images)
                .error(R.drawable.ic_empty)
                .placeholder(R.drawable.splashlogo)
                .into(subject_image);
        getAllTopics();


    }


    public void getAllTopics() {
        pDialog.setTitle("Loading All Topics...");
        showDialog();
        Call<TopicModel> topicsCall = Api_Client.getInstance().getAllTopics(ClassID, subjectId);
        topicsCall.enqueue(new Callback<TopicModel>() {

            @Override
            public void onResponse(Call<TopicModel> call, Response<TopicModel> response) {

                if (response.body().getStatus().equals(1)) {
                    hideDialog();
                    TopicModel topics = response.body();
                    ArrayList<TopicModelDetails> topics_details = (ArrayList<TopicModelDetails>) topics.getVideoTopicDetails();
                    if (!topics_details.isEmpty() && topics_details.size() > 0) {
                        TopicAdapter topic_adapter = new TopicAdapter(getApplication(), topics_details);
                        rvTopics.setAdapter(topic_adapter);
                        rvTopics.setHasFixedSize(true);
                        rvTopics.setLayoutManager(new LinearLayoutManager(getApplication(), RecyclerView.VERTICAL, false));
                    } else {
                        Toast.makeText(TopicActivity.this, "Topics Not Found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(TopicActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TopicModel> call, Throwable t) {
                hideDialog();
                Toast.makeText(TopicActivity.this, "network failure: Please check your Internet connection", Toast.LENGTH_SHORT).show();

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
