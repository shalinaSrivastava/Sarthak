package com.lecturedekhoelearn.in.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.queryActivity.UserQueryDiscussion;
import com.lecturedekhoelearn.in.model.queryModel.AllQueryDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.lecturedekhoelearn.in.Constant.queryImagePath;

public class AllQueryAdapter extends RecyclerView.Adapter<AllQueryAdapter.MyViewHolder> {
    Context context;
    MyViewHolder myViewHolder;
    LayoutInflater inflater;
    ArrayList<AllQueryDetails> allQueryDetails;
    String id;

    public AllQueryAdapter(Context context, ArrayList<AllQueryDetails> allQueryDetails) {
        this.context = context;
        this.allQueryDetails = allQueryDetails;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.query_card, viewGroup, false);
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final AllQueryDetails list = allQueryDetails.get(i);
        myViewHolder.name.setText(list.getTitle());
        myViewHolder.query.setText(list.getQuery());
        myViewHolder.query_time.setText(list.getCreated_at());

        String querryImage = list.getDocument();

        if (querryImage != null && !querryImage.isEmpty()) {
            myViewHolder.querry_image.setVisibility(View.VISIBLE);

            Picasso.get().load(queryImagePath + querryImage).placeholder(R.drawable.profile_pic_place_holder)
                    .error(R.drawable.profile_pic_place_holder)
                    .into(myViewHolder.querry_image);
        } else {

            myViewHolder.querry_image.setVisibility(View.GONE);
        }

        myViewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserQueryDiscussion.class);
                intent.putExtra("query_id", "" + list.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allQueryDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, query, query_time;
        LinearLayout card;
        ImageView querry_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.title);
            query = itemView.findViewById(R.id.query);
            card = itemView.findViewById(R.id.card);
            query_time = itemView.findViewById(R.id.query_time);
            querry_image = itemView.findViewById(R.id.querry_image);
        }
    }
}
