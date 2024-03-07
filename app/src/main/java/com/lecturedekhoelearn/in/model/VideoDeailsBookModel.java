package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoDeailsBookModel {

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("Bookmark")
    @Expose
    private List<VideoDataBookModel> videoDataBookModels = null;


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

    public List<VideoDataBookModel> getVideoDataBookModels() {
        return videoDataBookModels;
    }

    public void setVideoDataBookModels(List<VideoDataBookModel> videoDataBookModels) {
        this.videoDataBookModels = videoDataBookModels;
    }
}
