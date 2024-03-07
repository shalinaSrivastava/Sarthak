package com.lecturedekhoelearn.in.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.StartTest;
import com.lecturedekhoelearn.in.model.TestInsideVideoDetailsModel;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class TestSeriesInsideVideoAdapter extends RecyclerView.Adapter<TestSeriesInsideVideoAdapter.MyViewHolder> {

    Context context;
    private MyViewHolder myViewHolder;
    private LayoutInflater inflater;
    private ArrayList<TestInsideVideoDetailsModel> testInsideVideoDetailsModelArrayList;
    private String testName, time, total_marks, total_question, subject, topics, level;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    public TestSeriesInsideVideoAdapter(Activity context, ArrayList<TestInsideVideoDetailsModel> testInsideVideoDetailsModels) {
        this.context = context;
        this.testInsideVideoDetailsModelArrayList = testInsideVideoDetailsModels;
        this.inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_video_test_series, viewGroup, false);
        preferences = context.getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final TestInsideVideoDetailsModel allTestPacakgeDetails = testInsideVideoDetailsModelArrayList.get(i);

        testName = allTestPacakgeDetails.getName();
        time = allTestPacakgeDetails.getTime();
        total_question = allTestPacakgeDetails.getTotal_questions();
        total_marks = allTestPacakgeDetails.getTotal_marks();
        subject = allTestPacakgeDetails.getStart_date();
        topics = allTestPacakgeDetails.getEnd_date();
        level = allTestPacakgeDetails.getLevel_id();

        myViewHolder.tv_test_name.setText(testName);
        myViewHolder.tv_time.setText("Time:- " + time + "min.");
        myViewHolder.tv_marks.setText("Total Marks:- " + total_marks);
        myViewHolder.tv_question_no.setText("Total Question:- " + total_question);


        if (level.equals("1")) {

            myViewHolder.tv_level.setText("Easy");
        } else {
            myViewHolder.tv_level.setText("Hard");

        }


        myViewHolder.cv_package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(context, StartTest.class);
                editor.putString("testduration", time).apply();
                editor.putString("test_id", allTestPacakgeDetails.getId()).apply();
                editor.putString("testduration", time).apply();
                editor.putString("testName", allTestPacakgeDetails.getName()).apply();
                editor.putString("noQ", allTestPacakgeDetails.getTotal_questions()).apply();
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);


            }
        });


    }

    @Override
    public int getItemCount() {
        return testInsideVideoDetailsModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv_package;
        ImageView p_image;
        TextView tv_test_name, tv_question_no, tv_level, tv_marks, tv_time;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cv_package = itemView.findViewById(R.id.cv_package);
            p_image = itemView.findViewById(R.id.p_image);
            tv_test_name = itemView.findViewById(R.id.tv_test_name);
            tv_level = itemView.findViewById(R.id.tv_level);
            tv_time = itemView.findViewById(R.id.question_time);
            tv_question_no = itemView.findViewById(R.id.tv_question_no);
            tv_marks = itemView.findViewById(R.id.tv_marks);


        }
    }
}