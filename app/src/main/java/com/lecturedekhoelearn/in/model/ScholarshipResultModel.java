package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScholarshipResultModel {

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("Detail")
    @Expose
    private List<SchResultModelDetails> schResultModelDetailsList = null;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public List<SchResultModelDetails> getSchResultModelDetailsList() {
        return schResultModelDetailsList;
    }

    public void setSchResultModelDetailsList(List<SchResultModelDetails> schResultModelDetailsList) {
        this.schResultModelDetailsList = schResultModelDetailsList;
    }
}