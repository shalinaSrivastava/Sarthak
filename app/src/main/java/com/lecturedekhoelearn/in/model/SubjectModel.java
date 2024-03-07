package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubjectModel {

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("Subjects")
    @Expose
    private List<SubjectModelDetails> subjectModelDetails = null;

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

    public List<SubjectModelDetails> getSubjectModelDetails() {
        return subjectModelDetails;
    }

    public void setSubjectModelDetails(List<SubjectModelDetails> subjectModelDetails) {
        this.subjectModelDetails = subjectModelDetails;
    }

}
