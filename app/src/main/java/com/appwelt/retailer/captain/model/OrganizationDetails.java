package com.appwelt.retailer.captain.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrganizationDetails {

    @SerializedName("organisation_id")
    @Expose
    private String organisation_id;
    @SerializedName("organisation_name")
    @Expose
    private String organisation_name;
    @SerializedName("organisation_type")
    @Expose
    private String organisation_type;
    @SerializedName("organisation_logo")
    @Expose
    private String organisation_logo;
    @SerializedName("organisation_reg_address")
    @Expose
    private String organisation_reg_address;
    @SerializedName("organisation_reg_email")
    @Expose
    private String organisation_reg_email;
    @SerializedName("organisation_reg_number")
    @Expose
    private String organisation_reg_number;
    @SerializedName("organisation_gst")
    @Expose
    private String organisation_gst;
    @SerializedName("organisation_cin")
    @Expose
    private String organisation_cin;
    @SerializedName("organisation_pan")
    @Expose
    private String organisation_pan;
    @SerializedName("organisation_cashier_counter")
    @Expose
    private String organisation_cashier_counter;
    @SerializedName("organisation_tan")
    @Expose
    private String organisation_tan;
    @SerializedName("organisation_modified_on")
    @Expose
    private String organisation_modified_on;
    @SerializedName("organisation_created_by")
    @Expose
    private String organisation_created_by;
    @SerializedName("organisation_modified_by")
    @Expose
    private String organisation_modified_by;
    @SerializedName("organisation_is_deleted")
    @Expose
    private String organisation_is_deleted;
    @SerializedName("organisation_created_on")
    @Expose
    private String organisation_created_on;
    @SerializedName("organisation_status")
    @Expose
    private String organisation_status;

    public String getOrganisation_id() {
        return organisation_id;
    }

    public void setOrganisation_id(String organisation_id) {
        this.organisation_id = organisation_id;
    }

    public String getOrganisation_name() {
        return organisation_name;
    }

    public void setOrganisation_name(String organisation_name) {
        this.organisation_name = organisation_name;
    }

    public String getOrganisation_type() {
        return organisation_type;
    }

    public void setOrganisation_type(String organisation_type) {
        this.organisation_type = organisation_type;
    }

    public String getOrganisation_logo() {
        return organisation_logo;
    }

    public void setOrganisation_logo(String organisation_logo) {
        this.organisation_logo = organisation_logo;
    }

    public String getOrganisation_reg_address() {
        return organisation_reg_address;
    }

    public void setOrganisation_reg_address(String organisation_reg_address) {
        this.organisation_reg_address = organisation_reg_address;
    }

    public String getOrganisation_reg_email() {
        return organisation_reg_email;
    }

    public void setOrganisation_reg_email(String organisation_reg_email) {
        this.organisation_reg_email = organisation_reg_email;
    }

    public String getOrganisation_reg_number() {
        return organisation_reg_number;
    }

    public void setOrganisation_reg_number(String organisation_reg_number) {
        this.organisation_reg_number = organisation_reg_number;
    }

    public String getOrganisation_gst() {
        return organisation_gst;
    }

    public void setOrganisation_gst(String organisation_gst) {
        this.organisation_gst = organisation_gst;
    }

    public String getOrganisation_cin() {
        return organisation_cin;
    }

    public void setOrganisation_cin(String organisation_cin) {
        this.organisation_cin = organisation_cin;
    }

    public String getOrganisation_pan() {
        return organisation_pan;
    }

    public void setOrganisation_pan(String organisation_pan) {
        this.organisation_pan = organisation_pan;
    }

    public String getOrganisation_cashier_counter() {
        return organisation_cashier_counter;
    }

    public void setOrganisation_cashier_counter(String organisation_cashier_counter) {
        this.organisation_cashier_counter = organisation_cashier_counter;
    }

    public String getOrganisation_tan() {
        return organisation_tan;
    }

    public void setOrganisation_tan(String organisation_tan) {
        this.organisation_tan = organisation_tan;
    }

    public String getOrganisation_modified_on() {
        return organisation_modified_on;
    }

    public void setOrganisation_modified_on(String organisation_modified_on) {
        this.organisation_modified_on = organisation_modified_on;
    }

    public String getOrganisation_created_by() {
        return organisation_created_by;
    }

    public void setOrganisation_created_by(String organisation_created_by) {
        this.organisation_created_by = organisation_created_by;
    }

    public String getOrganisation_modified_by() {
        return organisation_modified_by;
    }

    public void setOrganisation_modified_by(String organisation_modified_by) {
        this.organisation_modified_by = organisation_modified_by;
    }

    public String getOrganisation_is_deleted() {
        return organisation_is_deleted;
    }

    public void setOrganisation_is_deleted(String organisation_is_deleted) {
        this.organisation_is_deleted = organisation_is_deleted;
    }

    public String getOrganisation_created_on() {
        return organisation_created_on;
    }

    public void setOrganisation_created_on(String organisation_created_on) {
        this.organisation_created_on = organisation_created_on;
    }

    public String getOrganisation_status() {
        return organisation_status;
    }

    public void setOrganisation_status(String organisation_status) {
        this.organisation_status = organisation_status;
    }

    @Override
    public String toString() {
        return "OrganizationDetails{" +
                "organisation_id='" + organisation_id + '\'' +
                ", organisation_name='" + organisation_name + '\'' +
                ", organisation_type='" + organisation_type + '\'' +
                ", organisation_logo='" + organisation_logo + '\'' +
                ", organisation_reg_address='" + organisation_reg_address + '\'' +
                ", organisation_reg_email='" + organisation_reg_email + '\'' +
                ", organisation_reg_number='" + organisation_reg_number + '\'' +
                ", organisation_gst='" + organisation_gst + '\'' +
                ", organisation_cin='" + organisation_cin + '\'' +
                ", organisation_pan='" + organisation_pan + '\'' +
                ", organisation_cashier_counter='" + organisation_cashier_counter + '\'' +
                ", organisation_tan='" + organisation_tan + '\'' +
                ", organisation_modified_on='" + organisation_modified_on + '\'' +
                ", organisation_created_by='" + organisation_created_by + '\'' +
                ", organisation_modified_by='" + organisation_modified_by + '\'' +
                ", organisation_is_deleted='" + organisation_is_deleted + '\'' +
                ", organisation_created_on='" + organisation_created_on + '\'' +
                ", organisation_status='" + organisation_status + '\'' +
                '}';
    }
}
