package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScholarShipModel {


    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("detail")
    @Expose
    private List<ScholarShipModelDetails> scholarShipModelDetails = null;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ScholarShipModelDetails> getScholarShipModelDetails() {
        return scholarShipModelDetails;
    }

    public void setScholarShipModelDetails(List<ScholarShipModelDetails> scholarShipModelDetails) {
        this.scholarShipModelDetails = scholarShipModelDetails;
    }
}
