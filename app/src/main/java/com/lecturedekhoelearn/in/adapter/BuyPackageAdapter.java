package com.lecturedekhoelearn.in.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.PackageDetailsActivity;
import com.lecturedekhoelearn.in.model.BuyPackageModelDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.lecturedekhoelearn.in.Constant.packgeThumbnailpath;

public class BuyPackageAdapter extends RecyclerView.Adapter<BuyPackageAdapter.MyViewHolder> {
    private Context context;
    private MyViewHolder myViewHolder;
    private LayoutInflater inflater;
    private ArrayList<BuyPackageModelDetails> buyPackageModelDetailsArrayList;
    private String packageName, studentId, packageId;
    private String discountAmount;
    private String actualAmount;
    private String type;
    private String cashback;
    private int discountprice;
    private int accurateDicount;
    private String validity;
    private String description;
    public BuyPackageAdapter(Context context, ArrayList<BuyPackageModelDetails> buyPackageModelDetails) {
        this.context = context;
        this.buyPackageModelDetailsArrayList = buyPackageModelDetails;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_buy_packages, viewGroup, false);
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final BuyPackageModelDetails buyPackageModelDetails = buyPackageModelDetailsArrayList.get(i);

        packageName = buyPackageModelDetails.getName();
        actualAmount = buyPackageModelDetails.getPrice();
        type = buyPackageModelDetails.getType();
        studentId = buyPackageModelDetails.getStudent_id();
        packageId = buyPackageModelDetails.getPackage_id();
        discountAmount = buyPackageModelDetails.getDiscount();
        cashback = buyPackageModelDetails.getCashback();
        validity = buyPackageModelDetails.getValidities();
        description = buyPackageModelDetails.getDescription();

        myViewHolder.tv_package_name.setText(packageName);

        myViewHolder.tv_description.setText("Description: " + Html.fromHtml(description));

        if (Integer.parseInt(cashback) > 0) {
            myViewHolder.tv_cash_back.setVisibility(View.VISIBLE);
            myViewHolder.tv_cash_back.setText("Cashback:-" + "\u20B9" + cashback);
        } else {

            myViewHolder.tv_cash_back.setVisibility(View.GONE);
        }

        if (studentId == null || studentId.isEmpty() && packageId == null || packageId.isEmpty()) {
            if (type.equals("free")) {
                if (validity == null) {
                    myViewHolder.tv_validity.setText("Validity:- " + "15 Days");
                } else {
                    myViewHolder.tv_validity.setText("Validity:- " + validity);
                }
                myViewHolder.tv_package_active.setVisibility(View.VISIBLE);
                myViewHolder.tv_package_active.setText("Active Package");
                myViewHolder.tv_actual_price.setVisibility(View.GONE);
                myViewHolder.tv_video_access.setText("Video Access:-" + "Limited Video Access");
                myViewHolder.tv_test_series.setText("Test Series:-" + "No");
                myViewHolder.tv_Counselling.setText("Counselling:-" + "No");


            } else {
                if (validity == null) {
                    myViewHolder.tv_validity.setText("Validity:- " + "N/A");
                } else {
                    myViewHolder.tv_validity.setText("Validity:- " + validity);
                }
                myViewHolder.tv_package_status.setVisibility(View.VISIBLE);
                myViewHolder.tv_package_status.setText("Upgrade Package");
                myViewHolder.tv_actual_price.setVisibility(View.VISIBLE);
                myViewHolder.tv_actual_price.setText("\u20B9" + actualAmount);
                myViewHolder.tv_video_access.setText("Video Access:-" + "Unlimited Video Access");
                myViewHolder.tv_test_series.setText("Test Series:-" + "Yes");
                myViewHolder.tv_Counselling.setText("Counselling:-" + "Yes");
            }

        } else if (type.equals("paid")) {
           /* if (validity == null) {
                myViewHolder.tv_validity.setText("Validity:- " + "15 Days");
            } else {
                myViewHolder.tv_validity.setText("Validity:- " + validity);
            }
            myViewHolder.tv_package_active.setVisibility(View.VISIBLE);
            myViewHolder.tv_package_active.setText("Active Package");
            myViewHolder.tv_actual_price.setVisibility(View.GONE);
            myViewHolder.tv_video_access.setText("Video Access:-" + "Limited Video Access");
            myViewHolder.tv_test_series.setText("Test Series:-" + "No");
            myViewHolder.tv_Counselling.setText("Counselling:-" + "No");*/

                Intent i12 = new Intent(context, PackageDetailsActivity.class);
                i12.putExtra("pid", buyPackageModelDetails.getId());
            i12.putExtra("p_id", buyPackageModelDetails.getPackage_id());
            i12.putExtra("packageName", buyPackageModelDetails.getName());
                i12.putExtra("amount", buyPackageModelDetails.getPrice());
                i12.putExtra("image", buyPackageModelDetails.getThumbnail());
                i12.putExtra("des", buyPackageModelDetails.getDescription());
                i12.putExtra("testType", buyPackageModelDetails.getType());
                i12.putExtra("discount", buyPackageModelDetails.getDiscount());
                i12.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              //  i12.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i12);
              //((Activity)context).finish();
        }

        if (discountAmount != null && Integer.parseInt(discountAmount) > 0) {
            discountprice = Integer.parseInt(actualAmount) * Integer.parseInt(discountAmount) / 100;
            accurateDicount = Integer.parseInt(actualAmount) - discountprice;
            myViewHolder.tv_discount_price.setText("\u20B9" + String.valueOf(accurateDicount));
            myViewHolder.tv_discount_per.setText(discountAmount + "% off");
            myViewHolder.tv_actual_price.setPaintFlags(myViewHolder.tv_actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        } else {
            myViewHolder.tv_discount_price.setText("");
            myViewHolder.tv_discount_per.setText("");
        }

        Picasso.get()
                .load(packgeThumbnailpath + buyPackageModelDetails.getThumbnail())
                .error(R.drawable.ic_empty)
                .placeholder(R.drawable.splashlogo)
                .into((myViewHolder.p_image));

        if (packageId == null) {
        myViewHolder.cv_package.setOnClickListener(v -> {
            Intent i1 = new Intent(context, PackageDetailsActivity.class);
            i1.putExtra("pid", buyPackageModelDetails.getId());
            i1.putExtra("p_id", buyPackageModelDetails.getPackage_id());
            i1.putExtra("packageName", buyPackageModelDetails.getName());
            i1.putExtra("amount", buyPackageModelDetails.getPrice());
            i1.putExtra("image", buyPackageModelDetails.getThumbnail());
            i1.putExtra("des", buyPackageModelDetails.getDescription());
            i1.putExtra("testType", buyPackageModelDetails.getType());
            i1.putExtra("discount", buyPackageModelDetails.getDiscount());
            i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i1);

        });
    }
        if (packageId == null) {
            myViewHolder.tv_package_status.setOnClickListener(v -> {
                Intent i12 = new Intent(context, PackageDetailsActivity.class);
                i12.putExtra("pid", buyPackageModelDetails.getId());
                i12.putExtra("packageName", buyPackageModelDetails.getName());
                i12.putExtra("p_id", buyPackageModelDetails.getPackage_id());
                i12.putExtra("amount", buyPackageModelDetails.getPrice());
                i12.putExtra("image", buyPackageModelDetails.getThumbnail());
                i12.putExtra("des", buyPackageModelDetails.getDescription());
                i12.putExtra("testType", buyPackageModelDetails.getType());
                i12.putExtra("discount", buyPackageModelDetails.getDiscount());
                i12.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i12);
            });

        }

    }

    @Override
    public int getItemCount() {
        return buyPackageModelDetailsArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv_package;
        ImageView p_image;
        TextView tv_package_name, tv_description, tv_Counselling, tv_test_series, tv_video_access, tv_actual_price, tv_validity, tv_discount_price, tv_discount_per, tv_cash_back, tv_number_days;
        Button tv_package_status,tv_package_active;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cv_package = itemView.findViewById(R.id.cv_package);
            p_image = itemView.findViewById(R.id.p_image);
            tv_package_name = itemView.findViewById(R.id.tv_package_name);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_actual_price = itemView.findViewById(R.id.tv_actual_price);
            tv_package_status = itemView.findViewById(R.id.tv_package_status);
            tv_discount_price = itemView.findViewById(R.id.tv_discount_price);
            tv_discount_per = itemView.findViewById(R.id.tv_discount_per);
            tv_cash_back = itemView.findViewById(R.id.tv_cash_back);
            tv_validity = itemView.findViewById(R.id.tv_validity);
            tv_Counselling = itemView.findViewById(R.id.tv_Counselling);
            tv_test_series = itemView.findViewById(R.id.tv_test_series);
            tv_video_access = itemView.findViewById(R.id.tv_video_access);
            tv_package_active = itemView.findViewById(R.id.tv_package_active);


        }
    }
}