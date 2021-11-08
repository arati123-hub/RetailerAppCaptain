package com.appwelt.retailer.captain.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetails {

    @SerializedName("product_id")
    @Expose
    private String product_id;
    @SerializedName("product_name")
    @Expose
    private String product_name;
    @SerializedName("product_photo")
    @Expose
    private String product_photo;
    @SerializedName("product_description")
    @Expose
    private String product_description;
    @SerializedName("product_price")
    @Expose
    private String product_price;
    @SerializedName("language_text")
    @Expose
    private String language_text;

    public String getLanguage_text() {
        return language_text;
    }

    public void setLanguage_text(String language_text) {
        this.language_text = language_text;
    }

    @SerializedName("product_code")
    @Expose
    private String product_code;
    @SerializedName("product_sequence")
    @Expose
    private String product_sequence;

    @SerializedName("product_bar_code")
    @Expose
    private String product_bar_code;

    @SerializedName("product_stock_yn")
    @Expose
    private String product_stock_yn;
    @SerializedName("product_current_stock")
    @Expose
    private String product_current_stock;

    public String getProduct_stock_yn() {
        return product_stock_yn;
    }

    public void setProduct_stock_yn(String product_stock_yn) {
        this.product_stock_yn = product_stock_yn;
    }

    public String getProduct_current_stock() {
        return product_current_stock;
    }

    public void setProduct_current_stock(String product_current_stock) {
        this.product_current_stock = product_current_stock;
    }

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

    public String getProduct_photo() {
        return product_photo;
    }

    public void setProduct_photo(String product_photo) {
        this.product_photo = product_photo;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }


    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }
    //
    public String getProduct_sequence() {
        return product_sequence;
    }

    public void setProduct_sequence(String product_sequence) {
        this.product_sequence = product_sequence;
    }

    public String getProduct_bar_code() {
        return product_bar_code;
    }

    public void setProduct_bar_code(String product_bar_code) {
        this.product_bar_code = product_bar_code;
    }

}
