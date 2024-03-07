package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherListDetailsModel {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("profile_pic")
    @Expose
    private String profile_pic;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("craeted_at")
    @Expose
    private String craeted_at;

    @SerializedName("rate_us")
    @Expose
    private String rate_us;

    @SerializedName("whatsapp")
    @Expose
    private String whatsapp;

    @SerializedName("about")
    @Expose
    private String about;


    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getRate_us() {
        return rate_us;
    }

    public void setRate_us(String rate_us) {
        this.rate_us = rate_us;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCraeted_at() {
        return craeted_at;
    }

    public void setCraeted_at(String craeted_at) {
        this.craeted_at = craeted_at;
    }
}
