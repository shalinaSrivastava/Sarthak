package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginUserModel {

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("msg")
    @Expose
    private String msg;


    @SerializedName("data")
    @Expose
    private LoginUserModelDetails loginUserModelDetails;

    public LoginUserModelDetails getLoginUserModelDetails() {
        return loginUserModelDetails;
    }

    public void setLoginUserModelDetails(LoginUserModelDetails loginUserModelDetails) {
        this.loginUserModelDetails = loginUserModelDetails;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }















}
