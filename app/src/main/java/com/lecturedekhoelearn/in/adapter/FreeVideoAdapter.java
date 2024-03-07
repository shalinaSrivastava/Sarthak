package com.lecturedekhoelearn.in.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.joooonho.SelectableRoundedImageView;
import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.VideoActivity;
import com.lecturedekhoelearn.in.model.VideoDetailsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FreeVideoAdapter extends RecyclerView.Adapter<FreeVideoAdapter.MyViewHolder> {
    Context context;
    MyViewHolder myViewHolder;
    LayoutInflater inflater;
    ArrayList<VideoDetailsModel> subVideos;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String thumbnails = "https://vclasses.in/admin/assets/images/videos/thumbnail/";
    String url = "https://vclasses.in/admin/assets/images/videos/video/";
    String Videos;
    String Youtube;

    public FreeVideoAdapter(Context context, ArrayList<VideoDetailsModel> subVideos) {
        this.context = context;
        this.subVideos = subVideos;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        preferences = context.getSharedPreferences(Constant.myprif, Context.MODE_PRIVATE);
        editor = preferences.edit();
        View view = inflater.inflate(R.layout.layout_videos, viewGroup, false);
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final VideoDetailsModel subVideo = subVideos.get(i);

        myViewHolder.video_title.setText(subVideo.getTitle());


        Glide
                .with(context)
                .load(thumbnails + subVideo.getThumbnail())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(myViewHolder.thumbnail);

        Picasso.get()
                .load(R.drawable.ic_video)
                .into((myViewHolder.imageView));


        myViewHolder.cv_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("url", url).apply();
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra("videoID", "" + subVideo.getId().toString());
                intent.putExtra("title", "" + subVideo.getTitle());
                intent.putExtra("video", "" + subVideo.getVideo());
                intent.putExtra("description", "" + subVideo.getDescription().replaceAll("\\<.*?\\>", ""));
                intent.putExtra("SubjectModel", "" + subVideo.getSubject_id());
                intent.putExtra("SubjectId", subVideo.getSubject_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return subVideos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout cv_videos;
        SelectableRoundedImageView thumbnail;
        TextView video_title;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cv_videos = itemView.findViewById(R.id.cv_videos);
            thumbnail = itemView.findViewById(R.id.video_thumbnails);
            video_title = itemView.findViewById(R.id.video_title);
            imageView = itemView.findViewById(R.id.img_button);

        }


    }
}