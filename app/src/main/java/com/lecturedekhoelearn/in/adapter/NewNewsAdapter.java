package com.lecturedekhoelearn.in.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.newsActivity;
import com.lecturedekhoelearn.in.model.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.lecturedekhoelearn.in.Constant.newspath;

public class NewNewsAdapter extends RecyclerView.Adapter<NewNewsAdapter.MyViewHolder> {
    Context context;
    MyViewHolder myViewHolder;
    LayoutInflater inflater;
    ArrayList<News> subNews;
    int tag;

    public NewNewsAdapter(Context context, ArrayList<News> subNews) {
        this.context = context;
        this.subNews = subNews;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.new_layout_news, viewGroup, false);
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        this.myViewHolder = myViewHolder;

        myViewHolder.name.setText(subNews.get(position).getTitle());
        myViewHolder.timestamp.setText(subNews.get(position).getCreated_at());


        Picasso.get()
                .load(newspath.concat(subNews.get(position).getThumbnail()))
                .error(R.drawable.ic_empty)
                .placeholder(R.drawable.splashlogo)
                .into((myViewHolder.profilePic));

            myViewHolder.feedImage1.setVisibility(View.GONE);
            myViewHolder.txtStatusMsg.setEllipsize(TextUtils.TruncateAt.END);
            myViewHolder.txtStatusMsg.setText(subNews.get(position).getDescription());



        myViewHolder.ll_current_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, newsActivity.class);
                intent.putExtra("imagelink", subNews.get(position).getThumbnail());
                intent.putExtra("newsdesc", subNews.get(position).getDescription());
                intent.putExtra("newstitle", subNews.get(position).getTitle());
                intent.putExtra("newstime", subNews.get(position).getCreated_at());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return subNews.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, timestamp, txtStatusMsg;
        ImageView profilePic, feedImage1;
        LinearLayout ll_current_news;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.profilePic);
            feedImage1 = itemView.findViewById(R.id.feedImage1);
            name = itemView.findViewById(R.id.name);
            timestamp = itemView.findViewById(R.id.timestamp);
            txtStatusMsg = itemView.findViewById(R.id.txtStatusMsg);
            ll_current_news = itemView.findViewById(R.id.ll_current_news);

        }


    }


}
