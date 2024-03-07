package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Testj {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("Detail")
    @Expose
    private List<TestJDetail> getTestDetail = null;


    public List<TestJDetail> getGetTestDetail() {
        return getTestDetail;
    }

    public void setGetTestDetail(List<TestJDetail> getTestDetail) {
        this.getTestDetail = getTestDetail;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
