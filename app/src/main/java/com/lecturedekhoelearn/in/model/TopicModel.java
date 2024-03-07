package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopicModel {



    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("Topic")
    @Expose
    private List<TopicModelDetails> videoTopicDetails = null;



    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<TopicModelDetails> getVideoTopicDetails() {
        return videoTopicDetails;
    }

    public void setVideoTopicDetails(List<TopicModelDetails> videoTopicDetails) {
        this.videoTopicDetails = videoTopicDetails;
    }
}
