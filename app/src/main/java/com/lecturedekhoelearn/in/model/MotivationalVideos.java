package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MotivationalVideos {

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("video")
    @Expose
    private List<MotivationalVideosDetails> motivationalVideosDetails = null;

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

    public List<MotivationalVideosDetails> getMotivationalVideosDetails() {
        return motivationalVideosDetails;
    }

    public void setMotivationalVideosDetails(List<MotivationalVideosDetails> motivationalVideosDetails) {
        this.motivationalVideosDetails = motivationalVideosDetails;
    }


}
