package com.appwelt.retailer.captain.utils;

import android.app.Application;

@SuppressWarnings("ALL")
public class Constants extends Application {


    public static String FOLDER_NAME = "RetailerApp";
    public static String FOLDER_NAME_IMAGES = "images";
    public static String DATABASE_NAME = "retailerdbv.db";
    public static String SHARE_PREFERENCE_FILE = "SharePreferenceValue.json";
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
//
//    public static String districtList() {
//        if (ip != null) {
//            return ip + "districtlist";
//        } else {
//            return "";
//        }
//    }

    public static String LOG_FILE_NAME = "CaptainService.log";
    public static String cmdTest = "#TEST#";
    public static String cmdLogin = "#LOGIN#";
    public static String cmdListTables = "#LISTTABLES#";
    public static String cmdChangeTable = "#CHANGETABLE#";
    public static String cmdMergeTable = "#MERGETABLE#";
    public static String cmdClearOrderTable = "#CLEARORDERTABLE#";
    public static String cmdClearKOTTable = "#CLEARKOTTABLE#";
    public static String cmdTableOrder = "#TABLEORDER#";
    public static String cmdPrintKKOT = "#PRINTKOT#";
    public static String cmdTableKOTData = "#TABLEKOTDATA#";
    public static String cmdPrintBill = "#PRINTBILL#";
    public static String cmdPrintCheckKOT = "#PRINTCHECKKOT#";
    public static String cmdTableSplit = "#TABLESPLIT#";
    public static String cmdTableUnsplit = "#TABLEUNSPLIT#";

    public static String cmdListProducts = "#LISTPRODUCTS#";
    public static String cmdListImages = "#LISTIMAGES#";

    public static String cmdNoAvailable = "#NOTAVAILABLE#";
}