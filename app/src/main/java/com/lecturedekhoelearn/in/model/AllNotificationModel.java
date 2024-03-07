package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllNotificationModel {

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("Msg")
    @Expose
    private String Msg;

    @SerializedName("Data")
    @Expose
    private List<NotificationDetailsModel> notificationDetailsModels = null;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public List<NotificationDetailsModel> getNotificationDetailsModels() {
        return notificationDetailsModels;
    }

    public void setNotificationDetailsModels(List<NotificationDetailsModel> notificationDetailsModels) {
        this.notificationDetailsModels = notificationDetailsModels;
    }
}
