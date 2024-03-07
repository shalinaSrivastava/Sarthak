package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherRatingModel {


    @SerializedName("status")
    @Expose
    private Integer status;


    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("data")
    @Expose
    private TeacherRatingDetailsMode teacherRatingDetailsMode;


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

    public TeacherRatingDetailsMode getTeacherRatingDetailsMode() {
        return teacherRatingDetailsMode;
    }

    public void setTeacherRatingDetailsMode(TeacherRatingDetailsMode teacherRatingDetailsMode) {
        this.teacherRatingDetailsMode = teacherRatingDetailsMode;
    }
}
