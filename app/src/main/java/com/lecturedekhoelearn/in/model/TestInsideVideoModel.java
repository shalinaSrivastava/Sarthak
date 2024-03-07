package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestInsideVideoModel {

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("data")
    @Expose

    private List<TestInsideVideoDetailsModel> testInsideVideoDetailsModels = null;

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

    public List<TestInsideVideoDetailsModel> getTestInsideVideoDetailsModels() {
        return testInsideVideoDetailsModels;
    }

    public void setTestInsideVideoDetailsModels(List<TestInsideVideoDetailsModel> testInsideVideoDetailsModels) {
        this.testInsideVideoDetailsModels = testInsideVideoDetailsModels;
    }







}
