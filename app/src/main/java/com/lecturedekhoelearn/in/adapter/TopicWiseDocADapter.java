package com.lecturedekhoelearn.in.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.BuyTestSeries;
import com.lecturedekhoelearn.in.activity.parentActivity.Video_player;
import com.lecturedekhoelearn.in.model.TopicWiseDocModel;
import com.lecturedekhoelearn.in.model.TopicWiseVideoDetailsModel;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static com.lecturedekhoelearn.in.Constant.packageVideothumbnail;

public class TopicWiseDocADapter extends RecyclerView.Adapter<TopicWiseDocADapter.MyViewHolder> {
    Context context;
    MyViewHolder myViewHolder;
    LayoutInflater inflater;
    ArrayList<TopicWiseDocModel> packageVideoDetailsModelArrayList;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private ProgressDialog pDialog;
    String pdf_dir="";
    private static ClickListener clickListener;

    public TopicWiseDocADapter(Context context, ArrayList<TopicWiseDocModel> subVideos) {
        this.context = context;
        this.packageVideoDetailsModelArrayList = subVideos;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_doc, viewGroup, false);
        preferences = context.getSharedPreferences(Constant.myprif, Context.MODE_PRIVATE);
        editor = preferences.edit();
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final TopicWiseDocModel subVideo = packageVideoDetailsModelArrayList.get(i);
        myViewHolder.file_name.setText(subVideo.getTitle());
        myViewHolder.file_name_hide.setText(subVideo.getTitle());
        Picasso.get()
                .load(packageVideothumbnail + subVideo.getThumbnail())
                .error(R.drawable.pdf_24dp)
                .placeholder(R.drawable.pdf_24dp)
                .into((myViewHolder.thumbnail_image));
        Picasso.get()
                .load(packageVideothumbnail + subVideo.getThumbnail())
                .error(R.drawable.pdf_24dp)
                .placeholder(R.drawable.pdf_24dp)
                .into((myViewHolder.thumb_image_hide));
        try {
            if (subVideo.getPackage_type().equals("1")){
                myViewHolder.blur_card.setVisibility(View.VISIBLE);
                myViewHolder.doc_card.setVisibility(View.GONE);
            }else {
                myViewHolder.doc_card.setVisibility(View.VISIBLE);
                myViewHolder.blur_card.setVisibility(View.GONE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }






    @Override
    public int getItemCount() {
        return packageVideoDetailsModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        LinearLayout doc_card,blur_card;
        ImageView thumbnail_image,thumb_image_hide;
        TextView file_name,file_name_hide;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            doc_card = itemView.findViewById(R.id.file_lay);
            blur_card=itemView.findViewById(R.id.file_lay_b);
            thumb_image_hide=itemView.findViewById(R.id.thumbnail_image_b);
            file_name_hide=itemView.findViewById(R.id.file_name_b);
            thumbnail_image=itemView.findViewById(R.id.thumbnail_image);
            file_name=itemView.findViewById(R.id.file_name);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }

    }

    public void setOnItemClickListener(ClickListener clickListener) {
        TopicWiseDocADapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }


}