package com.lecturedekhoelearn.in.adapter;

import android.content.Context;
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
import com.lecturedekhoelearn.in.model.SchResultModelDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;
import static com.lecturedekhoelearn.in.Constant.scholarship;

public class ScholarshipResultAdapter extends RecyclerView.Adapter<ScholarshipResultAdapter.MyViewHolder> {
    Context context;
    MyViewHolder myViewHolder;
    LayoutInflater inflater;
    ArrayList<SchResultModelDetails> teacherListDetailsModelArrayList;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public ScholarshipResultAdapter(Context context, ArrayList<SchResultModelDetails> teacherListDetailsModels) {
        this.context = context;
        this.teacherListDetailsModelArrayList = teacherListDetailsModels;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_scholar_result, viewGroup, false);
        preferences = context.getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final SchResultModelDetails teacherListDetailsModel = teacherListDetailsModelArrayList.get(i);
      //  myViewHolder.tv_name.setText(teacherListDetailsModel.getId());

        Picasso.get()
                .load(scholarship + teacherListDetailsModel.getResult())
                .error(R.drawable.ic_empty)
                .placeholder(R.drawable.splashlogo)
                .into((myViewHolder.image));


    }


    @Override
    public int getItemCount() {
        return teacherListDetailsModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
      //  TextView tv_name;
        ImageView image;
        CardView teacher_card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            teacher_card = itemView.findViewById(R.id.teacher_card);
         //   tv_name = itemView.findViewById(R.id.tv_name);
            image = itemView.findViewById(R.id.image);


        }


    }
}