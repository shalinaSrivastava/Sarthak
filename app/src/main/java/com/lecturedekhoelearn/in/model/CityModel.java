package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityModel {


    @SerializedName("status")
    @Expose
    private Integer status;


    @SerializedName("city_detail")
    @Expose
    private List<CityModelDetails> cityModelDetails = null;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<CityModelDetails> getCityModelDetails() {
        return cityModelDetails;
    }

    public void setCityModelDetails(List<CityModelDetails> cityModelDetails) {
        this.cityModelDetails = cityModelDetails;
    }
}
