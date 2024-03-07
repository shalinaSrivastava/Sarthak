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
import com.lecturedekhoelearn.in.login.CategoryActivity;
import com.lecturedekhoelearn.in.model.LevelTypeModelDetails;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SocietyLevelAdapter extends RecyclerView.Adapter<SocietyLevelAdapter.MyViewHolder> {
    Context context;
    MyViewHolder myViewHolder;
    LayoutInflater inflater;
    ArrayList<LevelTypeModelDetails> levelTypeModelDetailsArrayList;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public SocietyLevelAdapter(Context context, ArrayList<LevelTypeModelDetails> levelTypeModelDetails) {
        this.context = context;
        this.levelTypeModelDetailsArrayList = levelTypeModelDetails;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_society, viewGroup, false);
        preferences = context.getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();

        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final LevelTypeModelDetails levelTypeModelDetails = levelTypeModelDetailsArrayList.get(i);

        myViewHolder.tv_level.setText(levelTypeModelDetails.getLevel());

        myViewHolder.ll_school_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, CategoryActivity.class);
                editor.putString("level_id", levelTypeModelDetails.getId()).apply();
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);


            }
        });


    }

    @Override
    public int getItemCount() {
        return levelTypeModelDetailsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_level;
        ImageView img_level;
        LinearLayout ll_school_level;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ll_school_level = itemView.findViewById(R.id.ll_school_level);
            tv_level = itemView.findViewById(R.id.tv_level);
            img_level = itemView.findViewById(R.id.img_level);


        }


    }
}