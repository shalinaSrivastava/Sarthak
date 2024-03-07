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
import com.lecturedekhoelearn.in.activity.YoutubePlayerActivity;
import com.lecturedekhoelearn.in.activity.parentActivity.Video_player;
import com.lecturedekhoelearn.in.model.VideoDataBookModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.lecturedekhoelearn.in.Constant.packageVideothumbnail;


public class ShowVideoBookmarkAdapter extends RecyclerView.Adapter<ShowVideoBookmarkAdapter.MyViewHolder> {
    Context context;
    MyViewHolder myViewHolder;
    LayoutInflater inflater;
    ArrayList<VideoDataBookModel> subVideos;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    public ShowVideoBookmarkAdapter(Context context, ArrayList<VideoDataBookModel> subVideos) {
        this.context = context;
        this.subVideos = subVideos;
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
        final VideoDataBookModel subVideo = subVideos.get(i);

        Picasso.get()
                .load(packageVideothumbnail + subVideo.getThumbnail())
                .error(R.drawable.ic_empty)
                .placeholder(R.drawable.splashlogo)
                .into((myViewHolder.thumbnail));


        myViewHolder._title.setText(subVideo.getTitle());
        myViewHolder.watch_now.setOnClickListener(v -> {
            if (subVideo.getVideo_type().equalsIgnoreCase("1")){
                Intent intent = new Intent(context, YoutubePlayerActivity.class);
                intent.putExtra("video_id", subVideo.getVideo());
                intent.putExtra("v_id",subVideo.getId());
                intent.putExtra("b_mark","1");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }else {
                Intent intent = new Intent(context, Video_player.class);
                intent.putExtra("video_id", subVideo.getVideo());
                intent.putExtra("v_id", subVideo.getId());
                intent.putExtra("b_mark","1");
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
        ImageView thumbnail;
        TextView _title, _topic, _subject;
        CardView all_video_card;
        Button watch_now;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            all_video_card = itemView.findViewById(R.id.all_video_card);
            thumbnail = itemView.findViewById(R.id.video_thumbnail);
            _title = itemView.findViewById(R.id._video_title);
            _topic = itemView.findViewById(R.id._video_topic);
            _subject = itemView.findViewById(R.id._video_subject);
            watch_now = itemView.findViewById(R.id.watch_now);


        }
    }
}
