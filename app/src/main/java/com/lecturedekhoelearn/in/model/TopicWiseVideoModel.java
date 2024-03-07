package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopicWiseVideoModel {

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("data")
    @Expose
    private List<TopicWiseVideoDetailsModel> topicWiseVideoDetailsModels = null;



    @SerializedName("premiumVideo")
    @Expose
    private List<PreemiemVideosDetails> preemiemVideosDetails = null;


    public List<PreemiemVideosDetails> getPreemiemVideosDetails() {
        return preemiemVideosDetails;
    }

    public void setPreemiemVideosDetails(List<PreemiemVideosDetails> preemiemVideosDetails) {
        this.preemiemVideosDetails = preemiemVideosDetails;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<TopicWiseVideoDetailsModel> getTopicWiseVideoDetailsModels() {
        return topicWiseVideoDetailsModels;
    }

    public void setTopicWiseVideoDetailsModels(List<TopicWiseVideoDetailsModel> topicWiseVideoDetailsModels) {
        this.topicWiseVideoDetailsModels = topicWiseVideoDetailsModels;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
