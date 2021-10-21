package com.appwelt.retailer.captain.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrinterDetails {

    @SerializedName("printer_id")
    @Expose
    private String printer_id;
    @SerializedName("organisation_id")
    @Expose
    private String organisation_id;
    @SerializedName("branch_id")
    @Expose
    private String branch_id;
    @SerializedName("printer_type")
    @Expose
    private String printer_type;
    @SerializedName("printer_name")
    @Expose
    private String printer_name;
    @SerializedName("printer_ip")
    @Expose
    private String printer_ip;
    @SerializedName("printer_port")
    @Expose
    private String printer_port;
    @SerializedName("printer_com_port")
    @Expose
    private String printer_com_port;
    @SerializedName("printer_paper_size")
    @Expose
    private String printer_paper_size;
    @SerializedName("printer_created_on")
    @Expose
    private String printer_created_on;
    @SerializedName("printer_created_by")
    @Expose
    private String printer_created_by;

    public String getPrinter_id() {
        return printer_id;
    }

    public void setPrinter_id(String printer_id) {
        this.printer_id = printer_id;
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

    public String getPrinter_type() {
        return printer_type;
    }

    public void setPrinter_type(String printer_type) {
        this.printer_type = printer_type;
    }

    public String getPrinter_name() {
        return printer_name;
    }

    public void setPrinter_name(String printer_name) {
        this.printer_name = printer_name;
    }

    public String getPrinter_ip() {
        return printer_ip;
    }

    public void setPrinter_ip(String printer_ip) {
        this.printer_ip = printer_ip;
    }

    public String getPrinter_port() {
        return printer_port;
    }

    public void setPrinter_port(String printer_port) {
        this.printer_port = printer_port;
    }

    public String getPrinter_com_port() {
        return printer_com_port;
    }

    public void setPrinter_com_port(String printer_com_port) {
        this.printer_com_port = printer_com_port;
    }

    public String getPrinter_paper_size() {
        return printer_paper_size;
    }

    public void setPrinter_paper_size(String printer_paper_size) {
        this.printer_paper_size = printer_paper_size;
    }

    public String getPrinter_created_on() {
        return printer_created_on;
    }

    public void setPrinter_created_on(String printer_created_on) {
        this.printer_created_on = printer_created_on;
    }

    public String getPrinter_created_by() {
        return printer_created_by;
    }

    public void setPrinter_created_by(String printer_created_by) {
        this.printer_created_by = printer_created_by;
    }

    @Override
    public String toString() {
        return "PrinterDetails{" +
                "printer_id='" + printer_id + '\'' +
                ", organisation_id='" + organisation_id + '\'' +
                ", branch_id='" + branch_id + '\'' +
                ", printer_type='" + printer_type + '\'' +
                ", printer_name='" + printer_name + '\'' +
                ", printer_ip='" + printer_ip + '\'' +
                ", printer_port='" + printer_port + '\'' +
                ", printer_com_port='" + printer_com_port + '\'' +
                ", printer_paper_size='" + printer_paper_size + '\'' +
                ", printer_created_on='" + printer_created_on + '\'' +
                ", printer_created_by='" + printer_created_by + '\'' +
                '}';
    }
}
