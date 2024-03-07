package com.lecturedekhoelearn.in.model.queryModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Home on 26-09-2018.
 */

public class Query_Details {

    @SerializedName("student_id")
    @Expose
    private String student_id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("is_resolved")
    @Expose
    private String is_resolved;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("updated_at")
    @Expose
    private String updated_at;


    @SerializedName("document")
    @Expose
    private String document;


    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getIs_resolved() {
        return is_resolved;
    }

    public void setIs_resolved(String is_resolved) {
        this.is_resolved = is_resolved;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

}
