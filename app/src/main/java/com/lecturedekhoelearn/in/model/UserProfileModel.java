package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileModel {
    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("Details")
    @Expose
    private UserProfileModelDetails userProfileModelDetails;

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

    public UserProfileModelDetails getUserProfileModelDetails() {
        return userProfileModelDetails;
    }

    public void setUserProfileModelDetails(UserProfileModelDetails userProfileModelDetails) {
        this.userProfileModelDetails = userProfileModelDetails;

    }
}
