package com.lecturedekhoelearn.in.activity.parentActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.MySeriesVideoDetailsActivity;
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

public class Video_player extends AppCompatActivity{

    SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss aa");
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
    String currentTime="",lasttime="",current_date="";
    private PlayerView mExoPlayerView;
    private MediaSource mVideoSource;
    private boolean mExoPlayerFullscreen = false;
    private FrameLayout mFullScreenButton;
    private ImageView mFullScreenIcon,b_image;
    private Dialog mFullScreenDialog;
    private int mResumeWindow;
    private long mResumePosition;
    private String videoID="",v_id="";
    LinearLayout share_image,bookmark,rat_us;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        preferences = getApplicationContext().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        if (savedInstanceState != null) {
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }
        share_image = findViewById(R.id.share_image);
        videoID = getIntent().getStringExtra("video_id");
        b_image=findViewById(R.id.b_image);
        v_id = getIntent().getStringExtra("v_id");
        bookmark=findViewById(R.id.bookmark_v);
        rat_us=findViewById(R.id.rat_us);
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
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("please wait...");
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b_image.getBackground().getConstantState()==getResources().getDrawable(R.drawable.bookmark_blue).getConstantState()){
                    b_image.setBackgroundResource(R.drawable.bookmark);
                }else{
                    b_image.setBackground(getResources().getDrawable(R.drawable.bookmark_blue));
                }
                String s_id=preferences.getString("studentId", "");
                Call<VideoBookmarkModel> videoBookmarkModelCall = Api_Client.getInstance().insetVideoBookmark(s_id, v_id);
                videoBookmarkModelCall.enqueue(new Callback<VideoBookmarkModel>() {
                    @Override
                    public void onResponse(Call<VideoBookmarkModel> call, Response<VideoBookmarkModel> response) {

                            Toast.makeText(Video_player.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<VideoBookmarkModel> call, Throwable t) {

                    }
                });
            }
        });
        if (getIntent().getStringExtra("b_mark")!=null){
            b_image.setBackgroundResource(R.drawable.bookmark_blue);
        }else {
            b_image.setBackgroundResource(R.drawable.bookmark);
        }
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
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);
        super.onSaveInstanceState(outState);
    }


    private void initFullscreenDialog() {
        mFullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }


    @SuppressLint("SourceLockedOrientationActivity")
    private void openFullscreenDialog() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(Video_player.this, R.drawable.ic_fullscreen_skrink));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
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
    @SuppressLint("SourceLockedOrientationActivity")
    private void closeFullscreenDialog() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        ((FrameLayout) findViewById(R.id.main_media_frame)).addView(mExoPlayerView);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(Video_player.this, R.drawable.ic_fullscreen_expand));
    }


    private void initFullscreenButton() {

        PlaybackControlView controlView = mExoPlayerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen)
                    openFullscreenDialog();
                else
                    closeFullscreenDialog();
            }
        });
    }


    private void initExoPlayer() {

        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(this);
        mExoPlayerView.setPlayer(player);

        boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;

        if (haveResumePosition) {
            player.seekTo(mResumeWindow, mResumePosition);
        }

        player.prepare(mVideoSource);
        player.setPlayWhenReady(true);
    }


    @Override
    protected void onResume() {

        super.onResume();

        if (mExoPlayerView == null) {

            mExoPlayerView =  findViewById(R.id.exoplayer);
            initFullscreenDialog();
            initFullscreenButton();

            String userAgent = Util.getUserAgent(Video_player.this, getApplicationContext().getApplicationInfo().packageName);
            DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(Video_player.this, null, httpDataSourceFactory);

            Uri uri = Uri.parse("https://lecturedekho.com/admin/assets/images/app_videos/video/"+videoID);
            mVideoSource = buildMediaSource(uri);        }

        initExoPlayer();

        if (mExoPlayerFullscreen) {
            ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
            mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(Video_player.this, R.drawable.ic_fullscreen_skrink));
            mFullScreenDialog.show();
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(this, "exoplayer-codelab");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

    @Override
    protected void onPause() {

        super.onPause();

        if (mExoPlayerView != null && mExoPlayerView.getPlayer() != null) {
            mResumeWindow = mExoPlayerView.getPlayer().getCurrentWindowIndex();
            mResumePosition = Math.max(0, mExoPlayerView.getPlayer().getContentPosition());

            mExoPlayerView.getPlayer().release();
        }

        if (mFullScreenDialog != null)
            mFullScreenDialog.dismiss();
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
    public void onBackPressed() {
        lasttime = new SimpleDateFormat("HH:mm:ss aa", Locale.getDefault()).format(new Date());
        String Subject_name=preferences.getString("subject_Name", "");
        String topic_name=preferences.getString("topic_name", "");
        String image=preferences.getString("subject_imge","");
        String s_id=preferences.getString("studentId", "");
        String t_name=preferences.getString("topic_name","");
        String diff="";
        Date date1,date2;
        try {
            date1 = format.parse(currentTime);
            date2 = format.parse(lasttime);
            assert date1 != null;
            assert date2 != null;
            long mills = date1.getTime() - date2.getTime();
            int days = (int) (mills / (1000*60*60*24));
            int hours = (int) ((mills - (1000*60*60*24*days)) / (1000*60*60));
            int  min = (int) (mills - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
            diff= hours + ":" + min+":00";
            if (diff.contains("-")){
                diff=diff.replace("-","");
            }
            if (min!=0){
                getActivity(s_id,image,diff,t_name,current_date,topic_name,Subject_name);
            }else {
                super.onBackPressed();

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        current_date = new SimpleDateFormat("MMM d", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("HH:mm:ss aa", Locale.getDefault()).format(new Date());
    }


    private void getActivity(String s_id, String image, String start_time, String video_name, String date, String topic, String subject) {
        String urlParameters ="student_id="+s_id+"&image="+image+"&start_time="+start_time+"&video_name="+video_name
                +"&date="+date+"&topic="+topic+"&subject="+subject;
        showDialog();


        final ServerConnector loginConnection = new ServerConnector(urlParameters);
        loginConnection.setDataDowmloadListner(response -> {


            try {
                JSONObject jsonObject1 = new JSONObject(response);
                // String status = jsonObject1.getString("msg");
                hideDialog();
                super.onBackPressed();

            } catch (Exception e) {
                hideDialog();
                e.printStackTrace();
                super.onBackPressed();
            }
        });
        loginConnection.execute("https://lecturedekho.com/admin/api/insert_activity");
    }

}