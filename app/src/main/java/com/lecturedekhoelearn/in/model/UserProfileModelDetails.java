package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileModelDetails {


     @SerializedName("id")
     @Expose
     private String id;

     @SerializedName("level_id")
     @Expose
     private String level_id;

     @SerializedName("type_id")
     @Expose
     private String type_id;

     @SerializedName("class_id")
     @Expose
     private String class_id;

     @SerializedName("name")
     @Expose
     private String name;

     @SerializedName("email")
     @Expose
     private String email;

     @SerializedName("mobile")
     @Expose
     private String mobile;

     @SerializedName("address")
     @Expose
     private String address;

     @SerializedName("class")
     @Expose
     private String classID;

     @SerializedName("level")
     @Expose
     private String level;

     @SerializedName("category")
     @Expose
     private String category;


     @SerializedName("profile_pic")
     @Expose
     private String profile_pic;


     public String getProfile_pic() {
          return profile_pic;
     }

     public void setProfile_pic(String profile_pic) {
          this.profile_pic = profile_pic;
     }

     public String getId() {
          return id;
     }

     public void setId(String id) {
          this.id = id;
     }

     public String getLevel_id() {
          return level_id;
     }

     public void setLevel_id(String level_id) {
          this.level_id = level_id;
     }

     public String getType_id() {
          return type_id;
     }

     public void setType_id(String type_id) {
          this.type_id = type_id;
     }

     public String getClass_id() {
          return class_id;
     }

     public void setClass_id(String class_id) {
          this.class_id = class_id;
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

     public String getMobile() {
          return mobile;
     }

     public void setMobile(String mobile) {
          this.mobile = mobile;
     }

     public String getAddress() {
          return address;
     }

     public void setAddress(String address) {
          this.address = address;
     }

     public String getClassID() {
          return classID;
     }

     public void setClassID(String classID) {
          this.classID = classID;
     }

     public String getLevel() {
          return level;
     }

     public void setLevel(String level) {
          this.level = level;
     }

     public String getCategory() {
          return category;
     }

     public void setCategory(String category) {
          this.category = category;
     }
}
