package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherRatingDetailsMode {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("teacher_id")
    @Expose
    private String teacher_id;

    @SerializedName("student_id")
    @Expose
    private String student_id;

    @SerializedName("rate_us")
    @Expose
    private String rate_us;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getRate_us() {
        return rate_us;
    }

    public void setRate_us(String rate_us) {
        this.rate_us = rate_us;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
