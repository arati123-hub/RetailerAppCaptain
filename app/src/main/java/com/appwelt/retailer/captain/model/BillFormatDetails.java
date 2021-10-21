package com.appwelt.retailer.captain.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillFormatDetails {

    @SerializedName("bill_format_id")
    @Expose
    private String bill_format_id;
    @SerializedName("branch_id")
    @Expose
    private String branch_id;
    @SerializedName("organisation_id")
    @Expose
    private String organisation_id;
    @SerializedName("bill_printer_id")
    @Expose
    private String bill_printer_id;
    @SerializedName("bill_title")
    @Expose
    private String bill_title;
    @SerializedName("bill_address_line1")
    @Expose
    private String bill_address_line1;
    @SerializedName("bill_address_line2")
    @Expose
    private String bill_address_line2;
    @SerializedName("bill_telephone")
    @Expose
    private String bill_telephone;
    @SerializedName("bill_gst_no")
    @Expose
    private String bill_gst_no;
    @SerializedName("bill_head")
    @Expose
    private String bill_head;
    @SerializedName("bill_special_note_line1")
    @Expose
    private String bill_special_note_line1;
    @SerializedName("bill_special_note_line2")
    @Expose
    private String bill_special_note_line2;
    @SerializedName("bill_thank_you_line")
    @Expose
    private String bill_thank_you_line;
    @SerializedName("bill_format_created_by")
    @Expose
    private String bill_format_created_by;
    @SerializedName("bill_format_created_on")
    @Expose
    private String bill_format_created_on;
    @SerializedName("bill_format_modified_by")
    @Expose
    private String bill_format_modified_by;
    @SerializedName("bill_format_modified_on")
    @Expose
    private String bill_format_modified_on;

    public String getBill_format_id() {
        return bill_format_id;
    }

    public void setBill_format_id(String bill_format_id) {
        this.bill_format_id = bill_format_id;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getOrganisation_id() {
        return organisation_id;
    }

    public void setOrganisation_id(String organisation_id) {
        this.organisation_id = organisation_id;
    }

    public String getBill_printer_id() {
        return bill_printer_id;
    }

    public void setBill_printer_id(String bill_printer_id) {
        this.bill_printer_id = bill_printer_id;
    }

    public String getBill_title() {
        return bill_title;
    }

    public void setBill_title(String bill_title) {
        this.bill_title = bill_title;
    }

    public String getBill_address_line1() {
        return bill_address_line1;
    }

    public void setBill_address_line1(String bill_address_line1) {
        this.bill_address_line1 = bill_address_line1;
    }

    public String getBill_address_line2() {
        return bill_address_line2;
    }

    public void setBill_address_line2(String bill_address_line2) {
        this.bill_address_line2 = bill_address_line2;
    }

    public String getBill_telephone() {
        return bill_telephone;
    }

    public void setBill_telephone(String bill_telephone) {
        this.bill_telephone = bill_telephone;
    }

    public String getBill_gst_no() {
        return bill_gst_no;
    }

    public void setBill_gst_no(String bill_gst_no) {
        this.bill_gst_no = bill_gst_no;
    }

    public String getBill_head() {
        return bill_head;
    }

    public void setBill_head(String bill_head) {
        this.bill_head = bill_head;
    }

    public String getBill_special_note_line1() {
        return bill_special_note_line1;
    }

    public void setBill_special_note_line1(String bill_special_note_line1) {
        this.bill_special_note_line1 = bill_special_note_line1;
    }

    public String getBill_special_note_line2() {
        return bill_special_note_line2;
    }

    public void setBill_special_note_line2(String bill_special_note_line2) {
        this.bill_special_note_line2 = bill_special_note_line2;
    }

    public String getBill_thank_you_line() {
        return bill_thank_you_line;
    }

    public void setBill_thank_you_line(String bill_thank_you_line) {
        this.bill_thank_you_line = bill_thank_you_line;
    }

    public String getBill_format_created_by() {
        return bill_format_created_by;
    }

    public void setBill_format_created_by(String bill_format_created_by) {
        this.bill_format_created_by = bill_format_created_by;
    }

    public String getBill_format_created_on() {
        return bill_format_created_on;
    }

    public void setBill_format_created_on(String bill_format_created_on) {
        this.bill_format_created_on = bill_format_created_on;
    }

    public String getBill_format_modified_by() {
        return bill_format_modified_by;
    }

    public void setBill_format_modified_by(String bill_format_modified_by) {
        this.bill_format_modified_by = bill_format_modified_by;
    }

    public String getBill_format_modified_on() {
        return bill_format_modified_on;
    }

    public void setBill_format_modified_on(String bill_format_modified_on) {
        this.bill_format_modified_on = bill_format_modified_on;
    }

    @Override
    public String toString() {
        return "BillFormatDetails{" +
                "bill_format_id='" + bill_format_id + '\'' +
                ", branch_id='" + branch_id + '\'' +
                ", organisation_id='" + organisation_id + '\'' +
                ", bill_printer_id='" + bill_printer_id + '\'' +
                ", bill_title='" + bill_title + '\'' +
                ", bill_address_line1='" + bill_address_line1 + '\'' +
                ", bill_address_line2='" + bill_address_line2 + '\'' +
                ", bill_telephone='" + bill_telephone + '\'' +
                ", bill_gst_no='" + bill_gst_no + '\'' +
                ", bill_head='" + bill_head + '\'' +
                ", bill_special_note_line1='" + bill_special_note_line1 + '\'' +
                ", bill_special_note_line2='" + bill_special_note_line2 + '\'' +
                ", bill_thank_you_line='" + bill_thank_you_line + '\'' +
                ", bill_format_created_by='" + bill_format_created_by + '\'' +
                ", bill_format_created_on='" + bill_format_created_on + '\'' +
                ", bill_format_modified_by='" + bill_format_modified_by + '\'' +
                ", bill_format_modified_on='" + bill_format_modified_on + '\'' +
                '}';
    }
}
