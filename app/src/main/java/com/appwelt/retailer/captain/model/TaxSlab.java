package com.appwelt.retailer.captain.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaxSlab {

    @SerializedName("tax_slab_id")
    @Expose
    private String tax_slab_id;
    @SerializedName("organisation_id")
    @Expose
    private String organisation_id;
    @SerializedName("branch_id")
    @Expose
    private String branch_id;
    @SerializedName("tax_slab_name")
    @Expose
    private String tax_slab_name;
    @SerializedName("tax_slab_sgst")
    @Expose
    private String tax_slab_sgst;
    @SerializedName("tax_slab_cgst")
    @Expose
    private String tax_slab_cgst;
    @SerializedName("tax_slab_created_on")
    @Expose
    private String tax_slab_created_on;
    @SerializedName("tax_slab_created_by")
    @Expose
    private String tax_slab_created_by;

    public String getTax_slab_id() {
        return tax_slab_id;
    }

    public void setTax_slab_id(String tax_slab_id) {
        this.tax_slab_id = tax_slab_id;
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

    public String getTax_slab_name() {
        return tax_slab_name;
    }

    public void setTax_slab_name(String tax_slab_name) {
        this.tax_slab_name = tax_slab_name;
    }

    public String getTax_slab_sgst() {
        return tax_slab_sgst;
    }

    public void setTax_slab_sgst(String tax_slab_sgst) {
        this.tax_slab_sgst = tax_slab_sgst;
    }

    public String getTax_slab_cgst() {
        return tax_slab_cgst;
    }

    public void setTax_slab_cgst(String tax_slab_cgst) {
        this.tax_slab_cgst = tax_slab_cgst;
    }

    public String getTax_slab_created_on() {
        return tax_slab_created_on;
    }

    public void setTax_slab_created_on(String tax_slab_created_on) {
        this.tax_slab_created_on = tax_slab_created_on;
    }

    public String getTax_slab_created_by() {
        return tax_slab_created_by;
    }

    public void setTax_slab_created_by(String tax_slab_created_by) {
        this.tax_slab_created_by = tax_slab_created_by;
    }

    @Override
    public String toString() {
        return "TaxSlab{" +
                "tax_slab_id='" + tax_slab_id + '\'' +
                ", organisation_id='" + organisation_id + '\'' +
                ", branch_id='" + branch_id + '\'' +
                ", tax_slab_name='" + tax_slab_name + '\'' +
                ", tax_slab_sgst='" + tax_slab_sgst + '\'' +
                ", tax_slab_cgst='" + tax_slab_cgst + '\'' +
                ", tax_slab_created_on='" + tax_slab_created_on + '\'' +
                ", tax_slab_created_by='" + tax_slab_created_by + '\'' +
                '}';
    }
}
