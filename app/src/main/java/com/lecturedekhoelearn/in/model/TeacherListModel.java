package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeacherListModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<TeacherListDetailsModel> teacherListDetailsModels = null;


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

    public List<TeacherListDetailsModel> getTeacherListDetailsModels() {
        return teacherListDetailsModels;
    }

    public void setTeacherListDetailsModels(List<TeacherListDetailsModel> teacherListDetailsModels) {
        this.teacherListDetailsModels = teacherListDetailsModels;
    }
}
