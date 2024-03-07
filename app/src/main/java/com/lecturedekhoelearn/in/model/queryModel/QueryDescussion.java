package com.lecturedekhoelearn.in.model.queryModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Home on 31-10-2018.
 */

public class QueryDescussion {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("Query")
    @Expose
    private List<Query_Descussion_Details> query_descussion_details = null;

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

    public List<Query_Descussion_Details> getQuery_descussion_details() {
        return query_descussion_details;
    }

    public void setQuery_descussion_details(List<Query_Descussion_Details> query_descussion_details) {
        this.query_descussion_details = query_descussion_details;
    }
}
