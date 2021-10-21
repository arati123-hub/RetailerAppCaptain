package com.appwelt.retailer.captain.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryDetails {

    @SerializedName("category_id")
    @Expose
    private String category_id;
    @SerializedName("category_name")
    @Expose
    private String category_name;
    @SerializedName("category_description")
    @Expose
    private String category_description;
    @SerializedName("sequence_nr")
    @Expose
    private String sequence_nr;
    @SerializedName("parent_id")
    @Expose
    private String parent_id;
    @SerializedName("category_image")
    @Expose
    private String category_image;
    @SerializedName("category_type")
    @Expose
    private String category_type;

    @SerializedName("product_details")
    @Expose
    private List<ProductDetails> productDetails;


    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_description() {
        return category_description;
    }

    public void setCategory_description(String category_description) {
        this.category_description = category_description;
    }

    public String getSequence_nr() {
        return sequence_nr;
    }

    public void setSequence_nr(String sequence_nr) {
        this.sequence_nr = sequence_nr;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }

    public String getCategory_type() {
        return category_type;
    }

    public void setCategory_type(String category_type) {
        this.category_type = category_type;
    }

    //    public String getCategory_icon() {
//        return category_icon;
//    }
//
//    public void setCategory_icon(String category_icon) {
//        this.category_icon = category_icon;
//    }
//
//    public String getCategory_order() {
//        return category_order;
//    }
//
//    public void setCategory_order(String category_order) {
//        this.category_order = category_order;
//    }
//
//    public String getCategory_created_on() {
//        return category_created_on;
//    }
//
//    public void setCategory_created_on(String category_created_on) {
//        this.category_created_on = category_created_on;
//    }
//
//    public String getCategory_created_by() {
//        return category_created_by;
//    }
//
//    public void setCategory_created_by(String category_created_by) {
//        this.category_created_by = category_created_by;
//    }
//
//    public String getCategory_modified_on() {
//        return category_modified_on;
//    }
//
//    public void setCategory_modified_on(String category_modified_on) {
//        this.category_modified_on = category_modified_on;
//    }
//
//    public String getCategory_modified_by() {
//        return category_modified_by;
//    }
//
//    public void setCategory_modified_by(String category_modified_by) {
//        this.category_modified_by = category_modified_by;
//    }
//
//    public String getCategory_is_deleted() {
//        return category_is_deleted;
//    }
//
//    public void setCategory_is_deleted(String category_is_deleted) {
//        this.category_is_deleted = category_is_deleted;
//    }

    public List<ProductDetails> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<ProductDetails> productDetails) {
        this.productDetails = productDetails;
    }

    @Override
    public String toString() {
        return "CategoryDetails{" +
                "category_id='" + category_id + '\'' +
                ", category_name='" + category_name + '\'' +
                ", category_description='" + category_description + '\'' +
                ", sequence_nr='" + sequence_nr + '\'' +
                ", parent_id='" + parent_id + '\'' +
                ", category_image='" + category_image + '\'' +
//                ", category_icon='" + category_icon + '\'' +
//                ", category_order='" + category_order + '\'' +
//                ", category_created_on='" + category_created_on + '\'' +
//                ", category_created_by='" + category_created_by + '\'' +
//                ", category_modified_on='" + category_modified_on + '\'' +
//                ", category_modified_by='" + category_modified_by + '\'' +
//                ", category_is_deleted='" + category_is_deleted + '\'' +
                ", productDetails=" + productDetails +
                '}';
    }
}