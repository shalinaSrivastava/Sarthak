package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookmarkDetailsModel {

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("Bookmark")
    @Expose
    private List<ShowBookMarkModelDetails> showBookMarkModelDetails = null;


    public List<ShowBookMarkModelDetails> getShowBookMarkModelDetails() {
        return showBookMarkModelDetails;
    }

    public void setShowBookMarkModelDetails(List<ShowBookMarkModelDetails> showBookMarkModelDetails) {
        this.showBookMarkModelDetails = showBookMarkModelDetails;
    }

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
}