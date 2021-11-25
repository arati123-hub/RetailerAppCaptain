package com.appwelt.retailer.captain.utils;

import android.app.Application;

@SuppressWarnings("ALL")
public class Network_URLs extends Application {


    public static String FOLDER_NAME = "RetailerApp";
    public static String SHARE_PREFERENCE_FILE = "SharePreferenceValue.json";
    public static String LICENSE_FILE = "License.json";
    public static String SERVER_URL = "http://13.127.49.54/retailer/";
    public static String FILE_UPLOAD = SERVER_URL +"apiv1/uploads/fileupload.php";
    public static String API_URL = SERVER_URL + "apiv1/";
    public static String FILE_STORAGE = SERVER_URL + "storage/";

    public static String REGISTER_ORGANISATION = API_URL + "client/insert_client";
    public static String ORGANISATION_OTP = API_URL + "client/send_client_otp";
    public static String ORGANISATION_RESENDOTP = API_URL + "client/resend_client_otp";
    public static String ORGANISATION_VERIFYOTP = API_URL + "client/verify_client_otp";
    public static String ORGANISATION_REQUESTOTP = API_URL + "client/request_client_otp";
    public static String ORGANISATION_RESENDREQUESTOTP = API_URL + "client/resend_request_otp";
    public static String ORGANISATION_VERIFYREQUESTOTP = API_URL + "client/verify_request_otp";
    public static String SYNC_UPLOAD_DATABASE = API_URL + "sync/upload_database";
    public static String INSERT_DEVICE = API_URL + "device/insert_device";
    public static String ADD_DEVICE = API_URL + "device/add_device";
    public static String CHECK_DEVICE = API_URL + "device/check_device";
    public static String CHECK_DEVICE_VERSION = API_URL + "device/check_device_version";
    public static String DEVICE_VALIDITY = API_URL + "device/device_validity";
    public static String DOWNLOAD_JSON = API_URL + "retailer/downloadjson";
//    public static String DOWNLOAD_CATEGORY_LANG = API_URL + "retailer/downloadcategorylang";
//    public static String DOWNLOAD_PRODUCT = API_URL + "retailer/downloadproducts";
//    public static String DOWNLOAD_PRODUCT_LANG = API_URL + "retailer/downloadproductlang";
//    public static String DOWNLOAD_PRODUCT_CATEGORY = API_URL + "retailer/downloadproductcategory";
//    public static String CHECK_DOWNLOAD_AVAILABLE = API_URL + "retailer/check_downloadavailable";
//    public static String UPDATE_DOWNLOAD_DONE = API_URL + "retailer/update_downloaddone";
//http://13.127.49.54/retailer/apiv1/client/verify_request_otp
//    public static String districtList() {
//        if (ip != null) {
//            return ip + "districtlist";
//        } else {
//            return "";
//        }
//    }



}