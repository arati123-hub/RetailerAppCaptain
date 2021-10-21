package com.appwelt.retailer.captain.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerDetails {

    @SerializedName("customer_id")
    @Expose
    private String customer_id;
    @SerializedName("customer_mobile_no")
    @Expose
    private String customer_mobile_no;
    @SerializedName("customer_name")
    @Expose
    private String customer_name;
    @SerializedName("customer_address")
    @Expose
    private String customer_address;
    @SerializedName("bill_created_on")
    @Expose
    private String bill_created_on;
    @SerializedName("bill_created_by")
    @Expose
    private String bill_created_by;

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_mobile_no() {
        return customer_mobile_no;
    }

    public void setCustomer_mobile_no(String customer_mobile_no) {
        this.customer_mobile_no = customer_mobile_no;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getBill_created_on() {
        return bill_created_on;
    }

    public void setBill_created_on(String bill_created_on) {
        this.bill_created_on = bill_created_on;
    }

    public String getBill_created_by() {
        return bill_created_by;
    }

    public void setBill_created_by(String bill_created_by) {
        this.bill_created_by = bill_created_by;
    }

    @Override
    public String toString() {
        return "CustomerDetails{" +
                "customer_id='" + customer_id + '\'' +
                ", customer_mobile_no='" + customer_mobile_no + '\'' +
                ", customer_name='" + customer_name + '\'' +
                ", customer_address='" + customer_address + '\'' +
                ", bill_created_on='" + bill_created_on + '\'' +
                ", bill_created_by='" + bill_created_by + '\'' +
                '}';
    }
}
