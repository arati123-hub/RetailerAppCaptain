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
import android.os.Bundle;
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
import com.appwelt.retailer.captain.R;
import com.appwelt.retailer.captain.adapter.KOTListAdapter;
import com.appwelt.retailer.captain.model.BillDetail;
import com.appwelt.retailer.captain.model.BillDetails;
import com.appwelt.retailer.captain.model.CustomerDetails;
import com.appwelt.retailer.captain.model.ExtraItemList;
import com.appwelt.retailer.captain.model.OrderDetail;
import com.appwelt.retailer.captain.model.OrderExtraItem;
import com.appwelt.retailer.captain.model.TableBillDetail;
import com.appwelt.retailer.captain.services.CaptainOrderService;
import com.appwelt.retailer.captain.services.MessageDeframer;
import com.appwelt.retailer.captain.services.OnMessageListener;
import com.appwelt.retailer.captain.utils.Constants;
import com.appwelt.retailer.captain.utils.DateConversionClass;
import com.appwelt.retailer.captain.utils.FontStyle;
import com.appwelt.retailer.captain.utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CheckKOTActivity extends AppCompatActivity  implements OnMessageListener {

    private static final String TAG = "Check_KOT_ACTIVITY";

    RecyclerView recyclerViewOrderItem;
    AppCompatTextView orderNoTxt,totalAmountTxt,totalQuantityTxt,totalItemTxt;

    AppCompatTextView orderTitle,nameTitle,quantityTitle,rateTitle,noOfItemTitle,totalQuantityTitle,totalAmountTitle;
    AppCompatTextView btnCheckKOT,btnFinalizePrint,btnReprint,btnBack,btnFood,btnBar;
    AppCompatTextView customerNameTxt,customerNoTxt,billAmounTxt,sgstTxt,cgstTxt,billTotalTxt,discountTypeTxt,discountValueTxt,discountTotalTxt,discountReasonTxt,addTipTxt,extraTxt,tipTotalTxt,totolPayableTxt,totalPaidTxt,totalBalanceTxt,totolPayableEdt,totalPaidEdt,totalBalanceEdt;
    AppCompatEditText customerNameEdt,customerNoEdt,billAmounEdt,sgstEdt,cgstEdt,billTotalEdt,discountValueEdt,discountTotalEdt,discountReasonEdt,extraEdt,tipTotalEdt;
    AppCompatButton btnAddDiscount,btnAddTip;
    Spinner spinnerDiscountType;
    LinearLayoutCompat paidDiv,discountNTipDiv,tipDiv;

    String billAmount="0",sgstAmount="0",cgstAmount="0",discountPercent="0",discountAmount="0",tipAmount="0",balanceAmount="0",paidAmount="0",payableAmount="0",customerName,customerNo,customerAdd;

    AppCompatCheckBox extraItemIncludeBox;

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
                String response = getResources().getString(R.string.failed_with_server_call);
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
                String response = getResources().getString(R.string.failed_with_server_call);
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
                String response = getResources().getString(R.string.failed_with_server_call);
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

    @Override
    public void onReconnect() {

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
                        ItemList.add(bj);

                        ExtraItemList obj = new ExtraItemList();
                        obj.setOrder_extra_item_id(subObj.getString("order_extra_item_id"));
                        obj.setOrder_extra_item_name(subObj.getString("order_extra_item_name"));
                        obj.setOrder_extra_item_qty(subObj.getString("order_extra_item_qty"));
                        obj.setOrder_extra_item_price(subObj.getString("order_extra_item_price"));
                        extraItemLists.add(obj);
                    }
                    tableBillDetail.setExtra_bill_items(extraItemLists);
                }

            }

            recyclerViewOrderItem = findViewById(R.id.recyclerViewOrderItem);


            if (ItemList != null) {
                recyclerViewOrderItem.setLayoutManager(new LinearLayoutManager(CheckKOTActivity.this));
                KOTListAdapter adapter = new KOTListAdapter(CheckKOTActivity.this, ItemList, new KOTListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(List<OrderDetail> item) {
//                        startActivity(new Intent(getApplicationContext(),CheckKOTActivity.class));
//                        finish();
                    }
                });
                recyclerViewOrderItem.setAdapter(adapter);

                recountTotal();
                calculateTotalFigures();
            }

        } catch (JSONException e) {
            e.printStackTrace();
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
        btnFood.setTypeface(FontStyle.getFontRegular());
        btnBar.setTypeface(FontStyle.getFontRegular());

        selectedSection = SharedPref.getString(CheckKOTActivity.this,"section");
        selectedTable = SharedPref.getString(CheckKOTActivity.this,"table");

        CaptainOrderService.getInstance().ServiceInitiate();
        CaptainOrderService.getInstance().sendCommand(Constants.cmdTableKOTData + SharedPref.getString(CheckKOTActivity.this, "device_id") + "#{'section_id':'"+selectedSection+"','table_id':'"+selectedTable+"','user_id':'"+SharedPref.getString(CheckKOTActivity.this,"user_id")+"'}");


       tipDiv.setVisibility(View.GONE);
//        foodOrderId =  dataBaseHelper.checkTableStatus(SharedPref.getString(CheckKOTActivity.this,"table"),SharedPref.getString(CheckKOTActivity.this,"section"),SharedPref.getString(CheckKOTActivity.this,"user_id"),"FOOD","order_id");
//        barOrderId =  dataBaseHelper.checkTableStatus(SharedPref.getString(CheckKOTActivity.this,"table"),SharedPref.getString(CheckKOTActivity.this,"section"),SharedPref.getString(CheckKOTActivity.this,"user_id"),"BAR","order_id");
//        foodBillId = dataBaseHelper.selectByID("tbl_bill","order_id",foodOrderId,"bill_id");
//        barBillId = dataBaseHelper.selectByID("tbl_bill","order_id",barOrderId,"bill_id");
//        foodBillDetailsList = dataBaseHelper.BillDetailsByBillId(foodBillId);
//        barBillDetailsList = dataBaseHelper.BillDetailsByBillId(barBillId);
//
//        foodExtraItems = dataBaseHelper.BillExtraItem(foodBillId);
//        barExtraItem = dataBaseHelper.BillExtraItem(barBillId);

//        if (foodBillId.length()!=0 && barBillId.length()!=0){
//            orderNoTxt.setText(": "+foodBillId);
//        }else if (foodBillId.length()!=0){
//            orderNoTxt.setText(": "+foodBillId);
//        }else if (barBillId.length()!=0){
//            orderNoTxt.setText(": "+barBillId);
//        }

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


        String section = SharedPref.getString(getApplicationContext(),"section_name");
        orderTitle.setText(section+" "+SharedPref.getString(CheckKOTActivity.this,"table_name"));
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
        startActivity(new Intent(getApplicationContext(), TableSelectionActivity.class));
        finish();
    }

}