package com.appwelt.retailer.captain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.print.sdk.PrinterConstants;
import com.android.print.sdk.PrinterInstance;
import com.appwelt.retailer.captain.R;
import com.appwelt.retailer.captain.adapter.BillItemListAdapter;
import com.appwelt.retailer.captain.adapter.KOTItemListAdapter;
import com.appwelt.retailer.captain.adapter.KOTListAdapter;
import com.appwelt.retailer.captain.adapter.TableDetailsAdapter;
import com.appwelt.retailer.captain.model.BillDetail;
import com.appwelt.retailer.captain.model.BillDetails;
import com.appwelt.retailer.captain.model.BillFormatDetails;
import com.appwelt.retailer.captain.model.CustomerDetails;
import com.appwelt.retailer.captain.model.ExtraItemList;
import com.appwelt.retailer.captain.model.KOTItems;
import com.appwelt.retailer.captain.model.OrderDetail;
import com.appwelt.retailer.captain.model.OrderExtraItem;
import com.appwelt.retailer.captain.model.PrinterDetails;
import com.appwelt.retailer.captain.model.TableBillDetail;
import com.appwelt.retailer.captain.model.TableListDetails;
import com.appwelt.retailer.captain.printer.BluetoothOperation;
import com.appwelt.retailer.captain.printer.IPrinterOpertion;
import com.appwelt.retailer.captain.printer.UsbGenericPrinter;
import com.appwelt.retailer.captain.printer.UsbOperation;
import com.appwelt.retailer.captain.printer.WifiOperation;
import com.appwelt.retailer.captain.printer.utils.PrintUtils;
import com.appwelt.retailer.captain.services.CaptainOrderService;
import com.appwelt.retailer.captain.services.MessageDeframer;
import com.appwelt.retailer.captain.services.OnMessageListener;
import com.appwelt.retailer.captain.utils.Constants;
import com.appwelt.retailer.captain.utils.DateConversionClass;
import com.appwelt.retailer.captain.utils.FontStyle;
import com.appwelt.retailer.captain.utils.GenerateRandom;
import com.appwelt.retailer.captain.utils.SharedPref;
import com.appwelt.retailer.captain.utils.sqlitedatabase.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CheckKOTActivity extends AppCompatActivity  implements OnMessageListener {

    private static final String TAG = "Check_KOT_ACTIVITY";

    DatabaseHelper dataBaseHelper;

    RecyclerView recyclerViewOrderItem;
    AppCompatTextView orderNoTxt,totalAmountTxt,totalQuantityTxt,totalItemTxt;

    AppCompatTextView orderTitle,nameTitle,quantityTitle,rateTitle,noOfItemTitle,totalQuantityTitle,totalAmountTitle;
    AppCompatTextView btnCheckKOT,btnFinalizePrint,btnReprint,btnBack,btnFood,btnBar;
    AppCompatTextView customerNameTxt,customerNoTxt,billAmounTxt,sgstTxt,cgstTxt,billTotalTxt,discountTypeTxt,discountValueTxt,discountTotalTxt,discountReasonTxt,addTipTxt,extraTxt,tipTotalTxt,totolPayableTxt,totalPaidTxt,totalBalanceTxt,totolPayableEdt,totalPaidEdt,totalBalanceEdt;
    AppCompatEditText customerNameEdt,customerNoEdt,billAmounEdt,sgstEdt,cgstEdt,billTotalEdt,discountValueEdt,discountTotalEdt,discountReasonEdt,extraEdt,tipTotalEdt;
    AppCompatButton btnAddDiscount,btnAddTip;
    Spinner spinnerDiscountType;
    LinearLayoutCompat paidDiv,discountNTipDiv,tipDiv;

    String foodOrderId,barOrderId,foodBillId,barBillId;
    String billAmount="0",sgstAmount="0",cgstAmount="0",discountPercent="0",discountAmount="0",tipAmount="0",balanceAmount="0",paidAmount="0",payableAmount="0",customerName,customerNo,customerAdd;

    BillDetails foodBill,barBill;

    ArrayList<BillDetail> foodBillDetailsList,barBillDetailsList,checkKOTFinalPrint;
    ArrayList<BillDetail> paidFoodBillDetailsList,paidBarBillDetailsList;
    PrinterDetails printerBill;
    String selectedPrinterType, selectedPrinterIP,selectedPrinterPort,selectedPrinterComPort;

    //printer
    ArrayList<OrderExtraItem> foodExtraItems,barExtraItem;
    AppCompatCheckBox extraItemIncludeBox;

    String print_type = "";
    private int currIndex = 0;//  0--bluetooth,1--wifi,2--usb

    private static boolean isConnected;
    private IPrinterOpertion myOpertion;
    private PrinterInstance mPrinter;

    String selectedTable,selectedSection;
    TableBillDetail tableBillDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_kot);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        FontStyle.FontStyle(CheckKOTActivity.this);

        if (CaptainOrderService.getInstance() == null)
        {
            this.startService(new Intent(this, CaptainOrderService.class));
        }
        CaptainOrderService.getInstance().SetCaptainOrderServiceContext(this);

        String DATABASE_NAME = SharedPref.getString(CheckKOTActivity.this,"database_name");
        if (DATABASE_NAME != null && DATABASE_NAME.length()!=0){
            dataBaseHelper=new DatabaseHelper(this,DATABASE_NAME);
        }

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
        if (strCommand.equals(Constants.cmdTableKOTData)) {

            if (strData.startsWith("BILLLIST")) {
                strData = strData.replace("BILLLIST#", "");
                ShowBillUI(strData);
            }
            else if (strData.startsWith("INVALID"))
            {
                strData = strData.replace("INVALID", "");
                String response = "Failed with Server Call.";
                try {
                    JSONObject obj = new JSONObject(strData);
                    response = obj.getString("response");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                DialogBox(response);
            }
        }
        if (strCommand.equals(Constants.cmdPrintBill)) {

            if (strData.startsWith("PRINT")) {
                strData = strData.replace("PRINT#", "");
                try {
                    JSONObject jsonObject = new JSONObject(strData);
                    String response = jsonObject.getString("response");
                    DialogBox(response);
                    startActivity(new Intent(getApplicationContext(),TableSelectionActivity.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else if (strData.startsWith("INVALID"))
            {
                strData = strData.replace("INVALID", "");
                String response = "Failed with Server Call.";
                try {
                    JSONObject obj = new JSONObject(strData);
                    response = obj.getString("response");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                DialogBox(response);
            }
        }
        if (strCommand.equals(Constants.cmdPrintCheckKOT)) {

            if (strData.startsWith("PRINT")) {
                strData = strData.replace("PRINT#", "");
                try {
                    JSONObject jsonObject = new JSONObject(strData);
                    String response = jsonObject.getString("response");
                    DialogBox(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else if (strData.startsWith("INVALID"))
            {
                strData = strData.replace("INVALID", "");
                String response = "Failed with Server Call.";
                try {
                    JSONObject obj = new JSONObject(strData);
                    response = obj.getString("response");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                DialogBox(response);
            }
        }
    }

    private void ShowBillUI(String strData) {
        tableBillDetail = new TableBillDetail();
        try {
            JSONObject mainObj = new JSONObject(strData);
            tableBillDetail.setTable_id(mainObj.getString("table_id"));
            tableBillDetail.setSection_id(mainObj.getString("section_id"));
            tableBillDetail.setBill_amount(mainObj.getString("bill_amount"));
            tableBillDetail.setSgst_amount(mainObj.getString("sgst_amount"));
            tableBillDetail.setCgst_amount(mainObj.getString("cgst_amount"));

            Log.i(TAG, "ShowBillUI: "+mainObj.getString("bill_amount"));

            List<OrderDetail> ItemList = new ArrayList<>();
            JSONArray foodArray = new JSONArray(mainObj.getString("food_bill_items").toString());
            if (foodArray != null && foodArray.length()!=0){

                List<OrderDetail> foodItemList = new ArrayList<>();
                for (int i=0; i<foodArray.length(); i++){

                    JSONObject foodObj = new JSONObject(foodArray.get(i).toString());

                    OrderDetail obj = new OrderDetail();
                    obj.setProduct_id(foodObj.getString("product_id"));
                    obj.setProduct_name(foodObj.getString("product_name"));
                    obj.setProduct_quantity(foodObj.getString("product_quantity"));
                    obj.setProduct_price(foodObj.getString("product_price"));
                    if (foodObj.has("product_special_note")){
                        obj.setProduct_special_note(foodObj.getString("product_special_note"));
                    }
                    obj.setProduct_kot_yn(foodObj.getString("product_kot_yn"));
                    foodItemList.add(obj);
                    ItemList.add(obj);
                }
                tableBillDetail.setFood_bill_items(foodItemList);
            }

            JSONArray barArray = new JSONArray(mainObj.getString("bar_bill_items").toString());
            if (barArray != null && barArray.length()!=0){

                List<OrderDetail> barItemList = new ArrayList<>();
                for (int i=0; i<barArray.length(); i++){

                    JSONObject barObj = new JSONObject(barArray.get(i).toString());

                    OrderDetail obj = new OrderDetail();
                    obj.setProduct_id(barObj.getString("product_id"));
                    obj.setProduct_name(barObj.getString("product_name"));
                    obj.setProduct_quantity(barObj.getString("product_quantity"));
                    obj.setProduct_price(barObj.getString("product_price"));
                    if (barObj.has("product_special_note")){
                      obj.setProduct_special_note(barObj.getString("product_special_note"));
                    }
                    obj.setProduct_kot_yn(barObj.getString("product_kot_yn"));
                    barItemList.add(obj);
                    ItemList.add(obj);
                }
                tableBillDetail.setBar_bill_items(barItemList);
            }

            List<ExtraItemList> extraItemLists = new ArrayList<>();
            if (mainObj.has("extra_bill_items")){
                JSONArray mainArr = new JSONArray(mainObj.getString("extra_bill_items"));
                if (mainArr.length()!=0){
                    for (int i=0; i<mainArr.length(); i++){
                        JSONObject subObj = new JSONObject(mainArr.get(i).toString());

                        OrderDetail bj = new OrderDetail();
                        bj.setProduct_id(subObj.getString("order_extra_item_id"));
                        bj.setProduct_name(subObj.getString("order_extra_item_name"));
                        bj.setProduct_quantity(subObj.getString("order_extra_item_qty"));
                        bj.setProduct_price(subObj.getString("order_extra_item_price"));
//                        bj.setProduct_special_note("");
//                        bj.setProduct_kot_yn(subObj.getString("order_extra_item_kot_yn"));
                        ItemList.add(bj);

                        ExtraItemList obj = new ExtraItemList();
                        obj.setOrder_extra_item_id(subObj.getString("order_extra_item_id"));
                        obj.setOrder_extra_item_name(subObj.getString("order_extra_item_name"));
                        obj.setOrder_extra_item_qty(subObj.getString("order_extra_item_qty"));
                        obj.setOrder_extra_item_price(subObj.getString("order_extra_item_price"));
//                        obj.setOrder_extra_item_kot_yn(subObj.getString("order_extra_item_kot_yn"));
                        extraItemLists.add(obj);
                    }
                    tableBillDetail.setExtra_bill_items(extraItemLists);
                }

            }

            recyclerViewOrderItem = findViewById(R.id.recyclerViewOrderItem);


            if (ItemList != null){
                Log.i(TAG, "ShowBillUI: "+ItemList);
                recyclerViewOrderItem.setLayoutManager(new LinearLayoutManager(CheckKOTActivity.this));
                KOTListAdapter adapter = new KOTListAdapter(CheckKOTActivity.this, ItemList, new KOTListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(List<OrderDetail> item) {
//                        startActivity(new Intent(getApplicationContext(),CheckKOTActivity.class));
//                        finish();
                    }
                });
                recyclerViewOrderItem.setAdapter(adapter);
//                recyclerViewOrderItem.setLayoutManager(new LinearLayoutManager(CheckKOTActivity.this));

                recountTotal();
                calculateTotalFigures();
//                calculateNewItemBill();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReconnect() {

    }

    private void getCustomerDetails() {
        if (SharedPref.getString(CheckKOTActivity.this,"bill_type").equals("parcel")){
            String customer_id = dataBaseHelper.selectByID("tbl_parcel","parcel_id",SharedPref.getString(CheckKOTActivity.this,"table"),"parcel_customer_id");
            CustomerDetails customerDetails = dataBaseHelper.getCustomerDetails(customer_id);
            if (customerDetails != null){
                customerNameEdt.setText(customerDetails.getCustomer_name());
                customerNoEdt.setText(customerDetails.getCustomer_mobile_no());
            }
        }else if (SharedPref.getString(CheckKOTActivity.this,"bill_type").equals("grocery")){
            String customer_id = dataBaseHelper.selectByID("tbl_grocery_orders","grocery_id",SharedPref.getString(CheckKOTActivity.this,"table"),"grocery_customer_id");
            CustomerDetails customerDetails = dataBaseHelper.getCustomerDetails(customer_id);
            if (customerDetails != null){
                customerNameEdt.setText(customerDetails.getCustomer_name());
                customerNoEdt.setText(customerDetails.getCustomer_mobile_no());
            }
        }
    }

    private void init() {
        orderTitle = findViewById(R.id.order_title);
        nameTitle = findViewById(R.id.name_title);
        quantityTitle = findViewById(R.id.quantity_title);
        rateTitle = findViewById(R.id.rate_title);
        noOfItemTitle = findViewById(R.id.no_if_item_title);
        totalQuantityTitle = findViewById(R.id.total_quantity_title);
        totalAmountTitle = findViewById(R.id.total_amount_title);
        totalAmountTxt = findViewById(R.id.total_amount);
        totalQuantityTxt = findViewById(R.id.total_quantity);
        totalItemTxt = findViewById(R.id.total_item);
        orderNoTxt = findViewById(R.id.order_no);

        btnCheckKOT = findViewById(R.id.btn_check_kot);
        btnFinalizePrint = findViewById(R.id.btn_print_bill);
        btnReprint = findViewById(R.id.btn_reprint_bill);
        btnBack = findViewById(R.id.btn_back);

        customerNameTxt = findViewById(R.id.customer_name_txt);
        customerNoTxt = findViewById(R.id.customer_no_txt);
        billAmounTxt = findViewById(R.id.bill_amount_txt);
        sgstTxt = findViewById(R.id.sgst_txt);
        cgstTxt = findViewById(R.id.cgst_txt);
        billTotalTxt = findViewById(R.id.bill_total_txt);
        discountTypeTxt = findViewById(R.id.discount_type_txt);
        discountValueTxt = findViewById(R.id.discount_value_txt);
        discountTotalTxt = findViewById(R.id.discount_total_txt);
        discountReasonTxt = findViewById(R.id.discount_reason_txt);
        addTipTxt = findViewById(R.id.add_tip_txt);
        extraTxt = findViewById(R.id.extra_txt);
        tipTotalTxt = findViewById(R.id.tip_total_txt);
        totolPayableTxt = findViewById(R.id.total_payable_txt);
        totalPaidTxt = findViewById(R.id.total_paid_txt);
        totalBalanceTxt = findViewById(R.id.total_balance_txt);
        customerNameEdt = findViewById(R.id.customer_name_edt);
        customerNoEdt = findViewById(R.id.customer_no_edt);
        billAmounEdt = findViewById(R.id.bill_amount_edt);
        sgstEdt = findViewById(R.id.sgst_edt);
        cgstEdt = findViewById(R.id.cgst_edt);
        billTotalEdt = findViewById(R.id.bill_total_edt);
        discountValueEdt = findViewById(R.id.discount_value_edt);
        discountTotalEdt = findViewById(R.id.discount_total_edt);
        discountReasonEdt = findViewById(R.id.discount_reason_edt);
        extraEdt = findViewById(R.id.extra_edt);
        tipTotalEdt = findViewById(R.id.tip_total_edt);
        totolPayableEdt = findViewById(R.id.total_payable_edt);
        totalPaidEdt = findViewById(R.id.total_paid_edt);
        totalBalanceEdt = findViewById(R.id.total_balance_edt);
        extraItemIncludeBox = findViewById(R.id.allow_extra_item);
        extraItemIncludeBox.setChecked(true);

        spinnerDiscountType = findViewById(R.id.spinner_discount_type);
        paidDiv = findViewById(R.id.paid_div);
        discountNTipDiv = findViewById(R.id.discount_tip_div);
        tipDiv = findViewById(R.id.tip_div);

        btnAddTip = findViewById(R.id.btn_add_tip);
        btnAddDiscount = findViewById(R.id.btn_add_discount);
//        btnPayment = findViewById(R.id.btn_payment);
        btnFood = findViewById(R.id.food_title);
        btnBar = findViewById(R.id.bar_title);
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
        btnCheckKOT.setTypeface(FontStyle.getFontRegular());
        btnBack.setTypeface(FontStyle.getFontRegular());
        orderNoTxt.setTypeface(FontStyle.getFontRegular());
        customerNameTxt.setTypeface(FontStyle.getFontRegular());
        customerNoTxt.setTypeface(FontStyle.getFontRegular());
        billAmounTxt.setTypeface(FontStyle.getFontRegular());
        sgstTxt.setTypeface(FontStyle.getFontRegular());
        cgstTxt.setTypeface(FontStyle.getFontRegular());
        billTotalTxt.setTypeface(FontStyle.getFontRegular());
        discountTypeTxt.setTypeface(FontStyle.getFontRegular());
        discountValueTxt.setTypeface(FontStyle.getFontRegular());
        discountTotalTxt.setTypeface(FontStyle.getFontRegular());
        discountReasonTxt.setTypeface(FontStyle.getFontRegular());
        addTipTxt.setTypeface(FontStyle.getFontRegular());
        extraTxt.setTypeface(FontStyle.getFontRegular());
        tipTotalTxt.setTypeface(FontStyle.getFontRegular());
        totolPayableTxt.setTypeface(FontStyle.getFontRegular());
        totalPaidTxt.setTypeface(FontStyle.getFontRegular());
        totalBalanceTxt.setTypeface(FontStyle.getFontRegular());
        customerNameEdt.setTypeface(FontStyle.getFontRegular());
        customerNoEdt.setTypeface(FontStyle.getFontRegular());
        billAmounEdt.setTypeface(FontStyle.getFontRegular());
        sgstEdt.setTypeface(FontStyle.getFontRegular());
        cgstEdt.setTypeface(FontStyle.getFontRegular());
        billTotalEdt.setTypeface(FontStyle.getFontRegular());
        discountValueEdt.setTypeface(FontStyle.getFontRegular());
        discountTotalEdt.setTypeface(FontStyle.getFontRegular());
        discountReasonEdt.setTypeface(FontStyle.getFontRegular());
        extraEdt.setTypeface(FontStyle.getFontRegular());
        tipTotalEdt.setTypeface(FontStyle.getFontRegular());
        totolPayableEdt.setTypeface(FontStyle.getFontRegular());
        totalPaidEdt.setTypeface(FontStyle.getFontRegular());
        totalBalanceEdt.setTypeface(FontStyle.getFontRegular());
        btnAddTip.setTypeface(FontStyle.getFontRegular());
        btnAddDiscount.setTypeface(FontStyle.getFontRegular());
//        btnPayment.setTypeface(FontStyle.getFontRegular());
        btnFood.setTypeface(FontStyle.getFontRegular());
        btnBar.setTypeface(FontStyle.getFontRegular());

        selectedSection = SharedPref.getString(CheckKOTActivity.this,"section");
        selectedTable = SharedPref.getString(CheckKOTActivity.this,"table");

        CaptainOrderService.getInstance().ServiceInitiate();
        CaptainOrderService.getInstance().sendCommand(Constants.cmdTableKOTData + SharedPref.getString(CheckKOTActivity.this, "device_id") + "#{'section_id':'"+selectedSection+"','table_id':'"+selectedTable+"','user_id':'"+SharedPref.getString(CheckKOTActivity.this,"user_id")+"'}");

//        bill_info.setTypeface(FontStyle.getFontRegular());

        if (SharedPref.getString(CheckKOTActivity.this,"bill_type").equals("parcel")){
            tipDiv.setVisibility(View.GONE);
            foodOrderId =  dataBaseHelper.checkParcelStatus(SharedPref.getString(CheckKOTActivity.this,"table"),"FOOD",SharedPref.getString(CheckKOTActivity.this,"section"),"parcel_order_id");
            barOrderId =  dataBaseHelper.checkParcelStatus(SharedPref.getString(CheckKOTActivity.this,"table"),"BAR",SharedPref.getString(CheckKOTActivity.this,"section"),"parcel_order_id");
        }else if (SharedPref.getString(CheckKOTActivity.this,"bill_type").equals("grocery")){
            btnFood.setVisibility(View.GONE);
            btnBar.setVisibility(View.GONE);
            btnCheckKOT.setVisibility(View.GONE);
            addTipTxt.setText("Add Delivery Charges");
            foodOrderId =  dataBaseHelper.selectByID("tbl_grocery_orders","grocery_id",SharedPref.getString(CheckKOTActivity.this,"table"),"grocery_order_id");;
            barOrderId = "";
            tipDiv.setVisibility(View.VISIBLE);
        }else{
            tipDiv.setVisibility(View.GONE);
            foodOrderId =  dataBaseHelper.checkTableStatus(SharedPref.getString(CheckKOTActivity.this,"table"),SharedPref.getString(CheckKOTActivity.this,"section"),SharedPref.getString(CheckKOTActivity.this,"user_id"),"FOOD","order_id");
            barOrderId =  dataBaseHelper.checkTableStatus(SharedPref.getString(CheckKOTActivity.this,"table"),SharedPref.getString(CheckKOTActivity.this,"section"),SharedPref.getString(CheckKOTActivity.this,"user_id"),"BAR","order_id");
        }
        foodBillId = dataBaseHelper.selectByID("tbl_bill","order_id",foodOrderId,"bill_id");
        barBillId = dataBaseHelper.selectByID("tbl_bill","order_id",barOrderId,"bill_id");
        getCustomerDetails();
        foodBillDetailsList = dataBaseHelper.BillDetailsByBillId(foodBillId);
        barBillDetailsList = dataBaseHelper.BillDetailsByBillId(barBillId);

        foodExtraItems = dataBaseHelper.BillExtraItem(foodBillId);
        barExtraItem = dataBaseHelper.BillExtraItem(barBillId);

        if (foodBillId.length()!=0 && barBillId.length()!=0){
            orderNoTxt.setText(": "+foodBillId);
        }else if (foodBillId.length()!=0){
            orderNoTxt.setText(": "+foodBillId);
        }else if (barBillId.length()!=0){
            orderNoTxt.setText(": "+barBillId);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnCheckKOT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CaptainOrderService.getInstance().ServiceInitiate();
                CaptainOrderService.getInstance().sendCommand(Constants.cmdPrintCheckKOT + SharedPref.getString(CheckKOTActivity.this, "device_id") + "#{'table_id':'"+selectedTable+"','section_id':'"+selectedSection+"','user_id':'"+SharedPref.getString(CheckKOTActivity.this,"user_id")+"'}");
            }
        });

        btnFinalizePrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customerName = customerNameEdt.getText().toString();
                if (customerName.isEmpty()){
                    customerName = "";
                }
                customerNo = customerNoEdt.getText().toString();
                if (customerNo.isEmpty()){
                    customerNo = "";
                }
                String discountReason = "";
                if (!discountReasonEdt.getText().toString().isEmpty()){ discountReason = discountReasonEdt.getText().toString(); }
                CaptainOrderService.getInstance().ServiceInitiate();
                CaptainOrderService.getInstance().sendCommand(Constants.cmdPrintBill + SharedPref.getString(CheckKOTActivity.this, "device_id") + "#{'table_id':'"+selectedTable+"','section_id':'"+selectedSection+"','c_name':'"+customerName+"','c_no':'"+customerNo+"','discount_per':'"+discountPercent+"','discount_amount':'"+discountAmount+"','discount_reason':'"+discountReason+"','sgst':'"+sgstAmount+"','cgst':'"+cgstAmount+"','bill_amount':'"+billAmount+"','user_id':'"+SharedPref.getString(CheckKOTActivity.this,"user_id")+"'}");

            }
        });

        customerNoEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CustomerDetails customerDetails = dataBaseHelper.searchCustomerBy("customer_mobile_no",customerNoEdt.getText().toString());
                if (customerDetails !=  null){
                    customerNameEdt.setText(customerDetails.getCustomer_name());
                }else{ customerNameEdt.setText("");}
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        String section = dataBaseHelper.selectByID("tbl_sections","section_id", SharedPref.getString(CheckKOTActivity.this,"section"),"section_name");
        if (!section.equals("") && section != null){
            if (SharedPref.getString(CheckKOTActivity.this,"bill_type").equals("parcel")){
                String parcel_code = dataBaseHelper.selectByID("tbl_parcel","parcel_id",SharedPref.getString(CheckKOTActivity.this,"table"),"parcel_code");
                orderTitle.setText(section+" "+"P"+parcel_code);
            }else if (SharedPref.getString(CheckKOTActivity.this,"bill_type").equals("grocery")){
                String parcel_code = dataBaseHelper.selectByID("tbl_grocery_orders","grocery_id",SharedPref.getString(CheckKOTActivity.this,"table"),"grocery_code");
                orderTitle.setText(section+" "+"C"+parcel_code);
            }else {
                orderTitle.setText(section+" "+dataBaseHelper.selectByID("tbl_order_collectors","collector_id", SharedPref.getString(CheckKOTActivity.this,"table"),"collector_name"));
            }
        }
//        getOrderDetails();
    }

//    private void ReprintBill() {
//        print_type = "REPRINT";
//        openConn();
//        new Thread(new Runnable(){
//            public void run() {
//                myOpertion.open(null);
//            }
//        }).start();
//    }
//
    private void PrintBill() {
        print_type = "BILL";
        openConn();
        new Thread(new Runnable(){
            public void run() {
                myOpertion.open(null);
            }
        }).start();
    }

    private boolean addBillItemDetails() {
        boolean status = false;
        if (foodBillDetailsList != null){
            for (int i=0; i<foodBillDetailsList.size();i++){

                if (SharedPref.getString(CheckKOTActivity.this,"bill_type").equals("parcel")){
                    if (dataBaseHelper.selectByTwoID("tbl_parcel_food_bill_detail","bill_id","bill_detail_product_id",
                            foodBillDetailsList.get(i).getBill_id(),foodBillDetailsList.get(i).getBill_detail_product_id(),"bill_detail_product_quantity").length()!=0){
                        String qty = dataBaseHelper.selectByTwoID("tbl_food_bill_detail","bill_id","bill_detail_product_id",
                                foodBillDetailsList.get(i).getBill_id(),foodBillDetailsList.get(i).getBill_detail_product_id(),"bill_detail_product_quantity");
                        if (qty.length()==0){
                            qty = "0";
                        }
                        String newQty = String.valueOf(
                                Integer.valueOf(qty)
                                        + Integer.valueOf(foodBillDetailsList.get(i).getBill_detail_product_quantity())
                        );
                        ContentValues obj = new ContentValues();
                        obj.put("bill_detail_product_quantity",newQty);
                        if (dataBaseHelper.updateDetailsByTwoId("tbl_food_bill_detail","bill_id","bill_detail_product_id",foodBillDetailsList.get(i).getBill_id(),foodBillDetailsList.get(i).getBill_detail_product_id(),obj)){
                            dataBaseHelper.deleteDetails("tbl_bill_details","bill_detail_id",foodBillDetailsList.get(i).getBill_detail_id());
                            status = true;
                        }else{ status = false; }
                    }else{
                        ContentValues obj = new ContentValues();
                        obj.put("bill_detail_id",foodBillDetailsList.get(i).getBill_detail_id());
                        obj.put("bill_id",foodBillDetailsList.get(i).getBill_id());
                        obj.put("bill_detail_product_quantity",foodBillDetailsList.get(i).getBill_detail_product_quantity());
                        obj.put("bill_detail_product_price",foodBillDetailsList.get(i).getBill_detail_product_price());
                        obj.put("bill_detail_product_id",foodBillDetailsList.get(i).getBill_detail_product_id());
                        obj.put("bill_detail_product_discount",foodBillDetailsList.get(i).getBill_detail_product_discount());
                        obj.put("bill_detail_product_sgst",foodBillDetailsList.get(i).getBill_detail_product_sgst());
                        obj.put("bill_detail_product_cgst",foodBillDetailsList.get(i).getBill_detail_product_cgst());
                        obj.put("bill_detail_product_special_note",foodBillDetailsList.get(i).getBill_detail_product_special_note());
                        obj.put("bill_detail_created_on",new DateConversionClass().currentDateApoch());
                        obj.put("bill_detail_created_by",SharedPref.getString(CheckKOTActivity.this,"user_id"));
                        if (dataBaseHelper.insertDetails(obj,"tbl_parcel_food_bill_detail")){
                            dataBaseHelper.deleteDetails("tbl_bill_details","bill_detail_id",foodBillDetailsList.get(i).getBill_detail_id());
                            status = true;
                        }else{ status = false; }
                    }
                }else{
                    if (dataBaseHelper.selectByTwoID("tbl_food_bill_detail","bill_id","bill_detail_product_id",
                            foodBillDetailsList.get(i).getBill_id(),foodBillDetailsList.get(i).getBill_detail_product_id(),"bill_detail_product_quantity").length()!=0){
                        String newQty = String.valueOf(
                                Integer.valueOf(dataBaseHelper.selectByTwoID("tbl_food_bill_detail","bill_id","bill_detail_product_id",
                                        foodBillDetailsList.get(i).getBill_id(),foodBillDetailsList.get(i).getBill_detail_product_id(),"bill_detail_product_quantity"))
                                        + Integer.valueOf(foodBillDetailsList.get(i).getBill_detail_product_quantity())
                        );
                        ContentValues obj = new ContentValues();
                        obj.put("bill_detail_product_quantity",newQty);
                        if (dataBaseHelper.updateDetailsByTwoId("tbl_food_bill_detail","bill_id","bill_detail_product_id",foodBillDetailsList.get(i).getBill_id(),foodBillDetailsList.get(i).getBill_detail_product_id(),obj)){
                            dataBaseHelper.deleteDetails("tbl_bill_details","bill_detail_id",foodBillDetailsList.get(i).getBill_detail_id());
                            status = true;
                        }else{ status = false; }
                    }else{
                        ContentValues obj = new ContentValues();
                        obj.put("bill_detail_id",foodBillDetailsList.get(i).getBill_detail_id());
                        obj.put("bill_id",foodBillDetailsList.get(i).getBill_id());
                        obj.put("bill_detail_product_quantity",foodBillDetailsList.get(i).getBill_detail_product_quantity());
                        obj.put("bill_detail_product_price",foodBillDetailsList.get(i).getBill_detail_product_price());
                        obj.put("bill_detail_product_id",foodBillDetailsList.get(i).getBill_detail_product_id());
                        obj.put("bill_detail_product_discount",foodBillDetailsList.get(i).getBill_detail_product_discount());
                        obj.put("bill_detail_product_sgst",foodBillDetailsList.get(i).getBill_detail_product_sgst());
                        obj.put("bill_detail_product_cgst",foodBillDetailsList.get(i).getBill_detail_product_cgst());
                        obj.put("bill_detail_product_special_note",foodBillDetailsList.get(i).getBill_detail_product_special_note());
                        obj.put("bill_detail_created_on",new DateConversionClass().currentDateApoch());
                        obj.put("bill_detail_created_by",SharedPref.getString(CheckKOTActivity.this,"user_id"));
                        if (dataBaseHelper.insertDetails(obj,"tbl_food_bill_detail")){
                            dataBaseHelper.deleteDetails("tbl_bill_details","bill_detail_id",foodBillDetailsList.get(i).getBill_detail_id());
                            status = true;
                        }else{ status = false; }
                    }
                }
            }
        }
        if (barBillDetailsList != null){
            for (int i=0; i<barBillDetailsList.size();i++){

                if (SharedPref.getString(CheckKOTActivity.this,"bill_type").equals("parcel")){
                    if (dataBaseHelper.selectByTwoID("tbl_parcel_bar_bill_detail","bill_id","bill_detail_product_id",
                            barBillDetailsList.get(i).getBill_id(),barBillDetailsList.get(i).getBill_detail_product_id(),"bill_detail_product_quantity").length()!=0){
                        String newQty = String.valueOf(
                                Integer.valueOf(dataBaseHelper.selectByTwoID("tbl_bar_bill_detail","bill_id","bill_detail_product_id",
                                        barBillDetailsList.get(i).getBill_id(),barBillDetailsList.get(i).getBill_detail_product_id(),"bill_detail_product_quantity"))
                                        + Integer.valueOf(barBillDetailsList.get(i).getBill_detail_product_quantity())
                        );
                        ContentValues obj = new ContentValues();
                        obj.put("bill_detail_product_quantity",newQty);
                        if (dataBaseHelper.updateDetailsByTwoId("tbl_bar_bill_detail","bill_id","bill_detail_product_id",foodBillDetailsList.get(i).getBill_id(),barBillDetailsList.get(i).getBill_detail_product_id(),obj)){
                            dataBaseHelper.deleteDetails("tbl_bill_details","bill_detail_id",barBillDetailsList.get(i).getBill_detail_id());
                            status = true;
                        }else{ status = false; }
                    }else {
                        ContentValues obj = new ContentValues();
                        obj.put("bill_detail_id",barBillDetailsList.get(i).getBill_detail_id());
                        obj.put("bill_id",barBillDetailsList.get(i).getBill_id());
                        obj.put("bill_detail_product_quantity",barBillDetailsList.get(i).getBill_detail_product_quantity());
                        obj.put("bill_detail_product_price",barBillDetailsList.get(i).getBill_detail_product_price());
                        obj.put("bill_detail_product_id",barBillDetailsList.get(i).getBill_detail_product_id());
                        obj.put("bill_detail_product_discount",barBillDetailsList.get(i).getBill_detail_product_discount());
                        obj.put("bill_detail_product_sgst",barBillDetailsList.get(i).getBill_detail_product_sgst());
                        obj.put("bill_detail_product_cgst",barBillDetailsList.get(i).getBill_detail_product_cgst());
                        obj.put("bill_detail_product_special_note",barBillDetailsList.get(i).getBill_detail_product_special_note());
                        obj.put("bill_detail_created_on",new DateConversionClass().currentDateApoch());
                        obj.put("bill_detail_created_by",SharedPref.getString(CheckKOTActivity.this,"user_id"));
                        if (dataBaseHelper.insertDetails(obj,"tbl_parcel_bar_bill_detail")){
                            dataBaseHelper.deleteDetails("tbl_bill_details","bill_detail_id",barBillDetailsList.get(i).getBill_detail_id());
                            status = true;
                        }else{ status = false; }
                    }
                }else{
                    if (dataBaseHelper.selectByTwoID("tbl_bar_bill_detail","bill_id","bill_detail_product_id",
                            barBillDetailsList.get(i).getBill_id(),barBillDetailsList.get(i).getBill_detail_product_id(),"bill_detail_product_quantity").length()!=0){
                        String newQty = String.valueOf(
                                Integer.valueOf(dataBaseHelper.selectByTwoID("tbl_bar_bill_detail","bill_id","bill_detail_product_id",
                                        barBillDetailsList.get(i).getBill_id(),barBillDetailsList.get(i).getBill_detail_product_id(),"bill_detail_product_quantity"))
                                        + Integer.valueOf(barBillDetailsList.get(i).getBill_detail_product_quantity())
                        );
                        ContentValues obj = new ContentValues();
                        obj.put("bill_detail_product_quantity",newQty);
                        if (dataBaseHelper.updateDetailsByTwoId("tbl_bar_bill_detail","bill_id","bill_detail_product_id",foodBillDetailsList.get(i).getBill_id(),barBillDetailsList.get(i).getBill_detail_product_id(),obj)){
                            dataBaseHelper.deleteDetails("tbl_bill_details","bill_detail_id",barBillDetailsList.get(i).getBill_detail_id());
                            status = true;
                        }else{ status = false; }
                    }else {
                        ContentValues obj = new ContentValues();
                        obj.put("bill_detail_id",barBillDetailsList.get(i).getBill_detail_id());
                        obj.put("bill_id",barBillDetailsList.get(i).getBill_id());
                        obj.put("bill_detail_product_quantity",barBillDetailsList.get(i).getBill_detail_product_quantity());
                        obj.put("bill_detail_product_price",barBillDetailsList.get(i).getBill_detail_product_price());
                        obj.put("bill_detail_product_id",barBillDetailsList.get(i).getBill_detail_product_id());
                        obj.put("bill_detail_product_discount",barBillDetailsList.get(i).getBill_detail_product_discount());
                        obj.put("bill_detail_product_sgst",barBillDetailsList.get(i).getBill_detail_product_sgst());
                        obj.put("bill_detail_product_cgst",barBillDetailsList.get(i).getBill_detail_product_cgst());
                        obj.put("bill_detail_product_special_note",barBillDetailsList.get(i).getBill_detail_product_special_note());
                        obj.put("bill_detail_created_on",new DateConversionClass().currentDateApoch());
                        obj.put("bill_detail_created_by",SharedPref.getString(CheckKOTActivity.this,"user_id"));
                        if (dataBaseHelper.insertDetails(obj,"tbl_bar_bill_detail")){
                            dataBaseHelper.deleteDetails("tbl_bill_details","bill_detail_id",barBillDetailsList.get(i).getBill_detail_id());
                            status = true;
                        }else{ status = false; }
                    }
                }
            }
        }
        return status;
    }

    private void PrintCheckKOT(){

        ArrayList<BillDetail> finalCheckKOTList = new ArrayList<>();
        if (paidFoodBillDetailsList != null) {
            for (int i = 0; i < paidFoodBillDetailsList.size(); i++) {
                finalCheckKOTList.add(paidFoodBillDetailsList.get(i));
            }
        }
        if (foodBillDetailsList != null) {
            for (int i = 0; i < foodBillDetailsList.size(); i++) {
                finalCheckKOTList.add(foodBillDetailsList.get(i));
            }
        }
        if (paidBarBillDetailsList != null) {
            for (int i = 0; i < paidBarBillDetailsList.size(); i++) {
                finalCheckKOTList.add(paidBarBillDetailsList.get(i));
            }
        }
        if (barBillDetailsList != null) {
            for (int i = 0; i < barBillDetailsList.size(); i++) {
                finalCheckKOTList.add(barBillDetailsList.get(i));
            }
        }
        if (finalCheckKOTList != null && finalCheckKOTList.size() != 0) {
//            startPrintingKOT(finalCheckKOTList);
        }
    }

    private void calculateTotalFigures() {

        billAmounEdt.setText(billAmount);
        sgstEdt.setText(sgstAmount);
        cgstEdt.setText(cgstAmount);
        billTotalEdt.setText(String.format("%.2f", ( Double.parseDouble(billAmount)
                + Double.parseDouble(sgstAmount) + Double.parseDouble(cgstAmount) )));
        discountTotalEdt.setText(String.format("%.2f", ( Double.parseDouble(billAmount)
                + Double.parseDouble(sgstAmount) + Double.parseDouble(cgstAmount) )));
        tipTotalEdt.setText(String.format("%.2f", ( Double.parseDouble(billAmount)
                + Double.parseDouble(sgstAmount) + Double.parseDouble(cgstAmount) )));
        extraEdt.setText("0");
        getPaymentDetails();
    }

    private void checkBillPrintDetails(String fid, String bid) {
        BillDetails billDetails = new BillDetails();
        if (fid != null && !fid.isEmpty()){
            if (fid.length() !=0){
                if (SharedPref.getString(CheckKOTActivity.this,"bill_type").equals("parcel")){
                    billDetails = dataBaseHelper.getParcelBillHeaderByBillID(fid);
                }else{
                    billDetails = dataBaseHelper.getBillHeaderByBillID(fid);
                }
            }
        }else if (bid != null && !bid.isEmpty()){
            if (bid.length() !=0){
                if (SharedPref.getString(CheckKOTActivity.this,"bill_type").equals("parcel")){
                    billDetails = dataBaseHelper.getParcelBillHeaderByBillID(bid);
                }else{
                    billDetails = dataBaseHelper.getBillHeaderByBillID(bid);
                }
            }
        }else{
            billDetails = null;
        }

        if (billDetails != null){
            if (billDetails.getBill_id() != null){
                paidDiv.setVisibility(View.GONE);
                btnFinalizePrint.setVisibility(View.GONE);
                btnFood.setVisibility(View.GONE);
                btnBar.setVisibility(View.GONE);
                btnCheckKOT.setVisibility(View.GONE);
                btnReprint.setVisibility(View.VISIBLE);
//                btnPayment.setVisibility(View.VISIBLE);
                customerNoEdt.setText(billDetails.getCustomer_mobile_no());
                customerNameEdt.setText(billDetails.getCustomer_name());
                discountAmount = billDetails.getBill_discount_amount(); if (discountAmount == null){ discountAmount = "0";}
                tipAmount = billDetails.getBill_tip(); if (tipAmount == null){ tipAmount = "0";}
                billAmount = billDetails.getBill_amount(); if (billAmount == null){ billAmount = "0";}

//                bill_info.setText("Customer Name : "+billDetails.getCustomer_name()+"\n"
//                        + "Customer Mobile No : "+billDetails.getCustomer_mobile_no()+"\n"
//                        +"Discount : "+billDetails.getBill_discount_amount()+"\n"
//                        +"Tip : "+billDetails.getBill_tip()+"\n");
            }
        }

        recountTotal();
    }

    private void getPaymentDetails() {
        ArrayAdapter spinAdapter =  new ArrayAdapter<CharSequence>(this,
                R.layout.spinner_dropdown_small_item,
                CheckKOTActivity.this.getResources().getTextArray(R.array.discount_type));
        spinAdapter.setDropDownViewResource(R.layout.custom_small_spinner_layout);
        spinnerDiscountType.setAdapter(spinAdapter);
        spinnerDiscountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                discountValueEdt.setText("0");
                discountAmount = "0";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        btnAddTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!extraEdt.getText().toString().isEmpty()){
                    tipAmount = String.valueOf(Double.parseDouble(tipAmount)+Double.parseDouble(extraEdt.getText().toString()));
                    double total = (Double.parseDouble(billAmount) + Double.parseDouble(sgstAmount) + Double.parseDouble(cgstAmount) + Double.parseDouble(tipAmount)) - Double.parseDouble(discountAmount) ;
                    tipTotalEdt.setText(String.valueOf(total));
                    recountTotal();
                }
            }
        });
        btnAddDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!discountValueEdt.getText().toString().isEmpty()){
                    if(String.valueOf(spinnerDiscountType.getSelectedItem()).equals("Percentage")){
                        double amount = Double.parseDouble(billAmount) + Double.parseDouble(sgstAmount) + Double.parseDouble(cgstAmount);
                        discountPercent = discountValueEdt.getText().toString();
                        discountAmount = String.valueOf((Double.parseDouble(discountValueEdt.getText().toString()) * amount) / 100);
                        discountAmount = String.format("%.2f",( Double.parseDouble(discountAmount)) );

                        if ((amount - Double.parseDouble(discountAmount))  >= 0){
                            discountTotalEdt.setText( String.format("%.2f",( amount - Double.parseDouble(discountAmount) )));
                            tipTotalEdt.setText( String.format("%.2f",( amount - Double.parseDouble(discountAmount) )));
                            recountTotal();
                        }else{
                            discountValueEdt.setText("0");
                            discountValueEdt.setError(getResources().getString(R.string.invalid));
                        }
                    }else{
                        double total = Double.parseDouble(billAmount) + Double.parseDouble(sgstAmount) + Double.parseDouble(cgstAmount);
                        discountAmount = discountValueEdt.getText().toString();
                        discountAmount = String.format("%.2f",(Double.parseDouble(discountAmount)) );
                        discountPercent = String.valueOf((Double.parseDouble(discountValueEdt.getText().toString()) * 100 ) / total);

                        if ((total - Double.parseDouble(discountAmount)) >= 0){
                            discountTotalEdt.setText(  String.format("%.2f",( total - Double.parseDouble(discountAmount) )));
                            tipTotalEdt.setText( String.format("%.2f",( total - Double.parseDouble(discountAmount)) ));
                            recountTotal();
                        }else{
                            discountValueEdt.setText("0");
                            discountValueEdt.setError(getResources().getString(R.string.invalid));
                        }


                    }
                }
            }
        });

    }

    private void recountTotal() {

        billAmount = tableBillDetail.getBill_amount();
        sgstAmount = tableBillDetail.getSgst_amount();
        cgstAmount = tableBillDetail.getCgst_amount();
        if (tipAmount == null){ tipAmount = "0";}
        if (billAmount == null){ billAmount = "0";}
        if (sgstAmount == null){ sgstAmount = "0";}
        if (cgstAmount == null){ cgstAmount = "0";}
        if (discountAmount == null){ discountAmount = "0";}
        payableAmount = String.valueOf( Double.parseDouble(billAmount) + Double.parseDouble(sgstAmount) + Double.parseDouble(cgstAmount)
                + Double.parseDouble(tipAmount) - Double.parseDouble(discountAmount));

        payableAmount = removeMinus(payableAmount);
        totolPayableEdt.setText(String.format("%.2f",Double.parseDouble(payableAmount)));
        totalPaidEdt.setText(String.format("%.2f",Double.parseDouble(paidAmount)));
        totalBalanceEdt.setText( String.format("%.2f",( Double.parseDouble(payableAmount) - Double.parseDouble(paidAmount) )) );
        billAmounEdt.setText(String.format("%.2f",Double.parseDouble(billAmount)));
    }

    private String removeMinus(String str) {
        int i = 0;
        while (i < str.length() && str.charAt(i) == '-')
            i++;
        StringBuffer sb = new StringBuffer(str);
        sb.replace(0, i, "");

        return sb.toString();
    }

    private void sendOrderForBill(){
        boolean status = false;
        String table_id = SharedPref.getString(CheckKOTActivity.this,"table");
        String foodOrderId =  dataBaseHelper.checkTableStatus(table_id,SharedPref.getString(CheckKOTActivity.this,"section"),SharedPref.getString(CheckKOTActivity.this,"user_id"),"FOOD","order_id");
        String barOrderId =  dataBaseHelper.checkTableStatus(table_id,SharedPref.getString(CheckKOTActivity.this,"section"),SharedPref.getString(CheckKOTActivity.this,"user_id"),"BAR","order_id");
        String foodBillId = dataBaseHelper.selectByID("tbl_bill","order_id",foodOrderId,"bill_id");
        String barBillId = dataBaseHelper.selectByID("tbl_bill","order_id",barOrderId,"bill_id");

        ContentValues tbl_order0 = new ContentValues();
        tbl_order0.put("table_status","2");
        if (foodBillId.length()!=0){
            tbl_order0.put("bill_id",foodBillId);
            dataBaseHelper.updateDetails("tbl_table_order","order_id",foodOrderId,tbl_order0);
        }

        ContentValues tbl_order = new ContentValues();
        tbl_order.put("table_status","2");
        if (barBillId.length()!=0){
            tbl_order.put("bill_id",barBillId);
            dataBaseHelper.updateDetails("tbl_table_order","order_id",barOrderId,tbl_order);
        }

        dataBaseHelper.deleteDetails("tbl_order","order_id",foodOrderId);
        dataBaseHelper.deleteDetails("tbl_order","order_id",barOrderId);
        dataBaseHelper.deleteDetails("tbl_order_details","order_id",foodOrderId);
        dataBaseHelper.deleteDetails("tbl_order_details","order_id",barOrderId);
        dataBaseHelper.deleteDetails("tbl_bill","bill_id",foodBillId);
        dataBaseHelper.deleteDetails("tbl_bill","bill_id",barBillId);
        dataBaseHelper.deleteDetails("tbl_table_order","bill_id",foodBillId);
        dataBaseHelper.deleteDetails("tbl_table_order","bill_id",barBillId);

        status = true;

        if (status){
            if (addBillItemDetails()){
                DialogBox(getResources().getString(R.string.bill_printed_successfully));
                    startActivity(new Intent(getApplicationContext(),TableSelectionActivity.class));
                    finish();
            }else{ DialogBox(getResources().getString(R.string.fail_add_bill_details)); }
        }else{
            DialogBox(getResources().getString(R.string.table_type_update_fail));
        }
    }

    private void startPrintingKOT(ArrayList<BillDetail> orderDetails) {
        print_type = "CHECKKOT";
        checkKOTFinalPrint = orderDetails;
        Toast.makeText(this, "initialize printer", Toast.LENGTH_SHORT).show();
        openConn();
        new Thread(new Runnable(){
            public void run() {
                myOpertion.open(null);
            }
        }).start();
    }
//
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PrinterConstants.Connect.SUCCESS:
                    isConnected = true;
                     mPrinter = myOpertion.getPrinter();

                    new Thread(new Thread3(print_type)).start();
                    break;
                case PrinterConstants.Connect.FAILED:
                    isConnected = false;
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.printer_connection_fail), Toast.LENGTH_SHORT).show();
                    break;
                case PrinterConstants.Connect.CLOSED:
                    isConnected = false;
                    Toast.makeText(getApplicationContext(), "connect close...", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }

            updateButtonState();
        }

    };

    private void updateButtonState(){
        if(!isConnected)
        {
            String connStr =print_type+"(%1$s)";
            switch (currIndex) {
                case 0:
                    connStr = String.format(connStr, "Bluetooth");
                    break;
                case 1:
                    connStr = String.format(connStr, "WIFI");
                    break;
                case 2:
                    connStr = String.format(connStr, "USB");
                    break;
                case 3:
                    connStr = String.format(connStr, "Internal");
                    break;
                default:
                    break;
            }
            if (print_type.equals("BILL")){
                btnFinalizePrint.setText(connStr);
            }else {
                btnCheckKOT.setText(connStr);
            }
        }else{
            if (print_type.equals("BILL")){
                btnFinalizePrint.setText("Bill");
            }else {
                btnCheckKOT.setText("Check KOT");
            }
        }

    }

    private void CheckKOTPrintFail() {
        Dialog printerDialog = new Dialog(CheckKOTActivity.this);
        printerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        printerDialog.setContentView(R.layout.view_printer_conn_failed_dialog);
        printerDialog.setCancelable(true);
        printerDialog.setCanceledOnTouchOutside(true);
        Window window = printerDialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        AppCompatTextView edt_dialog_title = printerDialog.findViewById(R.id.edit_title);
        AppCompatTextView edt_dialog_msg = printerDialog.findViewById(R.id.edit_msg);
        AppCompatTextView btn_dialog_reprint = printerDialog.findViewById(R.id.btn_reprint);
        AppCompatTextView btn_dialog_cancel = printerDialog.findViewById(R.id.btn_cancel);

        edt_dialog_title.setText(getResources().getString(R.string.app_name));
        edt_dialog_msg.setText(R.string.printer_connection_fail);
        btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printerDialog.dismiss();
            }
        });

        btn_dialog_reprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrintCheckKOT();
                printerDialog.dismiss();
            }
        });

        edt_dialog_title.setTypeface(FontStyle.getFontRegular());
        edt_dialog_msg.setTypeface(FontStyle.getFontRegular());
        btn_dialog_reprint.setTypeface(FontStyle.getFontRegular());
        btn_dialog_cancel.setTypeface(FontStyle.getFontRegular());

        printerDialog.setCanceledOnTouchOutside(false);
        printerDialog.setCancelable(false);
        printerDialog.show();
    }

    private void BillPrintFail() {
        Dialog printerDialog = new Dialog(CheckKOTActivity.this);
        printerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        printerDialog.setContentView(R.layout.view_printer_conn_failed_dialog);
        printerDialog.setCancelable(true);
        printerDialog.setCanceledOnTouchOutside(true);
        Window window = printerDialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        AppCompatTextView edt_dialog_title = printerDialog.findViewById(R.id.edit_title);
        AppCompatTextView edt_dialog_msg = printerDialog.findViewById(R.id.edit_msg);
        AppCompatTextView btn_dialog_reprint = printerDialog.findViewById(R.id.btn_reprint);
        AppCompatTextView btn_dialog_cancel = printerDialog.findViewById(R.id.btn_cancel);

        edt_dialog_title.setText(getResources().getString(R.string.app_name));
        edt_dialog_msg.setText(R.string.printer_connection_fail);
        btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printerDialog.dismiss();
            }
        });

        btn_dialog_reprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrintBill();
                printerDialog.dismiss();
            }
        });

        edt_dialog_title.setTypeface(FontStyle.getFontRegular());
        edt_dialog_msg.setTypeface(FontStyle.getFontRegular());
        btn_dialog_reprint.setTypeface(FontStyle.getFontRegular());
        btn_dialog_cancel.setTypeface(FontStyle.getFontRegular());

        printerDialog.setCanceledOnTouchOutside(false);
        printerDialog.setCancelable(false);
        printerDialog.show();
    }

    private void openConn(){
        if (!isConnected) {
            switch (currIndex) {
                case 0: // bluetooth
                    myOpertion = new BluetoothOperation(CheckKOTActivity.this, mHandler);
                    break;
                case 1: // wifi
                    myOpertion = new WifiOperation(CheckKOTActivity.this, mHandler,selectedPrinterIP,selectedPrinterPort);
                    break;
                case 2: // usb
                    myOpertion = new UsbOperation(CheckKOTActivity.this, mHandler);
                    break;
                case 3: // usbGeneric
                    myOpertion = new UsbGenericPrinter(CheckKOTActivity.this, mHandler);
                    break;
                default:
                    break;
            }
            int port = 0;
            if (selectedPrinterPort != null && selectedPrinterPort.length() != 0) {
                port = Integer.parseInt(selectedPrinterPort);
            }
//            if (selectedPrinterType.equals("WIFI"))
                myOpertion.chooseDevice(selectedPrinterIP, port);
//            else if (selectedPrinterType.equals("USB"))
//                myOpertion.chooseDevice(selectedPrinterComPort, 0);
        } else {
            myOpertion.close();
            myOpertion = null;
            mPrinter = null;
        }
    }

    public void OnOrderOptionClick(View view) {
        SharedPref.putString(CheckKOTActivity.this,"order_type","FOOD");
        startActivity(new Intent(getApplicationContext(), RestaurantOrderActivity.class));
        finish();
    }

    public void OnBarOrderClick(View view) {
        SharedPref.putString(CheckKOTActivity.this,"order_type","BAR");
        startActivity(new Intent(getApplicationContext(), RestaurantOrderActivity.class));
        finish();
    }

    class Thread3 implements Runnable {
        private String msgType;

        Thread3(String msgType) {
            this.msgType  = msgType;
        }

        @Override
        public void run() {
            boolean printResult = false;
            if (msgType.equals("CHECKKOT")){
                String order_id="";
                if (foodOrderId.length()!=0 && barOrderId.length()!=0){
                    order_id = foodOrderId;
                }else if (foodOrderId.length()!=0){
                    order_id = foodOrderId;
                }else if (barOrderId.length()!=0){
                    order_id = barOrderId;
                }

//                if (currIndex == 3) {
//                    printResult = PrintUtils.printCheckKOTInternalPrinter(getApplicationContext(), mUsbConnection, mUsbEndpoint, order_id, checkKOTFinalPrint);
//                }else{
                    printResult = PrintUtils.printCheckKOT(getApplicationContext(), mPrinter, order_id, checkKOTFinalPrint);
//                }
            }else if (msgType.equals("BILL")){
                boolean includeInBill = extraItemIncludeBox.isSelected();
//                if (currIndex == 3) {
//                    printResult = PrintUtils.printBillInternalPrinter(getApplicationContext(), mUsbConnection, mUsbEndpoint,foodBillId, barBillId,includeInBill,discountAmount,tipAmount,customerName,customerNo);
//                }else{
                    printResult = PrintUtils.printBill(getApplicationContext(), mPrinter, foodBillId, barBillId,includeInBill,discountAmount,tipAmount,customerName,customerNo);
//                }
            }else if (msgType.equals("REPRINT")){
                boolean includeInBill = extraItemIncludeBox.isSelected();
                String fid,bid;

                fid = dataBaseHelper.selectByTwoID("tbl_table_order","table_id","order_type",
                        SharedPref.getString(CheckKOTActivity.this,"table"),"FOOD","bill_id");
                bid = dataBaseHelper.selectByTwoID("tbl_table_order","table_id","order_type",
                            SharedPref.getString(CheckKOTActivity.this,"table"),"BAR","bill_id");

//                if (currIndex == 3) {
//                    printResult = PrintUtils.reprintBillInternalPrinter(getApplicationContext(), mUsbConnection, mUsbEndpoint,fid, bid);
//                }else{
                    printResult = PrintUtils.reprintBill(getApplicationContext(), mPrinter, fid, bid);
//                }
            }

            final boolean KOTSuccess = printResult;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    myOpertion.close();
                    myOpertion = null;
                    mPrinter = null;
                    if (KOTSuccess) {
                        if (msgType.equals("BILL")){
                            BillPrintFinsh();
                        }
                    }else{
                        if (msgType.equals("BILL")){
                            BillPrintFail();
                        }else{
                            CheckKOTPrintFail();
                        }
                    }
                }
            });
        }
    }

    private void BillPrintFinsh() {
        if (customerNo.length() != 0){
            if (dataBaseHelper.selectByID("tbl_customer","customer_mobile_no",customerNo,"customer_id").length() == 0){
                ContentValues customerInfo = new ContentValues();
                customerInfo.put("customer_id", new GenerateRandom().getRandomString());
                customerInfo.put("customer_mobile_no", customerNo);
                customerInfo.put("customer_name", customerName);
                customerInfo.put("customer_address", "");
                customerInfo.put("customer_created_on", new DateConversionClass().currentDateApoch());
                customerInfo.put("customer_created_by", SharedPref.getString(CheckKOTActivity.this,"user_id"));
                dataBaseHelper.insertDetails(customerInfo,"tbl_customer");
            }
        }
        boolean status = false;
        if (barBillId.length()!=0){
            barBill = dataBaseHelper.getBillDetails(barOrderId);
            if (barBill != null){
                ContentValues obj = new ContentValues();
                obj.put("bill_id",barBill.getBill_id());
                obj.put("daily_cash_id",SharedPref.getString(CheckKOTActivity.this,"daily_cash_id"));
                obj.put("organisation_id", SharedPref.getString(CheckKOTActivity.this,"organisation_id"));
                obj.put("branch_id",SharedPref.getString(CheckKOTActivity.this,"branch_id"));
                if (!customerNameEdt.getText().toString().isEmpty()){ obj.put("customer_name",customerNameEdt.getText().toString()); }
                if (!customerNoEdt.getText().toString().isEmpty()){ obj.put("customer_mobile_no",customerNoEdt.getText().toString()); }
                obj.put("bill_amount",billAmount);
                obj.put("bill_discount_rate",discountPercent);
                obj.put("bill_discount_amount",discountAmount);
                if (discountReasonEdt.getText().toString().length()!=0){ obj.put("bill_discount_reason",discountReasonEdt.getText().toString()); }
                obj.put("bill_tip",tipAmount);
                obj.put("bill_created_on",new DateConversionClass().currentDateApoch());
                obj.put("bill_created_by",SharedPref.getString(CheckKOTActivity.this,"user_id"));

                if (SharedPref.getString(CheckKOTActivity.this,"bill_type").equals("parcel")){
                    if (dataBaseHelper.insertDetails(obj,"tbl_parcel_bar_bill_header")){ status = true;
                    }else{ status = false; }
                }else {
                    if (dataBaseHelper.insertDetails(obj,"tbl_bar_bill_header")){ status = true;
                    }else{ status = false; }
                }

            }
        }
        if (foodBillId.length()!=0){
            foodBill = dataBaseHelper.getBillDetails(foodOrderId);
            if (foodBill != null){
                ContentValues obj = new ContentValues();
                obj.put("bill_id",foodBill.getBill_id());
                obj.put("daily_cash_id",SharedPref.getString(CheckKOTActivity.this,"daily_cash_id"));
                obj.put("organisation_id", SharedPref.getString(CheckKOTActivity.this,"organisation_id"));
                obj.put("branch_id",SharedPref.getString(CheckKOTActivity.this,"branch_id"));
                if (customerNameEdt.getText().toString().length()!=0){ obj.put("customer_name",customerNameEdt.getText().toString()); }
                if (customerNoEdt.getText().toString().length()!=0){ obj.put("customer_mobile_no",customerNoEdt.getText().toString()); }
                obj.put("bill_amount",billAmount);
                obj.put("bill_discount_rate",discountPercent);
                obj.put("bill_discount_amount",discountAmount);
                if (discountReasonEdt.getText().toString().length()!=0){ obj.put("bill_discount_reason",discountReasonEdt.getText().toString()); }
                obj.put("bill_tip",tipAmount);
                obj.put("bill_created_on",new DateConversionClass().currentDateApoch());
                obj.put("bill_created_by",SharedPref.getString(CheckKOTActivity.this,"user_id"));
                if (SharedPref.getString(CheckKOTActivity.this,"bill_type").equals("parcel")){
                    if (dataBaseHelper.insertDetails(obj,"tbl_parcel_food_bill_header")){ status = true;
                    }else{ status = false; }
                }else {
                    if (dataBaseHelper.insertDetails(obj,"tbl_food_bill_header")){ status = true;
                    }else{ status = false; }
                }
            }
        }
        if (status){
            sendOrderForBill();
        }else{
            DialogBox(getResources().getString(R.string.fail_to_update));
        }
    }

    private void DialogBox(String msg) {
        Dialog dialog = new Dialog(CheckKOTActivity.this);
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
        edt_dialog_msg.setText(msg);
        btn_dialog_cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        edt_dialog_title.setTypeface(FontStyle.getFontRegular());
        edt_dialog_msg.setTypeface(FontStyle.getFontRegular());
        btn_dialog_cofirm.setTypeface(FontStyle.getFontRegular());
        dialog.show();
    }

    @Override
    public void onBackPressed() {
//        if (SharedPref.getString(CheckKOTActivity.this,"bill_type").equals("parcel")){
//            startActivity(new Intent(getApplicationContext(), ParcelSelectionActivity.class));
//        }else if (SharedPref.getString(CheckKOTActivity.this,"bill_type").equals("grocery")){
//            startActivity(new Intent(getApplicationContext(), GroceryOrderDashbaordActivity.class));
//        }else{
            startActivity(new Intent(getApplicationContext(), TableSelectionActivity.class));
//        }
        finish();
    }

}