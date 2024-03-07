package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateDetailsModel {


    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("country_id")
    @Expose
    private String country_id;

    @SerializedName("state")
    @Expose
    private String state;


    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("created_at")
    @Expose
    private String created_at;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
