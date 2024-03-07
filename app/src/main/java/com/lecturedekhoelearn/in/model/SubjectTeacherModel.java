package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubjectTeacherModel {


    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("Teacher")
    @Expose
    private TeacherListStudent teacherModel;


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


    public TeacherListStudent getTeacherModel() {
        return teacherModel;
    }

    public void setTeacherModel(TeacherListStudent teacherModel) {
        this.teacherModel = teacherModel;
    }
}


