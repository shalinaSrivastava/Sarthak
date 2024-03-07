package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ResulData implements Serializable {

    @SerializedName("Test Name")
    @Expose
    private ArrayList<String> testName = null;

    @SerializedName("Rank")
    @Expose
    private String rank;

    @SerializedName("Per Question Time")
    @Expose
    private ArrayList<String> perQuesTime;

    @SerializedName("Total Questions")
    @Expose
    private Integer totalQuestions;
    @SerializedName("Correct Answer")
    @Expose
    private Integer correctAnswer;
    @SerializedName("Wrong Answer")
    @Expose
    private Integer wrongAnswer;
    @SerializedName("Attend Questions")
    @Expose
    private Integer attendQuestions;
    @SerializedName("skip")
    @Expose
    private Integer skip;


    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getWrongAnswer() {
        return wrongAnswer;
    }

    public void setWrongAnswer(int wrongAnswer) {
        this.wrongAnswer = wrongAnswer;
    }

    public int getAttendsQuestions() {
        return attendQuestions;
    }

    public void setAttendsQuestions(int attendsQuestions) {
        this.attendQuestions = attendsQuestions;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getTotalQues() {
        return totalQuestions;
    }

    public void setTotalQues(int totalQues) {
        this.totalQuestions = totalQues;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public ArrayList<String> getPerQuesTime() {
        return perQuesTime;
    }

    public void setPerQuesTime(ArrayList<String> perQuesTime) {
        this.perQuesTime = perQuesTime;
    }

    public ArrayList<String> getTestName() {
        return testName;
    }

    public void setTestName(ArrayList<String> testName) {
        this.testName = testName;
    }
}

