package com.lecturedekhoelearn.in.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.TeacherListActivity;
import com.lecturedekhoelearn.in.model.TeacherListDetailsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.lecturedekhoelearn.in.Constant.teacherProfilethubnails;

public class TeacherListAdapter extends RecyclerView.Adapter<TeacherListAdapter.MyViewHolder> {
    Context context;
    MyViewHolder myViewHolder;
    LayoutInflater inflater;
    ArrayList<TeacherListDetailsModel> teacherListDetailsModelArrayList;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String rate_us;

    public TeacherListAdapter(Context context, ArrayList<TeacherListDetailsModel> teacherListDetailsModels) {
        this.context = context;
        this.teacherListDetailsModelArrayList = teacherListDetailsModels;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_teachers, viewGroup, false);
        preferences = context.getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final TeacherListDetailsModel teacherListDetailsModel = teacherListDetailsModelArrayList.get(i);
        rate_us = teacherListDetailsModel.getRate_us();
        myViewHolder.tv_name.setText(teacherListDetailsModel.getName());
        //  myViewHolder.tv_sub.setText(teacherListDetailsModel.getEmail());
        Picasso.get()
                .load(teacherProfilethubnails + teacherListDetailsModel.getProfile_pic())
                .error(R.drawable.ic_empty)
                .placeholder(R.drawable.splashlogo)
                .into((myViewHolder.image));

        if (rate_us == null) {
        } else {
            myViewHolder.rate_img.setRating(Float.parseFloat(rate_us));

        }

        myViewHolder.tv_qualification.setText(teacherListDetailsModel.getAbout());
        myViewHolder.tv_phone.setText(teacherListDetailsModel.getWhatsapp());

        myViewHolder.teacher_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (rate_us != null) {

                    Intent i = new Intent(context, TeacherListActivity.class);
                    editor.putString("teachName", teacherListDetailsModel.getName()).apply();
                    editor.putString("teacher_id", teacherListDetailsModel.getId()).apply();
                    editor.putString("teacher_email", teacherListDetailsModel.getEmail()).apply();
                    editor.putString("teacher_rating", teacherListDetailsModel.getRate_us()).apply();
                    editor.putString("teacher_profile", teacherListDetailsModel.getProfile_pic()).apply();
                    editor.putString("teacherNumber", teacherListDetailsModel.getWhatsapp()).apply();
                    editor.putString("teacherAbout", teacherListDetailsModel.getAbout()).apply();
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);

                } else {
                    Intent i = new Intent(context, TeacherListActivity.class);
                    editor.putString("teacher_id", teacherListDetailsModel.getId()).apply();
                    editor.putString("teacher_email", teacherListDetailsModel.getEmail()).apply();
                    editor.putString("teacher_rating", teacherListDetailsModel.getRate_us()).apply();
                    editor.putString("teacher_profile", teacherListDetailsModel.getProfile_pic()).apply();
                    editor.putString("teacherNumber", teacherListDetailsModel.getWhatsapp()).apply();
                    editor.putString("teachName", teacherListDetailsModel.getName()).apply();
                    editor.putString("teacherAbout", teacherListDetailsModel.getAbout()).apply();
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }


            }
        });

    }


    @Override
    public int getItemCount() {
        return teacherListDetailsModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_qualification, tv_phone;
        ImageView image;
        CardView teacher_card;
        RatingBar rate_img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            teacher_card = itemView.findViewById(R.id.teacher_card);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            image = itemView.findViewById(R.id.image);
            rate_img = itemView.findViewById(R.id.rate_img);
            tv_qualification = itemView.findViewById(R.id.tv_qualification);


        }


    }
}