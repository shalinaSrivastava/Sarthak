package com.lecturedekhoelearn.in.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.halilibo.bvpkotlin.BetterVideoPlayer;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.async.ServerConnector;
import com.lecturedekhoelearn.in.model.VideoBookmarkModel;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MotiVationalVideoPlayerActivity extends YouTubeBaseActivity{
    private static final String TAG = MotiVationalVideoPlayerActivity.class.getSimpleName();
    private String videoID;
    private YouTubePlayerView youTubePlayerView;
    String currentTime="",lasttime="",current_date="";
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss aa");
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private ProgressDialog pDialog;
    LinearLayout share_image,bookmark,rat_us;

    PlayerView playerView;

   // private static final String TAG = "ExoPlayerActivity";
    private static final String KEY_VIDEO_URI = "video_uri";

    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;

    String videoUri,v_id="";
    Handler mHandler;
    ProgressBar spinnerVideoDetails;
    Runnable mRunnable;
    BetterVideoPlayer player;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player_moti);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        share_image = findViewById(R.id.share_image);
        bookmark=findViewById(R.id.bookmark_v);
        rat_us=findViewById(R.id.rat_us);
        v_id = getIntent().getStringExtra("v_id");
        rat_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent rateIntent = rateIntentForUrl("market://details");
                    startActivity(rateIntent);
                } catch (ActivityNotFoundException e) {
                    Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
                    startActivity(rateIntent);
                }
            }
        });

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_id=preferences.getString("studentId", "");
                Call<VideoBookmarkModel> videoBookmarkModelCall = Api_Client.getInstance().insetVideoBookmark(s_id, v_id);
                videoBookmarkModelCall.enqueue(new Callback<VideoBookmarkModel>() {
                    @Override
                    public void onResponse(Call<VideoBookmarkModel> call, Response<VideoBookmarkModel> response) {
                        Toast.makeText(MotiVationalVideoPlayerActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<VideoBookmarkModel> call, Throwable t) {

                    }
                });
            }
        });

        share_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey! I just watched an awesome video. Watch it now for FREE on lecturedekho app. You can also enhance your learning through its online classes, practice,ask doubts and games.-\n" +
                        "https://play.google.com/store/apps/details?id=com.lecturedekhoelearn.in&hl=en");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });
        //get the video id
        videoID = getIntent().getStringExtra("video_id");
       initializeYoutubePlayer();
    }
    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }
    /**
     * initialize the youtube player
     */
    private void initializeYoutubePlayer() {
        youTubePlayerView.initialize(DeveloperKey.DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer,
                                                boolean wasRestored) {
                MediaController mediaController= new MediaController(getApplicationContext());
                mediaController.setAnchorView(youTubePlayerView);

                //if initialization success then load the video id to youtube player
                if (!wasRestored) {
                    //set the player style here: like CHROMELESS, MINIMAL, DEFAULT
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    //load the video
                    youTubePlayer.loadVideo(videoID);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                //print or show error if initialization failed
                Log.e(TAG, "Youtube Player View initialization failed");
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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();
    }


}