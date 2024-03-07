package com.lecturedekhoelearn.in.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.ActiveVideoAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.ActiveVideoModel;
import com.lecturedekhoelearn.in.model.ActiveVideoModelDetails;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class VideosFragment extends Fragment {


    RecyclerView rv_video;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String studentId;

    public VideosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vidoes, container, false);

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        preferences = getActivity().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();

        rv_video = view.findViewById(R.id.rv_video);

        studentId = preferences.getString("studentId", "");

        getActiveVideos();

        return view;


    }

    private void getActiveVideos() {
        Call<ActiveVideoModel> packageVideoModelCall = Api_Client.getInstance().getActiveVideo(studentId);
        packageVideoModelCall.enqueue(new Callback<ActiveVideoModel>() {
            @Override
            public void onResponse(Call<ActiveVideoModel> call, Response<ActiveVideoModel> response) {

                try {
                    if (response.body().getStatus().equals(1)) {

                        ActiveVideoModel packageVideoModel = response.body();

                        ArrayList<ActiveVideoModelDetails> activeVideoModelDetails = (ArrayList<ActiveVideoModelDetails>) packageVideoModel.getActiveVideoModelDetails();

                        if (!activeVideoModelDetails.isEmpty() && activeVideoModelDetails != null && activeVideoModelDetails.size() > 0) {

                            ActiveVideoAdapter analysisAdapter = new ActiveVideoAdapter(getActivity(), activeVideoModelDetails);
                            rv_video.setAdapter(analysisAdapter);
                            rv_video.setHasFixedSize(true);
                            rv_video.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

                        } else {

                            Toast.makeText(getActivity(), "No videos found in Series", Toast.LENGTH_SHORT).show();
                        }

                    }
                }catch (Exception e){
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<ActiveVideoModel> call, Throwable t) {

            }
        });


    }
}

