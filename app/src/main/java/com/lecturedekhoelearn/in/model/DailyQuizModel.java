package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyQuizModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<DailyQuizModelDetails> dailyQuizModelDetails = null;


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


    public List<DailyQuizModelDetails> getDailyQuizModelDetails() {
        return dailyQuizModelDetails;
    }

    public void setDailyQuizModelDetails(List<DailyQuizModelDetails> dailyQuizModelDetails) {
        this.dailyQuizModelDetails = dailyQuizModelDetails;
    }
}
