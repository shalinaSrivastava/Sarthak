package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StateModel {


    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("state_detail")
    @Expose
    private List<StateDetailsModel> stateDetailsModelts = null;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<StateDetailsModel> getStateDetailsModelts() {
        return stateDetailsModelts;
    }

    public void setStateDetailsModelts(List<StateDetailsModel> stateDetailsModelts) {
        this.stateDetailsModelts = stateDetailsModelts;
    }
}