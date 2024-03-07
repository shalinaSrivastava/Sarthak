package com.lecturedekhoelearn.in.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.SubjectWiseTeacherActivity;
import com.lecturedekhoelearn.in.model.SubjectModelDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.lecturedekhoelearn.in.Constant.subjectthumbnails;

public class QueryAdapterSubject extends RecyclerView.Adapter<QueryAdapterSubject.MyViewHolder> {
    Context context;
    MyViewHolder myViewHolder;
    LayoutInflater inflater;
    ArrayList<SubjectModelDetails> subjectModelDetailsArrayList;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    public QueryAdapterSubject(Context context, ArrayList<SubjectModelDetails> subjectModelDetails) {
        this.context = context;
        this.subjectModelDetailsArrayList = subjectModelDetails;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_subject, viewGroup, false);
        preferences = context.getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final SubjectModelDetails subjectModelDetails = subjectModelDetailsArrayList.get(i);
        myViewHolder.tv_subject.setText(subjectModelDetails.getSubject());

        Picasso.get()
                .load(subjectthumbnails + subjectModelDetails.getThumbnail())
                .error(R.drawable.ic_empty)
                .placeholder(R.drawable.splashlogo)
                .into(myViewHolder.sub_img);


        myViewHolder.ll_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, SubjectWiseTeacherActivity.class);
                editor.putString("subject_Id", subjectModelDetails.getId()).apply();
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return subjectModelDetailsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_subject, tv_subject2;
        ImageView sub_img;
        LinearLayout ll_subject;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ll_subject = itemView.findViewById(R.id.ll_subject);
            tv_subject = itemView.findViewById(R.id.tv_subject);
            sub_img = itemView.findViewById(R.id.sub_img);


        }


    }
}