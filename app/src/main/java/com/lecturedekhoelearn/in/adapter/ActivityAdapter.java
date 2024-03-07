package com.lecturedekhoelearn.in.adapter;

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
import com.lecturedekhoelearn.in.model.StudentActivityModel;
import com.lecturedekhoelearn.in.model.TopicModelDetails;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.lecturedekhoelearn.in.Constant.packageVideothumbnail;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.MyViewHolder> {
    Context context;
    MyViewHolder myViewHolder;
    LayoutInflater inflater;
    ArrayList<StudentActivityModel> topicModelDetailsArrayList;

    public ActivityAdapter(Context context, ArrayList<StudentActivityModel> studentActivityModels) {
        this.context = context;
        this.topicModelDetailsArrayList = studentActivityModels;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_sactivity, viewGroup, false);
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final StudentActivityModel list = topicModelDetailsArrayList.get(i);
        myViewHolder.topic.setText(list.getTopic());
        myViewHolder.subject.setText(list.getSubject());
        myViewHolder.str_time.setText(list.getEnd_time());
        Picasso.get()
                .load(packageVideothumbnail + "1564143125.png")
                .error(R.drawable.ic_empty)
                .placeholder(R.drawable.splashlogo)
                .into((myViewHolder.imageView));
    }
    @Override
    public int getItemCount() {
        return topicModelDetailsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView topic, str_time,subject;
        CircularImageView imageView;

        public MyViewHolder(@NonNull View view) {
            super(view);
            topic = itemView.findViewById(R.id.topic);
            subject=itemView.findViewById(R.id.subject);
            str_time = itemView.findViewById(R.id.str_time);
            imageView=itemView.findViewById(R.id.image);
        }
    }
}