package com.lecturedekhoelearn.in.model.parentModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParentLoginDetails {


    @SerializedName("id")
    @Expose
    private String id;


    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;


    @SerializedName("student_id")
    @Expose
    private String student_id;

    @SerializedName("mobile")
    @Expose
    private String mobile;


    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("created_at")
    @Expose
    private String created_at;


    @SerializedName("level_id")
    @Expose
    private String level_id;


    @SerializedName("category_id")
    @Expose
    private String category_id;

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    @SerializedName("class_id")
    @Expose
    private String class_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLevel_id() {
        return level_id;
    }

    public void setLevel_id(String level_id) {
        this.level_id = level_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }
}
