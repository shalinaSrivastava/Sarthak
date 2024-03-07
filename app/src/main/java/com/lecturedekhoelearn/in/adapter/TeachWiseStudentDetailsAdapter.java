package com.lecturedekhoelearn.in.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.MotiVationalVideoPlayerActivity;
import com.lecturedekhoelearn.in.activity.YoutubePlayerActivity;
import com.lecturedekhoelearn.in.model.MotivationalVideosDetails;
import com.lecturedekhoelearn.in.model.TeacherModel.TeacherWiseStudentDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.lecturedekhoelearn.in.Constant.motivationalThumbnails;
import static com.lecturedekhoelearn.in.Constant.teacherProfilethubnails;

public class TeachWiseStudentDetailsAdapter extends RecyclerView.Adapter<TeachWiseStudentDetailsAdapter.MyViewHolder> {
    Context context;
    MyViewHolder myViewHolder;
    LayoutInflater inflater;
    ArrayList<TeacherWiseStudentDetails> teacherWiseStudentDetails;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    PieDataSet dataSet;
    ArrayList<PieEntry> yvalues  = new ArrayList<>();

    public TeachWiseStudentDetailsAdapter(Context context, ArrayList<TeacherWiseStudentDetails> teacherProfilethubnails) {
        this.context = context;
        this.teacherWiseStudentDetails = teacherProfilethubnails;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        preferences = context.getSharedPreferences(Constant.myprif, Context.MODE_PRIVATE);
        editor = preferences.edit();
        View view = inflater.inflate(R.layout.custom_teacher_student, viewGroup, false);
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final TeacherWiseStudentDetails motivationalVideosDetails = teacherWiseStudentDetails.get(i);
        myViewHolder.pieChart.setUsePercentValues(true);
          myViewHolder.free_s.setText("Free Student : "+motivationalVideosDetails.getFree_student());
          myViewHolder.priminum_s.setText("Premium Student : "+motivationalVideosDetails.getPre_student());
          myViewHolder.total_s.setText("Total Student : "+motivationalVideosDetails.getTotal_student());
          myViewHolder.class_data.setText("Student Details of class "+motivationalVideosDetails.getClass_id());
         /* int pre=motivationalVideosDetails.getPre_student();
          int free=motivationalVideosDetails.getFree_student();
          yvalues.add(new PieEntry(pre,"premium_student"));
        yvalues.add(new PieEntry(free,"free_student"));
        //yvalues.add(new PieEntry(8f,"asdfasdfasdfas"));
                    dataSet = new PieDataSet(yvalues, "Student Data");
                    PieData datae = new PieData(dataSet);
                    // In Percentage term
                   // datae.setValueFormatter(new PercentFormatter());
                    // Default value
                    //data.setValueFormatter(new DefaultValueFormatter(0));
        myViewHolder.pieChart.setData(datae);
      //  dataSet.notifyDataSetChanged();  // update dataset for new values
        //myViewHolder.pieChart.notifyDataSetChanged();
        myViewHolder.pieChart.invalidate(); // refresh
                    //pieChart.setDescription("This is Pie Chart");
        myViewHolder.pieChart.setDrawHoleEnabled(true);
        myViewHolder.pieChart.setTransparentCircleRadius(25f);
        myViewHolder.pieChart.setHoleRadius(25f);
                    dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    datae.setValueTextSize(13f);
                    datae.setValueTextColor(Color.WHITE);
                    notifyDataSetChanged();*/

       // myViewHolder.pieChart.animateXY(1400, 1400);
    }

    @Override
    public int getItemCount() {
        return teacherWiseStudentDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv_videos;
        ImageView thumbnail;
        TextView class_data, total_s,free_s,priminum_s;
        PieChart pieChart;
        Button watch_now;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            class_data = itemView.findViewById(R.id.class_data);
            total_s = itemView.findViewById(R.id.total_s);
            free_s = itemView.findViewById(R.id.free_s);
            priminum_s = itemView.findViewById(R.id.priminum_s);
            pieChart=itemView.findViewById(R.id.pichart);
            // video_serial = itemView.findViewById(R.id.video_serial);

        }


    }
}