package com.lecturedekhoelearn.in.adapter;

import android.content.Context;
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
import com.lecturedekhoelearn.in.model.NotificationDetailsModel;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SeeAllNotificationAdapter extends RecyclerView.Adapter<SeeAllNotificationAdapter.MyViewHolder> {
    Context context;
    ArrayList<NotificationDetailsModel> studentTestDetails;
    LayoutInflater inflater;
    MyViewHolder myViewHolder;
    private String type;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public int num = 1;

    String Roll_id;

    public SeeAllNotificationAdapter(Context context, ArrayList<NotificationDetailsModel> studentTestDetails) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.studentTestDetails = studentTestDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_notification, viewGroup, false);
        this.myViewHolder = new MyViewHolder(view);
        preferences = context.getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final NotificationDetailsModel notificationDetailsModel = studentTestDetails.get(i);
        Roll_id = preferences.getString("role_idd", "");
        myViewHolder.notification_title.setText(notificationDetailsModel.getTitle());
        myViewHolder.notification_details.setText(notificationDetailsModel.getDescription());
    }

    @Override
    public int getItemCount() {
        // return studentTestDetails.size();
        if (num * 30 > studentTestDetails.size()) {
            return studentTestDetails.size();
        } else {
            return num * 30;
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView notification_title, notification_details;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.test_series_card);
            notification_title = itemView.findViewById(R.id.notification_title);
            notification_details = itemView.findViewById(R.id.notification_details);
        }
    }
}