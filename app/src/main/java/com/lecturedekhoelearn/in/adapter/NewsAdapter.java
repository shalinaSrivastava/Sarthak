package com.lecturedekhoelearn.in.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.joooonho.SelectableRoundedImageView;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.newsActivity;
import com.lecturedekhoelearn.in.model.News;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import static com.lecturedekhoelearn.in.Constant.newspath;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    Context context;
    MyViewHolder myViewHolder;
    LayoutInflater inflater;
    ArrayList<News> subNews;
    int tag;

    public NewsAdapter(Context context, ArrayList<News> subNews) {
        this.context = context;
        this.subNews = subNews;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.news, viewGroup, false);
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        this.myViewHolder = myViewHolder;

        myViewHolder.new_title.setText(subNews.get(position).getTitle());

        Picasso.get()
                .load(newspath.concat(subNews.get(position).getThumbnail()))
                .error(R.drawable.ic_empty)
                .placeholder(R.drawable.splashlogo)
                .into((myViewHolder.news_thumbnail));

        myViewHolder.cv_videos.setTag(position);



        myViewHolder.cv_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = (int) v.getTag();
                notifyDataSetChanged();

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
        RelativeLayout  cv_videos;
        SelectableRoundedImageView news_thumbnail;
        TextView new_title;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cv_videos = itemView.findViewById(R.id.cv_videos);
            news_thumbnail = itemView.findViewById(R.id.new_thumbnails);
            new_title = itemView.findViewById(R.id.news_title);

        }


    }


}
