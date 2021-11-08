package com.appwelt.retailer.captain.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetails {
    @SerializedName("order_details_id")
    @Expose
    private String order_details_id;
    @SerializedName("order_id")
    @Expose
    private String order_id;
    @SerializedName("product_id")
    @Expose
    private String product_id;
    @SerializedName("order_details_order_qty")
    @Expose
    private String order_details_order_qty;
    @SerializedName("order_deatils_unit_price")
    @Expose
    private String order_deatils_unit_price;
    @SerializedName("order_details_product_price")
    @Expose
    private String order_details_product_price;
    @SerializedName("order_deatils_discount_amount")
    @Expose
    private String order_deatils_discount_amount;
    @SerializedName("order_details_tax")
    @Expose
    private String order_details_tax;
    @SerializedName("order_details_total_price")
    @Expose
    private String order_details_total_price;
    @SerializedName("order_details_tax_no")
    @Expose
    private String order_details_tax_no;
    @SerializedName("order_details_tax_amount")
    @Expose
    private String order_details_tax_amount;
    @SerializedName("order_details_tax_type")
    @Expose
    private String order_details_tax_type;
    @SerializedName("order_details_special_note")
    @Expose
    private String order_details_special_note;
    @SerializedName("order_details_payment_stock_id")
    @Expose
    private String order_details_payment_stock_id;
    @SerializedName("order_details_status")
    @Expose
    private String order_details_status;
    @SerializedName("order_details_created_on")
    @Expose
    private String order_details_created_on;
    @SerializedName("order_details_created_by")
    @Expose
    private String order_details_created_by;
    @SerializedName("order_details_modified_on")
    @Expose
    private String order_details_modified_on;
    @SerializedName("order_details_modified_by")
    @Expose
    private String order_details_modified_by;
    @SerializedName("order_details_is_deleted")
    @Expose
    private String order_details_is_deleted;

    public String getOrder_details_id() {
        return order_details_id;
    }

    public void setOrder_details_id(String order_details_id) {
        this.order_details_id = order_details_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getOrder_details_order_qty() {
        return order_details_order_qty;
    }

    public void setOrder_details_order_qty(String order_details_order_qty) {
        this.order_details_order_qty = order_details_order_qty;
    }

    public String getOrder_deatils_unit_price() {
        return order_deatils_unit_price;
    }

    public void setOrder_deatils_unit_price(String order_deatils_unit_price) {
        this.order_deatils_unit_price = order_deatils_unit_price;
    }

    public String getOrder_details_product_price() {
        return order_details_product_price;
    }

    public void setOrder_details_product_price(String order_details_product_price) {
        this.order_details_product_price = order_details_product_price;
    }

    public String getOrder_deatils_discount_amount() {
        return order_deatils_discount_amount;
    }

    public void setOrder_deatils_discount_amount(String order_deatils_discount_amount) {
        this.order_deatils_discount_amount = order_deatils_discount_amount;
    }

    public String getOrder_details_tax() {
        return order_details_tax;
    }

    public void setOrder_details_tax(String order_details_tax) {
        this.order_details_tax = order_details_tax;
    }

    public String getOrder_details_total_price() {
        return order_details_total_price;
    }

    public void setOrder_details_total_price(String order_details_total_price) {
        this.order_details_total_price = order_details_total_price;
    }

    public String getOrder_details_tax_no() {
        return order_details_tax_no;
    }

    public void setOrder_details_tax_no(String order_details_tax_no) {
        this.order_details_tax_no = order_details_tax_no;
    }

    public String getOrder_details_tax_amount() {
        return order_details_tax_amount;
    }

    public void setOrder_details_tax_amount(String order_details_tax_amount) {
        this.order_details_tax_amount = order_details_tax_amount;
    }

    public String getOrder_details_tax_type() {
        return order_details_tax_type;
    }

    public void setOrder_details_tax_type(String order_details_tax_type) {
        this.order_details_tax_type = order_details_tax_type;
    }

    public String getOrder_details_special_note() {
        return order_details_special_note;
    }

    public void setOrder_details_special_note(String order_details_special_note) {
        this.order_details_special_note = order_details_special_note;
    }

    public String getOrder_details_payment_stock_id() {
        return order_details_payment_stock_id;
    }

    public void setOrder_details_payment_stock_id(String order_details_payment_stock_id) {
        this.order_details_payment_stock_id = order_details_payment_stock_id;
    }

    public String getOrder_details_status() {
        return order_details_status;
    }

    public void setOrder_details_status(String order_details_status) {
        this.order_details_status = order_details_status;
    }

    public String getOrder_details_created_on() {
        return order_details_created_on;
    }

    public void setOrder_details_created_on(String order_details_created_on) {
        this.order_details_created_on = order_details_created_on;
    }

    public String getOrder_details_created_by() {
        return order_details_created_by;
    }

    public void setOrder_details_created_by(String order_details_created_by) {
        this.order_details_created_by = order_details_created_by;
    }

    public String getOrder_details_modified_on() {
        return order_details_modified_on;
    }

    public void setOrder_details_modified_on(String order_details_modified_on) {
        this.order_details_modified_on = order_details_modified_on;
    }

    public String getOrder_details_modified_by() {
        return order_details_modified_by;
    }

    public void setOrder_details_modified_by(String order_details_modified_by) {
        this.order_details_modified_by = order_details_modified_by;
    }

    public String getOrder_details_is_deleted() {
        return order_details_is_deleted;
    }

    public void setOrder_details_is_deleted(String order_details_is_deleted) {
        this.order_details_is_deleted = order_details_is_deleted;
    }

}
