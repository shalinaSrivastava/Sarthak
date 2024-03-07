package com.lecturedekhoelearn.in.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.MotiVationalVideoPlayerActivity;
import com.lecturedekhoelearn.in.activity.YoutubePlayerActivity;
import com.lecturedekhoelearn.in.model.MotivationalVideosDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.lecturedekhoelearn.in.Constant.motivationalThumbnails;

public class MotivationalVideoAdapter extends RecyclerView.Adapter<MotivationalVideoAdapter.MyViewHolder> {
    Context context;
    MyViewHolder myViewHolder;
    LayoutInflater inflater;
    ArrayList<MotivationalVideosDetails> motivationalVideosDetailsArrayList;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public MotivationalVideoAdapter(Context context, ArrayList<MotivationalVideosDetails> motivationalVideosDetails) {
        this.context = context;
        this.motivationalVideosDetailsArrayList = motivationalVideosDetails;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        preferences = context.getSharedPreferences(Constant.myprif, Context.MODE_PRIVATE);
        editor = preferences.edit();
        View view = inflater.inflate(R.layout.layout_motivational, viewGroup, false);
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final MotivationalVideosDetails motivationalVideosDetails = motivationalVideosDetailsArrayList.get(i);

        myViewHolder.video_title.setText(motivationalVideosDetails.getTitle());
        //  myViewHolder.video_serial.setText(String.valueOf(i + 1));


        Picasso.get()
                .load(motivationalThumbnails + motivationalVideosDetails.getThumbnail())
                .error(R.drawable.ic_empty)
                .placeholder(R.drawable.splashlogo)
                .into((myViewHolder.thumbnail));


        myViewHolder.cv_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, YoutubePlayerActivity.class);
                intent.putExtra("video_id", motivationalVideosDetails.getVideo_url());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        myViewHolder.watch_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MotiVationalVideoPlayerActivity.class);
                intent.putExtra("video_id", motivationalVideosDetails.getVideo_url());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return motivationalVideosDetailsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv_videos;
        ImageView thumbnail;
        TextView video_title, video_serial;

        Button watch_now;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cv_videos = itemView.findViewById(R.id.cv_videos);
            thumbnail = itemView.findViewById(R.id.video_thumbnails);
            video_title = itemView.findViewById(R.id.video_title);
            watch_now = itemView.findViewById(R.id.watch_now);
            // video_serial = itemView.findViewById(R.id.video_serial);

        }


    }
}