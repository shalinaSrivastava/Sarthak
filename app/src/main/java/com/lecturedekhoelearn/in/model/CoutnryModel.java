package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CoutnryModel {


    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("Country_detail")
    @Expose
    private List<CountryDetails> countryDetails = null;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<CountryDetails> getCountryDetails() {
        return countryDetails;
    }

    public void setCountryDetails(List<CountryDetails> countryDetails) {
        this.countryDetails = countryDetails;
    }
}
