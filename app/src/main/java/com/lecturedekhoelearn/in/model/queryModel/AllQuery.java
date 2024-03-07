package com.lecturedekhoelearn.in.model.queryModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllQuery {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("Query")
    @Expose
    private List<AllQueryDetails> getDetails = null;

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

    public List<AllQueryDetails> getGetDetails() {
        return getDetails;
    }

    public void setGetDetails(List<AllQueryDetails> getDetails) {
        this.getDetails = getDetails;
    }
}
