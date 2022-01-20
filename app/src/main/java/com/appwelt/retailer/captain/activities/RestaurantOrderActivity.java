package com.appwelt.retailer.captain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appwelt.retailer.captain.R;
import com.appwelt.retailer.captain.adapter.CategoryAdapter;
import com.appwelt.retailer.captain.adapter.ExtraItemListAdapter;
import com.appwelt.retailer.captain.adapter.ExtraListAdapter;
import com.appwelt.retailer.captain.adapter.OrderListAdapter;
import com.appwelt.retailer.captain.adapter.ProductAdapter;
import com.appwelt.retailer.captain.model.BillDetail;
import com.appwelt.retailer.captain.model.CategoryDetails;
import com.appwelt.retailer.captain.model.ExtraItem;
import com.appwelt.retailer.captain.model.ExtraItemList;
import com.appwelt.retailer.captain.model.KOTItems;
import com.appwelt.retailer.captain.model.OrderDetail;
import com.appwelt.retailer.captain.model.OrderDetails;
import com.appwelt.retailer.captain.model.OrderExtraItem;
import com.appwelt.retailer.captain.model.OrderItemDetails;
import com.appwelt.retailer.captain.model.ProductCategoryDetails;
import com.appwelt.retailer.captain.model.ProductDetails;
import com.appwelt.retailer.captain.model.TableOrderDetail;
import com.appwelt.retailer.captain.services.CaptainOrderService;
import com.appwelt.retailer.captain.services.MessageDeframer;
import com.appwelt.retailer.captain.services.OnMessageListener;
import com.appwelt.retailer.captain.utils.ConnectionDetector;
import com.appwelt.retailer.captain.utils.Constants;
import com.appwelt.retailer.captain.utils.DateConversionClass;
import com.appwelt.retailer.captain.utils.FileTools;
import com.appwelt.retailer.captain.utils.FontStyle;
import com.appwelt.retailer.captain.utils.GenerateRandom;
import com.appwelt.retailer.captain.utils.Network_URLs;
import com.appwelt.retailer.captain.utils.ServiceHandler;
import com.appwelt.retailer.captain.utils.SharedPref;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class RestaurantOrderActivity extends AppCompatActivity  implements OnMessageListener {

    private static final String TAG = "ORDER_ACTIVITY";

    RecyclerView recyclerViewCategory,recyclerViewMenuItem,recyclerViewOrderItem,recyclerViewExtraItem;
    AppCompatTextView totalAmountTxt,totalQuantityTxt,totalItemTxt;
    ArrayList<OrderItemDetails> orderItemDetails;
    JSONObject orderIdObject;

    AppCompatTextView orderTitle,nameTitle,quantityTitle,rateTitle,noOfItemTitle,totalQuantityTitle,totalAmountTitle;
    AppCompatTextView btnKOT,btnBill,btnCancel,btnBack,btnFood,btnBar,btnExtraItem;

    AppCompatEditText searchByName;

    LinearLayoutCompat orderDiv,productDiv;

    ArrayList<ProductDetails> productCategoryDetailsAll;

    String order_type,section_id;


    AppCompatTextView section1,section2;
    LinearLayoutCompat sectionDiv;
    CardView cardSection1,cardSection2;
    String selectedSection = "",selectedTable ;
    ArrayList<CategoryDetails> categoryDetails;
    ProductAdapter productAdapter;
    ArrayList<ExtraItem> extraItem;
    TableOrderDetail tableOrderDetail;

    ArrayList<OrderDetail> nonKOTItems;
    List<ProductDetails> allProducts;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_order);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        FontStyle.FontStyle(RestaurantOrderActivity.this);
        progressDialog = new ProgressDialog(RestaurantOrderActivity.this);

        if (CaptainOrderService.getInstance() == null)
        {
            this.startService(new Intent(this, CaptainOrderService.class));
        }
        CaptainOrderService.getInstance().SetCaptainOrderServiceContext(this);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        MessageDeframer objMessagesDeframer = MessageDeframer.getInstance();
        objMessagesDeframer.SetInstanceContext(getApplicationContext(), this);
    }

    @Override
    public void onMessageReceived(String strCommand, String strData) {
        if (strCommand.equals(Constants.cmdTableOrder)) {

            if (strData.startsWith("ORDERLIST")) {
                strData = strData.replace("ORDERLIST#", "");
                updateOrderDetails(strData);
            }
            else if (strData.startsWith("INVALID"))
            {
                strData = strData.replace("INVALID", "");
                String response = getResources().getString(R.string.failed_with_server_call);
                try {
                    JSONObject obj = new JSONObject(strData);
                    response = obj.getString("response");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                DialogBox(response,null);
            }
        }if (strCommand.equals(Constants.cmdPrintKKOT)) {

            if (strData.startsWith("PRINT")) {
                startActivity(new Intent(getApplicationContext(),TableSelectionActivity.class));
            }
            else if (strData.startsWith("INVALID"))
            {
                strData = strData.replace("INVALID", "");
                String response = getResources().getString(R.string.failed_with_server_call);
                try {
                    JSONObject obj = new JSONObject(strData);
                    response = obj.getString("response");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                DialogBox(response,null);
            }
        }else if (strCommand.equals(Constants.cmdClearOrderTable)) {

            if (strData.startsWith("CLEARTABLE")) {
                strData = strData.replace("CLEARTABLE#", "");
                startActivity(new Intent(getApplicationContext(),TableSelectionActivity.class));
            }
            else if (strData.startsWith("INVALID"))
            {
                strData = strData.replace("INVALID#", "");
                String response = getResources().getString(R.string.failed_with_server_call);
                try {
                    JSONObject obj = new JSONObject(strData);
                    response = obj.getString("response");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                DialogBox(response,null);
            }
        }else if (strCommand.equals(Constants.cmdClearKOTTable)) {

            if (strData.startsWith("CLEARTABLE")) {
                strData = strData.replace("CLEARTABLE#", "");
                startActivity(new Intent(getApplicationContext(),TableSelectionActivity.class));
            }
            else if (strData.startsWith("INVALID"))
            {
                strData = strData.replace("INVALID#", "");
                String response = getResources().getString(R.string.failed_with_server_call);
                try {
                    JSONObject obj = new JSONObject(strData);
                    response = obj.getString("response");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                DialogBox(response,null);
            }
        }else if (strCommand.equals(Constants.cmdDayClose))
        {
            Dialog dialog = new Dialog(RestaurantOrderActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.view_dialog);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            Window window = dialog.getWindow();
            assert window != null;
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            AppCompatTextView edt_dialog_title = dialog.findViewById(R.id.edit_title);
            AppCompatTextView edt_dialog_msg = dialog.findViewById(R.id.edit_msg);
            AppCompatTextView btn_dialog_cofirm = dialog.findViewById(R.id.confirm_button);

            edt_dialog_title.setText(getResources().getString(R.string.app_name));
            edt_dialog_msg.setText(getResources().getString(R.string.master_day_close));
            btn_dialog_cofirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPref.putString(getApplicationContext(),"isLogin","false");
                    SharedPref.putString(getApplicationContext(),"user_status","");
                    SharedPref.putString(getApplicationContext(),"user_id","");
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    finish();
                }
            });

            edt_dialog_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            edt_dialog_title.setTypeface(FontStyle.getFontRegular());
            edt_dialog_msg.setTypeface(FontStyle.getFontRegular());
            btn_dialog_cofirm.setTypeface(FontStyle.getFontRegular());
            dialog.show();
        }
    }

    private void updateOrderDetails(String strData) {
        try {
            JSONObject mainObj = new JSONObject(strData);

            tableOrderDetail = new TableOrderDetail();
            tableOrderDetail.setTable_id(mainObj.getString("table_id"));
            tableOrderDetail.setSection_id(mainObj.getString("section_id"));
            tableOrderDetail.setOrder_id(mainObj.getString("order_id"));
            tableOrderDetail.setBill_id(mainObj.getString("bill_id"));
            tableOrderDetail.setBill_token(mainObj.getString("bill_token"));
            tableOrderDetail.setOrder_type(mainObj.getString("order_type"));
            tableOrderDetail.setOrder_status(mainObj.getString("order_status"));

            JSONArray mainArray = new JSONArray(mainObj.getString("order_detail"));
            List<OrderDetail> orderDetailList = new ArrayList<>();
            if (mainArray != null){
                for (int i=0;i<mainArray.length();i++){
                    JSONObject subObj = new JSONObject(mainArray.get(i).toString());
                    OrderDetail obj = new OrderDetail();
                    obj.setProduct_id(subObj.getString("product_id"));
                    obj.setProduct_name(subObj.getString("product_name"));
                    obj.setProduct_quantity(subObj.getString("product_quantity"));
                    obj.setProduct_price(subObj.getString("product_price"));
                    if (subObj.has("product_special_note")){
                        obj.setProduct_special_note(subObj.getString("product_special_note"));
                    }
                    obj.setProduct_kot_yn(subObj.getString("product_kot_yn"));
                    orderDetailList.add(obj);
                }
            }
            List<ExtraItemList> extraItemLists = new ArrayList<>();
            if (mainObj.has("extra_item")){
                JSONArray mainArr = new JSONArray(mainObj.getString("extra_item"));
                for (int i=0; i<mainArr.length(); i++){
                    ExtraItemList obj = new ExtraItemList();

                    JSONObject subObj = new JSONObject(mainArr.get(i).toString());
                    obj.setOrder_extra_item_id(subObj.getString("order_extra_item_id"));
                    obj.setOrder_extra_item_name(subObj.getString("order_extra_item_name"));
                    obj.setOrder_extra_item_qty(subObj.getString("order_extra_item_qty"));
                    obj.setOrder_extra_item_price(subObj.getString("order_extra_item_price"));
                    obj.setOrder_extra_item_kot_yn(subObj.getString("order_extra_item_kot_yn"));
                    extraItemLists.add(obj);
                }
                tableOrderDetail.setExtra_items(extraItemLists);
            }

            tableOrderDetail.setOrder_detail(orderDetailList);

            updateOrderItemRecyclerView(orderDetailList);
            updateOrderExtraItemRecyclerView(extraItemLists);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateOrderItemRecyclerView(List<OrderDetail> orderDetailList) {
        tableOrderDetail.setOrder_detail(orderDetailList);
        if (orderDetailList != null && orderDetailList.size() != 0){
            OrderListAdapter adapter = new OrderListAdapter(RestaurantOrderActivity.this, orderDetailList, new OrderListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(List<OrderDetail> item) {
                    if (item != null && item.size() != 0){
                        updateOrderItemRecyclerView(item);
                    }else{
                        startActivity(new Intent(getApplicationContext(),TableSelectionActivity.class));
                    }
                }
            });

            recyclerViewOrderItem.setAdapter(adapter);
            recyclerViewOrderItem.scrollToPosition(adapter.getItemCount() - 1);
            calculateTotalFigures(orderDetailList,tableOrderDetail.getExtra_items());
        }else{
            orderDiv.setVisibility(View.INVISIBLE);
        }
    }

    private void updateOrderExtraItemRecyclerView(List<ExtraItemList> extraItemLists) {
        tableOrderDetail.setExtra_items(extraItemLists);
        if (extraItemLists != null && extraItemLists.size() != 0){
            recyclerViewExtraItem.setVisibility(View.VISIBLE);
            ExtraListAdapter adapter = new ExtraListAdapter(RestaurantOrderActivity.this, extraItemLists, new ExtraListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(List<ExtraItemList> item) {
                    if (item != null && item.size() != 0){
                        updateOrderExtraItemRecyclerView(item);
                    }else{
                        recyclerViewExtraItem.setVisibility(View.GONE);
                    }
                }
            });

            recyclerViewExtraItem.setAdapter(adapter);
            recyclerViewExtraItem.scrollToPosition(adapter.getItemCount() - 1);
            calculateTotalFigures(tableOrderDetail.getOrder_detail(),extraItemLists);
        }else{
            orderDiv.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onReconnect() {

    }

    private void init() {

        nonKOTItems = new ArrayList<>();
        order_type = SharedPref.getString(RestaurantOrderActivity.this,"order_type");

        orderItemDetails = new ArrayList<>();
        orderIdObject = new JSONObject();

        sectionDiv = findViewById(R.id.section_div);
        section1 = findViewById(R.id.section1);
        section2 = findViewById(R.id.section2);
        cardSection1 = findViewById(R.id.section1_card);
        cardSection2 = findViewById(R.id.section2_card);

        orderTitle = findViewById(R.id.order_details);
        nameTitle = findViewById(R.id.name_title);
        quantityTitle = findViewById(R.id.quantity_title);
        rateTitle = findViewById(R.id.rate_title);
        noOfItemTitle = findViewById(R.id.no_if_item_title);
        totalQuantityTitle = findViewById(R.id.total_quantity_title);
        totalAmountTitle = findViewById(R.id.total_amount_title);
        searchByName = findViewById(R.id.search_by_name);

        btnKOT = findViewById(R.id.btn_kot);
        btnBill = findViewById(R.id.btn_bill);
        btnCancel = findViewById(R.id.btn_cancal);
        btnBack = findViewById(R.id.btn_back);
        btnFood = findViewById(R.id.order_menu_title);
        btnBar = findViewById(R.id.bar_order_title);
        btnExtraItem = findViewById(R.id.extra_item_txt);

        if (order_type.equals("FOOD")){
            btnFood.setVisibility(View.GONE);
            btnBar.setVisibility(View.VISIBLE);
        }else if (order_type.equals("BAR")){
            btnBar.setVisibility(View.GONE);
            btnFood.setVisibility(View.VISIBLE);
        }

        totalAmountTxt = findViewById(R.id.total_amount);
        totalQuantityTxt = findViewById(R.id.total_quantity);
        totalItemTxt = findViewById(R.id.total_item);
        orderDiv = findViewById(R.id.order_div);
        productDiv = findViewById(R.id.product_div);

        recyclerViewOrderItem = findViewById(R.id.recyclerViewOrderItem);
        recyclerViewOrderItem.setLayoutManager(new LinearLayoutManager(RestaurantOrderActivity.this));

        recyclerViewExtraItem = findViewById(R.id.recyclerViewExtraItem);
        recyclerViewExtraItem.setLayoutManager(new LinearLayoutManager(RestaurantOrderActivity.this));

        totalAmountTxt.setTypeface(FontStyle.getFontRegular());
        totalQuantityTxt.setTypeface(FontStyle.getFontRegular());
        totalItemTxt.setTypeface(FontStyle.getFontRegular());
        orderTitle.setTypeface(FontStyle.getFontRegular());
        nameTitle.setTypeface(FontStyle.getFontRegular());
        quantityTitle.setTypeface(FontStyle.getFontRegular());
        rateTitle.setTypeface(FontStyle.getFontRegular());
        noOfItemTitle.setTypeface(FontStyle.getFontRegular());
        totalQuantityTitle.setTypeface(FontStyle.getFontRegular());
        totalAmountTitle.setTypeface(FontStyle.getFontRegular());
        btnKOT.setTypeface(FontStyle.getFontRegular());
        btnBill.setTypeface(FontStyle.getFontRegular());
        btnCancel.setTypeface(FontStyle.getFontRegular());
        btnBack.setTypeface(FontStyle.getFontRegular());
        searchByName.setTypeface(FontStyle.getFontRegular());
        btnFood.setTypeface(FontStyle.getFontRegular());
        btnBar.setTypeface(FontStyle.getFontRegular());
        btnExtraItem.setTypeface(FontStyle.getFontRegular());
        section1.setTypeface(FontStyle.getFontRegular());
        section2.setTypeface(FontStyle.getFontRegular());

        section1.setText(getResources().getString(R.string.product));
        section2.setText(getResources().getString(R.string.order));

        selectedTable = SharedPref.getString(RestaurantOrderActivity.this,"table");

        selectedSection = "product";
        cardSection2.setBackgroundColor(getResources().getColor(R.color.light_gray));
        section2.setTextColor(getResources().getColor(R.color.black));
        cardSection1.setBackgroundColor(getResources().getColor(R.color.balajimaroon));
        section1.setTextColor(getResources().getColor(R.color.white));
        productDiv.setVisibility(View.VISIBLE);
        orderDiv.setVisibility(View.GONE);

        section1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedSection = "product";
                cardSection2.setBackgroundColor(getResources().getColor(R.color.light_gray));
                section2.setTextColor(getResources().getColor(R.color.black));
                cardSection1.setBackgroundColor(getResources().getColor(R.color.balajimaroon));
                section1.setTextColor(getResources().getColor(R.color.white));
                productDiv.setVisibility(View.VISIBLE);
                orderDiv.setVisibility(View.GONE);
            }
        });
        section2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedSection = "order";
                cardSection2.setBackgroundColor(getResources().getColor(R.color.balajimaroon));
                section2.setTextColor(getResources().getColor(R.color.white));
                cardSection1.setBackgroundColor(getResources().getColor(R.color.light_gray));
                section1.setTextColor(getResources().getColor(R.color.black));
                productDiv.setVisibility(View.GONE);
                orderDiv.setVisibility(View.VISIBLE);
            }
        });

        Log.i(TAG, "init: "+SharedPref.getString(getApplicationContext(),"section"));
        section_id = SharedPref.getString(RestaurantOrderActivity.this,"section");
        CaptainOrderService.getInstance().ServiceInitiate();
        CaptainOrderService.getInstance().sendCommand(Constants.cmdTableOrder + SharedPref.getString(RestaurantOrderActivity.this, "device_id") + "#{'section_id':'"+section_id+"','table_id':'"+selectedTable+"','bill_type':'"+SharedPref.getString(RestaurantOrderActivity.this,"bill_type")+"','order_type':'"+order_type+"','user_id':'"+SharedPref.getString(RestaurantOrderActivity.this,"user_id")+"'}");

        String section_name = SharedPref.getString(RestaurantOrderActivity.this,"section_name");
        if (!section_name.equals("") && section_name != null){

            String table_name = SharedPref.getString(RestaurantOrderActivity.this,"table_name");
            if (SharedPref.getString(getApplicationContext(),"table_split_status").equals("1")){
                table_name = table_name + "-" +SharedPref.getString(getApplicationContext(),"table_series_name");
            }
            orderTitle.setText(section_name + " "+ table_name);
        }

        recyclerViewMenuItem = findViewById(R.id.recyclerViewMenuItem);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerViewMenuItem.setLayoutManager(gridLayoutManager);
        recyclerViewMenuItem.setVisibility(View.VISIBLE);

        recyclerViewCategory = findViewById(R.id.recyclerViewCategory);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        linearLayoutManager.setReverseLayout(false);
        recyclerViewCategory.setLayoutManager(linearLayoutManager);
        recyclerViewMenuItem.setVisibility(View.VISIBLE);

        getItemsFromJSON();

        btnKOT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<OrderDetail> orderDetailList = tableOrderDetail.getOrder_detail();

                if (orderDetailList!=null){
                    if (orderDetailList.size() != 0){
                        nonKOTItems = new ArrayList<>();
                        for (int i=0; i<orderDetailList.size(); i++){
                            if (orderDetailList.get(i).getProduct_kot_yn().equals("NO")){
                                nonKOTItems.add(orderDetailList.get(i));
                            }
                        }

                        if (nonKOTItems.size()!=0){
                            String extraItemJson = "";
                            List<ExtraItemList> nonKOTExtraItem = new ArrayList<>();
                            List<ExtraItemList> AllExtraItem = tableOrderDetail.getExtra_items();
                            if (AllExtraItem!=null && AllExtraItem.size()!=0){
                                for (int i=0; i<AllExtraItem.size(); i++){
                                    if (AllExtraItem.get(i).getOrder_extra_item_kot_yn().equals("NO")){
                                        nonKOTExtraItem.add(AllExtraItem.get(i));
                                    }
                                }
                                if (nonKOTExtraItem.size()!=0){
                                    extraItemJson = new Gson().toJson(nonKOTExtraItem);
                                }

                            }

                            String json = new Gson().toJson(nonKOTItems);

                            CaptainOrderService.getInstance().ServiceInitiate();
                            CaptainOrderService.getInstance().sendCommand(Constants.cmdPrintKKOT + SharedPref.getString(RestaurantOrderActivity.this, "device_id") + "#{'section_id':'"+section_id+"','table_id':'"+selectedTable+"','table_series_no':'"+SharedPref.getString(RestaurantOrderActivity.this,"table_series_no")+"','user_id':'"+SharedPref.getString(RestaurantOrderActivity.this,"user_id")+"','order_type':'"+order_type+"','data':'"+json+"','extra_item':'"+extraItemJson+"'}");
                        }else{
                            DialogBox(getResources().getString(R.string.please_add_item_to_print_kot),null);
                        }
                    }else{
                        DialogBox(getResources().getString(R.string.please_add_item_to_print_kot),null);
                    }
                }else{
                    DialogBox(getResources().getString(R.string.please_add_item_to_print_kot),null);
                }


            }
        });
        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( nonKOTItems.size()!= 0){
                    DialogBox(getResources().getString(R.string.kot_pending),null);
                }else{
                    String tableStatus = "";
                    if (order_type.equals("FOOD")){
                        tableStatus = SharedPref.getString(RestaurantOrderActivity.this,"food_table_status");
                    }else{
                        tableStatus = SharedPref.getString(RestaurantOrderActivity.this,"bar_table_status");
                    }
                    if (tableStatus.equals("1")){
                        startActivity(new Intent(getApplicationContext(),CheckKOTActivity.class));
                    }else if (tableStatus.equals("0")){
                        DialogBox(getResources().getString(R.string.print_kot_to_proceed_furture),null);
                    }else if (tableStatus.equals("")){
                        DialogBox(getResources().getString(R.string.order_not_yet_place),null);
                    }
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelOrderForTable();
            }
        });

        searchByName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length()==0){
                    if (categoryDetails != null){

                        List<ProductDetails> p = categoryDetails.get(0).getProductDetails();
                        if (p!=null){
                            productAdapter = new ProductAdapter(RestaurantOrderActivity.this,p , new ProductAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(List<ProductDetails> item, int position) {
                                    addOrder(item.get(position));
                                }
                            });
                            recyclerViewMenuItem.setAdapter(productAdapter);
                        }


                    }
                }else{
                    filter(editable.toString());
                }
            }
        });


    }

    private void getItemsFromJSON() {

        File ItemList = new File(Environment.getExternalStorageDirectory().getPath() + "/" + Network_URLs.FOLDER_NAME + "/ItemList");

        allProducts = new ArrayList<>();
        if (ItemList.exists()) {
            try {
                String jsongString = readFromFile(Environment.getExternalStorageDirectory().getPath() + "/" + Network_URLs.FOLDER_NAME + "/ItemList");
                JSONObject obj = new JSONObject(jsongString);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    JSONArray bar_arr = null;
                    JSONArray extra_arr = null;
                    JSONArray food_arr = null;


                    if (obj.has("food_items") ){
                        food_arr = new JSONArray(obj.getString("food_items"));}
                    if (obj.has("bar_items") ){
                        bar_arr = new JSONArray(obj.getString("bar_items"));}
                    if (obj.has("extra_items") ){
                        extra_arr = new JSONArray(obj.getString("extra_items"));}


                    ArrayList<CategoryDetails> foodCategoryDetails = new ArrayList<>();
                    List<ProductDetails> foodProduct = new ArrayList<>();
                    if (food_arr != null) {
                        for (int i = 0; i < food_arr.length(); i++) {
                            JSONObject cat = new JSONObject(food_arr.get(i).toString());
                            CategoryDetails sub_obj = new CategoryDetails();
                            sub_obj.setCategory_id(cat.getString("category_id"));
                            sub_obj.setCategory_name(cat.getString("category_name"));
                            sub_obj.setCategory_description(cat.getString("category_description"));
                            sub_obj.setSequence_nr(cat.getString("sequence_nr"));
                            sub_obj.setCategory_image(cat.getString("category_image"));
                            sub_obj.setCategory_type(cat.getString("category_type"));

                            if (cat.has("product_details")) {
                                JSONArray product = new JSONArray(cat.getString("product_details"));
                                ArrayList<ProductDetails> productDetails = new ArrayList<>();
                                for (int j = 0; j < product.length(); j++) {
                                    JSONObject proDetail = new JSONObject(product.get(j).toString());
                                    ProductDetails proObj = new ProductDetails();
                                    proObj.setProduct_id(proDetail.getString("product_id"));
                                    proObj.setProduct_code(proDetail.getString("product_code"));
                                    proObj.setProduct_name(proDetail.getString("product_name"));
                                    if (proDetail.has("product_photo")) {
                                        proObj.setProduct_photo(proDetail.getString("product_photo"));
                                    }
                                    proObj.setProduct_description(proDetail.getString("product_description"));
                                    proObj.setProduct_price(proDetail.getString("product_price"));
                                    if (proDetail.has("product_bar_code")){
                                        proObj.setProduct_bar_code(proDetail.getString("product_bar_code"));
                                    }
                                    productDetails.add(proObj);
                                    foodProduct.add(proObj);
                                }
                                sub_obj.setProductDetails(productDetails);

                            }
                            foodCategoryDetails.add(sub_obj);
                        }
                    }

                    ArrayList<CategoryDetails> barCategoryDetails = new ArrayList<>();
                    List<ProductDetails> barProduct = new ArrayList<>();
                    if (bar_arr!=null) {
                        for (int i = 0; i < bar_arr.length(); i++) {
                            JSONObject cat = new JSONObject(bar_arr.get(i).toString());
                            CategoryDetails sub_obj = new CategoryDetails();
                            sub_obj.setCategory_id(cat.getString("category_id"));
                            sub_obj.setCategory_name(cat.getString("category_name"));
                            sub_obj.setCategory_description(cat.getString("category_description"));
                            sub_obj.setSequence_nr(cat.getString("sequence_nr"));
//                            sub_obj.setParent_id(cat.getString("parent_id"));
                            sub_obj.setCategory_image(cat.getString("category_image"));
                            sub_obj.setCategory_type(cat.getString("category_type"));

                            if (cat.has("product_details")) {
                                JSONArray product = new JSONArray(cat.getString("product_details"));
                                ArrayList<ProductDetails> productDetails = new ArrayList<>();
                                for (int j = 0; j < product.length(); j++) {
                                    JSONObject proDetail = new JSONObject(product.get(j).toString());
                                    ProductDetails proObj = new ProductDetails();
                                    proObj.setProduct_id(proDetail.getString("product_id"));
                                    proObj.setProduct_code(proDetail.getString("product_code"));
                                    proObj.setProduct_name(proDetail.getString("product_name"));
                                    if (proDetail.has("product_photo")) {
                                        proObj.setProduct_photo(proDetail.getString("product_photo"));
                                    }
                                    proObj.setProduct_description(proDetail.getString("product_description"));
                                    proObj.setProduct_price(proDetail.getString("product_price"));
                                    if (proDetail.has("product_bar_code")){
                                        proObj.setProduct_bar_code(proDetail.getString("product_bar_code"));
                                    }
                                    productDetails.add(proObj);
                                    barProduct.add(proObj);
                                }
                                sub_obj.setProductDetails(productDetails);

                            }
                            barCategoryDetails.add(sub_obj);
                        }
                    }

                    if (extra_arr != null) {
                        extraItem = new ArrayList<>();
                        for (int i = 0; i < extra_arr.length(); i++) {
                            JSONObject cat = new JSONObject(extra_arr.get(i).toString());
                            ExtraItem sub_obj = new ExtraItem();
                            sub_obj.setExtra_item_id(cat.getString("extra_item_id"));
                            sub_obj.setExtra_item_name(cat.getString("extra_item_name"));
                            sub_obj.setExtra_item_price(cat.getString("extra_item_price"));
                            sub_obj.setExtra_item_created_by(cat.getString("extra_item_created_by"));
                            sub_obj.setExtra_item_created_on(cat.getString("extra_item_created_on"));
                            extraItem.add(sub_obj);
                        }
                    }

                    if (SharedPref.getString(RestaurantOrderActivity.this,"order_type").equals("FOOD")){
                        categoryDetails = foodCategoryDetails;
                        allProducts = foodProduct;
                    }else{
                        categoryDetails = barCategoryDetails;
                        allProducts = barProduct;
                    }

                    if (categoryDetails != null){
                        loadProductByCategory(categoryDetails.get(0).getProductDetails());
                        CategoryAdapter adapter = new CategoryAdapter(RestaurantOrderActivity.this, categoryDetails, new CategoryAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(CategoryDetails item) {
                                loadProductByCategory(item.getProductDetails());
//                            filter(item.getCategory_id());
                            }
                        });

                        recyclerViewCategory.setAdapter(adapter);
                    }
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readFromFile(String path) {
        String ret = "";
        try {
            InputStream inputStream = new FileInputStream(new File(path));

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("FileToJson", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("FileToJson", "Can not read file: " + e.toString());
        }
        return ret;
    }

    void filter(String text){
        boolean flag = true;
        ArrayList<ProductDetails> temp = new ArrayList();
        for(ProductDetails d: allProducts){
            if (d.getProduct_bar_code()==null){ d.setProduct_bar_code("");}
            if(d.getProduct_name().toLowerCase().contains(text.toLowerCase())){
                temp.add(d);
            }else if ((d.getProduct_code()).toLowerCase().contains(text.toLowerCase())){
                temp.add(d);
            }else if ((d.getProduct_bar_code()).toLowerCase().equals(text.toLowerCase())){
                temp.add(d);
                addOrder(d);
                searchByName.setText("");
                flag = false;
                break;
            }
        }
        if (flag == true){
            productAdapter.updateList(temp);
        }
    }


    private void addExtraItem() {
        if (extraItem != null){
            Dialog dialog = new Dialog(RestaurantOrderActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.view_add_order_extra_item_dialog);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            Window window = dialog.getWindow();
            assert window != null;
            window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            AppCompatTextView edt_dialog_title = dialog.findViewById(R.id.edit_title);
            RecyclerView recyclerView = dialog.findViewById(R.id.recycler_view);

            edt_dialog_title.setText(getResources().getString(R.string.app_name));
            edt_dialog_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            edt_dialog_title.setTypeface(FontStyle.getFontRegular());

//        if (SharedPref.getString(RestaurantOrderActivity.this,"bill_type").equals("parcel")){
//            order_id =  dataBaseHelper.checkParcelStatus(SharedPref.getString(RestaurantOrderActivity.this,"table"),order_type,SharedPref.getString(RestaurantOrderActivity.this,"section"),"parcel_order_id");
//        }else{
//            order_id =  dataBaseHelper.checkTableStatus(SharedPref.getString(RestaurantOrderActivity.this,"table"),SharedPref.getString(RestaurantOrderActivity.this,"section"),SharedPref.getString(RestaurantOrderActivity.this,"user_id"),order_type,"order_id");
//        }

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
            linearLayoutManager.setReverseLayout(false);
            recyclerView.setLayoutManager(linearLayoutManager);
            if (extraItem != null){
                ExtraItemListAdapter adapter = new ExtraItemListAdapter(RestaurantOrderActivity.this, extraItem,"0", new ExtraItemListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ExtraItem item) {
                        addQuantity(item);
                        dialog.dismiss();
                    }
                });
                recyclerView.setAdapter(adapter);
            }

            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    private void addQuantity(ExtraItem item) {
        Dialog customDialog = new Dialog(RestaurantOrderActivity.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.view_change_quanitity);
        customDialog.setCancelable(true);
        customDialog.setCanceledOnTouchOutside(true);
        Window window = customDialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        AppCompatTextView name = customDialog.findViewById(R.id.product_name);
        AppCompatTextView description = customDialog.findViewById(R.id.product_description);
        AppCompatEditText quantity = customDialog.findViewById(R.id.quanitity);
        AppCompatButton addBtn = customDialog.findViewById(R.id.btn_add);
        AppCompatButton subBtn = customDialog.findViewById(R.id.btn_sub);
        AppCompatImageView btnClose = customDialog.findViewById(R.id.close_btn);
        CardView removeCard = customDialog.findViewById(R.id.removeCard);

        AppCompatTextView saveBtn = customDialog.findViewById(R.id.btn_submit);
        AppCompatTextView closeBtn = customDialog.findViewById(R.id.btn_cancel);
        closeBtn.setVisibility(View.GONE);
        removeCard.setVisibility(View.GONE);
        saveBtn.setText(getResources().getString(R.string.add));

        name.setTypeface(FontStyle.getFontRegular());
        description.setTypeface(FontStyle.getFontRegular());
        quantity.setTypeface(FontStyle.getFontRegular());
        addBtn.setTypeface(FontStyle.getFontRegular());
        subBtn.setTypeface(FontStyle.getFontRegular());
        saveBtn.setTypeface(FontStyle.getFontRegular());

        description.setVisibility(View.GONE);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });
        quantity.setText("1");
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.valueOf(quantity.getText().toString()) < 999){
                    quantity.setText(String.valueOf(Integer.valueOf(quantity.getText().toString())+1));
                }
            }});
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.valueOf(quantity.getText().toString())==1){

                }else if (Integer.valueOf(quantity.getText().toString())!=0){
                    quantity.setText(String.valueOf(Integer.valueOf(quantity.getText().toString())-1));
                }
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (quantity.getText().toString().isEmpty()){
                    quantity.setError(getResources().getString(R.string.required));
                }else{
                   List<ExtraItemList> extraItemLists = tableOrderDetail.getExtra_items();
                if (extraItemLists == null){ extraItemLists = new ArrayList<>(); }

                boolean status = true;
                if (extraItemLists.size()!=0){
                    for (int i=0; i<extraItemLists.size(); i++){
                        if (extraItemLists.get(i).getOrder_extra_item_name().equals(item.getExtra_item_name()) && extraItemLists.get(i).getOrder_extra_item_kot_yn().equals("NO")){
                            int newQty = Integer.valueOf(extraItemLists.get(i).getOrder_extra_item_qty()) +  Integer.valueOf(quantity.getText().toString());
                            extraItemLists.get(i).setOrder_extra_item_qty(String.valueOf(newQty));
                            updateOrderExtraItemRecyclerView(extraItemLists);
                            status = false;
                            break;
                        }
                    }

                }
                if (status){
                    int total = Integer.valueOf(quantity.getText().toString());
                    if (total!=0){
                        ExtraItemList obj = new ExtraItemList();
                        obj.setOrder_extra_item_id(new GenerateRandom().getRandomString());
                        obj.setOrder_extra_item_name(item.getExtra_item_name());
                        obj.setOrder_extra_item_qty(String.valueOf(total));
                        obj.setOrder_extra_item_price(item.getExtra_item_price());
                        obj.setOrder_extra_item_kot_yn("NO");
                        extraItemLists.add(obj);
                        updateOrderExtraItemRecyclerView(extraItemLists);
                    }
                }
                }


                customDialog.dismiss();
            }
        });
        customDialog.show();
    }

    private void cancelOrderForTable() {

        String foodStatus = SharedPref.getString(RestaurantOrderActivity.this,"food_table_status");
        String barStatus = SharedPref.getString(RestaurantOrderActivity.this,"bar_table_status");
        String foodBillId = SharedPref.getString(RestaurantOrderActivity.this,"food_bill_id");
        String barBillId = SharedPref.getString(RestaurantOrderActivity.this,"bar_bill_id");

        String table_id = selectedTable;

        if (foodStatus.equals("2") || barStatus.equals("2")) {
            DialogBox(getResources().getString(R.string.bill_printed_for_selected_table),null);
        }else{

            if (foodBillId.length()!=0 || barBillId.length()!=0){
                askForReason();
            }else{
                CaptainOrderService.getInstance().sendCommand(Constants.cmdClearOrderTable + SharedPref.getString(RestaurantOrderActivity.this, "device_id") + "#{'section_id':'" +section_id+"','user_id':'"+SharedPref.getString(RestaurantOrderActivity.this,"user_id")+"','table_id':'"+ table_id+"'}");

            }

        }

    }

    private void askForReason() {
        Dialog dialog = new Dialog(RestaurantOrderActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_kot_cancel_reason_dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        AppCompatTextView edt_dialog_title = dialog.findViewById(R.id.edit_title);
        AppCompatEditText edt_dialog_msg = dialog.findViewById(R.id.edit_msg);
        AppCompatTextView btn_dialog_cofirm = dialog.findViewById(R.id.confirm_button);

        edt_dialog_title.setText(getResources().getString(R.string.app_name));
        edt_dialog_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                dialog.dismiss();
            }
        });
        btn_dialog_cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_dialog_msg.getText().toString().isEmpty()){
                    edt_dialog_msg.setError(getResources().getString(R.string.required));
                }else{
                    InputMethodManager imm = (InputMethodManager) getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    String table_id = selectedTable;

                    String user_id = SharedPref.getString(RestaurantOrderActivity.this,"user_id");

                    CaptainOrderService.getInstance().sendCommand(Constants.cmdClearKOTTable + SharedPref.getString(RestaurantOrderActivity.this, "device_id") + "#{'section_id':'" +section_id+"','user_id':'"+SharedPref.getString(RestaurantOrderActivity.this,"user_id")+"','table_id':'"+ table_id+"','table_series_no':'"+SharedPref.getString(RestaurantOrderActivity.this,"table_series_no")+"','reason':'" + edt_dialog_msg.getText().toString()+"'}");
                    dialog.dismiss();

                }
            }
        });

        edt_dialog_title.setTypeface(FontStyle.getFontRegular());
        edt_dialog_msg.setTypeface(FontStyle.getFontRegular());
        btn_dialog_cofirm.setTypeface(FontStyle.getFontRegular());
        dialog.show();
    }

    private void addOrder(ProductDetails item) {

        //for cross check
        OrderDetail ob = new OrderDetail();
        ob.setProduct_id(item.getProduct_id());
        ob.setProduct_name(item.getProduct_name());
        ob.setProduct_quantity("1");
        ob.setProduct_price(item.getProduct_price());
        ob.setProduct_special_note("");
        ob.setProduct_kot_yn("NO");
        nonKOTItems.add(ob);
        //for cross check
        List<OrderDetail> orderDetail = tableOrderDetail.getOrder_detail();

        if (orderDetail == null && orderDetail.size() == 0){
            OrderDetail obj = new OrderDetail();
            obj.setProduct_id(item.getProduct_id());
            obj.setProduct_name(item.getProduct_name());
            obj.setProduct_quantity("1");
            obj.setProduct_price(item.getProduct_price());
            obj.setProduct_special_note("");
            obj.setProduct_kot_yn("NO");
            orderDetail.add(obj);
        }else{
            boolean status = true;
            for (int i=0; i<orderDetail.size(); i++){
                if (orderDetail.get(i).getProduct_id().equals(item.getProduct_id()) && orderDetail.get(i).getProduct_kot_yn().equals("NO")){
                    String quantity = String.valueOf(Integer.valueOf(orderDetail.get(i).getProduct_quantity())+1);
                    orderDetail.get(i).setProduct_quantity(quantity);
                    status = false;
                    break;
                }
            }
            if (status){
                OrderDetail obj = new OrderDetail();
                obj.setProduct_id(item.getProduct_id());
                obj.setProduct_name(item.getProduct_name());
                obj.setProduct_quantity("1");
                obj.setProduct_price(item.getProduct_price());
                obj.setProduct_special_note("");
                obj.setProduct_kot_yn("NO");
                orderDetail.add(obj);
            }
        }
        updateOrderItemRecyclerView(orderDetail);
    }

    private void calculateTotalFigures(List<OrderDetail> orderDetailList,List<ExtraItemList> extraItemLists) {
        int totalItem=0 ,totalQty=0 ;
        double totalPrice=0 ,totalSGST = 0 ,totalCGST = 0 ;

        if (orderDetailList != null){
            for (int i=0; i<orderDetailList.size(); i++){
                totalPrice = totalPrice+(
                        Double.parseDouble(orderDetailList.get(i).getProduct_quantity())
                                * Double.parseDouble(orderDetailList.get(i).getProduct_price())
                );
                totalQty = totalQty + Integer.valueOf(orderDetailList.get(i).getProduct_quantity());
            }
            totalItem = totalItem + orderDetailList.size();
        }

        if (extraItemLists.size()!=0){
            for (int i=0; i<extraItemLists.size(); i++){
                totalPrice = totalPrice+(
                        Double.parseDouble(extraItemLists.get(i).getOrder_extra_item_qty())
                                * Double.parseDouble(extraItemLists.get(i).getOrder_extra_item_price())
                );
                totalQty = totalQty + Integer.valueOf(extraItemLists.get(i).getOrder_extra_item_qty());
            }
            totalItem = totalItem + extraItemLists.size();
        }

        totalAmountTxt.setText(String.valueOf(totalPrice));
        totalQuantityTxt.setText(String.valueOf(totalQty));
        totalItemTxt.setText(String.valueOf( totalItem ));

    }

    public void OnOrderOptionClick(View view) {
        if ( nonKOTItems.size()!= 0){
            DialogBox(getResources().getString(R.string.kot_pending),null);
        }else{
            SharedPref.putString(RestaurantOrderActivity.this,"order_type","FOOD");
            startActivity(new Intent(getApplicationContext(), RestaurantOrderActivity.class));
            finish();
        }
    }

    public void OnBarOrderClick(View view) {
        if ( nonKOTItems.size()!= 0){
            DialogBox(getResources().getString(R.string.kot_pending),null);
        }else{
            SharedPref.putString(RestaurantOrderActivity.this,"order_type","BAR");
            startActivity(new Intent(getApplicationContext(), RestaurantOrderActivity.class));
            finish();
        }
    }

    private void DialogBox(String msg, Class<TableSelectionActivity> tableSelectionActivityClass) {
        Dialog dialog = new Dialog(RestaurantOrderActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        AppCompatTextView edt_dialog_title = dialog.findViewById(R.id.edit_title);
        AppCompatTextView edt_dialog_msg = dialog.findViewById(R.id.edit_msg);
        AppCompatTextView btn_dialog_cofirm = dialog.findViewById(R.id.confirm_button);

        edt_dialog_title.setText(getResources().getString(R.string.app_name));
        edt_dialog_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        edt_dialog_msg.setText(msg);
        btn_dialog_cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tableSelectionActivityClass != null){
                    startActivity(new Intent(getApplicationContext(),TableSelectionActivity.class));
                    finish();
                }else{
                    dialog.dismiss();
                }
            }
        });

        edt_dialog_title.setTypeface(FontStyle.getFontRegular());
        edt_dialog_msg.setTypeface(FontStyle.getFontRegular());
        btn_dialog_cofirm.setTypeface(FontStyle.getFontRegular());
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (nonKOTItems.size()!= 0){
            DialogBox(getResources().getString(R.string.kot_pending),null);
        }else{
            startActivity(new Intent(getApplicationContext(),TableSelectionActivity.class));
            finish();
        }
    }

    private void loadProductByCategory(List<ProductDetails> productDetails) {
        recyclerViewMenuItem.setVisibility(View.VISIBLE);
        if (productDetails != null){
            productAdapter = new ProductAdapter(RestaurantOrderActivity.this, productDetails, new ProductAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(List<ProductDetails> item, int position) {
                    addOrder(item.get(position));
                }
            });
            recyclerViewMenuItem.setAdapter(productAdapter);
        }else{
            recyclerViewMenuItem.setVisibility(View.INVISIBLE);
        }
    }

    public void OnExtraItemOptionClick(View view) {
        addExtraItem();
    }


}