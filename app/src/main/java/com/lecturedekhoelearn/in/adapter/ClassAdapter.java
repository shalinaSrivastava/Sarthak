package com.lecturedekhoelearn.in.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.login.SignupOtpActivity;
import com.lecturedekhoelearn.in.model.ClassModelDetails;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.MyViewHolder> {
    Context context;
    MyViewHolder myViewHolder;
    LayoutInflater inflater;
    private ArrayList<ClassModelDetails> classModelDetailsArrayList;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public ClassAdapter(Context context, ArrayList<ClassModelDetails> classModelDetails) {
        this.context = context;
        this.classModelDetailsArrayList = classModelDetails;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_class_student, viewGroup, false);
        preferences = context.getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();

        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final ClassModelDetails classModelDetails = classModelDetailsArrayList.get(i);

        myViewHolder.tv_class.setText(classModelDetails.getClassName());

        myViewHolder.ll_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SignupOtpActivity.class);
                editor.putString("class_id", classModelDetails.getId()).apply();
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return classModelDetailsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_class;
        LinearLayout ll_class;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ll_class = itemView.findViewById(R.id.ll_class);
            tv_class = itemView.findViewById(R.id.tv_class);


        }


    }
}









/*extends RecyclerView.Adapter<ClassAdapter.MyViewHolder> {

    List<ClassModel> horizontalList = new ArrayList<>();

    Context context;


    public ClassAdapter(List<ClassModel> horizontalList, Context context) {
        this.horizontalList = horizontalList;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout cv_class;
        TextView tv_class;



        public MyViewHolder(View view) {
            super(view);
            cv_class = itemView.findViewById(R.id.cv_class);
            tv_class = itemView.findViewById(R.id.tv_class);

        }


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_class_student, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_class.setText(horizontalList.get(position).txt);
        holder.cv_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MainActivity.class);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return horizontalList.size();

    }*/
