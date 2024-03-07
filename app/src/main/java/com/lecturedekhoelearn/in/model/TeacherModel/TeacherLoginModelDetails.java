package com.lecturedekhoelearn.in.model.TeacherModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherLoginModelDetails {


    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("profile_pic")
    @Expose
    private String profile_pic;

    @SerializedName("class_id")
    @Expose
    private String class_id;

    @SerializedName("subject")
    @Expose
    private String subject;

    @SerializedName("whatsapp")
    @Expose
    private String whatsapp;


    @SerializedName("facebook")
    @Expose
    private String facebook;

    @SerializedName("linkedin")
    @Expose
    private String linkedin;

    @SerializedName("about")
    @Expose
    private String about;


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

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
