package com.appwelt.retailer.captain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TableOrderDetail {
    @SerializedName("table_id")
    @Expose
    private String table_id;
    @SerializedName("section_id")
    @Expose
    private String section_id;
    @SerializedName("order_id")
    @Expose
    private String order_id;
    @SerializedName("bill_id")
    @Expose
    private String bill_id;
    @SerializedName("bill_token")
    @Expose
    private String bill_token;
    @SerializedName("order_type")
    @Expose
    private String order_type;
    @SerializedName("order_status")
    @Expose
    private String order_status;
    @SerializedName("order_detail")
    @Expose
    private List<OrderDetail> order_detail;
    @SerializedName("extra_items")
    @Expose
    private List<ExtraItemList> extra_items;

    public String getTable_id() {
        return table_id;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getBill_token() {
        return bill_token;
    }

    public void setBill_token(String bill_token) {
        this.bill_token = bill_token;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public List<OrderDetail> getOrder_detail() {
        return order_detail;
    }

    public void setOrder_detail(List<OrderDetail> order_detail) {
        this.order_detail = order_detail;
    }

    public List<ExtraItemList> getExtra_items() {
        return extra_items;
    }

    public void setExtra_items(List<ExtraItemList> extra_items) {
        this.extra_items = extra_items;
    }

    @Override
    public String toString() {
        return "TableOrderDetail{" +
                "table_id='" + table_id + '\'' +
                ", section_id='" + section_id + '\'' +
                ", order_id='" + order_id + '\'' +
                ", bill_id='" + bill_id + '\'' +
                ", bill_token='" + bill_token + '\'' +
                ", order_type='" + order_type + '\'' +
                ", order_status='" + order_status + '\'' +
                ", order_detail=" + order_detail +
                '}';
    }
}
