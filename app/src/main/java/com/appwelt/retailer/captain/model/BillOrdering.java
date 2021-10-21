package com.appwelt.retailer.captain.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillOrdering {

    @SerializedName("bill_section_id")
    @Expose
    private String bill_section_id;
    @SerializedName("bill_section_name")
    @Expose
    private String bill_section_name;
    @SerializedName("bill_section_order")
    @Expose
    private String bill_section_order;
    @SerializedName("bill_section_default_order")
    @Expose
    private String bill_section_default_order;
    @SerializedName("bill_section_visibility")
    @Expose
    private String bill_section_visibility;

    public String getBill_section_id() {
        return bill_section_id;
    }

    public void setBill_section_id(String bill_section_id) {
        this.bill_section_id = bill_section_id;
    }

    public String getBill_section_name() {
        return bill_section_name;
    }

    public void setBill_section_name(String bill_section_name) {
        this.bill_section_name = bill_section_name;
    }

    public String getBill_section_order() {
        return bill_section_order;
    }

    public void setBill_section_order(String bill_section_order) {
        this.bill_section_order = bill_section_order;
    }

    public String getBill_section_default_order() {
        return bill_section_default_order;
    }

    public void setBill_section_default_order(String bill_section_default_order) {
        this.bill_section_default_order = bill_section_default_order;
    }

    public String getBill_section_visibility() {
        return bill_section_visibility;
    }

    public void setBill_section_visibility(String bill_section_visibility) {
        this.bill_section_visibility = bill_section_visibility;
    }

    @Override
    public String toString() {
        return "BillOrdering{" +
                "bill_section_id='" + bill_section_id + '\'' +
                ", bill_section_name='" + bill_section_name + '\'' +
                ", bill_section_order='" + bill_section_order + '\'' +
                ", bill_section_default_order='" + bill_section_default_order + '\'' +
                ", bill_section_visibility='" + bill_section_visibility + '\'' +
                '}';
    }
}
