package com.lecturedekhoelearn.in.model.TeacherModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherLoginModel {


    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private TeacherLoginModelDetails teacherLoginModelDetails;


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

    public TeacherLoginModelDetails getTeacherLoginModelDetails() {
        return teacherLoginModelDetails;
    }

    public void setTeacherLoginModelDetails(TeacherLoginModelDetails teacherLoginModelDetails) {
        this.teacherLoginModelDetails = teacherLoginModelDetails;
    }
}
