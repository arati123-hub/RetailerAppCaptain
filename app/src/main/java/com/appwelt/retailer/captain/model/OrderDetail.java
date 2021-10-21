package com.appwelt.retailer.captain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetail {
    @SerializedName("product_id")
    @Expose
    private String product_id;
    @SerializedName("product_name")
    @Expose
    private String product_name;
    @SerializedName("product_quantity")
    @Expose
    private String product_quantity;
    @SerializedName("product_price")
    @Expose
    private String product_price;
    @SerializedName("product_special_note")
    @Expose
    private String product_special_note;
    @SerializedName("product_kot_yn")
    @Expose
    private String product_kot_yn;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_special_note() {
        return product_special_note;
    }

    public void setProduct_special_note(String product_special_note) {
        this.product_special_note = product_special_note;
    }

    public String getProduct_kot_yn() {
        return product_kot_yn;
    }

    public void setProduct_kot_yn(String product_kot_yn) {
        this.product_kot_yn = product_kot_yn;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "product_id='" + product_id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", product_quantity='" + product_quantity + '\'' +
                ", product_price='" + product_price + '\'' +
                ", product_special_note='" + product_special_note + '\'' +
                ", product_kot_yn='" + product_kot_yn + '\'' +
                '}';
    }
}
