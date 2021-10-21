package com.appwelt.retailer.captain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileInfo {

    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("user_name")
    @Expose
    private String user_name;
    @SerializedName("user_address")
    @Expose
    private String user_address;
    @SerializedName("user_email")
    @Expose
    private String user_email;
    @SerializedName("user_status")
    @Expose
    private String user_status;
    @SerializedName("user_dob")
    @Expose
    private String user_dob;
    @SerializedName("user_joining_date")
    @Expose
    private String user_joining_date;
    @SerializedName("user_gender")
    @Expose
    private String user_gender;
    @SerializedName("user_experience")
    @Expose
    private String user_experience;
    @SerializedName("user_blood_grp")
    @Expose
    private String user_blood_grp;
    @SerializedName("user_mobile")
    @Expose
    private String user_mobile;
    @SerializedName("user_education")
    @Expose
    private String user_education;
    @SerializedName("user_aadhar_card")
    @Expose
    private String user_aadhar_card;
    @SerializedName("user_pan")
    @Expose
    private String user_pan;
    @SerializedName("user_maritial_status")
    @Expose
    private String user_maritial_status;
    @SerializedName("user_password")
    @Expose
    private String user_password;
    @SerializedName("user_created_on")
    @Expose
    private String user_created_on;
    @SerializedName("user_modify_on")
    @Expose
    private String user_modify_on;
    @SerializedName("user_created_by")
    @Expose
    private String user_created_by;
    @SerializedName("user_modify_by")
    @Expose
    private String user_modify_by;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getUser_dob() {
        return user_dob;
    }

    public void setUser_dob(String user_dob) {
        this.user_dob = user_dob;
    }

    public String getUser_joining_date() {
        return user_joining_date;
    }

    public void setUser_joining_date(String user_joining_date) {
        this.user_joining_date = user_joining_date;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_experience() {
        return user_experience;
    }

    public void setUser_experience(String user_experience) {
        this.user_experience = user_experience;
    }

    public String getUser_blood_grp() {
        return user_blood_grp;
    }

    public void setUser_blood_grp(String user_blood_grp) {
        this.user_blood_grp = user_blood_grp;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_education() {
        return user_education;
    }

    public void setUser_education(String user_education) {
        this.user_education = user_education;
    }

    public String getUser_aadhar_card() {
        return user_aadhar_card;
    }

    public void setUser_aadhar_card(String user_aadhar_card) {
        this.user_aadhar_card = user_aadhar_card;
    }

    public String getUser_pan() {
        return user_pan;
    }

    public void setUser_pan(String user_pan) {
        this.user_pan = user_pan;
    }

    public String getUser_maritial_status() {
        return user_maritial_status;
    }

    public void setUser_maritial_status(String user_maritial_status) {
        this.user_maritial_status = user_maritial_status;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_created_on() {
        return user_created_on;
    }

    public void setUser_created_on(String user_created_on) {
        this.user_created_on = user_created_on;
    }

    public String getUser_modify_on() {
        return user_modify_on;
    }

    public void setUser_modify_on(String user_modify_on) {
        this.user_modify_on = user_modify_on;
    }

    public String getUser_created_by() {
        return user_created_by;
    }

    public void setUser_created_by(String user_created_by) {
        this.user_created_by = user_created_by;
    }

    public String getUser_modify_by() {
        return user_modify_by;
    }

    public void setUser_modify_by(String user_modify_by) {
        this.user_modify_by = user_modify_by;
    }

    @Override
    public String toString() {
        return "ProfileInfo{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_address='" + user_address + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_status='" + user_status + '\'' +
                ", user_dob='" + user_dob + '\'' +
                ", user_joining_date='" + user_joining_date + '\'' +
                ", user_gender='" + user_gender + '\'' +
                ", user_experience='" + user_experience + '\'' +
                ", user_blood_grp='" + user_blood_grp + '\'' +
                ", user_mobile='" + user_mobile + '\'' +
                ", user_education='" + user_education + '\'' +
                ", user_aadhar_card='" + user_aadhar_card + '\'' +
                ", user_pan='" + user_pan + '\'' +
                ", user_maritial_status='" + user_maritial_status + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_created_on='" + user_created_on + '\'' +
                ", user_modify_on='" + user_modify_on + '\'' +
                ", user_created_by='" + user_created_by + '\'' +
                ", user_modify_by='" + user_modify_by + '\'' +
                '}';
    }
}
