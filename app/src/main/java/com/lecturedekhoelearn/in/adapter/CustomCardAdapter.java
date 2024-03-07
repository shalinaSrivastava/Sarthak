package com.lecturedekhoelearn.in.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.CurrentAffairsActivity;
import com.lecturedekhoelearn.in.activity.DailyQuizActivity;
import com.lecturedekhoelearn.in.activity.MotivationVideoActivity;
import com.lecturedekhoelearn.in.activity.NewCurrentAffairs;
import com.lecturedekhoelearn.in.activity.StartDailyQuiz;
import com.lecturedekhoelearn.in.model.CustomData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CustomCardAdapter extends RecyclerView.Adapter<CustomCardAdapter.MyViewHolder> {

    List<CustomData> horizontalList = new ArrayList<>();
    Context context;


    public CustomCardAdapter(List<CustomData> horizontalList, Context context) {
        this.horizontalList = horizontalList;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout cv_subject;
        TextView invite_title;
        ImageView img_subject;

        public MyViewHolder(View view) {
            super(view);
            invite_title = itemView.findViewById(R.id.tv_subject);
            img_subject = itemView.findViewById(R.id.sub_img);
            cv_subject = itemView.findViewById(R.id.ll_subject);
        }
    }


    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_subject, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.img_subject.setImageResource(horizontalList.get(position).imageId);
        holder.invite_title.setText(horizontalList.get(position).txt);


        holder.cv_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    Intent intent = new Intent(context, MotivationVideoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(context, DailyQuizActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(context, NewCurrentAffairs.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }
}