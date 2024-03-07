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
import com.lecturedekhoelearn.in.activity.ScholarshipResultActivity;
import com.lecturedekhoelearn.in.model.ScholarShipModelDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;
import static com.lecturedekhoelearn.in.Constant.scholarship;

public class UserScholarAdapter extends RecyclerView.Adapter<UserScholarAdapter.MyViewHolder> {
    Context context;
    MyViewHolder myViewHolder;
    LayoutInflater inflater;
    ArrayList<ScholarShipModelDetails> teacherListDetailsModelArrayList;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public UserScholarAdapter(Context context, ArrayList<ScholarShipModelDetails> teacherListDetailsModels) {
        this.context = context;
        this.teacherListDetailsModelArrayList = teacherListDetailsModels;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_scholarship_list, viewGroup, false);
        preferences = context.getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final ScholarShipModelDetails teacherListDetailsModel = teacherListDetailsModelArrayList.get(i);
        myViewHolder.tv_name.setText(teacherListDetailsModel.getTitle());

        Picasso.get()
                .load(scholarship + teacherListDetailsModel.getBanner())
                .error(R.drawable.ic_empty)
                .placeholder(R.drawable.splashlogo)
                .into((myViewHolder.image));


        myViewHolder.teacher_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ScholarshipResultActivity.class);
                editor.putString("banner", teacherListDetailsModel.getBanner()).apply();
                editor.putString("sch_id", teacherListDetailsModel.getId()).apply();
                editor.putString("title", teacherListDetailsModel.getTitle()).apply();
                editor.putString("desc", teacherListDetailsModel.getDescription()).apply();
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