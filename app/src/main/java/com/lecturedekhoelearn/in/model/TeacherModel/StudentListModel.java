package com.lecturedekhoelearn.in.model.TeacherModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentListModel {


    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<StudentListDetailsModel> studentListDetailsModels = null;


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

    public List<StudentListDetailsModel> getStudentListDetailsModels() {
        return studentListDetailsModels;
    }

    public void setStudentListDetailsModels(List<StudentListDetailsModel> studentListDetailsModels) {
        this.studentListDetailsModels = studentListDetailsModels;
    }

}
