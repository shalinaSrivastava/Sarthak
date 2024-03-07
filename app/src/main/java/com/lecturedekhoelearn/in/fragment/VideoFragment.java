package com.lecturedekhoelearn.in.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.TopicWiseVideoActivity;
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

import static android.content.Context.MODE_PRIVATE;

public class VideoFragment extends Fragment {

    RecyclerView rv_video,rv_video_premiem;
    String TopicId, StudentId, ClassId, SubjectId;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private ProgressDialog pDialog;
    TextView tv_paid, tv_free;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_topic_wise_video, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        preferences = getActivity().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        StudentId = preferences.getString("studentId", "");
        ClassId = preferences.getString("class_id", "");
        SubjectId = preferences.getString("subject_Id", "");
        TopicId = getActivity().getIntent().getStringExtra("topic_id");

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        rv_video = view.findViewById(R.id.rv_video);
        // tv_free = findViewById(R.id.tv_free);
        //  tv_paid = findViewById(R.id.tv_paid);
        rv_video_premiem = view.findViewById(R.id.rv_video_premiem);

        getTestSeriesVideos();
        return view;
    }

    public void getTestSeriesVideos() {

        pDialog.setTitle("Loading All Videos...");
        showDialog();
        Call<TopicWiseVideoModel> packageVideoModelCall = Api_Client.getInstance().getTopicWiseVideos(ClassId, TopicId, SubjectId,StudentId);
        packageVideoModelCall.enqueue(new Callback<TopicWiseVideoModel>() {
            @Override
            public void onResponse(Call<TopicWiseVideoModel> call, Response<TopicWiseVideoModel> response) {

                try {
                    if (response.body().getStatus().equals(2)) {
                        hideDialog();
                        TopicWiseVideoModel topicWiseVideoModel = response.body();
                        ArrayList<TopicWiseVideoDetailsModel> topicWiseVideoDetailsModels = (ArrayList<TopicWiseVideoDetailsModel>) topicWiseVideoModel.getTopicWiseVideoDetailsModels();
                        ArrayList<PreemiemVideosDetails> preemiemVideosDetails = (ArrayList<PreemiemVideosDetails>) topicWiseVideoModel.getPreemiemVideosDetails();
                        if (!topicWiseVideoDetailsModels.isEmpty() && topicWiseVideoDetailsModels.size() > 0) {
                            rv_video.setVisibility(View.VISIBLE);
                            rv_video_premiem.setVisibility(View.GONE);
                            TopicWiseVideosADapter analysisAdapter = new TopicWiseVideosADapter(getActivity(), topicWiseVideoDetailsModels);
                            rv_video.setAdapter(analysisAdapter);
                            rv_video.setHasFixedSize(true);
                            rv_video.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                        } else {
                            rv_video.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "No videos found in Topics", Toast.LENGTH_SHORT).show();
                        }
                    /*if (!preemiemVideosDetails.isEmpty() && preemiemVideosDetails.size() > 0) {
                        rv_video.setVisibility(View.GONE);
                        rv_video_premiem.setVisibility(View.VISIBLE);
                        PremiumVideoAdapter analysisAdapter = new PremiumVideoAdapter(getActivity(), preemiemVideosDetails);
                        rv_video_premiem.setAdapter(analysisAdapter);
                        rv_video_premiem.setHasFixedSize(true);
                        rv_video_premiem.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                    } else {
                          rv_video_premiem.setVisibility(View.GONE);
                        // Toast.makeText(TopicWiseVideoActivity.this, "No videos found in Topics", Toast.LENGTH_SHORT).show();
                    }*/
                    } else if (response.body().getStatus().equals(1)) {
                        hideDialog();
                        TopicWiseVideoModel topicWiseVideoModel = response.body();
                        ArrayList<TopicWiseVideoDetailsModel> preemiemVideosDetails = (ArrayList<TopicWiseVideoDetailsModel>) topicWiseVideoModel.getTopicWiseVideoDetailsModels();
                        if (!preemiemVideosDetails.isEmpty() && preemiemVideosDetails.size() > 0) {
                            rv_video.setVisibility(View.GONE);
                            rv_video_premiem.setVisibility(View.VISIBLE);
                            PremiumVideoAdapter analysisAdapter = new PremiumVideoAdapter(getActivity(), preemiemVideosDetails);
                            rv_video_premiem.setAdapter(analysisAdapter);
                            rv_video_premiem.setHasFixedSize(true);
                            rv_video_premiem.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                        } else {
                            rv_video_premiem.setVisibility(View.GONE);
                            // Toast.makeText(TopicWiseVideoActivity.this, "No videos found in Topics", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        hideDialog();
                        //    Toast.makeText(TopicWiseVideoActivity.this, "No videos found in Topics", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
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


}
