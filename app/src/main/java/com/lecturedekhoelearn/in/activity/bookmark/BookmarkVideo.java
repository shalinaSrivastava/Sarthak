package com.lecturedekhoelearn.in.activity.bookmark;

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
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.VideoShowBookmarkActivity;
import com.lecturedekhoelearn.in.adapter.ShowVideoBookmarkAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.VideoDataBookModel;
import com.lecturedekhoelearn.in.model.VideoDeailsBookModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class BookmarkVideo extends Fragment {

    RecyclerView rv_videos_lect;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private ProgressDialog pDialog;
    String StudentId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.activity_video_show_bookmark, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);

        rv_videos_lect = view.findViewById(R.id.rv_videos_lect);

        preferences = getActivity().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();

        StudentId = preferences.getString("studentId", "");

        getVideoLect();

        return view;
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
                        Toast.makeText(getActivity(), "You have not purchased any videos", Toast.LENGTH_SHORT).show();
                    } else {
                        ShowVideoBookmarkAdapter adapter = new ShowVideoBookmarkAdapter(getActivity(), videoDataBookModels);
                        rv_videos_lect.setAdapter(adapter);
                        rv_videos_lect.setHasFixedSize(true);
                        rv_videos_lect.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                    }
                } else {
                    hideDialog();
                    Toast.makeText(getActivity(), "You have not purchased any videos", Toast.LENGTH_SHORT).show();
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


}
