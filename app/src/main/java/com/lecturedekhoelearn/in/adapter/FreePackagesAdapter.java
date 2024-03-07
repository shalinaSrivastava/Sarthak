package com.lecturedekhoelearn.in.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.MyTestSeriesVideoActivity;
import com.lecturedekhoelearn.in.model.FreePackagesModelDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.lecturedekhoelearn.in.Constant.packgeThumbnailpath;


public class FreePackagesAdapter extends RecyclerView.Adapter<FreePackagesAdapter.MyViewHolder> {
    Context context;
    private MyViewHolder myViewHolder;
    private LayoutInflater inflater;
    private ArrayList<FreePackagesModelDetails> myTestSeriesModelDetailsArrayList;
    private String packageName, studentId, packageId;
    private String discountAmount;
    private String actualAmount;
    private String type;
    private String cashback;
    private int discountprice;
    private int accurateDicount;
    private String numberofDays;


    public FreePackagesAdapter(Context context, ArrayList<FreePackagesModelDetails> myTestSeriesModelDetailsArray) {
        this.context = context;
        this.myTestSeriesModelDetailsArrayList = myTestSeriesModelDetailsArray;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_free_packages, viewGroup, false);
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final FreePackagesModelDetails myTestSeriesModelDetails = myTestSeriesModelDetailsArrayList.get(i);

        packageName = myTestSeriesModelDetails.getName();
        actualAmount = myTestSeriesModelDetails.getPrice();
        type = myTestSeriesModelDetails.getType();
        myViewHolder.tv_package_name.setText(packageName);


        if(type.equals("free")){

            myViewHolder.tv_package_status.setText("Active");
        }


        Picasso.get()
                .load(packgeThumbnailpath + myTestSeriesModelDetails.getThumbnail())
                .error(R.drawable.ic_empty)
                .placeholder(R.drawable.splashlogo)
                .into((myViewHolder.p_image));


        myViewHolder.cv_package.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, MyTestSeriesVideoActivity.class);
                i.putExtra("pid", myTestSeriesModelDetails.getId());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return myTestSeriesModelDetailsArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv_package;
        ImageView p_image;
        TextView tv_package_name, tv_actual_price, tv_number_days, tv_package_status, tv_discount_price, tv_discount_per, tv_cash_back;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cv_package = itemView.findViewById(R.id.cv_package);
            p_image = itemView.findViewById(R.id.p_image);
            tv_package_name = itemView.findViewById(R.id.tv_package_name);
            tv_package_status = itemView.findViewById(R.id.tv_package_status);

        }
    }
}