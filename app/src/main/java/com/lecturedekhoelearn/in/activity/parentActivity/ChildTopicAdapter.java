package com.lecturedekhoelearn.in.activity.parentActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.TopicWiseVideoActivity;
import com.lecturedekhoelearn.in.model.TopicModelDetails;

import java.util.ArrayList;

public class ChildTopicAdapter extends RecyclerView.Adapter<ChildTopicAdapter.MyViewHolder> {
    Context context;
    MyViewHolder myViewHolder;
    LayoutInflater inflater;
    ArrayList<TopicModelDetails> topicModelDetailsArrayList;
    private static ClickListenerTopic clickListenerTopic;
    public ChildTopicAdapter(Context context, ArrayList<TopicModelDetails> topicModelDetails) {
        this.context = context;
        this.topicModelDetailsArrayList = topicModelDetails;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_topic_child, viewGroup, false);
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final TopicModelDetails list = topicModelDetailsArrayList.get(i);
        myViewHolder.name.setText(list.getTopics());
        myViewHolder.topic_serial.setText(String.valueOf(i+1)+".");




    }

    @Override
    public int getItemCount() {
        return topicModelDetailsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        public TextView name, topic_serial;
        LinearLayout ll_topic;

        public MyViewHolder(@NonNull View view) {
            super(view);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            name = itemView.findViewById(R.id.topic_name);
            topic_serial = itemView.findViewById(R.id.topic_serial);
            ll_topic = itemView.findViewById(R.id.ll_topic);
        }


        @Override
        public void onClick(View v) {
            clickListenerTopic.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListenerTopic.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }
    public void setOnItemClickListener(ClickListenerTopic clickListener) {
        ChildTopicAdapter.clickListenerTopic = clickListener;
    }

    public interface ClickListenerTopic {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}