package com.lecturedekhoelearn.in.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.halilibo.bvpkotlin.BetterVideoPlayer;
import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.parentActivity.Video_player;
import com.lecturedekhoelearn.in.adapter.ActivityAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.async.ServerConnector;
import com.lecturedekhoelearn.in.model.StudentActivityModel;
import com.lecturedekhoelearn.in.model.VideoBookmarkModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class YoutubePlayerActivity extends YouTubeBaseActivity{
    private static final String TAG = YoutubePlayerActivity.class.getSimpleName();
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
    ImageView b_image;
    boolean bkmrk=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        pDialog = new ProgressDialog(this);
        preferences = getApplicationContext().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        pDialog.setCancelable(false);
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        share_image = findViewById(R.id.share_image);
        bookmark=findViewById(R.id.bookmark_v);
        b_image=findViewById(R.id.b_image);
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

        if (getIntent().getStringExtra("b_mark")!=null){
            b_image.setBackgroundResource(R.drawable.bookmark_blue);
            bkmrk=true;
        }else {
            b_image.setBackgroundResource(R.drawable.bookmark);
            bkmrk=false;
        }


        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_id=preferences.getString("studentId", "");
                if (bkmrk){
                    bkmrk=false;
                    b_image.setBackgroundResource(R.drawable.bookmark);
                }else{
                    bkmrk=true;
                    b_image.setBackgroundResource(R.drawable.bookmark_blue);
                }
                Call<VideoBookmarkModel> videoBookmarkModelCall = Api_Client.getInstance().insetVideoBookmark(s_id, v_id);
                videoBookmarkModelCall.enqueue(new Callback<VideoBookmarkModel>() {
                    @Override
                    public void onResponse(Call<VideoBookmarkModel> call, Response<VideoBookmarkModel> response) {
                        Toast.makeText(YoutubePlayerActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
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
        current_date = new SimpleDateFormat("MMM d", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("HH:mm:ss aa", Locale.getDefault()).format(new Date());
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        lasttime = new SimpleDateFormat("HH:mm:ss aa", Locale.getDefault()).format(new Date());
        preferences = getApplicationContext().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
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
            }
        });
        loginConnection.execute("https://lecturedekho.com/admin/api/insert_activity");
    }


}