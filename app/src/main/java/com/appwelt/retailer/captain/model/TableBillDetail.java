package com.appwelt.retailer.captain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TableBillDetail {
    @SerializedName("table_id")
    @Expose
    private String table_id;
    @SerializedName("section_id")
    @Expose
    private String section_id;
    @SerializedName("bill_amount")
    @Expose
    private String bill_amount;
    @SerializedName("sgst_amount")
    @Expose
    private String sgst_amount;
    @SerializedName("cgst_amount")
    @Expose
    private String cgst_amount;
    @SerializedName("food_bill_items")
    @Expose
    private List<OrderDetail> food_bill_items;
    @SerializedName("bar_bill_items")
    @Expose
    private List<OrderDetail> bar_bill_items;
    @SerializedName("extra_bill_items")
    @Expose
    private List<ExtraItemList> extra_bill_items;

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

    public String getBill_amount() {
        return bill_amount;
    }

    public void setBill_amount(String bill_amount) {
        this.bill_amount = bill_amount;
    }

    public String getSgst_amount() {
        return sgst_amount;
    }

    public void setSgst_amount(String sgst_amount) {
        this.sgst_amount = sgst_amount;
    }

    public String getCgst_amount() {
        return cgst_amount;
    }

    public void setCgst_amount(String cgst_amount) {
        this.cgst_amount = cgst_amount;
    }

    public List<OrderDetail> getFood_bill_items() {
        return food_bill_items;
    }

    public void setFood_bill_items(List<OrderDetail> food_bill_items) {
        this.food_bill_items = food_bill_items;
    }

    public List<OrderDetail> getBar_bill_items() {
        return bar_bill_items;
    }

    public void setBar_bill_items(List<OrderDetail> bar_bill_items) {
        this.bar_bill_items = bar_bill_items;
    }

    public List<ExtraItemList> getExtra_bill_items() {
        return extra_bill_items;
    }

    public void setExtra_bill_items(List<ExtraItemList> extra_bill_items) {
        this.extra_bill_items = extra_bill_items;
    }

    @Override
    public String toString() {
        return "TableBillDetail{" +
                "table_id='" + table_id + '\'' +
                ", section_id='" + section_id + '\'' +
                ", sgst_amount='" + sgst_amount + '\'' +
                ", cgst_amount='" + cgst_amount + '\'' +
                ", food_bill_items=" + food_bill_items +
                ", bar_bill_items=" + bar_bill_items +
                '}';
    }
}
