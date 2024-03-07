package com.lecturedekhoelearn.in.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.model.PaymentHistoryDetailsModel;

import java.util.ArrayList;


public class PaymentHistroyAdapter extends RecyclerView.Adapter<PaymentHistroyAdapter.MyViewHolder> {
    Context context;
    private MyViewHolder myViewHolder;
    private LayoutInflater inflater;
    private ArrayList<PaymentHistoryDetailsModel> paymentHistoryDetailsModelArrayList;

    private String ProductName;
    private String txnID;
    private String createdDate;

    public PaymentHistroyAdapter(Context context, ArrayList<PaymentHistoryDetailsModel> paymentHistoryDetailsModels) {
        this.context = context;
        this.paymentHistoryDetailsModelArrayList = paymentHistoryDetailsModels;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_payment_history, viewGroup, false);
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final PaymentHistoryDetailsModel paymentHistoryDetailsModel = paymentHistoryDetailsModelArrayList.get(i);
        txnID = paymentHistoryDetailsModel.getTxnid();
        ProductName = paymentHistoryDetailsModel.getProduct_name();
        createdDate = paymentHistoryDetailsModel.getCreated_at();
        myViewHolder.tv_product_name.setText(ProductName);
        myViewHolder.tv_txnId.setText(txnID);
        myViewHolder.tv_purchased_date.setText(createdDate);
        myViewHolder.tv_validity.setText("Valid till:- " + "31 March 2021");


    }


    @Override
    public int getItemCount() {
        return paymentHistoryDetailsModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv_order_history;
        TextView tv_product_name, tv_txnId, tv_purchased_date, tv_validity;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cv_order_history = itemView.findViewById(R.id.cv_order_history);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_txnId = itemView.findViewById(R.id.tv_txnId);
            tv_purchased_date = itemView.findViewById(R.id.tv_purchased_date);
            tv_validity = itemView.findViewById(R.id.tv_validity);

        }
    }
}