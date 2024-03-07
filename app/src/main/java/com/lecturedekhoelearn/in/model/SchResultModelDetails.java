package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchResultModelDetails {


    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("student_id")
    @Expose
    private String student_id;

    @SerializedName("scholar_id")
    @Expose
    private String scholar_id;

    @SerializedName("result")
    @Expose
    private String result;

    @SerializedName("created_at")
    @Expose
    private String created_at;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getScholar_id() {
        return scholar_id;
    }

    public void setScholar_id(String scholar_id) {
        this.scholar_id = scholar_id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
