package com.appwelt.retailer.captain.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderItemDetails {

    @SerializedName("MIID")
    @Expose
    private String MIID;
    @SerializedName("MICategoryID")
    @Expose
    private String MICategoryID;
    @SerializedName("MINr")
    @Expose
    private String MINr;
    @SerializedName("MIShortName")
    @Expose
    private String MIShortName;
    @SerializedName("MIDescription")
    @Expose
    private String MIDescription;
    @SerializedName("Price")
    @Expose
    private String Price;
    @SerializedName("Quantity")
    @Expose
    private String Quantity;
    @SerializedName("rate")
    @Expose
    private String rate;

    public String getMIID() {
        return MIID;
    }

    public void setMIID(String MIID) {
        this.MIID = MIID;
    }

    public String getMICategoryID() {
        return MICategoryID;
    }

    public void setMICategoryID(String MICategoryID) {
        this.MICategoryID = MICategoryID;
    }

    public String getMINr() {
        return MINr;
    }

    public void setMINr(String MINr) {
        this.MINr = MINr;
    }

    public String getMIShortName() {
        return MIShortName;
    }

    public void setMIShortName(String MIShortName) {
        this.MIShortName = MIShortName;
    }

    public String getMIDescription() {
        return MIDescription;
    }

    public void setMIDescription(String MIDescription) {
        this.MIDescription = MIDescription;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "OrderItemDetails{" +
                "MIID='" + MIID + '\'' +
                ", MICategoryID='" + MICategoryID + '\'' +
                ", MINr='" + MINr + '\'' +
                ", MIShortName='" + MIShortName + '\'' +
                ", MIDescription='" + MIDescription + '\'' +
                ", Price='" + Price + '\'' +
                ", Quantity='" + Quantity + '\'' +
                ", rate='" + rate + '\'' +
                '}';
    }
}
