package com.lecturedekhoelearn.in.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentHistoryDetailsModel {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("student_id")
    @Expose
    private String student_id;

    @SerializedName("txnid")
    @Expose
    private String txnid;

    @SerializedName("product_id")
    @Expose
    private String product_id;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("qnt")
    @Expose
    private String qnt;

    @SerializedName("product_name")
    @Expose
    private String product_name;



    @SerializedName("created_at")
    @Expose
    private String created_at;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQnt() {
        return qnt;
    }

    public void setQnt(String qnt) {
        this.qnt = qnt;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
