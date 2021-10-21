package com.appwelt.retailer.captain.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductCategoryDetails {

    @SerializedName("product_category_id")
    @Expose
    private String product_category_id;
    @SerializedName("category_id")
    @Expose
    private String category_id;
    @SerializedName("product_id")
    @Expose
    private String product_id;
    @SerializedName("product_category_created_on")
    @Expose
    private String product_category_created_on;
    @SerializedName("product_category_created_by")
    @Expose
    private String product_category_created_by;
    @SerializedName("product_category_modified_on")
    @Expose
    private String product_category_modified_on;
    @SerializedName("product_category_modified_by")
    @Expose
    private String product_category_modified_by;
    @SerializedName("product_category_is_deleted")
    @Expose
    private String product_category_is_deleted;
    @SerializedName("language_text")
    @Expose
    private String language_text;
    @SerializedName("product_code")
    @Expose
    private String product_code;
    @SerializedName("product_sequence")
    @Expose
    private String product_sequence;
    @SerializedName("product_sku")
    @Expose
    private String product_sku;
    @SerializedName("product_bar_code")
    @Expose
    private String product_bar_code;
    @SerializedName("product_tax_slab")
    @Expose
    private String product_tax_slab;
    @SerializedName("product_sgst")
    @Expose
    private String product_sgst;
    @SerializedName("product_cgst")
    @Expose
    private String product_cgst;

    public String getProduct_category_id() {
        return product_category_id;
    }

    public void setProduct_category_id(String product_category_id) {
        this.product_category_id = product_category_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_category_created_on() {
        return product_category_created_on;
    }

    public void setProduct_category_created_on(String product_category_created_on) {
        this.product_category_created_on = product_category_created_on;
    }

    public String getProduct_category_created_by() {
        return product_category_created_by;
    }

    public void setProduct_category_created_by(String product_category_created_by) {
        this.product_category_created_by = product_category_created_by;
    }

    public String getProduct_category_modified_on() {
        return product_category_modified_on;
    }

    public void setProduct_category_modified_on(String product_category_modified_on) {
        this.product_category_modified_on = product_category_modified_on;
    }

    public String getProduct_category_modified_by() {
        return product_category_modified_by;
    }

    public void setProduct_category_modified_by(String product_category_modified_by) {
        this.product_category_modified_by = product_category_modified_by;
    }

    public String getProduct_category_is_deleted() {
        return product_category_is_deleted;
    }

    public void setProduct_category_is_deleted(String product_category_is_deleted) {
        this.product_category_is_deleted = product_category_is_deleted;
    }

    public String getLanguage_text() {
        return language_text;
    }

    public void setLanguage_text(String language_text) {
        this.language_text = language_text;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getProduct_sequence() {
        return product_sequence;
    }

    public void setProduct_sequence(String product_sequence) {
        this.product_sequence = product_sequence;
    }

    public String getProduct_sku() {
        return product_sku;
    }

    public void setProduct_sku(String product_sku) {
        this.product_sku = product_sku;
    }

    public String getProduct_bar_code() {
        return product_bar_code;
    }

    public void setProduct_bar_code(String product_bar_code) {
        this.product_bar_code = product_bar_code;
    }

    public String getProduct_tax_slab() {
        return product_tax_slab;
    }

    public void setProduct_tax_slab(String product_tax_slab) {
        this.product_tax_slab = product_tax_slab;
    }

    public String getProduct_sgst() {
        return product_sgst;
    }

    public void setProduct_sgst(String product_sgst) {
        this.product_sgst = product_sgst;
    }

    public String getProduct_cgst() {
        return product_cgst;
    }

    public void setProduct_cgst(String product_cgst) {
        this.product_cgst = product_cgst;
    }

    @Override
    public String toString() {
        return "ProductCategoryDetails{" +
                "product_category_id='" + product_category_id + '\'' +
                ", category_id='" + category_id + '\'' +
                ", product_id='" + product_id + '\'' +
                ", product_category_created_on='" + product_category_created_on + '\'' +
                ", product_category_created_by='" + product_category_created_by + '\'' +
                ", product_category_modified_on='" + product_category_modified_on + '\'' +
                ", product_category_modified_by='" + product_category_modified_by + '\'' +
                ", product_category_is_deleted='" + product_category_is_deleted + '\'' +
                ", language_text='" + language_text + '\'' +
                ", product_code='" + product_code + '\'' +
                ", product_sequence='" + product_sequence + '\'' +
                ", product_sku='" + product_sku + '\'' +
                ", product_bar_code='" + product_bar_code + '\'' +
                ", product_tax_slab='" + product_tax_slab + '\'' +
                ", product_sgst='" + product_sgst + '\'' +
                ", product_cgst='" + product_cgst + '\'' +
                '}';
    }
}
