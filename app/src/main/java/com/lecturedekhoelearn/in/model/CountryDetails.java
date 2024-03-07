package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryDetails {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("status")
    @Expose
    private String status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}