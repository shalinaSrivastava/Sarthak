package com.lecturedekhoelearn.in.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.ileaf.fullscreenmediacontrollerlibrary.FullScreenMediaController;
import com.ileaf.fullscreenmediacontrollerlibrary.FullScreenVideoCallBack;
import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoActivity extends AppCompatActivity implements FullScreenVideoCallBack {

    @BindView(R.id.iv_banner)
    ImageView ivBanner;
    @BindView(R.id.iv_play_video)
    ImageView ivPlayVideo;
    @BindView(R.id.video_view)
    VideoView videoView;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;
    @BindView(R.id.fl_start_crunch)
    FrameLayout flStartCrunch;
    FullScreenMediaController mediaControls;
    int video_position;
    @BindView(R.id.tv_video_title)
    TextView tv_title;
    @BindView(R.id.tv_video_description)
    TextView tv_description;

    String _video_id, _subject_Id;
    String _video_title, _video_desc, subject_name;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String VideoId;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        //Preferences
        preferences = getApplication().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();


        //Intent : get value from intent.
        _video_id = getIntent().getExtras().getString("videoID");
        _subject_Id = getIntent().getExtras().getString("SubjectId");
        _video_title = getIntent().getExtras().getString("title");
        _video_desc = getIntent().getExtras().getString("description");
        subject_name = getIntent().getExtras().getString("SubjectModel");
        VideoId = getIntent().getExtras().getString("video");

        tv_title.setText(_video_title);
        tv_description.setText(_video_desc);

        url = preferences.getString("url", "");


        ivPlayVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivPlayVideo.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);
                progressBar1.setVisibility(View.VISIBLE);
                try {
                    //set the media controller in the VideoView
                    if (mediaControls == null) {
                        mediaControls = new FullScreenMediaController(VideoActivity.this, false, VideoActivity.this);
                    }
                    videoView.setMediaController(mediaControls);
                    //set the uri of the video to be played
                    //  videoView.setVideoPath(url.concat(VideoId));
                    videoView.setVideoPath(url.concat(VideoId));
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }

                videoView.requestFocus();
                //we also set an setOnPreparedListener in order to know when the video file is ready for playback
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    public void onPrepared(MediaPlayer mediaPlayer) {
                        progressBar1.setVisibility(View.GONE);
                        //if we have a position on savedInstanceState, the video playback should start from here
                        videoView.seekTo(video_position);
                        if (video_position == 0) {
                            videoView.start();
                          //  videoView.setMediaController(mediaControls);
                        } else {
                            //if we come from a resumed activity, video playback will be paused
                            videoView.pause();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void navigateToFullScreenVideoScreen() {
        Intent intent = new Intent(this, FullVideoScreenActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("video_url", url.concat(VideoId));
        bundle.putInt("video_position", videoView.getCurrentPosition());
        intent.putExtras(bundle);
        startActivity(intent);
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