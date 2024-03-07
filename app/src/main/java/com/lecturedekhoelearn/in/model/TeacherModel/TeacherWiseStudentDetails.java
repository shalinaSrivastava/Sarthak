package com.lecturedekhoelearn.in.model.TeacherModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherWiseStudentDetails {
    public int getTotal_student() {
        return total_student;
    }

    public void setTotal_student(int total_student) {
        this.total_student = total_student;
    }

    public int getFree_student() {
        return free_student;
    }

    public void setFree_student(int free_student) {
        this.free_student = free_student;
    }

    public int getPre_student() {
        return pre_student;
    }

    public void setPre_student(int pre_student) {
        this.pre_student = pre_student;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    int total_student,free_student,pre_student,class_id;
}
