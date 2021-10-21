package com.appwelt.retailer.captain.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderExtraItem {

    @SerializedName("order_extra_item_id")
    @Expose
    private String order_extra_item_id;
    @SerializedName("order_id")
    @Expose
    private String order_id;
    @SerializedName("order_extra_item_name")
    @Expose
    private String order_extra_item_name;
    @SerializedName("order_extra_item_qty")
    @Expose
    private String order_extra_item_qty;
    @SerializedName("order_extra_item_price")
    @Expose
    private String order_extra_item_price;
    @SerializedName("order_extra_item_created_by")
    @Expose
    private String order_extra_item_created_by;
    @SerializedName("order_extra_item_created_on")
    @Expose
    private String order_extra_item_created_on;

    public String getOrder_extra_item_id() {
        return order_extra_item_id;
    }

    public void setOrder_extra_item_id(String order_extra_item_id) {
        this.order_extra_item_id = order_extra_item_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_extra_item_name() {
        return order_extra_item_name;
    }

    public void setOrder_extra_item_name(String order_extra_item_name) {
        this.order_extra_item_name = order_extra_item_name;
    }

    public String getOrder_extra_item_qty() {
        return order_extra_item_qty;
    }

    public void setOrder_extra_item_qty(String order_extra_item_qty) {
        this.order_extra_item_qty = order_extra_item_qty;
    }

    public String getOrder_extra_item_price() {
        return order_extra_item_price;
    }

    public void setOrder_extra_item_price(String order_extra_item_price) {
        this.order_extra_item_price = order_extra_item_price;
    }

    public String getOrder_extra_item_created_by() {
        return order_extra_item_created_by;
    }

    public void setOrder_extra_item_created_by(String order_extra_item_created_by) {
        this.order_extra_item_created_by = order_extra_item_created_by;
    }

    public String getOrder_extra_item_created_on() {
        return order_extra_item_created_on;
    }

    public void setOrder_extra_item_created_on(String order_extra_item_created_on) {
        this.order_extra_item_created_on = order_extra_item_created_on;
    }

    @Override
    public String toString() {
        return "OrderExtraItem{" +
                "order_extra_item_id='" + order_extra_item_id + '\'' +
                ", order_id='" + order_id + '\'' +
                ", order_extra_item_name='" + order_extra_item_name + '\'' +
                ", order_extra_item_qty='" + order_extra_item_qty + '\'' +
                ", order_extra_item_price='" + order_extra_item_price + '\'' +
                ", order_extra_item_created_by='" + order_extra_item_created_by + '\'' +
                ", order_extra_item_created_on='" + order_extra_item_created_on + '\'' +
                '}';
    }
}
