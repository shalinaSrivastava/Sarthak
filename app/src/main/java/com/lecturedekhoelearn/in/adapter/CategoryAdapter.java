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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.login.ChildOtpActivity;
import com.lecturedekhoelearn.in.login.ClassVerified;

import com.lecturedekhoelearn.in.model.CategoryModelDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    Context context;
    MyViewHolder myViewHolder;
    LayoutInflater inflater;
    ArrayList<CategoryModelDetails> categoryModelDetailsArrayList;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String img_url = " ";

    public CategoryAdapter(Context context, ArrayList<CategoryModelDetails> categoryModelDetails) {
        this.context = context;
        this.categoryModelDetailsArrayList = categoryModelDetails;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_category, viewGroup, false);
        preferences = context.getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();

        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final CategoryModelDetails categoryModelDetails = categoryModelDetailsArrayList.get(i);

        String images = categoryModelDetails.getCat_pic();
        myViewHolder.tv_category.setText(categoryModelDetails.getCategory());

        if (i == 0) {
            if (images == null) {
                Picasso.get().load(images).placeholder(R.drawable.ic_school)// Place holder image from drawable folder
                        .error(R.drawable.ic_school)
                        .into(myViewHolder.img_category);
            }

        } else if (i == 1) {

            if (images == null) {
                Picasso.get().load(images).placeholder(R.drawable.ic_parent)// Place holder image from drawable folder
                        .error(R.drawable.ic_teacher)
                        .into(myViewHolder.img_category);
            }

        }
        myViewHolder.ll_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (i == 0) {
                    Intent i = new Intent(context, ClassVerified.class);
                    editor.putString("category_id", categoryModelDetails.getId()).apply();
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);

                } else if (i == 1) {
                    Intent i = new Intent(context, ChildOtpActivity.class);
                    editor.putString("category_id", categoryModelDetails.getId()).apply();
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);

                } else {

                    Toast.makeText(context, "Category not found", Toast.LENGTH_SHORT).show();

                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryModelDetailsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_category;
        ImageView img_category;
        LinearLayout ll_category;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ll_category = itemView.findViewById(R.id.ll_category);
            tv_category = itemView.findViewById(R.id.tv_category);
            img_category = itemView.findViewById(R.id.img_category);


        }


    }
}