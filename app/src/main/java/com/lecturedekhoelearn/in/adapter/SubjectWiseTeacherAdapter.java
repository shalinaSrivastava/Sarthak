package com.lecturedekhoelearn.in.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.queryActivity.UserAllQuery;
import com.lecturedekhoelearn.in.model.SubjectTeacherDetailsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;
import static com.lecturedekhoelearn.in.Constant.teacherProfilethubnails;

public class SubjectWiseTeacherAdapter extends RecyclerView.Adapter<SubjectWiseTeacherAdapter.MyViewHolder> {
    Context context;
    MyViewHolder myViewHolder;
    LayoutInflater inflater;
    ArrayList<SubjectTeacherDetailsModel> teacherListDetailsModelArrayList;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public SubjectWiseTeacherAdapter(Context context, ArrayList<SubjectTeacherDetailsModel> teacherListDetailsModels) {
        this.context = context;
        this.teacherListDetailsModelArrayList = teacherListDetailsModels;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_subject_teacher, viewGroup, false);
        preferences = context.getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final SubjectTeacherDetailsModel teacherListDetailsModel = teacherListDetailsModelArrayList.get(i);
        myViewHolder.tv_name.setText(teacherListDetailsModel.getName());

        Picasso.get()
                .load(teacherProfilethubnails + teacherListDetailsModel.getProfile_pic())
                .error(R.drawable.ic_empty)
                .placeholder(R.drawable.splashlogo)
                .into((myViewHolder.image));


        myViewHolder.teacher_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, UserAllQuery.class);
                editor.putString("teacher_id", teacherListDetailsModel.getId()).apply();
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return teacherListDetailsModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        CircleImageView image;
        CardView teacher_card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            teacher_card = itemView.findViewById(R.id.teacher_card);
            tv_name = itemView.findViewById(R.id.tv_name);
            image = itemView.findViewById(R.id.image);


        }


    }
}