package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchModel {

    private String search;

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("Message")
    @Expose
    private String  Message;

    @SerializedName("topic")
    @Expose
    private List<SearchTopicModel> searchTopicModels = null;


    @SerializedName("video")
    @Expose
    private List<SearchVideoModel> searchVideoModels = null;


    public List<SearchTopicModel> getSearchTopicModels() {
        return searchTopicModels;
    }

    public void setSearchTopicModels(List<SearchTopicModel> searchTopicModels) {
        this.searchTopicModels = searchTopicModels;
    }

    public List<SearchVideoModel> getSearchVideoModels() {
        return searchVideoModels;
    }

    public void setSearchVideoModels(List<SearchVideoModel> searchVideoModels) {
        this.searchVideoModels = searchVideoModels;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
