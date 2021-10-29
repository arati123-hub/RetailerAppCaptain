package com.appwelt.retailer.captain.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TableListDetails {

    @SerializedName("collector_id")
    @Expose
    private String collector_id;
    @SerializedName("collector_name")
    @Expose
    private String collector_name;
    @SerializedName("collector_image")
    @Expose
    private String collector_image;
    @SerializedName("collector_type")
    @Expose
    private String collector_type;
    @SerializedName("collector_status")
    @Expose
    private String collector_status;
    @SerializedName("collector_split_series_no")
    @Expose
    private String collector_split_series_no;

    public String getCollector_status() {
        return collector_status;
    }

    public void setCollector_status(String collector_status) {
        this.collector_status = collector_status;
    }

    public String getCollector_split_series_no() {
        return collector_split_series_no;
    }

    public void setCollector_split_series_no(String collector_split_series_no) {
        this.collector_split_series_no = collector_split_series_no;
    }

    @SerializedName("food_data")
    @Expose
    private FoodData food_data;

    @SerializedName("bar_data")
    @Expose
    private BarData bar_data;

    public String getCollector_id() {
        return collector_id;
    }

    public void setCollector_id(String collector_id) {
        this.collector_id = collector_id;
    }

    public String getCollector_name() {
        return collector_name;
    }

    public void setCollector_name(String collector_name) {
        this.collector_name = collector_name;
    }

    public String getCollector_image() {
        return collector_image;
    }

    public void setCollector_image(String collector_image) {
        this.collector_image = collector_image;
    }

    public String getCollector_type() {
        return collector_type;
    }

    public void setCollector_type(String collector_type) {
        this.collector_type = collector_type;
    }

    public FoodData getFood_data() {
        return food_data;
    }

    public void setFood_data(FoodData food_data) {
        this.food_data = food_data;
    }

    public BarData getBar_data() {
        return bar_data;
    }

    public void setBar_data(BarData bar_data) {
        this.bar_data = bar_data;
    }

    public static class FoodData {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("order_id")
        @Expose
        private String order_id;
        @SerializedName("bill_id")
        @Expose
        private String bill_id;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getBill_id() {
            return bill_id;
        }

        public void setBill_id(String bill_id) {
            this.bill_id = bill_id;
        }

        @Override
        public String toString() {
            return "FoodData{" +
                    "status='" + status + '\'' +
                    ", order_id='" + order_id + '\'' +
                    ", bill_id='" + bill_id + '\'' +
                    '}';
        }
    }

    public static class BarData {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("order_id")
        @Expose
        private String order_id;
        @SerializedName("bill_id")
        @Expose
        private String bill_id;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getBill_id() {
            return bill_id;
        }

        public void setBill_id(String bill_id) {
            this.bill_id = bill_id;
        }

        @Override
        public String toString() {
            return "BarData{" +
                    "status='" + status + '\'' +
                    ", order_id='" + order_id + '\'' +
                    ", bill_id='" + bill_id + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TableListDetails{" +
                "collector_id='" + collector_id + '\'' +
                ", collector_name='" + collector_name + '\'' +
                ", collector_image='" + collector_image + '\'' +
                ", collector_type='" + collector_type + '\'' +
                ", collector_status='" + collector_status + '\'' +
                ", collector_split_series_no='" + collector_split_series_no + '\'' +
                ", food_data=" + food_data +
                ", bar_data=" + bar_data +
                '}';
    }
}