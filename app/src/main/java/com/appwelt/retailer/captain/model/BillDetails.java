package com.appwelt.retailer.captain.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillDetails {

    @SerializedName("bill_id")
    @Expose
    private String bill_id;
    @SerializedName("order_id")
    @Expose
    private String order_id;
    @SerializedName("organisation_id")
    @Expose
    private String organisation_id;
    @SerializedName("branch_id")
    @Expose
    private String branch_id;
    @SerializedName("customer_name")
    @Expose
    private String customer_name;
    @SerializedName("customer_mobile_no")
    @Expose
    private String customer_mobile_no;
    @SerializedName("bill_amount")
    @Expose
    private String bill_amount;
    @SerializedName("bill_discount_rate")
    @Expose
    private String bill_discount_rate;
    @SerializedName("bill_discount_amount")
    @Expose
    private String bill_discount_amount;
    @SerializedName("bill_discount_reason")
    @Expose
    private String bill_discount_reason;
    @SerializedName("bill_tip")
    @Expose
    private String bill_tip;
    @SerializedName("bill_created_on")
    @Expose
    private String bill_created_on;
    @SerializedName("bill_created_by")
    @Expose
    private String bill_created_by;

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrganisation_id() {
        return organisation_id;
    }

    public void setOrganisation_id(String organisation_id) {
        this.organisation_id = organisation_id;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_mobile_no() {
        return customer_mobile_no;
    }

    public void setCustomer_mobile_no(String customer_mobile_no) {
        this.customer_mobile_no = customer_mobile_no;
    }

    public String getBill_amount() {
        return bill_amount;
    }

    public void setBill_amount(String bill_amount) {
        this.bill_amount = bill_amount;
    }

    public String getBill_discount_rate() {
        return bill_discount_rate;
    }

    public void setBill_discount_rate(String bill_discount_rate) {
        this.bill_discount_rate = bill_discount_rate;
    }

    public String getBill_discount_amount() {
        return bill_discount_amount;
    }

    public void setBill_discount_amount(String bill_discount_amount) {
        this.bill_discount_amount = bill_discount_amount;
    }

    public String getBill_discount_reason() {
        return bill_discount_reason;
    }

    public void setBill_discount_reason(String bill_discount_reason) {
        this.bill_discount_reason = bill_discount_reason;
    }

    public String getBill_tip() {
        return bill_tip;
    }

    public void setBill_tip(String bill_tip) {
        this.bill_tip = bill_tip;
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


}
