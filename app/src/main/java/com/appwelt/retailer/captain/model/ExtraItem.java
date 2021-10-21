package com.appwelt.retailer.captain.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExtraItem {

    @SerializedName("extra_item_id")
    @Expose
    private String extra_item_id;
    @SerializedName("extra_item_name")
    @Expose
    private String extra_item_name;
    @SerializedName("extra_item_price")
    @Expose
    private String extra_item_price;
    @SerializedName("extra_item_created_by")
    @Expose
    private String extra_item_created_by;
    @SerializedName("extra_item_created_on")
    @Expose
    private String extra_item_created_on;

    public String getExtra_item_id() {
        return extra_item_id;
    }

    public void setExtra_item_id(String extra_item_id) {
        this.extra_item_id = extra_item_id;
    }

    public String getExtra_item_name() {
        return extra_item_name;
    }

    public void setExtra_item_name(String extra_item_name) {
        this.extra_item_name = extra_item_name;
    }

    public String getExtra_item_price() {
        return extra_item_price;
    }

    public void setExtra_item_price(String extra_item_price) {
        this.extra_item_price = extra_item_price;
    }

    public String getExtra_item_created_by() {
        return extra_item_created_by;
    }

    public void setExtra_item_created_by(String extra_item_created_by) {
        this.extra_item_created_by = extra_item_created_by;
    }

    public String getExtra_item_created_on() {
        return extra_item_created_on;
    }

    public void setExtra_item_created_on(String extra_item_created_on) {
        this.extra_item_created_on = extra_item_created_on;
    }

    @Override
    public String toString() {
        return "ExtraItem{" +
                "extra_item_id='" + extra_item_id + '\'' +
                ", extra_item_name='" + extra_item_name + '\'' +
                ", extra_item_price='" + extra_item_price + '\'' +
                ", extra_item_created_by='" + extra_item_created_by + '\'' +
                ", extra_item_created_on='" + extra_item_created_on + '\'' +
                '}';
    }
}
