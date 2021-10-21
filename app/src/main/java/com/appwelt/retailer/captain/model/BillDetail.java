package com.appwelt.retailer.captain.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillDetail {

    @SerializedName("bill_detail_id")
    @Expose
    private String bill_detail_id;
    @SerializedName("bill_id")
    @Expose
    private String bill_id;
    @SerializedName("bill_detail_product_quantity")
    @Expose
    private String bill_detail_product_quantity;
    @SerializedName("bill_detail_product_price")
    @Expose
    private String bill_detail_product_price;
    @SerializedName("bill_detail_product_id")
    @Expose
    private String bill_detail_product_id;
    @SerializedName("bill_detail_product_discount")
    @Expose
    private String bill_detail_product_discount;
    @SerializedName("bill_detail_product_sgst")
    @Expose
    private String bill_detail_product_sgst;
    @SerializedName("bill_detail_product_cgst")
    @Expose
    private String bill_detail_product_cgst;
    @SerializedName("bill_detail_product_special_note")
    @Expose
    private String bill_detail_product_special_note;
    @SerializedName("bill_detail_created_on")
    @Expose
    private String bill_detail_created_on;
    @SerializedName("bill_detail_created_by")
    @Expose
    private String bill_detail_created_by;

    public String getBill_detail_id() {
        return bill_detail_id;
    }

    public void setBill_detail_id(String bill_detail_id) {
        this.bill_detail_id = bill_detail_id;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getBill_detail_product_quantity() {
        return bill_detail_product_quantity;
    }

    public void setBill_detail_product_quantity(String bill_detail_product_quantity) {
        this.bill_detail_product_quantity = bill_detail_product_quantity;
    }

    public String getBill_detail_product_price() {
        return bill_detail_product_price;
    }

    public void setBill_detail_product_price(String bill_detail_product_price) {
        this.bill_detail_product_price = bill_detail_product_price;
    }

    public String getBill_detail_product_id() {
        return bill_detail_product_id;
    }

    public void setBill_detail_product_id(String bill_detail_product_id) {
        this.bill_detail_product_id = bill_detail_product_id;
    }

    public String getBill_detail_product_discount() {
        return bill_detail_product_discount;
    }

    public void setBill_detail_product_discount(String bill_detail_product_discount) {
        this.bill_detail_product_discount = bill_detail_product_discount;
    }

    public String getBill_detail_product_sgst() {
        return bill_detail_product_sgst;
    }

    public void setBill_detail_product_sgst(String bill_detail_product_sgst) {
        this.bill_detail_product_sgst = bill_detail_product_sgst;
    }

    public String getBill_detail_product_cgst() {
        return bill_detail_product_cgst;
    }

    public void setBill_detail_product_cgst(String bill_detail_product_cgst) {
        this.bill_detail_product_cgst = bill_detail_product_cgst;
    }

    public String getBill_detail_product_special_note() {
        return bill_detail_product_special_note;
    }

    public void setBill_detail_product_special_note(String bill_detail_product_special_note) {
        this.bill_detail_product_special_note = bill_detail_product_special_note;
    }

    public String getBill_detail_created_on() {
        return bill_detail_created_on;
    }

    public void setBill_detail_created_on(String bill_detail_created_on) {
        this.bill_detail_created_on = bill_detail_created_on;
    }

    public String getBill_detail_created_by() {
        return bill_detail_created_by;
    }

    public void setBill_detail_created_by(String bill_detail_created_by) {
        this.bill_detail_created_by = bill_detail_created_by;
    }

    @Override
    public String toString() {
        return "BillProductDetails{" +
                "bill_detail_id='" + bill_detail_id + '\'' +
                ", bill_id='" + bill_id + '\'' +
                ", bill_detail_product_quantity='" + bill_detail_product_quantity + '\'' +
                ", bill_detail_product_price='" + bill_detail_product_price + '\'' +
                ", bill_detail_product_id='" + bill_detail_product_id + '\'' +
                ", bill_detail_product_discount='" + bill_detail_product_discount + '\'' +
                ", bill_detail_product_sgst='" + bill_detail_product_sgst + '\'' +
                ", bill_detail_product_cgst='" + bill_detail_product_cgst + '\'' +
                ", bill_detail_product_special_note='" + bill_detail_product_special_note + '\'' +
                ", bill_detail_created_on='" + bill_detail_created_on + '\'' +
                ", bill_detail_created_by='" + bill_detail_created_by + '\'' +
                '}';
    }
}
