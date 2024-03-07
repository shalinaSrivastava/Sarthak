package com.lecturedekhoelearn.in.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.BuildConfig;
import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.model.InviteData;

import java.util.ArrayList;
import java.util.List;

public class InviteCardAdapter extends RecyclerView.Adapter<InviteCardAdapter.MyViewHolder> {

    List<InviteData> horizontalList = new ArrayList<>();
    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String coupon;

    public InviteCardAdapter(List<InviteData> horizontalList, Context context) {
        this.horizontalList = horizontalList;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cv_subject;
        TextView invite_title;
        ImageView img_subject;

        public MyViewHolder(View view) {
            super(view);
            invite_title = itemView.findViewById(R.id.invite_title);
            img_subject = itemView.findViewById(R.id.img_subject);
            cv_subject = itemView.findViewById(R.id.cv_subject);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_invitecard, parent, false);

        preferences = context.getSharedPreferences(Constant.myprif, Context.MODE_PRIVATE);
        editor = preferences.edit();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.img_subject.setImageResource(horizontalList.get(position).imageId);
        holder.invite_title.setText(horizontalList.get(position).txt);
        coupon = preferences.getString("refer", "");

        holder.cv_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "LectureDekho");
                    String shareMessage =  "\n Use referral code:"+coupon+ ",for Cashback \n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    context.startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });


    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }
}