package com.lecturedekhoelearn.in.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.OptionsForVideo;
import com.lecturedekhoelearn.in.activity.TopicWiseVideoActivity;
import com.lecturedekhoelearn.in.model.TopicModelDetails;

import java.util.ArrayList;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.MyViewHolder> {
    Context context;
    MyViewHolder myViewHolder;
    LayoutInflater inflater;
    ArrayList<TopicModelDetails> topicModelDetailsArrayList;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public TopicAdapter(Context context, ArrayList<TopicModelDetails> topicModelDetails) {
        this.context = context;
        this.topicModelDetailsArrayList = topicModelDetails;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_topic, viewGroup, false);
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final TopicModelDetails list = topicModelDetailsArrayList.get(i);
        myViewHolder.name.setText(list.getTopics());
        myViewHolder.topic_serial.setText(String.valueOf(i+1)+".");

        preferences = context.getSharedPreferences(Constant.myprif, Context.MODE_PRIVATE);
        editor = preferences.edit();
        myViewHolder.ll_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (list.getId() != null) {
                    Intent intent = new Intent(context, OptionsForVideo.class);
                    intent.putExtra("topic_id", "" + list.getId());
                    editor.putString("topic_name", list.getTopics()).apply();
                    editor.putString("topic_id", "" + list.getId()).apply();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "No Topic available for you", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return topicModelDetailsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, topic_serial;
        LinearLayout ll_topic;

        public MyViewHolder(@NonNull View view) {
            super(view);
            name = itemView.findViewById(R.id.topic_name);
            topic_serial = itemView.findViewById(R.id.topic_serial);
            ll_topic = itemView.findViewById(R.id.ll_topic);
        }
    }
}