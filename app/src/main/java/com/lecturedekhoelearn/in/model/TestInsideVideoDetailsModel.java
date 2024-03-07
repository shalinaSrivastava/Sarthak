package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestInsideVideoDetailsModel {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("test_series_id")
    @Expose
    private String test_series_id;

    @SerializedName("test_id")
    @Expose
    private String test_id;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("total_marks")
    @Expose
    private String total_marks;

    @SerializedName("total_questions")
    @Expose
    private String total_questions;

    @SerializedName("subject_id")
    @Expose
    private String subject_id;

    @SerializedName("topic_id")
    @Expose
    private String topic_id;

    @SerializedName("level_id")
    @Expose
    private String level_id;

    @SerializedName("start_date")
    @Expose
    private String start_date;


    @SerializedName("end_date")
    @Expose
    private String end_date;

    @SerializedName("status")
    @Expose
    private String status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTest_series_id() {
        return test_series_id;
    }

    public void setTest_series_id(String test_series_id) {
        this.test_series_id = test_series_id;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotal_marks() {
        return total_marks;
    }

    public void setTotal_marks(String total_marks) {
        this.total_marks = total_marks;
    }

    public String getTotal_questions() {
        return total_questions;
    }

    public void setTotal_questions(String total_questions) {
        this.total_questions = total_questions;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getLevel_id() {
        return level_id;
    }

    public void setLevel_id(String level_id) {
        this.level_id = level_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
