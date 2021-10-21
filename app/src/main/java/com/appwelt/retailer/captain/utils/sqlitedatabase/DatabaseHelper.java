package com.appwelt.retailer.captain.utils.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.appwelt.retailer.captain.model.BillDetail;
import com.appwelt.retailer.captain.model.BillDetails;
import com.appwelt.retailer.captain.model.BillFormatDetails;
import com.appwelt.retailer.captain.model.BillOrdering;
import com.appwelt.retailer.captain.model.CategoryDetails;
import com.appwelt.retailer.captain.model.CustomerDetails;
import com.appwelt.retailer.captain.model.ExtraItem;
import com.appwelt.retailer.captain.model.OrderDetails;
import com.appwelt.retailer.captain.model.OrderExtraItem;
import com.appwelt.retailer.captain.model.OrganizationDetails;
import com.appwelt.retailer.captain.model.PrinterDetails;
import com.appwelt.retailer.captain.model.ProductCategoryDetails;
import com.appwelt.retailer.captain.model.ProductDetails;
import com.appwelt.retailer.captain.model.ProfileInfo;
import com.appwelt.retailer.captain.model.SectionDetails;
import com.appwelt.retailer.captain.model.TableListDetails;
import com.appwelt.retailer.captain.model.TaxSlab;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by my-pc on 9/4/19.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
//    public static final String GRO_DATABASE_NAME = "grocerydbv.db";
//    public static final String RES_DATABASE_NAME = "resturantdbv.db";
//    public static final String DATABASE_NAME = "retailerdbv.db";
    private String TAG = "DATABASE_RESPONSE";

    /**
     * Constructor
     *
     * @param context
     */

    public DatabaseHelper(Context context, String DB_NAME) {
        super(new DatabaseContext(context), DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       /* db.execSQL(CREATE_CASHIER_TABLE);
        db.execSQL(CREATE_SHOP_TABLE);
        db.execSQL(CREATE_SHOPCASHIERMAP_TABLE);
        db.execSQL(CREATE_BANK_TABLE);
        db.execSQL(CREATE_DAILY_COLLECTION_TABLE);
        db.execSQL(CREATE_DEPOSIT_TABLE);*/
        //insert shop
//        addShop(db);
//        addCashierDynamically(db);
//        addBankDynamically(db);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
      /*  db.execSQL(DROP_CASHIER_TABLE);
        db.execSQL(DROP_SHOP_TABLE);
        db.execSQL(DROP_MAPPING_TABLE);
        db.execSQL(DROP_BANK_TABLE);
        db.execSQL(DROP_DAILY_COLLECTION_TABLE);
        db.execSQL(DROP_DEPOSIT_TABLE);
        onCreate(db);*/

    }

    public boolean  insertDetails(ContentValues values, String tbl_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean id = db.insert(tbl_name, null, values) > 0;
        Log.i(TAG, "insertDetails: "+id);
        return id;
    }

    public String selectByID(String tableName, String fieldName, String myValue, String fieldNeed) {
        String response = "";
        String[] columns = {fieldNeed};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = fieldName + " = ? ";
        String[] selectionArgs = {myValue};

        try {
            Cursor cursor = db.query(tableName,  columns, selection,  selectionArgs,null,null,null);

            if (cursor.moveToFirst()) {
                response = cursor.getString(0);
            }else{
                response = "";
            }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error inserting " + String.valueOf(e));
        }

        return response;
    }

    public String selectByTwoID(String tableName, String fieldName1, String fieldName2, String myValue1, String myValue2, String fieldNeed) {
        String response = "";
        String[] columns = {fieldNeed};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = fieldName1 + " = ? and "+fieldName2+" = ? ";
        String[] selectionArgs = {myValue1,myValue2};

        try {
            Cursor cursor = db.query(tableName,  columns, selection,  selectionArgs,null,null,null);

            if (cursor.moveToFirst()) {
                response = cursor.getString(0);
            }else{
                response = "";
            }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error inserting " + String.valueOf(e));
        }
        return response;
    }

    public String getDetailByName(String tableName, String fieldNeed) {
        String response = "";
        String[] columns = {fieldNeed};
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.query(tableName,  columns, null,  null,null,null,null);

            if (cursor.moveToFirst()) {
                response = cursor.getString(0);
            }else{
                response = "";
            }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error inserting " + String.valueOf(e));
        }
        return response;
    }

//    public ArrayList<ProfileInfo> getUserList() {
//        ArrayList<ProfileInfo> userList = new ArrayList<ProfileInfo>();
//
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query("tbl_users",  columns,null,  null,null, null,"user_modify_on ASC");                     //The sort order
//
//        if (cursor.moveToFirst()) {
//            while ( !cursor.isAfterLast() ) {
//                ProfileInfo profileInfo = new ProfileInfo();
//                profileInfo.setUser_id(cursor.getString(cursor.getColumnIndex("user_id")));
//                profileInfo.setUser_name(cursor.getString(cursor.getColumnIndex("user_name")));
//                profileInfo.setUser_address(cursor.getString(cursor.getColumnIndex("user_address")));
//                profileInfo.setUser_email(cursor.getString(cursor.getColumnIndex("user_email")));
//                profileInfo.setUser_status(cursor.getString(cursor.getColumnIndex("user_status")));
//                profileInfo.setUser_dob(cursor.getString(cursor.getColumnIndex("user_dob")));
//                profileInfo.setUser_joining_date(cursor.getString(cursor.getColumnIndex("user_joining_date")));
//                profileInfo.setUser_gender(cursor.getString(cursor.getColumnIndex("user_gender")));
//                profileInfo.setUser_experience(cursor.getString(cursor.getColumnIndex("user_experience")));
//                profileInfo.setUser_blood_grp(cursor.getString(cursor.getColumnIndex("user_blood_grp")));
//                profileInfo.setUser_mobile(cursor.getString(cursor.getColumnIndex("user_mobile")));
//                profileInfo.setUser_education(cursor.getString(cursor.getColumnIndex("user_education")));
//                profileInfo.setUser_aadhar_card(cursor.getString(cursor.getColumnIndex("user_aadhar_card")));
//                profileInfo.setUser_pan(cursor.getString(cursor.getColumnIndex("user_pan")));
//                profileInfo.setUser_maritial_status("");
//                profileInfo.setUser_password(cursor.getString(cursor.getColumnIndex("user_password")));
//                profileInfo.setUser_created_on(cursor.getString(cursor.getColumnIndex("user_created_on")));
//                profileInfo.setUser_modify_on(cursor.getString(cursor.getColumnIndex("user_modify_on")));
//                profileInfo.setUser_created_by(cursor.getString(cursor.getColumnIndex("user_created_by")));
//                profileInfo.setUser_modify_by(cursor.getString(cursor.getColumnIndex("user_modify_by")));
//                userList.add(profileInfo);
//                cursor.moveToNext();
//            }
//        }else{
//            userList = null;
//        }
//        cursor.close();
//        return userList;
//    }
//
    public ArrayList<ExtraItem> getExtraItemList() {
        ArrayList<ExtraItem> itemList = new ArrayList<ExtraItem>();

        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("tbl_extra_item",  columns,null,  null,null, null,"extra_item_created_on ASC");                     //The sort order

        if (cursor.moveToFirst()) {
            while ( !cursor.isAfterLast() ) {
                ExtraItem obj = new ExtraItem();
                obj.setExtra_item_id(cursor.getString(cursor.getColumnIndex("extra_item_id")));
                obj.setExtra_item_name(cursor.getString(cursor.getColumnIndex("extra_item_name")));
                obj.setExtra_item_price(cursor.getString(cursor.getColumnIndex("extra_item_price")));
                obj.setExtra_item_created_by(cursor.getString(cursor.getColumnIndex("extra_item_created_by")));
                obj.setExtra_item_created_on(cursor.getString(cursor.getColumnIndex("extra_item_created_on")));
                itemList.add(obj);
                cursor.moveToNext();
            }
        }else{
            itemList = null;
        }
        cursor.close();
        return itemList;
    }

//    public ArrayList<PrinterDetails> getPrinterList() {
//        ArrayList<PrinterDetails> printerList = new ArrayList<PrinterDetails>();
//
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query("tbl_printer_details",  columns,null,  null,null, null,null);                     //The sort order
//
//        if (cursor.moveToFirst()) {
//            while ( !cursor.isAfterLast() ) {
//                PrinterDetails obj = new PrinterDetails();
//                obj.setPrinter_id(cursor.getString(cursor.getColumnIndex("printer_id")));
//                obj.setOrganisation_id(cursor.getString(cursor.getColumnIndex("organisation_id")));
//                obj.setBranch_id(cursor.getString(cursor.getColumnIndex("branch_id")));
//                obj.setPrinter_type(cursor.getString(cursor.getColumnIndex("printer_type")));
//                obj.setPrinter_name(cursor.getString(cursor.getColumnIndex("printer_name")));
//                obj.setPrinter_ip(cursor.getString(cursor.getColumnIndex("printer_ip")));
//                obj.setPrinter_port(cursor.getString(cursor.getColumnIndex("printer_port")));
//                obj.setPrinter_com_port(cursor.getString(cursor.getColumnIndex("printer_com_port")));
//                obj.setPrinter_paper_size(cursor.getString(cursor.getColumnIndex("printer_paper_size")));
//                obj.setPrinter_created_on(cursor.getString(cursor.getColumnIndex("printer_created_on")));
//                obj.setPrinter_created_by(cursor.getString(cursor.getColumnIndex("printer_created_by")));
//                printerList.add(obj);
//                cursor.moveToNext();
//            }
//        }else{
//            printerList = null;
//        }
//        cursor.close();
//        return printerList;
//    }
//
//    public PrinterDetails getPrinter(String id) {
//        PrinterDetails printer = new PrinterDetails();
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = " printer_id = ? ";
//        String[] selectionArgs = {id};
//
//        Cursor cursor = db.query("tbl_printer_details",  columns,selection,  selectionArgs,null, null,null);                     //The sort order
//
//        if (cursor.moveToFirst()) {
//            printer.setPrinter_id(cursor.getString(cursor.getColumnIndex("printer_id")));
//            printer.setOrganisation_id(cursor.getString(cursor.getColumnIndex("organisation_id")));
//            printer.setBranch_id(cursor.getString(cursor.getColumnIndex("branch_id")));
//            printer.setPrinter_type(cursor.getString(cursor.getColumnIndex("printer_type")));
//            printer.setPrinter_name(cursor.getString(cursor.getColumnIndex("printer_name")));
//            printer.setPrinter_ip(cursor.getString(cursor.getColumnIndex("printer_ip")));
//            printer.setPrinter_port(cursor.getString(cursor.getColumnIndex("printer_port")));
//            printer.setPrinter_com_port(cursor.getString(cursor.getColumnIndex("printer_com_port")));
//            printer.setPrinter_paper_size(cursor.getString(cursor.getColumnIndex("printer_paper_size")));
//            printer.setPrinter_created_on(cursor.getString(cursor.getColumnIndex("printer_created_on")));
//            printer.setPrinter_created_by(cursor.getString(cursor.getColumnIndex("printer_created_by")));
//        }else{
//            printer = null;
//        }
//        cursor.close();
//        return printer;
//    }
//
//    public ArrayList<TaxSlab> getTaxSlabList() {
//        ArrayList<TaxSlab> taxList = new ArrayList<TaxSlab>();
//
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query("tbl_tax_slab",  columns,null,  null,null, null,null);                     //The sort order
//
//        if (cursor.moveToFirst()) {
//            while ( !cursor.isAfterLast() ) {
//                TaxSlab obj = new TaxSlab();
//                obj.setTax_slab_id(cursor.getString(cursor.getColumnIndex("tax_slab_id")));
//                obj.setOrganisation_id(cursor.getString(cursor.getColumnIndex("organisation_id")));
//                obj.setBranch_id(cursor.getString(cursor.getColumnIndex("branch_id")));
//                obj.setTax_slab_name(cursor.getString(cursor.getColumnIndex("tax_slab_name")));
//                obj.setTax_slab_sgst(cursor.getString(cursor.getColumnIndex("tax_slab_sgst")));
//                obj.setTax_slab_cgst(cursor.getString(cursor.getColumnIndex("tax_slab_cgst")));
//                obj.setTax_slab_created_on(cursor.getString(cursor.getColumnIndex("tax_slab_created_on")));
//                obj.setTax_slab_created_by(cursor.getString(cursor.getColumnIndex("tax_slab_created_by")));
//                taxList.add(obj);
//                cursor.moveToNext();
//            }
//        }else{
//            taxList = null;
//        }
//        cursor.close();
//        return taxList;
//    }
//
    public ArrayList<ProductCategoryDetails> getProductCategory() {
        ArrayList<ProductCategoryDetails> productCategoryDetails = new ArrayList<>();
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = "product_category_is_deleted = ? ";
        String[] selectionArgs = {"0"};
        try {
            Cursor cursor = db.query("tbl_product_categories", columns,selection,selectionArgs,"category_id",null,"product_category_modified_on ASC");                      //The sort order

            if (cursor.moveToFirst()) {
                ProductCategoryDetails obj;
                do {
                    obj = new ProductCategoryDetails();
                    obj.setProduct_category_id(cursor.getString(cursor.getColumnIndex("product_category_id")));
                    obj.setCategory_id(cursor.getString(cursor.getColumnIndex("category_id")));
                    obj.setProduct_id(cursor.getString(cursor.getColumnIndex("product_id")));
                    obj.setProduct_category_created_on(cursor.getString(cursor.getColumnIndex("product_category_created_on")));
                    obj.setProduct_category_created_by(cursor.getString(cursor.getColumnIndex("product_category_created_by")));
                    obj.setProduct_category_modified_on(cursor.getString(cursor.getColumnIndex("product_category_modified_on")));
                    obj.setProduct_category_modified_by(cursor.getString(cursor.getColumnIndex("product_category_modified_by")));
                    obj.setProduct_category_is_deleted(cursor.getString(cursor.getColumnIndex("product_category_is_deleted")));
                    productCategoryDetails.add(obj);
                } while (cursor.moveToNext());
            }else{
                productCategoryDetails = null;
            }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error inserting " + String.valueOf(e));
        }
        return productCategoryDetails;
    }
//
    public ArrayList<ProductCategoryDetails> getProductCategoryByOrderType(String order_type) {
        String order_code = "0";
        if (order_type.equals("FOOD")){
            order_code = "1";
        }else if (order_type.equals("BAR")){
            order_code = "2";
        }
        ArrayList<ProductCategoryDetails> productCategoryDetails = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("select a.* from tbl_product_categories as a left join tbl_category as b on a.category_id=b.category_id where a.product_category_is_deleted = '0' and b.category_type = '"+order_code+"' group by b.category_id order by b.sequence_nr",null);                      //The sort order

            if (cursor.moveToFirst()) {
                ProductCategoryDetails obj;
                do {
                    obj = new ProductCategoryDetails();
                    obj.setProduct_category_id(cursor.getString(cursor.getColumnIndex("product_category_id")));
                    obj.setCategory_id(cursor.getString(cursor.getColumnIndex("category_id")));
                    obj.setProduct_id(cursor.getString(cursor.getColumnIndex("product_id")));
                    obj.setProduct_category_created_on(cursor.getString(cursor.getColumnIndex("product_category_created_on")));
                    obj.setProduct_category_created_by(cursor.getString(cursor.getColumnIndex("product_category_created_by")));
                    obj.setProduct_category_modified_on(cursor.getString(cursor.getColumnIndex("product_category_modified_on")));
                    obj.setProduct_category_modified_by(cursor.getString(cursor.getColumnIndex("product_category_modified_by")));
                    obj.setProduct_category_is_deleted(cursor.getString(cursor.getColumnIndex("product_category_is_deleted")));
                    productCategoryDetails.add(obj);
                } while (cursor.moveToNext());
            }else{
                productCategoryDetails = null;
            }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error inserting " + String.valueOf(e));
        }
        return productCategoryDetails;
    }

//    public ArrayList<ProductCategoryDetails> getProduct(String category) {
//        ArrayList<ProductCategoryDetails> productCategoryDetails = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        try {
//            String sql = "SELECT a.*,b.language_text,c.product_code,c.product_sequence,c.product_sku,c.product_bar_code,c.product_tax_slab from tbl_product_categories as a left join  tbl_language_text as b on a.product_id=b.table_id left join tbl_product as c on b.table_id=c.product_id WHERE a.product_category_is_deleted = 0 ";
//            if (!category.equals(""))
//                sql = sql + " and a.category_id = '"+category+"'";
//
//            sql = sql + " and a.product_id != 0 and b.language_reference_id like '%PN_%'";
//
//
//            Cursor cursor = db.rawQuery(sql ,null);
//            if (cursor.moveToFirst()) {
//                ProductCategoryDetails obj;
//                do {
//                    obj = new ProductCategoryDetails();
//                    obj.setProduct_category_id(cursor.getString(cursor.getColumnIndex("product_category_id")));
//                    obj.setCategory_id(cursor.getString(cursor.getColumnIndex("category_id")));
//                    obj.setProduct_id(cursor.getString(cursor.getColumnIndex("product_id")));
//                    obj.setProduct_category_created_on(cursor.getString(cursor.getColumnIndex("product_category_created_on")));
//                    obj.setProduct_category_created_by(cursor.getString(cursor.getColumnIndex("product_category_created_by")));
//                    obj.setProduct_category_modified_on(cursor.getString(cursor.getColumnIndex("product_category_modified_on")));
//                    obj.setProduct_category_modified_by(cursor.getString(cursor.getColumnIndex("product_category_modified_by")));
//                    obj.setProduct_category_is_deleted(cursor.getString(cursor.getColumnIndex("product_category_is_deleted")));
//                    obj.setLanguage_text(cursor.getString(cursor.getColumnIndex("language_text")));
//                    obj.setProduct_code(cursor.getString(cursor.getColumnIndex("product_code")));
//                    obj.setProduct_sequence(cursor.getString(cursor.getColumnIndex("product_sequence")));
//                    obj.setProduct_sku(cursor.getString(cursor.getColumnIndex("product_sku")));
//                    obj.setProduct_tax_slab(cursor.getString(cursor.getColumnIndex("product_tax_slab")));
//                    String xzy = cursor.getString(cursor.getColumnIndex("product_bar_code"));
//                    if (xzy == null){
//                        xzy = "";
//                    }
//                    obj.setProduct_bar_code(xzy);
//                    productCategoryDetails.add(obj);
//                } while (cursor.moveToNext());
//            }else{
//                productCategoryDetails = null;
//            }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error inserting " + String.valueOf(e));
//        }
//        return productCategoryDetails;
//    }
//
    public ArrayList<ProductCategoryDetails> getProductByOrderType(String category, String order_type) {
        String ot = "0";
        if (order_type.equals("FOOD")){ ot = "1";}else{ ot = "2";}
        ArrayList<ProductCategoryDetails> productCategoryDetails = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {

            String sql = "SELECT a.*,b.language_text,c.product_code,c.product_sequence,c.product_sku,c.product_bar_code " +
                    "from tbl_product_categories as a,  tbl_language_text as b, tbl_product as c,  tbl_category as e " +
                    "WHERE a.product_id=b.table_id and b.table_id=c.product_id and a.category_id = e.category_id and a.product_category_is_deleted = 0";

            if (!category.equals("")){
                sql = sql + " and a.category_id = '"+category+"'";}

            sql = sql + " and a.product_id != 0 ";

            if (!order_type.equals("")){
                sql = sql + " and e.category_type = '"+ot+"'";
            }
            sql = sql + " and b.language_reference_id like '%PN_%'";

            Cursor cursor = db.rawQuery(sql ,null);
            if (cursor.moveToFirst()) {
                ProductCategoryDetails obj;
                do {
                    obj = new ProductCategoryDetails();
                    obj.setProduct_category_id(cursor.getString(cursor.getColumnIndex("product_category_id")));
                    obj.setCategory_id(cursor.getString(cursor.getColumnIndex("category_id")));
                    obj.setProduct_id(cursor.getString(cursor.getColumnIndex("product_id")));
                    obj.setProduct_category_created_on(cursor.getString(cursor.getColumnIndex("product_category_created_on")));
                    obj.setProduct_category_created_by(cursor.getString(cursor.getColumnIndex("product_category_created_by")));
                    obj.setProduct_category_modified_on(cursor.getString(cursor.getColumnIndex("product_category_modified_on")));
                    obj.setProduct_category_modified_by(cursor.getString(cursor.getColumnIndex("product_category_modified_by")));
                    obj.setProduct_category_is_deleted(cursor.getString(cursor.getColumnIndex("product_category_is_deleted")));
                    obj.setLanguage_text(cursor.getString(cursor.getColumnIndex("language_text")));
                    obj.setProduct_code(cursor.getString(cursor.getColumnIndex("product_code")));
                    obj.setProduct_sequence(cursor.getString(cursor.getColumnIndex("product_sequence")));
                    obj.setProduct_sku(cursor.getString(cursor.getColumnIndex("product_sku")));
                    String xzy = cursor.getString(cursor.getColumnIndex("product_bar_code"));
                    if (xzy == null || xzy.equals("")){
                        xzy = "";
                    }
                    obj.setProduct_bar_code(xzy);
                    productCategoryDetails.add(obj);
                } while (cursor.moveToNext());
            }else{
                productCategoryDetails = null;
            }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error inserting " + String.valueOf(e));
        }
        return productCategoryDetails;
    }

//    public String searchBy(String myValue) {
//        String product_id = "";
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = " language_text like ? and language_reference_id like ? ";
//        String[] selectionArgs = {"%"+myValue+"%","%PN_%"};
//        try {
//            Cursor cursor = db.query("tbl_language_text", columns,selection,selectionArgs,"table_id",null,null);                      //The sort order
//
//            if (cursor.moveToFirst()) {
//                product_id = cursor.getString(cursor.getColumnIndex("table_id"));
//            }else{
//                product_id = "";
//            }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error inserting " + String.valueOf(e));
//        }
//
//        return product_id;
//    }
//
    public CustomerDetails searchCustomerBy(String searchBy, String myValue) {
        CustomerDetails obj = new CustomerDetails();
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = searchBy+" = ? ";
        String[] selectionArgs = {myValue};

        try {
            Cursor cursor = db.query("tbl_customer",  columns,  selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                obj.setCustomer_id(cursor.getString(cursor.getColumnIndex("customer_id")));
                obj.setCustomer_mobile_no(cursor.getString(cursor.getColumnIndex("customer_mobile_no")));
                obj.setCustomer_name(cursor.getString(cursor.getColumnIndex("customer_name")));
                obj.setCustomer_address(cursor.getString(cursor.getColumnIndex("customer_address")));
                obj.setBill_created_on(cursor.getString(cursor.getColumnIndex("customer_created_on")));
                obj.setBill_created_by(cursor.getString(cursor.getColumnIndex("customer_created_by")));
            }else{ obj = null; }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error Database " + String.valueOf(e));
        }

        return obj;
    }

    public ProfileInfo checkLogin(String password) {
        ProfileInfo profileInfo = new ProfileInfo();
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = "user_password" + " = ?";
        String[] selectionArgs = {password};

        try {
            Cursor cursor = db.query("tbl_users",  columns,  selection, selectionArgs, null, null, null);                      //The sort order

            if (cursor.moveToFirst()) {
                profileInfo.setUser_id(cursor.getString(cursor.getColumnIndex("user_id")));
                profileInfo.setUser_name(cursor.getString(cursor.getColumnIndex("user_name")));
                profileInfo.setUser_address(cursor.getString(cursor.getColumnIndex("user_address")));
                profileInfo.setUser_email(cursor.getString(cursor.getColumnIndex("user_email")));
                profileInfo.setUser_status(cursor.getString(cursor.getColumnIndex("user_status")));
                profileInfo.setUser_dob(cursor.getString(cursor.getColumnIndex("user_dob")));
                profileInfo.setUser_joining_date(cursor.getString(cursor.getColumnIndex("user_joining_date")));
                profileInfo.setUser_gender(cursor.getString(cursor.getColumnIndex("user_gender")));
                profileInfo.setUser_experience(cursor.getString(cursor.getColumnIndex("user_experience")));
                profileInfo.setUser_blood_grp(cursor.getString(cursor.getColumnIndex("user_blood_grp")));
                profileInfo.setUser_mobile(cursor.getString(cursor.getColumnIndex("user_mobile")));
                profileInfo.setUser_education(cursor.getString(cursor.getColumnIndex("user_education")));
                profileInfo.setUser_aadhar_card(cursor.getString(cursor.getColumnIndex("user_aadhar_card")));
                profileInfo.setUser_pan(cursor.getString(cursor.getColumnIndex("user_pan")));
                profileInfo.setUser_maritial_status(cursor.getString(cursor.getColumnIndex("user_maritial_status")));
                profileInfo.setUser_password(cursor.getString(cursor.getColumnIndex("user_password")));
                profileInfo.setUser_created_on(cursor.getString(cursor.getColumnIndex("user_created_on")));
                profileInfo.setUser_modify_on(cursor.getString(cursor.getColumnIndex("user_modify_on")));
                profileInfo.setUser_created_by(cursor.getString(cursor.getColumnIndex("user_created_by")));
                profileInfo.setUser_modify_by(cursor.getString(cursor.getColumnIndex("user_modify_by")));
            }else{ profileInfo = null; }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error inserting " + String.valueOf(e));
        }

        return profileInfo;
    }
//
//    public boolean checkPinValidation(String password) {
//        boolean status = false;
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = " user_password" + " = ?";
//        String[] selectionArgs = {password};
//
//        try {
//            Cursor cursor = db.query("tbl_users", columns,  selection, selectionArgs, null, null, null);                      //The sort order
//
//            if (cursor.moveToFirst()) { status = true; }else{ status = false; }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error inserting " + String.valueOf(e));
//        }
//
//        return status;
//    }
//
    public ArrayList<SectionDetails> getSectiondetails() {
        ArrayList<SectionDetails> sectionList = new ArrayList<SectionDetails>();

        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("tbl_sections", //Table to query
                columns,                    //columns to return
                null,                  //columns for the WHERE clause
                null,              //The values for the WHERE clause
                null, //COLUMN_CandidateAssesmentStatus,//group the rows
                null,                       //filter by row groups
                "section_id");                     //The sort order

        if (cursor.moveToFirst()) {
            SectionDetails obj;

            do {
                obj = new SectionDetails();
                obj.setSection_id(cursor.getString(cursor.getColumnIndex("section_id")));
                obj.setSection_name(cursor.getString(cursor.getColumnIndex("section_name")));
                obj.setSection_photo(Environment.getExternalStorageDirectory().getPath() + "/" + "RetailerApp/images"+cursor.getString(cursor.getColumnIndex("section_photo")));
                sectionList.add(obj);
            } while (cursor.moveToNext());
        }else{
            sectionList = null;
        }
        cursor.close();
        return sectionList;
    }

    public boolean insertDailyCash(ContentValues requestBody) {

        boolean createSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();

        createSuccessful = db.insert("tbl_dailycash", null, requestBody) > 0;
        return createSuccessful;
    }

    public boolean updateDailyCash(ContentValues requestBody, String daily_cash_id) {
        boolean createSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause =  " daily_cash_id = ? ";
        String whereArgs[] = {daily_cash_id};

        createSuccessful = db.update("tbl_dailycash", requestBody, whereClause, whereArgs) > 0;
        return createSuccessful;
    }

    public boolean updateDetails(String tblName, String fieldName, String inputValue, ContentValues requestBody) {

        boolean createSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause =  fieldName+" = ? ";
        String whereArgs[] = {inputValue};

        createSuccessful = db.update(tblName, requestBody, whereClause, whereArgs) > 0;
        return createSuccessful;
    }

    public boolean updateDetailsByTwoId(String tblName, String fieldName1, String fieldName2, String inputValue1, String inputValue2, ContentValues requestBody) {

        boolean createSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause =  fieldName1+" = ? and "+fieldName2+" = ? ";
        String whereArgs[] = {inputValue1,inputValue2};

        createSuccessful = db.update(tblName, requestBody, whereClause, whereArgs) > 0;
        return createSuccessful;
    }
    public boolean deleteDetails(String tblName, String fieldName, String inputValue) {

        boolean createSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = fieldName+" = ? ";
        String[] whereArgs = new String[] { inputValue };
        createSuccessful = db.delete(tblName, whereClause, whereArgs) > 0;
        return createSuccessful;
    }

    public boolean deleteDetailsByTwo(String tblName, String fieldName, String fieldNam1, String inputValue, String inputValue1) {

        boolean createSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = fieldName+" = ? and "+fieldNam1+" = ? ";
        String[] whereArgs = new String[] { inputValue,inputValue1 };
        createSuccessful = db.delete(tblName, whereClause, whereArgs) > 0;
        return createSuccessful;
    }
//
//    public ArrayList<DailyCashDetails> checkDailyCash(String user_id, String startDate) {
//        ArrayList<DailyCashDetails> dailyCashDetails = new ArrayList<>();
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = "closing_by_user = ? and closing_cash = ? and opening_by_user = ? and opening_at_datetime < ? ";
//        String[] selectionArgs = {"","",user_id,startDate};
//
//        try {
//            Cursor cursor = db.query("tbl_dailycash",  columns,  selection, selectionArgs, null, null, null);                      //The sort order
//
//            if (cursor.moveToFirst()) {
//                DailyCashDetails obj;
//                do {
//                    obj = new DailyCashDetails();
//                    obj.setDaily_cash_id(cursor.getString(cursor.getColumnIndex("daily_cash_id")));
//                    obj.setSection_id(cursor.getString(cursor.getColumnIndex("section_id")));
//                    obj.setOpening_by_user(cursor.getString(cursor.getColumnIndex("opening_by_user")));
//                    obj.setOpening_at_datetime(cursor.getString(cursor.getColumnIndex("opening_at_datetime")));
//                    obj.setOpening_cash(cursor.getString(cursor.getColumnIndex("opening_cash")));
//                    obj.setClosing_by_user(cursor.getString(cursor.getColumnIndex("closing_by_user")));
//                    obj.setClosing_at_datetime(cursor.getString(cursor.getColumnIndex("closing_at_datetime")));
//                    obj.setClosing_cash(cursor.getString(cursor.getColumnIndex("closing_cash")));
//                    dailyCashDetails.add(obj);
//                } while (cursor.moveToNext());
//            }else{
//                dailyCashDetails = null;
//            }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error inserting " + String.valueOf(e));
//        }
//
//        return dailyCashDetails;
//    }
//
    public ArrayList<TableListDetails> getTableList() {
        ArrayList<TableListDetails> tableListDetails = new ArrayList<>();
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.query("tbl_order_collectors", columns,null,null,null,null,null);                      //The sort order

            if (cursor.moveToFirst()) {
                TableListDetails obj;
                do {
                    obj = new TableListDetails();
                    obj.setCollector_id(cursor.getString(cursor.getColumnIndex("collector_id")));
                    obj.setCollector_name(cursor.getString(cursor.getColumnIndex("collector_name")));
                    obj.setCollector_image(cursor.getString(cursor.getColumnIndex("collector_image")));
                    obj.setCollector_type(cursor.getString(cursor.getColumnIndex("collector_type")));
                    tableListDetails.add(obj);
                } while (cursor.moveToNext());
            }else{
                tableListDetails = null;
            }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error inserting " + String.valueOf(e));
        }

        return tableListDetails;
    }
//
//    public ArrayList<ParcelModel> getParcelList(String section) {
//        ArrayList<ParcelModel> parcelListDetails = new ArrayList<>();
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = " section_id = ? ";
//        String[] selectionArgs = {section};
//
//        try {
//            Cursor cursor = db.query("tbl_parcel",  columns, selection,  selectionArgs,null,null,null);
//
//            if (cursor.moveToFirst()) {
//                ParcelModel obj;
//                do {
//                    obj = new ParcelModel();
//                    obj.setParcel_id(cursor.getString(cursor.getColumnIndex("parcel_id")));
//                    obj.setParcel_code(cursor.getString(cursor.getColumnIndex("parcel_code")));
//                    obj.setSection_id(cursor.getString(cursor.getColumnIndex("section_id")));
//                    obj.setParcel_customer_id(cursor.getString(cursor.getColumnIndex("parcel_customer_id")));
//                    obj.setParcel_created_by(cursor.getString(cursor.getColumnIndex("parcel_created_by")));
//                    obj.setParcel_created_on(cursor.getString(cursor.getColumnIndex("parcel_created_on")));
//                    parcelListDetails.add(obj);
//                } while (cursor.moveToNext());
//            }else{
//                parcelListDetails = null;
//            }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error inserting " + String.valueOf(e));
//        }
//
//        return parcelListDetails;
//    }
//
//    public ArrayList<OrganizationDetails> getOrganizationdetails() {
//        ArrayList<OrganizationDetails> organizationList = new ArrayList<OrganizationDetails>();
//
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query("tbl_organisation", //Table to query
//                columns,                    //columns to return
//                null,                  //columns for the WHERE clause
//                null,              //The values for the WHERE clause
//                null, //COLUMN_CandidateAssesmentStatus,//group the rows
//                null,                       //filter by row groups
//                null);                     //The sort order
//
//        if (cursor.moveToFirst()) {
//            OrganizationDetails obj;
//
//            do {
//                obj = new OrganizationDetails();
//                obj.setOrganisation_id(cursor.getString(cursor.getColumnIndex("organisation_id")));
//                obj.setOrganisation_name(cursor.getString(cursor.getColumnIndex("organisation_name")));
//                obj.setOrganisation_type(cursor.getString(cursor.getColumnIndex("organisation_category")));
//                obj.setOrganisation_logo(cursor.getString(cursor.getColumnIndex("organisation_logo")));
//                obj.setOrganisation_reg_address(cursor.getString(cursor.getColumnIndex("organisation_reg_address")));
//                obj.setOrganisation_reg_email(cursor.getString(cursor.getColumnIndex("organisation_reg_email")));
//                obj.setOrganisation_reg_number(cursor.getString(cursor.getColumnIndex("organisation_reg_number")));
//                obj.setOrganisation_gst(cursor.getString(cursor.getColumnIndex("organisation_gst")));
//                obj.setOrganisation_cin(cursor.getString(cursor.getColumnIndex("organisation_cin")));
//                obj.setOrganisation_pan(cursor.getString(cursor.getColumnIndex("organisation_pan")));
//                obj.setOrganisation_tan(cursor.getString(cursor.getColumnIndex("organisation_tan")));
//                obj.setOrganisation_modified_on(cursor.getString(cursor.getColumnIndex("organisation_modified_on")));
//                obj.setOrganisation_created_by(cursor.getString(cursor.getColumnIndex("organisation_created_by")));
//                obj.setOrganisation_modified_by(cursor.getString(cursor.getColumnIndex("organisation_modified_by")));
//                obj.setOrganisation_is_deleted(cursor.getString(cursor.getColumnIndex("organisation_is_deleted")));
//                obj.setOrganisation_created_on(cursor.getString(cursor.getColumnIndex("organisation_created_on")));
//                obj.setOrganisation_status(cursor.getString(cursor.getColumnIndex("organisation_status")));
//                organizationList.add(obj);
//            } while (cursor.moveToNext());
//        }else{
//            organizationList = null;
//        }
//        cursor.close();
//        return organizationList;
//    }
//
//    public ArrayList<RolesDetail> getRolesDetail() {
//        ArrayList<RolesDetail> rolesList = new ArrayList<RolesDetail>();
//
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query("tbl_roles", columns,null, null,null,null,null);
//        if (cursor.moveToFirst()) {
//            RolesDetail obj;
//
//            do {
//                obj = new RolesDetail();
//                obj.setRole_id(cursor.getString(cursor.getColumnIndex("role_id")));
//                obj.setRole_name(cursor.getString(cursor.getColumnIndex("role_name")));
//                obj.setRole_created_on(cursor.getString(cursor.getColumnIndex("role_created_on")));
//                obj.setRole_created_by(cursor.getString(cursor.getColumnIndex("role_created_by")));
//                obj.setRole_modified_on(cursor.getString(cursor.getColumnIndex("role_modified_on")));
//                obj.setRole_modified_by(cursor.getString(cursor.getColumnIndex("role_modified_by")));
//                obj.setRole_is_deleted(cursor.getString(cursor.getColumnIndex("role_is_deleted")));
//                rolesList.add(obj);
//            } while (cursor.moveToNext());
//        }else{
//            rolesList = null;
//        }
//        cursor.close();
//        return rolesList;
//    }
//
    public OrderExtraItem checkExtraItemExist(String order_id, String name) {
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = " order_id = ? and order_extra_item_name = ? ";
        String[] selectionArgs = {order_id,name};

        OrderExtraItem obj = new OrderExtraItem();
        try {
            Cursor cursor = db.query("tbl_order_extra_item",  columns,  selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    obj.setOrder_extra_item_id(cursor.getString(cursor.getColumnIndex("order_extra_item_id")));
                    obj.setOrder_id(cursor.getString(cursor.getColumnIndex("order_id")));
                    obj.setOrder_extra_item_name(cursor.getString(cursor.getColumnIndex("order_extra_item_name")));
                    obj.setOrder_extra_item_qty(cursor.getString(cursor.getColumnIndex("order_extra_item_qty")));
                    obj.setOrder_extra_item_price(cursor.getString(cursor.getColumnIndex("order_extra_item_price")));
                    obj.setOrder_extra_item_created_by(cursor.getString(cursor.getColumnIndex("order_extra_item_created_by")));
                    obj.setOrder_extra_item_created_on(cursor.getString(cursor.getColumnIndex("order_extra_item_created_on")));
                } while (cursor.moveToNext());
            }else{ obj = null; }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error Database " + String.valueOf(e));
        }

        return obj;
    }

//    public BillFormatDetails getBillFormatDetails(String bill_format) {
//        BillFormatDetails obj ;
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = "bill_format_id" + " = ? ";
//        String[] selectionArgs = {bill_format};
//
//        Cursor cursor = db.query("tbl_bill_format", columns, selection,selectionArgs, null,null,  null);                     //The sort order
//
//        if (cursor.moveToFirst()) {
//            do {
//                obj = new BillFormatDetails();
//                obj.setBill_format_id(cursor.getString(cursor.getColumnIndex("bill_format_id")));
//                obj.setBranch_id(cursor.getString(cursor.getColumnIndex("branch_id")));
//                obj.setOrganisation_id(cursor.getString(cursor.getColumnIndex("organisation_id")));
//                obj.setBill_printer_id(cursor.getString(cursor.getColumnIndex("bill_printer_id")));
//                obj.setBill_title(cursor.getString(cursor.getColumnIndex("bill_title")));
//                obj.setBill_address_line1(cursor.getString(cursor.getColumnIndex("bill_address_line1")));
//                obj.setBill_address_line2(cursor.getString(cursor.getColumnIndex("bill_address_line2")));
//                obj.setBill_telephone(cursor.getString(cursor.getColumnIndex("bill_telephone")));
//                obj.setBill_gst_no(cursor.getString(cursor.getColumnIndex("bill_gst_no")));
//                obj.setBill_head(cursor.getString(cursor.getColumnIndex("bill_head")));
//                obj.setBill_special_note_line1(cursor.getString(cursor.getColumnIndex("bill_special_note_line1")));
//                obj.setBill_special_note_line2(cursor.getString(cursor.getColumnIndex("bill_special_note_line2")));
//                obj.setBill_thank_you_line(cursor.getString(cursor.getColumnIndex("bill_thank_you_line")));
//                obj.setBill_format_created_by(cursor.getString(cursor.getColumnIndex("bill_format_created_by")));
//                obj.setBill_format_created_on(cursor.getString(cursor.getColumnIndex("bill_format_created_on")));
//                obj.setBill_format_modified_by(cursor.getString(cursor.getColumnIndex("bill_format_modified_by")));
//                obj.setBill_format_modified_on(cursor.getString(cursor.getColumnIndex("bill_format_modified_on")));
//            } while (cursor.moveToNext());
//        }else{
//            obj = null;
//        }
//        cursor.close();
//        return obj;
//    }
//
    public BillFormatDetails getBillFormatByOrg(String organisation_id) {
        BillFormatDetails obj ;
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = "organisation_id" + " = ? ";
        String[] selectionArgs = {organisation_id};

        Cursor cursor = db.query("tbl_bill_format", columns, selection,selectionArgs, null,null,  null);                     //The sort order

        if (cursor.moveToFirst()) {
            do {
                obj = new BillFormatDetails();
                obj.setBill_format_id(cursor.getString(cursor.getColumnIndex("bill_format_id")));
                obj.setBranch_id(cursor.getString(cursor.getColumnIndex("branch_id")));
                obj.setOrganisation_id(cursor.getString(cursor.getColumnIndex("organisation_id")));
                obj.setBill_printer_id(cursor.getString(cursor.getColumnIndex("bill_printer_id")));
                obj.setBill_title(cursor.getString(cursor.getColumnIndex("bill_title")));
                obj.setBill_address_line1(cursor.getString(cursor.getColumnIndex("bill_address_line1")));
                obj.setBill_address_line2(cursor.getString(cursor.getColumnIndex("bill_address_line2")));
                obj.setBill_telephone(cursor.getString(cursor.getColumnIndex("bill_telephone")));
                obj.setBill_gst_no(cursor.getString(cursor.getColumnIndex("bill_gst_no")));
                obj.setBill_head(cursor.getString(cursor.getColumnIndex("bill_head")));
                obj.setBill_special_note_line1(cursor.getString(cursor.getColumnIndex("bill_special_note_line1")));
                obj.setBill_special_note_line2(cursor.getString(cursor.getColumnIndex("bill_special_note_line2")));
                obj.setBill_thank_you_line(cursor.getString(cursor.getColumnIndex("bill_thank_you_line")));
                obj.setBill_format_created_by(cursor.getString(cursor.getColumnIndex("bill_format_created_by")));
                obj.setBill_format_created_on(cursor.getString(cursor.getColumnIndex("bill_format_created_on")));
                obj.setBill_format_modified_by(cursor.getString(cursor.getColumnIndex("bill_format_modified_by")));
                obj.setBill_format_modified_on(cursor.getString(cursor.getColumnIndex("bill_format_modified_on")));
            } while (cursor.moveToNext());
        }else{
            obj = null;
        }
        cursor.close();
        return obj;
    }

//    public ArrayList<BillPaymentDetails> getBillPaymentByDailyCashID(String id) {
//        ArrayList<BillPaymentDetails> billPaymentList = new ArrayList<>();
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = " daily_cash_id like ? ";
//        String[] selectionArgs = {id};
//
//        try {
//            Cursor cursor = db.query("tbl_bill_payment",  columns,  selection, selectionArgs, null, null, null);
//            if (cursor.moveToFirst()) {
//                do {
//                    BillPaymentDetails obj = new BillPaymentDetails();
//                    obj.setBill_payment_id(cursor.getString(cursor.getColumnIndex("bill_payment_id")));
//                    obj.setBill_id(cursor.getString(cursor.getColumnIndex("bill_id")));
//                    obj.setOrganisation_id(cursor.getString(cursor.getColumnIndex("organisation_id")));
//                    obj.setBranch_id(cursor.getString(cursor.getColumnIndex("branch_id")));
//                    obj.setBill_payment_mode(cursor.getString(cursor.getColumnIndex("bill_payment_mode")));
//                    obj.setBill_transaction_details(cursor.getString(cursor.getColumnIndex("bill_transaction_details")));
//                    obj.setBill_transaction_no(cursor.getString(cursor.getColumnIndex("bill_transaction_no")));
//                    obj.setBill_paid_amount(cursor.getString(cursor.getColumnIndex("bill_paid_amount")));
//                    obj.setBill_paid_datetime(cursor.getString(cursor.getColumnIndex("bill_paid_datetime")));
//                    obj.setBill_paid_by(cursor.getString(cursor.getColumnIndex("bill_paid_by")));
//                    billPaymentList.add(obj);
//                } while (cursor.moveToNext());
//            }else{ billPaymentList = null; }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error Database " + String.valueOf(e));
//        }
//
//        return billPaymentList;
//    }
//
//    public ArrayList<BillPaymentDetails> getBillPayments(String BillId) {
//        ArrayList<BillPaymentDetails> billPaymentList = new ArrayList<>();
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = " bill_id like ? ";
//        String[] selectionArgs = {"%"+BillId+"%"};
//
//        try {
//            Cursor cursor = db.query("tbl_bill_payment",  columns,  selection, selectionArgs, null, null, null);
//            if (cursor.moveToFirst()) {
//                do {
//                    BillPaymentDetails obj = new BillPaymentDetails();
//                    obj.setBill_payment_id(cursor.getString(cursor.getColumnIndex("bill_payment_id")));
//                    obj.setBill_id(cursor.getString(cursor.getColumnIndex("bill_id")));
//                    obj.setOrganisation_id(cursor.getString(cursor.getColumnIndex("organisation_id")));
//                    obj.setBranch_id(cursor.getString(cursor.getColumnIndex("branch_id")));
//                    obj.setBill_payment_mode(cursor.getString(cursor.getColumnIndex("bill_payment_mode")));
//                    obj.setBill_transaction_details(cursor.getString(cursor.getColumnIndex("bill_transaction_details")));
//                    obj.setBill_transaction_no(cursor.getString(cursor.getColumnIndex("bill_transaction_no")));
//                    obj.setBill_paid_amount(cursor.getString(cursor.getColumnIndex("bill_paid_amount")));
//                    obj.setBill_paid_datetime(cursor.getString(cursor.getColumnIndex("bill_paid_datetime")));
//                    obj.setBill_paid_by(cursor.getString(cursor.getColumnIndex("bill_paid_by")));
//                    billPaymentList.add(obj);
//                } while (cursor.moveToNext());
//            }else{ billPaymentList = null; }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error Database " + String.valueOf(e));
//        }
//
//        return billPaymentList;
//    }
//
//    public ArrayList<BillPaymentDetails> getOrderBillPayments(String BillId) {
//        ArrayList<BillPaymentDetails> billPaymentList = new ArrayList<>();
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = " bill_id = ? ";
//        String[] selectionArgs = {BillId};
//
//        try {
//            Cursor cursor = db.query("tbl_bill_payment",  columns,  selection, selectionArgs, null, null, null);
//            if (cursor.moveToFirst()) {
//                do {
//                    BillPaymentDetails obj = new BillPaymentDetails();
//                    obj.setBill_payment_id(cursor.getString(cursor.getColumnIndex("bill_payment_id")));
//                    obj.setBill_id(cursor.getString(cursor.getColumnIndex("bill_id")));
//                    obj.setOrganisation_id(cursor.getString(cursor.getColumnIndex("organisation_id")));
//                    obj.setBranch_id(cursor.getString(cursor.getColumnIndex("branch_id")));
//                    obj.setBill_payment_mode(cursor.getString(cursor.getColumnIndex("bill_payment_mode")));
//                    obj.setBill_transaction_details(cursor.getString(cursor.getColumnIndex("bill_transaction_details")));
//                    obj.setBill_transaction_no(cursor.getString(cursor.getColumnIndex("bill_transaction_no")));
//                    obj.setBill_paid_amount(cursor.getString(cursor.getColumnIndex("bill_paid_amount")));
//                    obj.setBill_paid_datetime(cursor.getString(cursor.getColumnIndex("bill_paid_datetime")));
//                    obj.setBill_paid_by(cursor.getString(cursor.getColumnIndex("bill_paid_by")));
//                    billPaymentList.add(obj);
//                } while (cursor.moveToNext());
//            }else{ billPaymentList = null; }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error Database " + String.valueOf(e));
//        }
//
//        return billPaymentList;
//    }
//
    public BillDetails getBillDetails(String orderBillId) {
        BillDetails billDetails = new BillDetails();
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = " order_id = ? ";
        String[] selectionArgs = {orderBillId};

        try {
            Cursor cursor = db.query("tbl_bill",  columns,  selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                billDetails.setBill_id(cursor.getString(cursor.getColumnIndex("bill_id")));
                billDetails.setOrder_id(cursor.getString(cursor.getColumnIndex("order_id")));
                billDetails.setOrganisation_id(cursor.getString(cursor.getColumnIndex("organisation_id")));
                billDetails.setBranch_id(cursor.getString(cursor.getColumnIndex("branch_id")));
                billDetails.setCustomer_name(cursor.getString(cursor.getColumnIndex("customer_name")));
                billDetails.setCustomer_mobile_no(cursor.getString(cursor.getColumnIndex("customer_mobile_no")));
                billDetails.setBill_amount(cursor.getString(cursor.getColumnIndex("bill_amount")));
                billDetails.setBill_discount_rate(cursor.getString(cursor.getColumnIndex("bill_discount_rate")));
                billDetails.setBill_discount_amount(cursor.getString(cursor.getColumnIndex("bill_discount_amount")));
                billDetails.setBill_discount_reason(cursor.getString(cursor.getColumnIndex("bill_discount_reason")));
                billDetails.setBill_tip(cursor.getString(cursor.getColumnIndex("bill_tip")));
                billDetails.setBill_created_on(cursor.getString(cursor.getColumnIndex("bill_created_on")));
                billDetails.setBill_created_by(cursor.getString(cursor.getColumnIndex("bill_created_by")));
            }else{ billDetails = null; }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error Database " + String.valueOf(e));
        }

        return billDetails;
    }

    public BillDetails getBillDetailsByBillID(String BillId) {
        BillDetails billDetails = new BillDetails();
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = " bill_id = ? ";
        String[] selectionArgs = {BillId};

        try {
            Cursor cursor = db.query("tbl_bill",  columns,  selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                billDetails.setBill_id(cursor.getString(cursor.getColumnIndex("bill_id")));
                billDetails.setOrder_id(cursor.getString(cursor.getColumnIndex("order_id")));
                billDetails.setOrganisation_id(cursor.getString(cursor.getColumnIndex("organisation_id")));
                billDetails.setBranch_id(cursor.getString(cursor.getColumnIndex("branch_id")));
                billDetails.setCustomer_name(cursor.getString(cursor.getColumnIndex("customer_name")));
                billDetails.setCustomer_mobile_no(cursor.getString(cursor.getColumnIndex("customer_mobile_no")));
                billDetails.setBill_amount(cursor.getString(cursor.getColumnIndex("bill_amount")));
                billDetails.setBill_discount_rate(cursor.getString(cursor.getColumnIndex("bill_discount_rate")));
                billDetails.setBill_discount_amount(cursor.getString(cursor.getColumnIndex("bill_discount_amount")));
                billDetails.setBill_discount_reason(cursor.getString(cursor.getColumnIndex("bill_discount_reason")));
                billDetails.setBill_tip(cursor.getString(cursor.getColumnIndex("bill_tip")));
                billDetails.setBill_created_on(cursor.getString(cursor.getColumnIndex("bill_created_on")));
                billDetails.setBill_created_by(cursor.getString(cursor.getColumnIndex("bill_created_by")));
            }else{ billDetails = null; }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error Database " + String.valueOf(e));
        }

        return billDetails;
    }
//
    public BillDetails getBillHeaderByBillID(String BillId) {
        BillDetails billDetails = new BillDetails();
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = " bill_id = ? ";
        String[] selectionArgs = {BillId};

        try {
            Cursor cursor = db.query("tbl_food_bill_header",  columns,  selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                billDetails.setBill_id(cursor.getString(cursor.getColumnIndex("bill_id")));
                billDetails.setOrganisation_id(cursor.getString(cursor.getColumnIndex("organisation_id")));
                billDetails.setBranch_id(cursor.getString(cursor.getColumnIndex("branch_id")));
                billDetails.setCustomer_name(cursor.getString(cursor.getColumnIndex("customer_name")));
                billDetails.setCustomer_mobile_no(cursor.getString(cursor.getColumnIndex("customer_mobile_no")));
                billDetails.setBill_amount(cursor.getString(cursor.getColumnIndex("bill_amount")));
                billDetails.setBill_discount_rate(cursor.getString(cursor.getColumnIndex("bill_discount_rate")));
                billDetails.setBill_discount_amount(cursor.getString(cursor.getColumnIndex("bill_discount_amount")));
                billDetails.setBill_discount_reason(cursor.getString(cursor.getColumnIndex("bill_discount_reason")));
                billDetails.setBill_tip(cursor.getString(cursor.getColumnIndex("bill_tip")));
                billDetails.setBill_created_on(cursor.getString(cursor.getColumnIndex("bill_created_on")));
                billDetails.setBill_created_by(cursor.getString(cursor.getColumnIndex("bill_created_by")));
            }else{ billDetails = null; }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error Database " + String.valueOf(e));
        }

        return billDetails;
    }

    public BillDetails getParcelBillHeaderByBillID(String BillId) {
        BillDetails billDetails = new BillDetails();
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = " bill_id = ? ";
        String[] selectionArgs = {BillId};

        try {
            Cursor cursor = db.query("tbl_parcel_food_bill_header",  columns,  selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                billDetails.setBill_id(cursor.getString(cursor.getColumnIndex("bill_id")));
                billDetails.setOrganisation_id(cursor.getString(cursor.getColumnIndex("organisation_id")));
                billDetails.setBranch_id(cursor.getString(cursor.getColumnIndex("branch_id")));
                billDetails.setCustomer_name(cursor.getString(cursor.getColumnIndex("customer_name")));
                billDetails.setCustomer_mobile_no(cursor.getString(cursor.getColumnIndex("customer_mobile_no")));
                billDetails.setBill_amount(cursor.getString(cursor.getColumnIndex("bill_amount")));
                billDetails.setBill_discount_rate(cursor.getString(cursor.getColumnIndex("bill_discount_rate")));
                billDetails.setBill_discount_amount(cursor.getString(cursor.getColumnIndex("bill_discount_amount")));
                billDetails.setBill_discount_reason(cursor.getString(cursor.getColumnIndex("bill_discount_reason")));
                billDetails.setBill_tip(cursor.getString(cursor.getColumnIndex("bill_tip")));
                billDetails.setBill_created_on(cursor.getString(cursor.getColumnIndex("bill_created_on")));
                billDetails.setBill_created_by(cursor.getString(cursor.getColumnIndex("bill_created_by")));
            }else{ billDetails = null; }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error Database " + String.valueOf(e));
        }

        return billDetails;
    }

//    public KOTFormat getKOTFormat(String organisation_id, String branch_id) {
//        KOTFormat kotFormat = new KOTFormat();
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = " organisation_id = ? and branch_id = ? ";
//        String[] selectionArgs = {organisation_id,branch_id};
//
//        try {
//            Cursor cursor = db.query("tbl_kot_format",  columns,  selection, selectionArgs, null, null, null);
//            if (cursor.moveToFirst()) {
//                kotFormat.setKot_format_id(cursor.getString(cursor.getColumnIndex("kot_format_id")));
//                kotFormat.setOrganisation_id(cursor.getString(cursor.getColumnIndex("organisation_id")));
//                kotFormat.setBranch_id(cursor.getString(cursor.getColumnIndex("branch_id")));
//                kotFormat.setKot_format_print(cursor.getString(cursor.getColumnIndex("kot_format_print")));
//                kotFormat.setKot_format_printer_id(cursor.getString(cursor.getColumnIndex("kot_format_printer_id")));
//                kotFormat.setKot_format_header(cursor.getString(cursor.getColumnIndex("kot_format_header")));
//                kotFormat.setKot_format_created_on(cursor.getString(cursor.getColumnIndex("kot_format_created_on")));
//                kotFormat.setKot_format_created_by(cursor.getString(cursor.getColumnIndex("kot_format_created_by")));
//            }else{ kotFormat = null; }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error Database " + String.valueOf(e));
//        }
//
//        return kotFormat;
//    }
//
    public ArrayList<BillOrdering> getBillOrdering(String order_by) {
        ArrayList<BillOrdering> billList = new ArrayList<>();
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.query("tbl_bill_sections",  columns,  null, null, null, null, order_by);
            if (cursor.moveToFirst()) {
                do {
                    BillOrdering billOrdering = new BillOrdering();
                    billOrdering.setBill_section_id(cursor.getString(cursor.getColumnIndex("bill_section_id")));
                    billOrdering.setBill_section_name(cursor.getString(cursor.getColumnIndex("bill_section_name")));
                    billOrdering.setBill_section_order(cursor.getString(cursor.getColumnIndex("bill_section_order")));
                    billOrdering.setBill_section_default_order(cursor.getString(cursor.getColumnIndex("bill_section_default_order")));
                    billOrdering.setBill_section_visibility(cursor.getString(cursor.getColumnIndex("bill_section_visibility")));
                    billList.add(billOrdering);
                } while (cursor.moveToNext());

            }else{ billList = null; }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error Database " + String.valueOf(e));
        }

        return billList;
    }
//
//    public ArrayList<Bill> getBillDetailsByDailyCashID(String tbl_name, String id) {
//        ArrayList<Bill> billList = new ArrayList<>();
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = " daily_cash_id = ? ";
//        String[] selectionArgs = {id};
//
//        try {
//            Cursor cursor = db.query(tbl_name,  columns,  selection, selectionArgs, null, null, null);
//            if (cursor.moveToFirst()) {
//                do {
//                    Bill obj = new Bill();
//                    obj.setBill_id(cursor.getString(cursor.getColumnIndex("bill_id")));
//                    obj.setOrganisation_id(cursor.getString(cursor.getColumnIndex("organisation_id")));
//                    obj.setBranch_id(cursor.getString(cursor.getColumnIndex("branch_id")));
//                    obj.setDaily_cash_id(cursor.getString(cursor.getColumnIndex("daily_cash_id")));
//                    obj.setCustomer_name(cursor.getString(cursor.getColumnIndex("customer_name")));
//                    obj.setCustomer_mobile_no(cursor.getString(cursor.getColumnIndex("customer_mobile_no")));
//                    obj.setBill_amount(cursor.getString(cursor.getColumnIndex("bill_amount")));
//                    obj.setBill_discount_rate(cursor.getString(cursor.getColumnIndex("bill_discount_rate")));
//                    obj.setBill_discount_amount(cursor.getString(cursor.getColumnIndex("bill_discount_amount")));
//                    obj.setBill_discount_reason(cursor.getString(cursor.getColumnIndex("bill_discount_reason")));
//                    obj.setBill_tip(cursor.getString(cursor.getColumnIndex("bill_tip")));
//                    obj.setBill_created_on(cursor.getString(cursor.getColumnIndex("bill_created_on")));
//                    obj.setBill_created_by(cursor.getString(cursor.getColumnIndex("bill_created_by")));
//                    billList.add(obj);
//                } while (cursor.moveToNext());
//            }else{ billList = null; }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error Database " + String.valueOf(e));
//        }
//
//        return billList;
//    }
//
//    public DailyCashDetails getDailyCashData(String user_id, String section, String selected_date) {
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = "opening_by_user = ? and opening_at_datetime = ? and section_id = ? ";
//        String[] selectionArgs = {user_id,selected_date,section};
//
//        DailyCashDetails obj = null;
//
//        try {
//            Cursor cursor = db.query("tbl_dailycash",  columns,  selection, selectionArgs, null, null, null);                      //The sort order
//
//            if (cursor.moveToFirst()) {
//
//                do {
//                    obj = new DailyCashDetails();
//                    obj.setDaily_cash_id(cursor.getString(cursor.getColumnIndex("daily_cash_id")));
//                    obj.setSection_id(cursor.getString(cursor.getColumnIndex("section_id")));
//                    obj.setOpening_by_user(cursor.getString(cursor.getColumnIndex("opening_by_user")));
//                    obj.setOpening_at_datetime(cursor.getString(cursor.getColumnIndex("opening_at_datetime")));
//                    obj.setOpening_cash(cursor.getString(cursor.getColumnIndex("opening_cash")));
//                    obj.setClosing_by_user(cursor.getString(cursor.getColumnIndex("closing_by_user")));
//                    obj.setClosing_at_datetime(cursor.getString(cursor.getColumnIndex("closing_at_datetime")));
//                    obj.setClosing_cash(cursor.getString(cursor.getColumnIndex("closing_cash")));
//
//                } while (cursor.moveToNext());
//            }else{
//                obj = null;
//            }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error inserting " + String.valueOf(e));
//        }
//
//        return obj;
//    }
//
    public String checkTableStatus(String collector_id, String section,String user_id, String order_type, String requiredField) {
        String response = "";
        String[] columns = {requiredField};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = " table_id = ? and section_id = ? and user_id = ? and order_type = ?";
        String[] selectionArgs = {collector_id,section,user_id,order_type};

        try {
            Cursor cursor = db.query("tbl_table_order",  columns, selection,  selectionArgs,null,null,null);

            if (cursor.moveToFirst()) {
                response = cursor.getString(0);
            }else{
                response = "";
            }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error inserting " + String.valueOf(e));
        }
        return response;
    }
    public String checkTableStatus1(String collector_id, String section,String order_type, String requiredField) {
        String response = "";
        String[] columns = {requiredField};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = " table_id = ? and section_id = ? and order_type = ?";
        String[] selectionArgs = {collector_id,section,order_type};

        try {
            Cursor cursor = db.query("tbl_table_order",  columns, selection,  selectionArgs,null,null,null);

            if (cursor.moveToFirst()) {
                response = cursor.getString(0);
            }else{
                response = "";
            }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error inserting " + String.valueOf(e));
        }
        return response;
    }
//
    public String checkParcelStatus(String parcel_id, String order_type,String section, String requiredField) {
        String response = "";
        String[] columns = {requiredField};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = " parcel_id = ? and parcel_order_type = ? and parcel_section_id = ? ";
        String[] selectionArgs = {parcel_id,order_type,section};

        try {
            Cursor cursor = db.query("tbl_parcel_order",  columns, selection,  selectionArgs,null,null,null);

            if (cursor.moveToFirst()) {
                response = cursor.getString(0);
            }else{
                response = "";
            }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error inserting " + String.valueOf(e));
        }
        return response;
    }
//
//    public ArrayList<TableOrderDetails> getTableForSection(String section) {
//        ArrayList<TableOrderDetails> tableOrderDetails = new ArrayList<>();
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = " section_id = ? ";
//        String[] selectionArgs = {section};
//
//        try {
//            Cursor cursor = db.query("tbl_table_order",  columns, selection,  selectionArgs,null,null,null);
//
//            if (cursor.moveToFirst()) {
//                TableOrderDetails obj;
//                do {
//                    obj = new TableOrderDetails();
//                    obj.setTable_id(cursor.getString(cursor.getColumnIndex("table_id")));
//                    obj.setOrder_id(cursor.getString(cursor.getColumnIndex("order_id")));
//                    obj.setSection_id(cursor.getString(cursor.getColumnIndex("section_id")));
//                    obj.setUser_id(cursor.getString(cursor.getColumnIndex("user_id")));
//                    obj.setTable_status(cursor.getString(cursor.getColumnIndex("table_status")));
//                    obj.setCreated_on(cursor.getString(cursor.getColumnIndex("created_on")));
//                    tableOrderDetails.add(obj);
//
//                    Log.e("DailyCashDetials", tableOrderDetails.toString());
//                } while (cursor.moveToNext());
//            }else{
//                tableOrderDetails = null;
//            }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error inserting " + String.valueOf(e));
//        }
//        return tableOrderDetails;
//    }
//
//    public ArrayList<BillDetails> getOrderHistory( String startDate, String endDate, String table_name) {
//        ArrayList<BillDetails> billDetails = new ArrayList<>();
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = " bill_created_on >= ? and bill_created_on <= ? ";
//        String[] selectionArgs = {startDate,endDate};
//
//        try {
//            Cursor cursor = db.query(table_name,  columns,  selection, selectionArgs, null, null, null);
//            if (cursor.moveToFirst()) {
//                do {
//                    BillDetails obj = new BillDetails();
//                    obj.setBill_id(cursor.getString(cursor.getColumnIndex("bill_id")));
//                    obj.setOrganisation_id(cursor.getString(cursor.getColumnIndex("organisation_id")));
//                    obj.setBranch_id(cursor.getString(cursor.getColumnIndex("branch_id")));
//                    obj.setCustomer_name(cursor.getString(cursor.getColumnIndex("customer_name")));
//                    obj.setCustomer_mobile_no(cursor.getString(cursor.getColumnIndex("customer_mobile_no")));
//                    obj.setBill_amount(cursor.getString(cursor.getColumnIndex("bill_amount")));
//                    obj.setBill_discount_rate(cursor.getString(cursor.getColumnIndex("bill_discount_rate")));
//                    obj.setBill_discount_amount(cursor.getString(cursor.getColumnIndex("bill_discount_amount")));
//                    obj.setBill_discount_reason(cursor.getString(cursor.getColumnIndex("bill_discount_reason")));
//                    obj.setBill_tip(cursor.getString(cursor.getColumnIndex("bill_tip")));
//                    obj.setBill_created_on(cursor.getString(cursor.getColumnIndex("bill_created_on")));
//                    obj.setBill_created_by(cursor.getString(cursor.getColumnIndex("bill_created_by")));
//                    billDetails.add(obj);
//                } while (cursor.moveToNext());
//            }else{ billDetails = null; }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error Database " + String.valueOf(e));
//        }
////        finally { db.close(); }
//
//        return billDetails;
//    }
//
//    public ArrayList<BillProductDetails> getBillProductDetails(String bill_id) {
//        ArrayList<BillProductDetails> billDetails = new ArrayList<>();
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = " bill_id = ? ";
//        String[] selectionArgs = {bill_id};
//
//        try {
//            Cursor cursor = db.query("tbl_restaurant_bill_detail",  columns,  selection, selectionArgs, null, null, null);
//            if (cursor.moveToFirst()) {
//                do {
//                    BillProductDetails obj = new BillProductDetails();
//                    obj.setBill_detail_id(cursor.getString(cursor.getColumnIndex("bill_detail_id")));
//                    obj.setBill_detail_product_quantity(cursor.getString(cursor.getColumnIndex("bill_detail_product_quantity")));
//                    obj.setBill_detail_product_price(cursor.getString(cursor.getColumnIndex("bill_detail_product_price")));
//                    obj.setBill_detail_product_id(cursor.getString(cursor.getColumnIndex("bill_detail_product_id")));
//                    obj.setBill_detail_product_discount(cursor.getString(cursor.getColumnIndex("bill_detail_product_discount")));
//                    obj.setBill_detail_product_sgst(cursor.getString(cursor.getColumnIndex("bill_detail_product_sgst")));
//                    obj.setBill_detail_product_cgst(cursor.getString(cursor.getColumnIndex("bill_detail_product_cgst")));
//                    billDetails.add(obj);
//                } while (cursor.moveToNext());
//            }else{ billDetails = null; }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error Database " + String.valueOf(e));
//        }
////        finally { db.close(); }
//
//        return billDetails;
//    }
//
    public BillDetails getBillDetailsByBillId(String bill_id) {
        BillDetails billDetails = new BillDetails();
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = " bill_id = ? ";
        String[] selectionArgs = {bill_id};

        try {
            Cursor cursor = db.query("tbl_restaurant_bill_header",  columns,  selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                billDetails.setBill_id(cursor.getString(cursor.getColumnIndex("bill_id")));
                billDetails.setOrganisation_id(cursor.getString(cursor.getColumnIndex("organisation_id")));
                billDetails.setBranch_id(cursor.getString(cursor.getColumnIndex("branch_id")));
                billDetails.setCustomer_name(cursor.getString(cursor.getColumnIndex("customer_name")));
                billDetails.setCustomer_mobile_no(cursor.getString(cursor.getColumnIndex("customer_mobile_no")));
                billDetails.setBill_amount(cursor.getString(cursor.getColumnIndex("bill_amount")));
                billDetails.setBill_discount_rate(cursor.getString(cursor.getColumnIndex("bill_discount_rate")));
                billDetails.setBill_discount_amount(cursor.getString(cursor.getColumnIndex("bill_discount_amount")));
                billDetails.setBill_discount_reason(cursor.getString(cursor.getColumnIndex("bill_discount_reason")));
                billDetails.setBill_tip(cursor.getString(cursor.getColumnIndex("bill_tip")));
                billDetails.setBill_created_on(cursor.getString(cursor.getColumnIndex("bill_created_on")));
                billDetails.setBill_created_by(cursor.getString(cursor.getColumnIndex("bill_created_by")));
                Log.i(TAG, "getBillDetails: "+billDetails);
            }else{ billDetails = null; }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error Database " + String.valueOf(e));
        }
//        finally { db.close(); }

        return billDetails;
    }

    public ArrayList<BillDetail> BillDetailsByBillId(String bill_id) {
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = " bill_id = ? ";
        String[] selectionArgs = {bill_id};

        ArrayList<BillDetail> billDetails = new ArrayList<>();
        try {
            Cursor cursor = db.query("tbl_bill_details",  columns,  selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                BillDetail obj;
                do {
                    obj = new BillDetail();
                    obj.setBill_detail_id(cursor.getString(cursor.getColumnIndex("bill_detail_id")));
                    obj.setBill_id(cursor.getString(cursor.getColumnIndex("bill_id")));
                    obj.setBill_detail_product_quantity(cursor.getString(cursor.getColumnIndex("bill_detail_product_quantity")));
                    obj.setBill_detail_product_price(cursor.getString(cursor.getColumnIndex("bill_detail_product_price")));
                    obj.setBill_detail_product_id(cursor.getString(cursor.getColumnIndex("bill_detail_product_id")));
                    obj.setBill_detail_product_discount(cursor.getString(cursor.getColumnIndex("bill_detail_product_discount")));
                    obj.setBill_detail_product_sgst(cursor.getString(cursor.getColumnIndex("bill_detail_product_sgst")));
                    obj.setBill_detail_product_cgst(cursor.getString(cursor.getColumnIndex("bill_detail_product_cgst")));
                    billDetails.add(obj);
                } while (cursor.moveToNext());
            }else{ billDetails = null; }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error Database " + String.valueOf(e));
        }
//        finally { db.close(); }

        return billDetails;
    }

    public ArrayList<OrderExtraItem> BillExtraItem(String bill_id) {
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = " order_id = ? ";
        String[] selectionArgs = {bill_id};

        ArrayList<OrderExtraItem> billDetails = new ArrayList<>();
        try {
            Cursor cursor = db.query("tbl_order_extra_item",  columns,  selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                OrderExtraItem obj;
                do {
                    obj = new OrderExtraItem();
                    obj.setOrder_extra_item_id(cursor.getString(cursor.getColumnIndex("order_extra_item_id")));
                    obj.setOrder_id(cursor.getString(cursor.getColumnIndex("order_id")));
                    obj.setOrder_extra_item_name(cursor.getString(cursor.getColumnIndex("order_extra_item_name")));
                    obj.setOrder_extra_item_qty(cursor.getString(cursor.getColumnIndex("order_extra_item_qty")));
                    obj.setOrder_extra_item_price(cursor.getString(cursor.getColumnIndex("order_extra_item_price")));
                    obj.setOrder_extra_item_created_by(cursor.getString(cursor.getColumnIndex("order_extra_item_created_by")));
                    obj.setOrder_extra_item_created_on(cursor.getString(cursor.getColumnIndex("order_extra_item_created_on")));
                    billDetails.add(obj);
                } while (cursor.moveToNext());
            }else{ billDetails = null; }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error Database " + String.valueOf(e));
        }
//        finally { db.close(); }

        return billDetails;
    }

//    public ArrayList<OrderExtraItem> BillExtraItemByDailyId(String tbl_name, String dailyCashId) {
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        ArrayList<OrderExtraItem> billDetails = new ArrayList<>();
//        try {
//            String sql = "SELECT count(b.order_extra_item_qty) as qty,b.* from "+tbl_name+" as a left join tbl_order_extra_item as b on a.bill_id=b.order_id where a.daily_cash_id = '"+dailyCashId+"' and b.order_extra_item_id != '' GROUP by b.order_extra_item_id";
//            Cursor cursor = db.rawQuery(sql, null);
//            if (cursor.moveToFirst()) {
//                OrderExtraItem obj;
//                do {
//                    obj = new OrderExtraItem();
//                    obj.setOrder_extra_item_id(cursor.getString(cursor.getColumnIndex("order_extra_item_id")));
//                    obj.setOrder_id(cursor.getString(cursor.getColumnIndex("order_id")));
//                    obj.setOrder_extra_item_name(cursor.getString(cursor.getColumnIndex("order_extra_item_name")));
//                    obj.setOrder_extra_item_qty(cursor.getString(cursor.getColumnIndex("qty")));
//                    obj.setOrder_extra_item_price(cursor.getString(cursor.getColumnIndex("order_extra_item_price")));
//                    obj.setOrder_extra_item_created_by(cursor.getString(cursor.getColumnIndex("order_extra_item_created_by")));
//                    obj.setOrder_extra_item_created_on(cursor.getString(cursor.getColumnIndex("order_extra_item_created_on")));
//                    billDetails.add(obj);
//                } while (cursor.moveToNext());
//            }else{ billDetails = null; }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error Database " + String.valueOf(e));
//        }
////        finally { db.close(); }
//
//        return billDetails;
//    }
//
//    public String getProductSale(String myDate, String pro_id, String tbl_name) {
//        String count = "0";
//        String[] columns = {"count(bill_detail_product_id) as pro_count"};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = " date(bill_detail_created_on) = ? and bill_detail_product_id = ? ";
//        String[] selectionArgs = {myDate,pro_id};
//
//        try {
//            Cursor cursor = db.query(tbl_name,  columns,  selection, selectionArgs, null, null, null);
//            if (cursor.moveToFirst()) {
//                count = cursor.getString(cursor.getColumnIndex("pro_count"));
//            }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error Database " + String.valueOf(e));
//        }
////        finally { db.close(); }
//
//        return count;
//    }
//
    public ArrayList<OrderDetails> getOrderDetails(String order_id) {
        ArrayList<OrderDetails> orderDetailsList = new ArrayList<>();
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = " order_id = ? ";
        String[] selectionArgs = {order_id};

        Cursor cursor = db.query("tbl_order_details", columns, selection,selectionArgs, null,null,  null);                     //The sort order

        if (cursor.moveToFirst()) {
            OrderDetails obj;

            do {
                obj = new OrderDetails();
                obj.setOrder_details_id(cursor.getString(cursor.getColumnIndex("order_details_id")));
                obj.setOrder_id(cursor.getString(cursor.getColumnIndex("order_id")));
                obj.setProduct_id(cursor.getString(cursor.getColumnIndex("product_id")));
                obj.setOrder_details_order_qty(cursor.getString(cursor.getColumnIndex("order_details_order_qty")));
                obj.setOrder_deatils_unit_price(cursor.getString(cursor.getColumnIndex("order_deatils_unit_price")));
                obj.setOrder_details_product_price(cursor.getString(cursor.getColumnIndex("order_details_product_price")));
                obj.setOrder_deatils_discount_amount(cursor.getString(cursor.getColumnIndex("order_deatils_discount_amount")));
                obj.setOrder_details_tax(cursor.getString(cursor.getColumnIndex("order_details_tax")));
                obj.setOrder_details_total_price(cursor.getString(cursor.getColumnIndex("order_details_total_price")));
                obj.setOrder_details_tax_no(cursor.getString(cursor.getColumnIndex("order_details_tax_no")));
                obj.setOrder_details_tax_amount(cursor.getString(cursor.getColumnIndex("order_details_tax_amount")));
                obj.setOrder_details_tax_type(cursor.getString(cursor.getColumnIndex("order_details_tax_type")));
                obj.setOrder_details_special_note(cursor.getString(cursor.getColumnIndex("order_details_special_note")));
                obj.setOrder_details_payment_stock_id(cursor.getString(cursor.getColumnIndex("order_details_payment_stock_id")));
                obj.setOrder_details_status(cursor.getString(cursor.getColumnIndex("order_details_status")));
                obj.setOrder_details_created_on(cursor.getString(cursor.getColumnIndex("order_details_created_on")));
                obj.setOrder_details_created_by(cursor.getString(cursor.getColumnIndex("order_details_created_by")));
                obj.setOrder_details_modified_on(cursor.getString(cursor.getColumnIndex("order_details_modified_on")));
                obj.setOrder_details_modified_by(cursor.getString(cursor.getColumnIndex("order_details_modified_by")));
                obj.setOrder_details_is_deleted(cursor.getString(cursor.getColumnIndex("order_details_is_deleted")));
                orderDetailsList.add(obj);
                Log.i(TAG, "getOrderBillDetails: "+orderDetailsList);
            } while (cursor.moveToNext());
        }else{
            orderDetailsList = null;
        }
        cursor.close();
//        db.close();
        return orderDetailsList;
    }
//
    public ArrayList<BillDetail> paidBillDetail(String tbl, String bill_id) {
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = " bill_id = ? ";
        String[] selectionArgs = {bill_id};

        ArrayList<BillDetail> billDetails = new ArrayList<>();
        try {
            Cursor cursor = db.query(tbl,  columns,  selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                BillDetail obj;
                do {
                    obj = new BillDetail();
                    obj.setBill_detail_id(cursor.getString(cursor.getColumnIndex("bill_detail_id")));
                    obj.setBill_id(cursor.getString(cursor.getColumnIndex("bill_id")));
                    obj.setBill_detail_product_quantity(cursor.getString(cursor.getColumnIndex("bill_detail_product_quantity")));
                    obj.setBill_detail_product_price(cursor.getString(cursor.getColumnIndex("bill_detail_product_price")));
                    obj.setBill_detail_product_id(cursor.getString(cursor.getColumnIndex("bill_detail_product_id")));
                    obj.setBill_detail_product_discount(cursor.getString(cursor.getColumnIndex("bill_detail_product_discount")));
                    obj.setBill_detail_product_sgst(cursor.getString(cursor.getColumnIndex("bill_detail_product_sgst")));
                    obj.setBill_detail_product_cgst(cursor.getString(cursor.getColumnIndex("bill_detail_product_cgst")));
                    billDetails.add(obj);
                } while (cursor.moveToNext());
            }else{ billDetails = null; }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error Database " + String.valueOf(e));
        }

        return billDetails;
    }

//    public ArrayList<BillDetail> getAllProductOfDailyId(String tbl_header, String tbl_detail, String daily_cash_id) {
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        ArrayList<BillDetail> billDetails = new ArrayList<>();
//        try {
//            Cursor cursor = db.rawQuery("SELECT count(b.bill_detail_product_quantity) as qty,b.* from "+tbl_header+" as a " +
//                    "left join "+tbl_detail+" as b on a.bill_id=b.bill_id " +
//                    "where a.daily_cash_id = '"+daily_cash_id+"' " +
//                    "GROUP by b.bill_detail_product_id",null);
////            Cursor cursor = db.query(tbl,  columns,  selection, selectionArgs, null, null, null);
//            if (cursor.moveToFirst()) {
//                BillDetail obj;
//                do {
//                    obj = new BillDetail();
////                    obj.setBill_detail_id(cursor.getString(cursor.getColumnIndex("bill_detail_id")));
////                    obj.setBill_id(cursor.getString(cursor.getColumnIndex("bill_id")));
//                    obj.setBill_detail_product_quantity(cursor.getString(cursor.getColumnIndex("qty")));
//                    obj.setBill_detail_product_price(cursor.getString(cursor.getColumnIndex("bill_detail_product_price")));
//                    obj.setBill_detail_product_id(cursor.getString(cursor.getColumnIndex("bill_detail_product_id")));
////                    obj.setBill_detail_product_discount(cursor.getString(cursor.getColumnIndex("bill_detail_product_discount")));
////                    obj.setBill_detail_product_sgst(cursor.getString(cursor.getColumnIndex("bill_detail_product_sgst")));
////                    obj.setBill_detail_product_cgst(cursor.getString(cursor.getColumnIndex("bill_detail_product_cgst")));
//                    billDetails.add(obj);
//                } while (cursor.moveToNext());
//            }else{ billDetails = null; }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error Database " + String.valueOf(e));
//        }
//
//        return billDetails;
//    }
//
//    public ArrayList<BillPaymentDetails> getAllPayment(String startDate, String endDate){
//        ArrayList<BillPaymentDetails> billPaymentList = new ArrayList<>();
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = " bill_paid_datetime >= ? and bill_paid_datetime <= ? ";
//        String[] selectionArgs = {startDate,endDate};
//
//        try {
//            Cursor cursor = db.query("tbl_bill_payment",  columns,  selection, selectionArgs, "bill_id", null, null);
//            if (cursor.moveToFirst()) {
//                do {
//                    BillPaymentDetails obj = new BillPaymentDetails();
//                    obj.setBill_payment_id(cursor.getString(cursor.getColumnIndex("bill_payment_id")));
//                    obj.setBill_id(cursor.getString(cursor.getColumnIndex("bill_id")));
//                    obj.setOrganisation_id(cursor.getString(cursor.getColumnIndex("organisation_id")));
//                    obj.setBranch_id(cursor.getString(cursor.getColumnIndex("branch_id")));
//                    obj.setBill_payment_mode(cursor.getString(cursor.getColumnIndex("bill_payment_mode")));
//                    obj.setBill_transaction_details(cursor.getString(cursor.getColumnIndex("bill_transaction_details")));
//                    obj.setBill_transaction_no(cursor.getString(cursor.getColumnIndex("bill_transaction_no")));
//                    obj.setBill_paid_amount(cursor.getString(cursor.getColumnIndex("bill_paid_amount")));
//                    obj.setBill_paid_datetime(cursor.getString(cursor.getColumnIndex("bill_paid_datetime")));
//                    obj.setBill_paid_by(cursor.getString(cursor.getColumnIndex("bill_paid_by")));
//                    Log.i(TAG, "getOrderBillPayments: "+obj);
//                    billPaymentList.add(obj);
//                } while (cursor.moveToNext());
//            }else{ billPaymentList = null; }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error Database " + String.valueOf(e));
//        }
////        finally { db.close(); }
//
//        return billPaymentList;
//    }
//
//    public ArrayList<BillPaymentDetails> getPaymentByDailyCashID(String id){
//        ArrayList<BillPaymentDetails> billPaymentList = new ArrayList<>();
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = " daily_cash_id = ? ";
//        String[] selectionArgs = {id};
//
//        try {
//            Cursor cursor = db.query("tbl_bill_payment",  columns,  selection, selectionArgs, null, null, null);
//            if (cursor.moveToFirst()) {
//                do {
//                    BillPaymentDetails obj = new BillPaymentDetails();
//                    obj.setBill_payment_id(cursor.getString(cursor.getColumnIndex("bill_payment_id")));
//                    obj.setBill_id(cursor.getString(cursor.getColumnIndex("bill_id")));
//                    obj.setOrganisation_id(cursor.getString(cursor.getColumnIndex("organisation_id")));
//                    obj.setBranch_id(cursor.getString(cursor.getColumnIndex("branch_id")));
//                    obj.setDaily_cash_id(cursor.getString(cursor.getColumnIndex("daily_cash_id")));
//                    obj.setBill_payment_mode(cursor.getString(cursor.getColumnIndex("bill_payment_mode")));
//                    obj.setBill_transaction_details(cursor.getString(cursor.getColumnIndex("bill_transaction_details")));
//                    obj.setBill_transaction_no(cursor.getString(cursor.getColumnIndex("bill_transaction_no")));
//                    obj.setBill_paid_amount(cursor.getString(cursor.getColumnIndex("bill_paid_amount")));
//                    obj.setBill_paid_datetime(cursor.getString(cursor.getColumnIndex("bill_paid_datetime")));
//                    obj.setBill_paid_by(cursor.getString(cursor.getColumnIndex("bill_paid_by")));
//                    Log.i(TAG, "getOrderBillPayments: "+obj);
//                    billPaymentList.add(obj);
//                } while (cursor.moveToNext());
//            }else{ billPaymentList = null; }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error Database " + String.valueOf(e));
//        }
////        finally { db.close(); }
//
//        return billPaymentList;
//    }
//
//    public ArrayList<TableOrderDetails> getParcelOrderList(String section, String user_id) {
//        ArrayList<TableOrderDetails> tableOrderDetails = new ArrayList<>();
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = " section_id = ? and user_id = ? and table_id like ?";
//        String[] selectionArgs = {section,user_id,"%PARCEL_%"};
//
//        try {
//            Cursor cursor = db.query("tbl_table_order",  columns, selection,  selectionArgs,null,null,null);
//
//            if (cursor.moveToFirst()) {
//                TableOrderDetails obj;
//                do {
//                    obj = new TableOrderDetails();
//                    obj.setTable_id(cursor.getString(cursor.getColumnIndex("table_id")));
//                    obj.setOrder_id(cursor.getString(cursor.getColumnIndex("order_id")));
//                    obj.setSection_id(cursor.getString(cursor.getColumnIndex("section_id")));
//                    obj.setUser_id(cursor.getString(cursor.getColumnIndex("user_id")));
//                    obj.setTable_status(cursor.getString(cursor.getColumnIndex("table_status")));
//                    obj.setCreated_on(cursor.getString(cursor.getColumnIndex("created_on")));
//                    tableOrderDetails.add(obj);
//
//                    Log.e("DailyCashDetials", tableOrderDetails.toString());
//                } while (cursor.moveToNext());
//            }else{
//                tableOrderDetails = null;
//            }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error inserting " + String.valueOf(e));
//        } finally {
////            db.close();
//        }
//        return tableOrderDetails;
//    }
//
//    public ArrayList<GroceryCustomer> getGroceryOrderList(String section, String user_id) {
//        ArrayList<GroceryCustomer> orderList = new ArrayList<>();
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = " grocery_section_id = ? and grocery_created_by = ? ";
//        String[] selectionArgs = {section,user_id};
//
//        try {
//            Cursor cursor = db.query("tbl_grocery_orders",  columns, selection,  selectionArgs,null,null,"grocery_code ASC");
//
//            if (cursor.moveToFirst()) {
//                GroceryCustomer obj;
//                do {
//                    obj = new GroceryCustomer();
//                    obj.setGrocery_id(cursor.getString(cursor.getColumnIndex("grocery_id")));
//                    obj.setGrocery_code(cursor.getString(cursor.getColumnIndex("grocery_code")));
//                    obj.setGrocery_order_id(cursor.getString(cursor.getColumnIndex("grocery_order_id")));
//                    obj.setGrocery_bill_id(cursor.getString(cursor.getColumnIndex("grocery_bill_id")));
//                    obj.setGrocery_customer_id(cursor.getString(cursor.getColumnIndex("grocery_customer_id")));
//                    obj.setGrocery_section_id(cursor.getString(cursor.getColumnIndex("grocery_section_id")));
//                    obj.setGrocery_status(cursor.getString(cursor.getColumnIndex("grocery_status")));
//                    obj.setGrocery_created_on(cursor.getString(cursor.getColumnIndex("grocery_created_on")));
//                    obj.setGrocery_created_by(cursor.getString(cursor.getColumnIndex("grocery_created_by")));
//                    orderList.add(obj);
//                } while (cursor.moveToNext());
//            }else{
//                orderList = null;
//            }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error inserting " + String.valueOf(e));
//        }
//        return orderList;
//    }
//
    public CustomerDetails getCustomerDetails(String order_id) {
        CustomerDetails obj = new CustomerDetails();
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = " customer_id = ? ";
        String[] selectionArgs = {order_id};

        try {
            Cursor cursor = db.query("tbl_customer",  columns,  selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                obj.setCustomer_id(cursor.getString(cursor.getColumnIndex("customer_id")));
                obj.setCustomer_mobile_no(cursor.getString(cursor.getColumnIndex("customer_mobile_no")));
                obj.setCustomer_name(cursor.getString(cursor.getColumnIndex("customer_name")));
                obj.setCustomer_address(cursor.getString(cursor.getColumnIndex("customer_address")));
                obj.setBill_created_on(cursor.getString(cursor.getColumnIndex("customer_created_on")));
                obj.setBill_created_by(cursor.getString(cursor.getColumnIndex("customer_created_by")));
            }else{ obj = null; }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error Database " + String.valueOf(e));
        }
//        finally { db.close(); }

        return obj;
    }

//    public String getLastParcelCode(String section) {
//        String response = "";
//        String[] columns = {"MAX(parcel_code)"};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = " section_id = ? ";
//        String[] selectionArgs = {section};
//
//        try {
//            Cursor cursor = db.query("tbl_parcel",  columns, selection,  selectionArgs,null,null,null);
//
//            if (cursor.moveToFirst()) {
//                response = cursor.getString(0);
//            }else{
//                response = "";
//            }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error inserting " + String.valueOf(e));
//        }
////        finally {
////            db.close();
////        }
//
//        return response;
//    }
//    public String getLastCode(String tbl_name,String maxOf, String id, String value) {
//        String response = "";
//        String[] columns = {"MAX("+maxOf+")"};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = id+" = ? ";
//        String[] selectionArgs = {value};
//
//        try {
//            Cursor cursor = db.query(tbl_name,  columns, selection,  selectionArgs,null,null,null);
//
//            if (cursor.moveToFirst()) {
//                response = cursor.getString(0);
//            }else{
//                response = "";
//            }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error inserting " + String.valueOf(e));
//        }
////        finally {
////            db.close();
////        }
//
//        return response;
//    }
//    public String getProductLastCode(String tbl_name,String maxOf) {
//        String response = "";
//        String[] columns = {"MAX("+maxOf+")"};
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        try {
//            Cursor cursor = db.query(tbl_name,  columns, null,  null,null,null,null);
//
//            if (cursor.moveToFirst()) {
//                response = cursor.getString(0);
//            }else{
//                response = "";
//            }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error inserting " + String.valueOf(e));
//        }
//
//        return response;
//    }
//
//    public ArrayList<DailyCashDetails> getDailyCashDetails(String sDate, String tDate, String captain) {
//        ArrayList<DailyCashDetails> dailyCashDetails = new ArrayList<>();
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String selection = "opening_by_user = ? and opening_at_datetime > ? and opening_at_datetime < ? ";
//        String[] selectionArgs = {captain,sDate,tDate};
//
//        String selection1 = " opening_at_datetime > ? and opening_at_datetime < ? ";
//        String[] selectionArgs1 = {sDate,tDate};
//
//        DailyCashDetails obj = null;
//
//        try {
//            Cursor cursor = null;
//            if (captain.equals("")){
//                cursor = db.query("tbl_dailycash",  columns,  selection1, selectionArgs1, null, null, null);
//            }else{
//                cursor = db.query("tbl_dailycash",  columns,  selection, selectionArgs, null, null, null);
//            }
//
//            if (cursor.moveToFirst()) {
//
//                do {
//                    obj = new DailyCashDetails();
//                    obj.setDaily_cash_id(cursor.getString(cursor.getColumnIndex("daily_cash_id")));
//                    obj.setSection_id(cursor.getString(cursor.getColumnIndex("section_id")));
//                    obj.setOpening_by_user(cursor.getString(cursor.getColumnIndex("opening_by_user")));
//                    obj.setOpening_at_datetime(cursor.getString(cursor.getColumnIndex("opening_at_datetime")));
//                    obj.setOpening_cash(cursor.getString(cursor.getColumnIndex("opening_cash")));
//                    obj.setClosing_by_user(cursor.getString(cursor.getColumnIndex("closing_by_user")));
//                    obj.setClosing_at_datetime(cursor.getString(cursor.getColumnIndex("closing_at_datetime")));
//                    obj.setClosing_cash(cursor.getString(cursor.getColumnIndex("closing_cash")));
//                    dailyCashDetails.add(obj);
//                } while (cursor.moveToNext());
//            }else{
//                dailyCashDetails = null;
//            }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error inserting " + String.valueOf(e));
//        }
//        return dailyCashDetails;
//    }
//
//    public ArrayList<DailyCashDetails> getDailyCashDetailsClient() {
//        ArrayList<DailyCashDetails> dailyCashDetails = new ArrayList<>();
//        String[] columns = {"*"};
//        SQLiteDatabase db = this.getReadableDatabase();
////        String selection = "opening_by_user = ? and opening_at_datetime > ? and opening_at_datetime < ? and section_id = ? ";
////        String[] selectionArgs = {user_id,startDate,endDate,section};
//
//        DailyCashDetails obj = null;
//
//        try {
//            Cursor cursor = db.query("tbl_dailycash",  columns,  null, null, "opening_by_user", null, null);                      //The sort order
//
//            if (cursor.moveToFirst()) {
//
//                do {
//                    obj = new DailyCashDetails();
//                    obj.setDaily_cash_id(cursor.getString(cursor.getColumnIndex("daily_cash_id")));
//                    obj.setSection_id(cursor.getString(cursor.getColumnIndex("section_id")));
//                    obj.setOpening_by_user(cursor.getString(cursor.getColumnIndex("opening_by_user")));
//                    obj.setOpening_at_datetime(cursor.getString(cursor.getColumnIndex("opening_at_datetime")));
//                    obj.setOpening_cash(cursor.getString(cursor.getColumnIndex("opening_cash")));
//                    obj.setClosing_by_user(cursor.getString(cursor.getColumnIndex("closing_by_user")));
//                    obj.setClosing_at_datetime(cursor.getString(cursor.getColumnIndex("closing_at_datetime")));
//                    obj.setClosing_cash(cursor.getString(cursor.getColumnIndex("closing_cash")));
//                    dailyCashDetails.add(obj);
//                } while (cursor.moveToNext());
//            }else{
//                dailyCashDetails = null;
//            }
//            cursor.close();
//        } catch (Exception e) {
//            Log.d("Error", "Error inserting " + String.valueOf(e));
//        }
//        return dailyCashDetails;
//    }
//
////    public ArrayList<String> getTableName() {
////        SQLiteDatabase db = this.getReadableDatabase();
////        String sql = "SELECT * FROM 'tbl_menu_item'";
////        Cursor mCursor = db.rawQuery(sql, null);
////        ArrayList<String> xyz = new ArrayList<>();
////        if (mCursor.moveToFirst()) {
////            while ( !mCursor.isAfterLast() ) {
////                Log.i(TAG, "Table Name=> "+mCursor.getString(1));
////                xyz.add(mCursor.getString(0));
////                mCursor.moveToNext();
////            }
////        }
////        return xyz;
////    }
//
//
////    public boolean createTable(String query) {
////        SQLiteDatabase db = this.getWritableDatabase();
////        Cursor mCursor = db.rawQuery(query, null);
////        if (mCursor.getCount() > 0) {
////            return true;
////        }
////        mCursor.close();
////        return false;
////    }
//
//
    public String CancelTableOrder(String collector_id, String section,String user_id, String bill_type) {
        String response = "";
        String[] colOrder = {"order_id"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selectionOrder = " table_id = ? and section_id = ? and user_id = ? and order_type = ?";
        String[] selectionArgsFoodOrder = {collector_id, section, user_id, "FOOD"};
        String[] selectionArgsBarOrder = {collector_id, section, user_id, "BAR"};

        String foodOrderId = ""; String barOrderId = ""; String foodBillId = ""; String barBillId= "";
        try {
            Cursor cursor = db.query("tbl_table_order",  colOrder, selectionOrder,  selectionArgsFoodOrder,null,null,null);
            if (cursor.moveToFirst()) {
                foodOrderId = cursor.getString(0);
            }
            cursor.close();

            cursor = db.query("tbl_table_order",  colOrder, selectionOrder,  selectionArgsBarOrder,null,null,null);
            if (cursor.moveToFirst()) {
                barOrderId = cursor.getString(0);
            }
            cursor.close();

            String[] colBill = {"bill_id"};
            String[] selectionArgsBarBill = {barOrderId};
            String[] selectionArgsFoodBill = {foodOrderId};
            String selectionBill =  " order_id = ? ";

            cursor = db.query("tbl_bill",  colBill, selectionBill,  selectionArgsFoodBill,null,null,null);
            if (cursor.moveToFirst()) {
                foodBillId = cursor.getString(0);
            }
            cursor = db.query("tbl_bill",  colBill, selectionBill, selectionArgsBarBill,null,null,null);

            if (cursor.moveToFirst()) {
                barBillId = cursor.getString(0);
            }

            boolean createSuccessful = false;
            createSuccessful = db.delete("tbl_table_order",  "order_id = ? " , new String[] { foodOrderId }) > 0;
            createSuccessful = db.delete("tbl_table_order",  "order_id = ? " , new String[] { barOrderId }) > 0;
            createSuccessful = db.delete("tbl_table_order",  "bill_id = ? " , new String[] { foodBillId }) > 0;
            createSuccessful = db.delete("tbl_table_order",  "bill_id = ? " , new String[] { barBillId }) > 0;
            createSuccessful = db.delete("tbl_order",  "order_id = ? " , new String[] { foodOrderId }) > 0;
            createSuccessful = db.delete("tbl_order",  "order_id = ? " , new String[] { barOrderId }) > 0;
            createSuccessful = db.delete("tbl_order_details",  "order_id = ? " , new String[] { foodOrderId }) > 0;
            createSuccessful = db.delete("tbl_order_details",  "order_id = ? " , new String[] { barOrderId }) > 0;
            createSuccessful = db.delete("tbl_bill",  "bill_id = ? " , new String[] { foodBillId }) > 0;
            createSuccessful = db.delete("tbl_bill",  "bill_id = ? " , new String[] { barBillId }) > 0;
            createSuccessful = db.delete("tbl_bill_details",  "order_id = ? " , new String[] { foodBillId }) > 0;
            createSuccessful = db.delete("tbl_bill_details",  "order_id = ? " , new String[] { barBillId }) > 0;
            createSuccessful = db.delete("tbl_order_extra_item",  "bill_id = ? " , new String[] { foodOrderId }) > 0;
            createSuccessful = db.delete("tbl_order_extra_item",  "bill_id = ? " , new String[] { barOrderId }) > 0;
            createSuccessful = db.delete("tbl_order_extra_item",  "order_id = ? " , new String[] { foodBillId }) > 0;
            createSuccessful = db.delete("tbl_order_extra_item",  "order_id = ? " , new String[] { barBillId }) > 0;

            if (bill_type.equals("parcel")){
                createSuccessful = db.delete("tbl_parcel_order",  "parcel_id = ? " , new String[] { collector_id }) > 0;
            }else{
                createSuccessful = db.delete("tbl_table_order",  "table_id = ? " , new String[] { collector_id }) > 0;
            }

        } catch (Exception e) {
            Log.d("Error", "Error inserting " + String.valueOf(e));
        }
//        finally {
//            db.close();
//        }
        return response;
    }

    public ArrayList<OrganizationDetails> getOrganizationdetails() {
        ArrayList<OrganizationDetails> organizationList = new ArrayList<OrganizationDetails>();

        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("tbl_organisation", //Table to query
                columns,                    //columns to return
                null,                  //columns for the WHERE clause
                null,              //The values for the WHERE clause
                null, //COLUMN_CandidateAssesmentStatus,//group the rows
                null,                       //filter by row groups
                null);                     //The sort order

        if (cursor.moveToFirst()) {
            OrganizationDetails obj;

            do {
                obj = new OrganizationDetails();
                obj.setOrganisation_id(cursor.getString(cursor.getColumnIndex("organisation_id")));
                obj.setOrganisation_name(cursor.getString(cursor.getColumnIndex("organisation_name")));
                obj.setOrganisation_type(cursor.getString(cursor.getColumnIndex("organisation_category")));
                obj.setOrganisation_logo(cursor.getString(cursor.getColumnIndex("organisation_logo")));
                obj.setOrganisation_reg_address(cursor.getString(cursor.getColumnIndex("organisation_reg_address")));
                obj.setOrganisation_reg_email(cursor.getString(cursor.getColumnIndex("organisation_reg_email")));
                obj.setOrganisation_reg_number(cursor.getString(cursor.getColumnIndex("organisation_reg_number")));
                obj.setOrganisation_gst(cursor.getString(cursor.getColumnIndex("organisation_gst")));
                obj.setOrganisation_cin(cursor.getString(cursor.getColumnIndex("organisation_cin")));
                obj.setOrganisation_pan(cursor.getString(cursor.getColumnIndex("organisation_pan")));
                obj.setOrganisation_tan(cursor.getString(cursor.getColumnIndex("organisation_tan")));
                obj.setOrganisation_modified_on(cursor.getString(cursor.getColumnIndex("organisation_modified_on")));
                obj.setOrganisation_created_by(cursor.getString(cursor.getColumnIndex("organisation_created_by")));
                obj.setOrganisation_modified_by(cursor.getString(cursor.getColumnIndex("organisation_modified_by")));
                obj.setOrganisation_is_deleted(cursor.getString(cursor.getColumnIndex("organisation_is_deleted")));
                obj.setOrganisation_created_on(cursor.getString(cursor.getColumnIndex("organisation_created_on")));
                obj.setOrganisation_status(cursor.getString(cursor.getColumnIndex("organisation_status")));
                organizationList.add(obj);
            } while (cursor.moveToNext());
        }else{
            organizationList = null;
        }
        cursor.close();
        return organizationList;
    }

    public void getVacuum() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("VACUUM");
    }

    public ArrayList<TaxSlab> getTaxSlabList() {
        ArrayList<TaxSlab> taxList = new ArrayList<TaxSlab>();

        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("tbl_tax_slab",  columns,null,  null,null, null,null);                     //The sort order

        if (cursor.moveToFirst()) {
            while ( !cursor.isAfterLast() ) {
                TaxSlab obj = new TaxSlab();
                obj.setTax_slab_id(cursor.getString(cursor.getColumnIndex("tax_slab_id")));
                obj.setOrganisation_id(cursor.getString(cursor.getColumnIndex("organisation_id")));
                obj.setBranch_id(cursor.getString(cursor.getColumnIndex("branch_id")));
                obj.setTax_slab_name(cursor.getString(cursor.getColumnIndex("tax_slab_name")));
                obj.setTax_slab_sgst(cursor.getString(cursor.getColumnIndex("tax_slab_sgst")));
                obj.setTax_slab_cgst(cursor.getString(cursor.getColumnIndex("tax_slab_cgst")));
                obj.setTax_slab_created_on(cursor.getString(cursor.getColumnIndex("tax_slab_created_on")));
                obj.setTax_slab_created_by(cursor.getString(cursor.getColumnIndex("tax_slab_created_by")));
                taxList.add(obj);
                cursor.moveToNext();
            }
        }else{
            taxList = null;
        }
        cursor.close();
        return taxList;
    }

    public ArrayList<CategoryDetails> getCategory(String order_type) {

        String order_code = "0";
        if (order_type.equals("FOOD")){
            order_code = "1";
        }else if (order_type.equals("BAR")){
            order_code = "2";
        }

        String selection = "category_type = ? ";
        String[] selectionArgs = {order_code};

        ArrayList<CategoryDetails> categoryDetails = new ArrayList<>();
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.query("tbl_category", columns,selection,selectionArgs,null,null,null);                      //The sort order

            if (cursor.moveToFirst()) {
                CategoryDetails obj;
                do {
                    obj = new CategoryDetails();
                    obj.setCategory_id(cursor.getString(cursor.getColumnIndex("category_id")));
                    obj.setCategory_name(cursor.getString(cursor.getColumnIndex("category_name")));
                    obj.setCategory_description(cursor.getString(cursor.getColumnIndex("category_description")));
                    obj.setSequence_nr(cursor.getString(cursor.getColumnIndex("sequence_nr")));
                    obj.setParent_id(cursor.getString(cursor.getColumnIndex("parent_id")));
                    obj.setCategory_image(cursor.getString(cursor.getColumnIndex("category_image")));
                    obj.setProductDetails(getProductByCatId(cursor.getString(cursor.getColumnIndex("category_id"))));
                    categoryDetails.add(obj);
                } while (cursor.moveToNext());
            }else{
                categoryDetails = null;
            }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error inserting " + String.valueOf(e));
        }
        return categoryDetails;
    }

    private List<ProductDetails> getProductByCatId(String category_id) {
        ArrayList<ProductDetails> productDetails = new ArrayList<>();
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String sql = "select a.* from tbl_product as a " +
                    "left join tbl_product_categories as b on a.product_id = b.product_id " +
                    "where b.category_id='"+category_id+"' order by product_id ASC ";
            Cursor cursor = db.rawQuery(sql,null);                      //The sort order

            if (cursor.moveToFirst()) {
                ProductDetails obj;
                do {
                    obj = new ProductDetails();
                    obj.setProduct_id(cursor.getString(cursor.getColumnIndex("product_id")));
                    obj.setProduct_name(cursor.getString(cursor.getColumnIndex("product_name")));
                    obj.setProduct_photo(cursor.getString(cursor.getColumnIndex("product_photo")));
                    obj.setProduct_description(cursor.getString(cursor.getColumnIndex("product_description")));
                    obj.setProduct_price(cursor.getString(cursor.getColumnIndex("product_price")));
                    productDetails.add(obj);
                } while (cursor.moveToNext());
            }else{
                productDetails = null;
            }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error inserting " + String.valueOf(e));
        }
        return productDetails;
    }

    public ArrayList<PrinterDetails> getPrinterList() {
        ArrayList<PrinterDetails> printerList = new ArrayList<PrinterDetails>();

        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("tbl_printer_details",  columns,null,  null,null, null,null);                     //The sort order

        if (cursor.moveToFirst()) {
            while ( !cursor.isAfterLast() ) {
                PrinterDetails obj = new PrinterDetails();
                obj.setPrinter_id(cursor.getString(cursor.getColumnIndex("printer_id")));
                obj.setOrganisation_id(cursor.getString(cursor.getColumnIndex("organisation_id")));
                obj.setBranch_id(cursor.getString(cursor.getColumnIndex("branch_id")));
                obj.setPrinter_type(cursor.getString(cursor.getColumnIndex("printer_type")));
                obj.setPrinter_name(cursor.getString(cursor.getColumnIndex("printer_name")));
                obj.setPrinter_ip(cursor.getString(cursor.getColumnIndex("printer_ip")));
                obj.setPrinter_port(cursor.getString(cursor.getColumnIndex("printer_port")));
                obj.setPrinter_com_port(cursor.getString(cursor.getColumnIndex("printer_com_port")));
                obj.setPrinter_paper_size(cursor.getString(cursor.getColumnIndex("printer_paper_size")));
                obj.setPrinter_created_on(cursor.getString(cursor.getColumnIndex("printer_created_on")));
                obj.setPrinter_created_by(cursor.getString(cursor.getColumnIndex("printer_created_by")));
                printerList.add(obj);
                cursor.moveToNext();
            }
        }else{
            printerList = null;
        }
        cursor.close();
        return printerList;
    }

    public ArrayList<ProductDetails> getAllProduct(String order_type) {

        String order_code = "0";
        if (order_type.equals("FOOD")){
            order_code = "1";
        }else if (order_type.equals("BAR")){
            order_code = "2";
        }
        ArrayList<ProductDetails> productDetails = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String sql = "select a.*,e.language_text from tbl_product as a " +
                    "left join tbl_product_categories as b on a.product_id = b.product_id " +
                    "left join tbl_category as c on b.category_id=c.category_id  " +
                    "left join tbl_language_text as e on a.product_id = e.table_id " +
                    "where c.category_type = '"+order_code+"' and e.language_reference_id like '%PN_%' order by a.product_id ASC ";
            Cursor cursor = db.rawQuery(sql,null);                      //The sort order

            if (cursor.moveToFirst()) {
                ProductDetails obj;
                do {
                    obj = new ProductDetails();
                    obj.setProduct_id(cursor.getString(cursor.getColumnIndex("product_id")));
                    obj.setProduct_name(cursor.getString(cursor.getColumnIndex("product_name")));
                    obj.setProduct_photo(cursor.getString(cursor.getColumnIndex("product_photo")));
                    obj.setProduct_description(cursor.getString(cursor.getColumnIndex("product_description")));
                    obj.setProduct_price(cursor.getString(cursor.getColumnIndex("product_price")));
                    obj.setProduct_bar_code(cursor.getString(cursor.getColumnIndex("product_bar_code")));
                    obj.setProduct_sequence(cursor.getString(cursor.getColumnIndex("product_sequence")));
                    obj.setLanguage_text(cursor.getString(cursor.getColumnIndex("language_text")));
                    productDetails.add(obj);
                } while (cursor.moveToNext());
            }else{
                productDetails = null;
            }
            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error inserting " + String.valueOf(e));
        }
        return productDetails;
    }

    public ArrayList<TableListDetails> getTableListwithStatus(String Section) {
        ArrayList<TableListDetails> tableListDetails = new ArrayList<>();
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = " table_id = ? and section_id = ? ";
//        String[] columnsStatus = {"table_status", "order_type"};



        try {
            Cursor cursor = db.query("tbl_order_collectors", columns,null,null,null,null,null);                      //The sort order

            if (cursor.moveToFirst()) {
                TableListDetails obj;
                do {
                    obj = new TableListDetails();
                    obj.setCollector_id(cursor.getString(cursor.getColumnIndex("collector_id")));
                    obj.setCollector_name(cursor.getString(cursor.getColumnIndex("collector_name")));
                    obj.setCollector_image(cursor.getString(cursor.getColumnIndex("collector_image")));
                    obj.setCollector_type(cursor.getString(cursor.getColumnIndex("collector_type")));
                    obj.setFood_data(null);
                    obj.setBar_data(null);

                    String[] selectionArgs = {obj.getCollector_id(), Section};
                    Cursor cursorstatus = db.query("tbl_table_order",  columns, selection,  selectionArgs,null,null,"table_id");
                    if (cursorstatus.moveToFirst()) {
                        do {
                            String order_type = cursorstatus.getString(cursorstatus.getColumnIndex("order_type"));
                            if (order_type.equals("FOOD")) {

                                TableListDetails.FoodData foodObj = new TableListDetails.FoodData();
                                foodObj.setStatus(cursorstatus.getString(cursorstatus.getColumnIndex("table_status")));
                                foodObj.setOrder_id(cursorstatus.getString(cursorstatus.getColumnIndex("order_id")));

                                String order_id = cursorstatus.getString(cursorstatus.getColumnIndex("order_id"));
                                foodObj.setBill_id(selectByID("tbl_bill","order_id",order_id,"bill_id"));
                                obj.setFood_data(foodObj);
                            }
                            if (order_type.equals("BAR")) {
                                TableListDetails.BarData barObj = new TableListDetails.BarData();
                                barObj.setStatus(cursorstatus.getString(cursorstatus.getColumnIndex("table_status")));
                                barObj.setOrder_id(cursorstatus.getString(cursorstatus.getColumnIndex("order_id")));

                                String order_id = cursorstatus.getString(cursorstatus.getColumnIndex("order_id"));
                                barObj.setBill_id(selectByID("tbl_bill","order_id",order_id,"bill_id"));
                                obj.setBar_data(barObj);
                            }
                        } while (cursorstatus.moveToNext());
                    }

                    cursorstatus.close();

                    tableListDetails.add(obj);
                } while (cursor.moveToNext());
            }else{
                tableListDetails = null;
            }

            cursor.close();
        } catch (Exception e) {
            Log.d("Error", "Error inserting " + String.valueOf(e));
        }

        return tableListDetails;
    }
}