package com.lecturedekhoelearn.in.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.BuyTestSeries;
import com.lecturedekhoelearn.in.activity.YoutubePlayerActivity;
import com.lecturedekhoelearn.in.activity.parentActivity.Video_player;
import com.lecturedekhoelearn.in.model.PreemiemVideosDetails;
import com.lecturedekhoelearn.in.model.TopicWiseVideoDetailsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.blurry.Blurry;

import static com.lecturedekhoelearn.in.Constant.packageVideothumbnail;

public class TopicWiseVideosADapter extends RecyclerView.Adapter<TopicWiseVideosADapter.MyViewHolder> {
    Context context;
    MyViewHolder myViewHolder;
    LayoutInflater inflater;
    ArrayList<TopicWiseVideoDetailsModel> packageVideoDetailsModelArrayList;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public TopicWiseVideosADapter(Context context, ArrayList<TopicWiseVideoDetailsModel> subVideos) {
        this.context = context;
        this.packageVideoDetailsModelArrayList = subVideos;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_test_series_videos, viewGroup, false);
        preferences = context.getSharedPreferences(Constant.myprif, Context.MODE_PRIVATE);
        editor = preferences.edit();
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final TopicWiseVideoDetailsModel subVideo = packageVideoDetailsModelArrayList.get(i);
       // final TopicWiseVideoDetailsModel subVideo = packageVideoDetailsModelArrayList.get(i);

        Picasso.get()
                .load(packageVideothumbnail + subVideo.getThumbnail())
                .error(R.drawable.ic_empty)
                .placeholder(R.drawable.splashlogo)
                .into((myViewHolder.video_thumbnail));
        Picasso.get()
                .load(packageVideothumbnail + subVideo.getThumbnail())
                .error(R.drawable.ic_empty)
                .placeholder(R.drawable.splashlogo)
                .into((myViewHolder.video_thumbnail_l));

        myViewHolder._title.setText(subVideo.getTitle());
        myViewHolder._video_title_l.setText(subVideo.getTitle());
        try {
            if (subVideo.getPackage_type().equals("1")){
                myViewHolder.v_card.setVisibility(View.GONE);
                myViewHolder.blur_bg.setVisibility(View.VISIBLE);
            }else {
                myViewHolder.blur_bg.setVisibility(View.GONE);
                myViewHolder.v_card.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        myViewHolder.buy_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, BuyTestSeries.class);
                context.startActivity(intent);
            }
        });



       /* myViewHolder.all_video_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MySeriesVideoDetailsActivity.class);
                intent.putExtra("videoID", subVideo.getId());
                intent.putExtra("title", "" + subVideo.getTitle());
                intent.putExtra("video_id", subVideo.getVideo());
                intent.putExtra("description", "" + subVideo.getDescription().replaceAll("\\<.*?\\>", ""));
                intent.putExtra("thumbnail", subVideo.getThumbnail());
                intent.putExtra("testCount", subVideo.getTest_count());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });*/


        myViewHolder.watch_now.setOnClickListener(v -> {
            if (subVideo.getVideo_type().equalsIgnoreCase("1")){
                Intent intent = new Intent(context, YoutubePlayerActivity.class);
                intent.putExtra("video_id", subVideo.getVideo());
                intent.putExtra("v_id",subVideo.getId());
                intent.putExtra("b_mark",subVideo.getBookmark());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }else {
                Intent intent = new Intent(context, Video_player.class);
                intent.putExtra("video_id", subVideo.getVideo());
                intent.putExtra("v_id", subVideo.getId());
                intent.putExtra("b_mark",subVideo.getBookmark());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return packageVideoDetailsModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView video_thumbnail,video_thumbnail_l;
        TextView _title, _topic, _subject,_video_title_l,_video_topic_l,_video_subject_l;
        CardView all_video_card;
        Button watch_now,buy_know;
        LinearLayout blur_bg;
        LinearLayout v_card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            v_card=itemView.findViewById(R.id.v_card);
            all_video_card = itemView.findViewById(R.id.all_video_card);
            video_thumbnail = itemView.findViewById(R.id.video_thumbnail);
            _title = itemView.findViewById(R.id._video_title);
            _topic = itemView.findViewById(R.id._video_topic);
            _subject = itemView.findViewById(R.id._video_subject);
            watch_now = itemView.findViewById(R.id.watch_now);
            blur_bg=itemView.findViewById(R.id.blur_bg);
            buy_know=itemView.findViewById(R.id.buy_now);
            video_thumbnail_l=itemView.findViewById(R.id.video_thumbnail_l);
            _video_title_l=itemView.findViewById(R.id._video_title_l);
            _video_topic_l=itemView.findViewById(R.id._video_topic_l);
            _video_subject_l=itemView.findViewById(R.id._video_subject_l);
        }
    }
}