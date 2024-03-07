package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StudentAnalysisModel implements Serializable {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("attend_question")
    @Expose
    private String attend_question;

    @SerializedName("wrong_answer")
    @Expose
    private String wrong_answer;

    @SerializedName("right_answer")
    @Expose
    private String right_answer;

    @SerializedName("total_mark")
    @Expose
    private String total_mark;


    @SerializedName("rank")
    @Expose
    private String rank;

    @SerializedName("skip")
    @Expose
    private String skip;

    @SerializedName("get_mark")
    @Expose
    private String get_mark;


    public String getGet_mark() {
        return get_mark;
    }

    public void setGet_mark(String get_mark) {
        this.get_mark = get_mark;
    }

    public String getSkip() {
        return skip;
    }

    public void setSkip(String skip) {
        this.skip = skip;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

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

    public String getAttend_question() {
        return attend_question;
    }

    public void setAttend_question(String attend_question) {
        this.attend_question = attend_question;
    }


    public String getTotal_mark() {
        return total_mark;
    }

    public void setTotal_mark(String total_mark) {
        this.total_mark = total_mark;
    }


    public String getWrong_answer() {
        return wrong_answer;
    }

    public void setWrong_answer(String wrong_answer) {
        this.wrong_answer = wrong_answer;
    }

    public String getRight_answer() {
        return right_answer;
    }

    public void setRight_answer(String right_answer) {
        this.right_answer = right_answer;
    }
}
