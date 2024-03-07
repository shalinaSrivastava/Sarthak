package com.lecturedekhoelearn.in.model;

public class CustomData {


    public int imageId;
    public String txt;

    public CustomData(int imageId, String text) {

        this.imageId = imageId;
        this.txt = text;
    }


    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
