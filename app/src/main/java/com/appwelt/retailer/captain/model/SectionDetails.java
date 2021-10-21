package com.appwelt.retailer.captain.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SectionDetails {

    @SerializedName("section_id")
    @Expose
    private String section_id;
    @SerializedName("section_name")
    @Expose
    private String section_name;
    @SerializedName("section_photo")
    @Expose
    private String section_photo;

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public String getSection_photo() {
        return section_photo;
    }

    public void setSection_photo(String section_photo) {
        this.section_photo = section_photo;
    }

    @Override
    public String toString() {
        return "SectionDetails{" +
                "section_id='" + section_id + '\'' +
                ", section_name='" + section_name + '\'' +
                ", section_photo='" + section_photo + '\'' +
                '}';
    }
}
