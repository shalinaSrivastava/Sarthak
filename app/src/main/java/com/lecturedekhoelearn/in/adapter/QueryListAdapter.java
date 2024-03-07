package com.lecturedekhoelearn.in.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.model.queryModel.Query_Details;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.lecturedekhoelearn.in.Constant.queryImagePath;


public class QueryListAdapter extends RecyclerView.Adapter<QueryListAdapter.MyViewHolder> {
    Context context;
    MyViewHolder myViewHolder;
    LayoutInflater inflater;
    ArrayList<Query_Details> qry;

    public QueryListAdapter(Context context, ArrayList<Query_Details> qry) {
        this.context = context;
        this.qry = qry;
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
        final Query_Details list = qry.get(i);
        myViewHolder.title.setText(list.getTitle());
        myViewHolder.query.setText(list.getQuery());

        String querryImage = list.getDocument();

        if (querryImage != null && !querryImage.isEmpty()) {
            myViewHolder.querry_image.setVisibility(View.VISIBLE);
            Picasso.get().load(queryImagePath + querryImage).placeholder(R.drawable.profile_pic_place_holder)// Place holder image from drawable folder
                    .error(R.drawable.profile_pic_place_holder)
                    .into(myViewHolder.querry_image);

        } else {
            myViewHolder.querry_image.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return qry.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, query;

        ImageView querry_image;

        public MyViewHolder(@NonNull View view) {
            super(view);
            title = itemView.findViewById(R.id.title);
            query = itemView.findViewById(R.id.query);
            query = itemView.findViewById(R.id.query);
            querry_image = itemView.findViewById(R.id.querry_image);
        }
    }
}
