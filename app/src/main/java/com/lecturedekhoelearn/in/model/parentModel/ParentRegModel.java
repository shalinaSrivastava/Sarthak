package com.lecturedekhoelearn.in.model.parentModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParentRegModel {

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("Msg")
    @Expose
    private String Msg;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("Detail")
    @Expose
    private  ParentRegModelDetails parentRegModelDetails;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public ParentRegModelDetails getParentRegModelDetails() {
        return parentRegModelDetails;
    }

    public void setParentRegModelDetails(ParentRegModelDetails parentRegModelDetails) {
        this.parentRegModelDetails = parentRegModelDetails;
    }
}
