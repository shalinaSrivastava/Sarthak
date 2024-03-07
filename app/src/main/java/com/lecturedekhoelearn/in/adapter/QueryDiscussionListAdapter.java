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
import com.lecturedekhoelearn.in.model.queryModel.Query_Descussion_Details;

import java.util.ArrayList;


public class QueryDiscussionListAdapter extends RecyclerView.Adapter<QueryDiscussionListAdapter.MyViewHolder> {
    Context context;
    SharedPreferences preferences;
    MyViewHolder myViewHolder;
    LayoutInflater inflater;
    ArrayList<Query_Descussion_Details> qry;

    public QueryDiscussionListAdapter(Context context, ArrayList<Query_Descussion_Details> qry) {
        this.context = context;
        this.qry = qry;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.query_descussion_card, viewGroup, false);
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final Query_Descussion_Details list = qry.get(i);

        preferences = context.getSharedPreferences(Constant.myprif, Context.MODE_PRIVATE);

        String User_id  = preferences.getString("studentId", "");
        String uid = list.getStudent_id();
        if (User_id.equals(uid)) {
            myViewHolder.descussion.setText(list.getReply());
            myViewHolder.descussion_time.setText(list.getCreated_at());
            myViewHolder.userreply.setVisibility(View.VISIBLE);
            myViewHolder.adminreply.setVisibility(View.GONE);
        } else {
            myViewHolder.descussion_reply.setText(list.getReply());
            myViewHolder.descussion_time.setText(list.getCreated_at());
            myViewHolder.adminreply.setVisibility(View.VISIBLE);
            myViewHolder.userreply.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return qry.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView descussion, descussion_reply,descussion_time;
        LinearLayout userreply, adminreply;

        public MyViewHolder(@NonNull View view) {
            super(view);
            descussion = itemView.findViewById(R.id.descussion);
            descussion_reply = itemView.findViewById(R.id.descussion_reply);
            userreply = itemView.findViewById(R.id.userreply);
            adminreply = itemView.findViewById(R.id.adminreply);
            descussion_time = itemView.findViewById(R.id.descussion_time);
        }
    }
}
