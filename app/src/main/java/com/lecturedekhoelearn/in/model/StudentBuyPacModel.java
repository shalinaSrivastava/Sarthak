package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentBuyPacModel {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("Msg")
    @Expose
    private String Msg;

    @SerializedName("Detail")
    @Expose
    private List<BuyPackageModelDetails> buyPackageModelDetails = null;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public List<BuyPackageModelDetails> getBuyPackageModelDetails() {
        return buyPackageModelDetails;
    }

    public void setBuyPackageModelDetails(List<BuyPackageModelDetails> buyPackageModelDetails) {
        this.buyPackageModelDetails = buyPackageModelDetails;
    }
}