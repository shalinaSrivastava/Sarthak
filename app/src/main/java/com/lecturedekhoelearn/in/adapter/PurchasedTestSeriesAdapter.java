package com.lecturedekhoelearn.in.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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
import com.lecturedekhoelearn.in.model.MyPurchasedTestSeriesModelDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.lecturedekhoelearn.in.Constant.packgeThumbnailpath;

public class PurchasedTestSeriesAdapter extends RecyclerView.Adapter<PurchasedTestSeriesAdapter.MyViewHolder> {
    Context context;
    private MyViewHolder myViewHolder;
    private LayoutInflater inflater;
    private ArrayList<MyPurchasedTestSeriesModelDetails> myTestSeriesModelDetailsArrayList;
    private String packageName, studentId, packageId;
    private String discountAmount;
    private String actualAmount;
    private String type;
    private String cashback;
    private int discountprice;
    private int accurateDicount;
    private String numberofDays;


    public PurchasedTestSeriesAdapter(Context context, ArrayList<MyPurchasedTestSeriesModelDetails> myTestSeriesModelDetailsArray) {
        this.context = context;
        this.myTestSeriesModelDetailsArrayList = myTestSeriesModelDetailsArray;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_purchased_test_series, viewGroup, false);
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final MyPurchasedTestSeriesModelDetails myTestSeriesModelDetails = myTestSeriesModelDetailsArrayList.get(i);

        packageName = myTestSeriesModelDetails.getName();
        actualAmount = myTestSeriesModelDetails.getPrice();
        type = myTestSeriesModelDetails.getType();
        studentId = myTestSeriesModelDetails.getStudent_id();
        packageId = myTestSeriesModelDetails.getPackage_id();
        discountAmount = myTestSeriesModelDetails.getDiscount();
        cashback = myTestSeriesModelDetails.getCashback();
        numberofDays = myTestSeriesModelDetails.getNo_of_days();
        myViewHolder.tv_package_name.setText(packageName);
        myViewHolder.tv_number_days.setText("Active for:-"+numberofDays +"Day");


        if (studentId != null && packageId != null) {
            myViewHolder.tv_package_status.setText("Active");

        }

        if (Integer.parseInt(discountAmount) > 0) {

            discountprice = Integer.parseInt(actualAmount) * Integer.parseInt(discountAmount) / 100;
            accurateDicount = Integer.parseInt(actualAmount) - discountprice;
            myViewHolder.tv_discount_price.setText("\u20B9" + String.valueOf(accurateDicount));
            myViewHolder.tv_actual_price.setText("\u20B9" + actualAmount);
            myViewHolder.tv_discount_per.setText(discountAmount + "% off");
            myViewHolder.tv_actual_price.setPaintFlags(myViewHolder.tv_actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        } else {
            myViewHolder.tv_discount_price.setText("");
            myViewHolder.tv_actual_price.setText("\u20B9" + actualAmount);
            myViewHolder.tv_discount_per.setText("");
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
            tv_actual_price = itemView.findViewById(R.id.tv_actual_price);
            tv_package_status = itemView.findViewById(R.id.tv_package_status);
            tv_discount_price = itemView.findViewById(R.id.tv_discount_price);
            tv_discount_per = itemView.findViewById(R.id.tv_discount_per);
            tv_cash_back = itemView.findViewById(R.id.tv_cash_back);
            tv_number_days = itemView.findViewById(R.id.tv_number_days);
        }
    }
}