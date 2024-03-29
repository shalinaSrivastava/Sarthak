package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentHistroyModel {

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("Data")
    @Expose
    private List<PaymentHistoryDetailsModel> paymentHistoryDetailsModels;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<PaymentHistoryDetailsModel> getPaymentHistoryDetailsModels() {
        return paymentHistoryDetailsModels;
    }

    public void setPaymentHistoryDetailsModels(List<PaymentHistoryDetailsModel> paymentHistoryDetailsModels) {
        this.paymentHistoryDetailsModels = paymentHistoryDetailsModels;
    }
}
