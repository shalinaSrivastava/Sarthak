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
import com.lecturedekhoelearn.in.activity.MySeriesVideoDetailsActivity;
import com.lecturedekhoelearn.in.activity.YoutubePlayerActivity;
import com.lecturedekhoelearn.in.model.PackageVideoDetailsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.lecturedekhoelearn.in.Constant.packageVideothumbnail;

public class MyTestSeriesVideoAdapter extends RecyclerView.Adapter<MyTestSeriesVideoAdapter.MyViewHolder> {
    Context context;
    MyViewHolder myViewHolder;
    LayoutInflater inflater;
    ArrayList<PackageVideoDetailsModel> packageVideoDetailsModelArrayList;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public MyTestSeriesVideoAdapter(Context context, ArrayList<PackageVideoDetailsModel> subVideos) {
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
        final PackageVideoDetailsModel subVideo = packageVideoDetailsModelArrayList.get(i);

        Picasso.get()
                .load(packageVideothumbnail + subVideo.getThumbnail())
                .error(R.drawable.ic_empty)
                .placeholder(R.drawable.splashlogo)
                .into((myViewHolder.video_thumbnail));


        myViewHolder._title.setText(subVideo.getTitle());

        myViewHolder.all_video_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MySeriesVideoDetailsActivity.class);
                intent.putExtra("videoID",subVideo.getId());
                intent.putExtra("title", "" + subVideo.getTitle());
                intent.putExtra("video_id", subVideo.getVideo());
                intent.putExtra("description", "" + subVideo.getDescription().replaceAll("\\<.*?\\>", ""));
                intent.putExtra("thumbnail", subVideo.getThumbnail());
                intent.putExtra("testCount", subVideo.getTest_count());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        myViewHolder.watch_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, YoutubePlayerActivity.class);
                intent.putExtra("video_id", subVideo.getVideo());
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
        ImageView video_thumbnail;
        TextView _title, _topic, _subject;
        CardView all_video_card;
        Button watch_now;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            all_video_card = itemView.findViewById(R.id.all_video_card);
            video_thumbnail = itemView.findViewById(R.id.video_thumbnail);
            _title = itemView.findViewById(R.id._video_title);
            _topic = itemView.findViewById(R.id._video_topic);
            _subject = itemView.findViewById(R.id._video_subject);
            watch_now = itemView.findViewById(R.id.watch_now);


        }
    }
}