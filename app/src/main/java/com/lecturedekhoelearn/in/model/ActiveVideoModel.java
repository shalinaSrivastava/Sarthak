package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActiveVideoModel {


    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("Bookmark")
    @Expose

    private List<ActiveVideoModelDetails> activeVideoModelDetails = null;


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

    public List<ActiveVideoModelDetails> getActiveVideoModelDetails() {
        return activeVideoModelDetails;
    }

    public void setActiveVideoModelDetails(List<ActiveVideoModelDetails> activeVideoModelDetails) {
        this.activeVideoModelDetails = activeVideoModelDetails;
    }
}
