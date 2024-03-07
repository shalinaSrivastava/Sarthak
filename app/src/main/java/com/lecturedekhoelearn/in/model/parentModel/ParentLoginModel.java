package com.lecturedekhoelearn.in.model.parentModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParentLoginModel {


    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("Msg")
    @Expose
    private String Msg;


    @SerializedName("Detail")
    @Expose
    private ParentLoginDetails parentLoginDetails;


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

    public ParentLoginDetails getParentLoginDetails() {
        return parentLoginDetails;
    }

    public void setParentLoginDetails(ParentLoginDetails parentLoginDetails) {
        this.parentLoginDetails = parentLoginDetails;
    }
}
