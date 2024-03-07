package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterModel {


    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("user_id")
    @Expose
    private String userId;


    @SerializedName("data")
    @Expose
    RegisterModelDetails registerModelDetails;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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


    public RegisterModelDetails getRegisterModelDetails() {
        return registerModelDetails;
    }

    public void setRegisterModelDetails(RegisterModelDetails registerModelDetails) {
        this.registerModelDetails = registerModelDetails;
    }
}
