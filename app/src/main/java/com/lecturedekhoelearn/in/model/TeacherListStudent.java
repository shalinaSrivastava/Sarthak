package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeacherListStudent {


    @SerializedName("data")
    @Expose
    private List<SubjectTeacherDetailsModel> subjectTeacherDetailsModels = null;


    public List<SubjectTeacherDetailsModel> getSubjectTeacherDetailsModels() {
        return subjectTeacherDetailsModels;
    }

    public void setSubjectTeacherDetailsModels(List<SubjectTeacherDetailsModel> subjectTeacherDetailsModels) {
        this.subjectTeacherDetailsModels = subjectTeacherDetailsModels;
    }
}

