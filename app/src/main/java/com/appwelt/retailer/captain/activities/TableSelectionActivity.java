package com.appwelt.retailer.captain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.appwelt.retailer.captain.R;
import com.appwelt.retailer.captain.adapter.TableDetailsAdapter;
import com.appwelt.retailer.captain.adapter.spinner_adapter.TableSpinnerAdapter;
import com.appwelt.retailer.captain.model.TableListDetails;
import com.appwelt.retailer.captain.services.CaptainOrderService;
import com.appwelt.retailer.captain.services.MessageDeframer;
import com.appwelt.retailer.captain.services.OnMessageListener;
import com.appwelt.retailer.captain.utils.Constants;
import com.appwelt.retailer.captain.utils.FontStyle;
import com.appwelt.retailer.captain.utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class TableSelectionActivity extends AppCompatActivity  implements OnMessageListener,SwipeRefreshLayout.OnRefreshListener  {

    private static final String TAG = "TABLE_SELECTION";

    RecyclerView recyclerView;
    String selectedTableType="",selectedTable = "", selectedTableName = "", selectedSection = "";

    String foodStatus,barStatus,foodOrderId,barOrderId,foodBillId,barBillId;

    ArrayList<TableListDetails> Halltables  = new ArrayList<>();
    private TableDetailsAdapter adapter;

    AppCompatTextView bill_title,change_table_title,bar_order_title,clean_table_title,btnSplit,btnJoin,btn_merge,btn_back;
    FileOutputStream stream;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_selection);
        FontStyle.FontStyle(TableSelectionActivity.this);

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


    /**
     * OnMessageRecevied is the Override method from the OnSOCMessageListener
     * @param strCommand required String contains message data.
     *     @param strData required String contains message data.
     */
    @Override
    public void onMessageReceived(String strCommand, String strData) {
        if (strCommand.equals(Constants.cmdListTables)) {

            if (strData.startsWith("TABLELIST")) {
                strData = strData.replace("TABLELIST#", "");
                ShowTableUI(strData);
            }
            else if (strData.startsWith("CHANGETABLE"))
            {
                strData = strData.replace("CHANGETABLE", "");
                ShowTableUI(strData);
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
        }else if (strCommand.equals(Constants.cmdClearOrderTable)) {

            if (strData.startsWith("CLEARTABLE")) {
                strData = strData.replace("CLEARTABLE#", "");
                ShowTableUI(strData);
            }
            else if (strData.startsWith("INVALID"))
            {
                strData = strData.replace("INVALID#", "");
                String response = "Failed with Server Call.";
                try {
                    JSONObject obj = new JSONObject(strData);
                    response = obj.getString("response");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                DialogBox(response);
            }
        }else if (strCommand.equals(Constants.cmdClearKOTTable)) {

            if (strData.startsWith("CLEARTABLE")) {
                strData = strData.replace("CLEARTABLE#", "");
                ShowTableUI(strData);
            }
            else if (strData.startsWith("INVALID"))
            {
                strData = strData.replace("INVALID#", "");
                String response = "Failed with Server Call.";
                try {
                    JSONObject obj = new JSONObject(strData);
                    response = obj.getString("response");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                DialogBox(response);
            }
        }else if (strCommand.equals(Constants.cmdChangeTable)) {

            if (strData.startsWith("CHANGETBL")) {
                strData = strData.replace("CHANGETBL#", "");
                ShowTableUI(strData);
            }
            else if (strData.startsWith("INVALID"))
            {
                strData = strData.replace("INVALID#", "");
                String response = "Failed with Server Call.";
                try {
                    JSONObject obj = new JSONObject(strData);
                    response = obj.getString("response");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                DialogBox(response);
            }
        }else if (strCommand.equals(Constants.cmdMergeTable)) {

            if (strData.startsWith("MERGETBL")) {
                strData = strData.replace("MERGETBL#", "");
                ShowTableUI(strData);
            }
            else if (strData.startsWith("INVALID"))
            {
                strData = strData.replace("INVALID#", "");
                String response = "Failed with Server Call.";
                try {
                    JSONObject obj = new JSONObject(strData);
                    response = obj.getString("response");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                DialogBox(response);
            }
        }else if (strCommand.equals(Constants.cmdTableSplit)) {
            Log.i(TAG, "onMessageReceived: "+strData);
            if (strData.startsWith("SPLIT")) {
                strData = strData.replace("SPLIT#", "");
                getTableDetails(selectedSection);
            }
            else if (strData.startsWith("INVALID"))
            {
                strData = strData.replace("INVALID#", "");
                String response = "Failed with Server Call.";
                try {
                    JSONObject obj = new JSONObject(strData);
                    response = obj.getString("response");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                DialogBox(response);
            }
        }else if (strCommand.equals(Constants.cmdTableUnsplit)) {
            Log.i(TAG, "onMessageReceived: "+strData);
            if (strData.startsWith("UNSPLIT")) {
                strData = strData.replace("UNSPLIT#", "");
                getTableDetails(selectedSection);
            }
            else if (strData.startsWith("INVALID"))
            {
                strData = strData.replace("INVALID#", "");
                String response = "Failed with Server Call.";
                try {
                    JSONObject obj = new JSONObject(strData);
                    response = obj.getString("response");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                DialogBox(response);
            }
        }else if (strCommand.equals(Constants.cmdDayClose))
        {
            Dialog dialog = new Dialog(TableSelectionActivity.this);
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

    private void ShowTableUI(String strData)
    {
        Halltables  = new ArrayList<>();

        try {
            JSONArray jArray = new JSONArray(strData); // json string to json array conversion
            if (jArray != null) {
                for (int i=0;i<jArray.length();i++){
                    JSONObject jsonObj = jArray.getJSONObject(i);

                    TableListDetails obj = new TableListDetails();
                    obj.setCollector_id(jsonObj.getString("collector_id"));
                    obj.setCollector_name(jsonObj.getString("collector_name"));
                    obj.setCollector_image(jsonObj.getString("collector_image"));
                    obj.setCollector_type(jsonObj.getString("collector_type"));
                    obj.setCollector_status(jsonObj.getString("collector_status"));
                    obj.setCollector_split_series_no(jsonObj.getString("collector_split_series_no"));

                    if (jsonObj.has("food_data")){
                        JSONObject foodObj = jsonObj.getJSONObject("food_data");

                        if (foodObj != null){
                            TableListDetails.FoodData food = new TableListDetails.FoodData();
                            String status = foodObj.getString("status");
                            if (!status.equals("")) {
                                food.setStatus(foodObj.getString("status"));
                                food.setOrder_id(foodObj.getString("order_id"));
                                food.setBill_id(foodObj.getString("bill_id"));
                                obj.setFood_data(food);
                            }
                        }else{
                            obj.setFood_data(null);
                        }
                    }else{
                        obj.setFood_data(null);
                    }

                    if (jsonObj.has("bar_data")){
                        JSONObject barObj = jsonObj.getJSONObject("bar_data");

                        if (barObj != null){
                            String status = barObj.getString("status");
                            if (!status.equals("")) {
                                TableListDetails.BarData bar = new TableListDetails.BarData();
                                bar.setStatus(barObj.getString("status"));
                                bar.setOrder_id(barObj.getString("order_id"));
                                bar.setBill_id(barObj.getString("bill_id"));
                                obj.setBar_data(bar);
                            }
                        }else{
                            obj.setBar_data(null);
                        }
                    }else{
                        obj.setBar_data(null);
                    }

                    Halltables.add(obj);
                }
            }

            if (Halltables != null && Halltables.size() != 0){

                adapter = new TableDetailsAdapter(this, Halltables, new TableDetailsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(TableListDetails item) {
                        SharedPref.putString(TableSelectionActivity.this,"table",item.getCollector_id());
                        selectedTableType = item.getCollector_type();
                        selectedTable = item.getCollector_id();
                        selectedTableName = item.getCollector_name();
                        foodStatus = ""; barStatus = "";
                        if (item.getFood_data()!= null) {
                            foodStatus = item.getFood_data().getStatus();
                            foodOrderId = item.getFood_data().getOrder_id();
                            foodBillId = item.getFood_data().getBill_id();
                        }
                        if (item.getBar_data() != null ) {
                            barStatus = item.getBar_data().getStatus();
                            barOrderId= item.getBar_data().getOrder_id();
                            foodBillId = item.getBar_data().getBill_id();
                        }

                        if (foodStatus == null){ foodStatus = ""; }
                        if (barStatus == null){ barStatus = ""; }

                        if (foodOrderId == null){ foodOrderId = ""; }
                        if (barOrderId == null){ barOrderId = ""; }

                        if (foodBillId == null){ foodBillId = ""; }
                        if (barBillId == null){ barBillId = ""; }

                        String table_name = item.getCollector_name();
                        String[] series_name = {"","A","B","C","D"};
                        if (item.getCollector_status().equals("1")){

                            table_name = table_name + "-" + series_name[Integer.valueOf(item.getCollector_split_series_no())];
                        }

                        SharedPref.putString(TableSelectionActivity.this,"table_name",table_name);
                        SharedPref.putString(TableSelectionActivity.this,"table_series_no",item.getCollector_split_series_no());
                        SharedPref.putString(TableSelectionActivity.this,"table_series_name",series_name[Integer.valueOf(item.getCollector_split_series_no())]);
                        SharedPref.putString(TableSelectionActivity.this,"table_split_status",item.getCollector_status());
                        SharedPref.putString(TableSelectionActivity.this,"food_table_status",foodStatus);
                        SharedPref.putString(TableSelectionActivity.this,"bar_table_status",barStatus);
                        SharedPref.putString(TableSelectionActivity.this,"food_order_id",foodOrderId);
                        SharedPref.putString(TableSelectionActivity.this,"bar_order_id",barOrderId);
                        SharedPref.putString(TableSelectionActivity.this,"food_bill_id",foodBillId);
                        SharedPref.putString(TableSelectionActivity.this,"bar_bill_id",barBillId);
                    }
                });
                recyclerView.setAdapter(adapter);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReconnect() {

    }

    private void init() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        SharedPref.putString(TableSelectionActivity.this,"bill_type","table");


        bill_title = findViewById(R.id.bill_title);
        change_table_title = findViewById(R.id.change_table_title);
        bar_order_title = findViewById(R.id.bar_order_title);
        clean_table_title = findViewById(R.id.clean_table_title);
        btn_merge = findViewById(R.id.merge_title);
        btnSplit = findViewById(R.id.split_title);
        btnJoin = findViewById(R.id.join_title);
        btn_back = findViewById(R.id.back_title);

        //by default section selection
        selectedSection = SharedPref.getString(TableSelectionActivity.this, "section"); //dataBaseHelper.getSectiondetails().get(0).getSection_id();



        bill_title.setTypeface(FontStyle.getFontRegular());
        change_table_title.setTypeface(FontStyle.getFontRegular());
        bar_order_title.setTypeface(FontStyle.getFontRegular());
        clean_table_title.setTypeface(FontStyle.getFontRegular());
        btn_merge.setTypeface(FontStyle.getFontRegular());
        btnSplit.setTypeface(FontStyle.getFontRegular());
        btnJoin.setTypeface(FontStyle.getFontRegular());
        btn_back.setTypeface(FontStyle.getFontRegular());

        //SwipeRefreshLayout
        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.balajimaroon,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                getTableDetails(selectedSection);
            }
        });
        getTableDetails(selectedSection);
    }

    private void getTableDetails(String selectedSection) {
        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),4);
        recyclerView.setLayoutManager(gridLayoutManager);

        CaptainOrderService.getInstance().ServiceInitiate();
        CaptainOrderService.getInstance().sendCommand(Constants.cmdListTables + SharedPref.getString(TableSelectionActivity.this, "device_id") + "#" + selectedSection);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void OnOrderOptionClick(View view) {
        SharedPref.putString(TableSelectionActivity.this,"order_type","FOOD");
        if (selectedTableType.equals("")){
            DialogBox(getResources().getString(R.string.select_table));
        }else{
            if (foodStatus.equals("2") || barStatus.equals("2")) {
                DialogBox(getResources().getString(R.string.bill_printed_for_selected_table));
            }else{
                startActivity(new Intent(getApplicationContext(), RestaurantOrderActivity.class));
                finish();
            }

        }
    }

    public void OnBillOptionClick(View view) {

        if (selectedTableType.equals("")){
            DialogBox(getResources().getString(R.string.select_table));
        }else{
            String table_food_status =  foodStatus;
            String table_bar_status = barStatus;

            if (table_food_status.length()!=0 && table_bar_status.length()!=0){
                if (table_food_status.equals("2") && table_bar_status.equals("2")){
                    DialogBox(getResources().getString(R.string.bill_printed_for_selected_table));
                }else if (table_food_status.equals("1") && table_bar_status.equals("1")){
                    startActivity(new Intent(getApplicationContext(),CheckKOTActivity.class));
                    finish();
                }else if (table_food_status.equals("0") || table_bar_status.equals("0")){
                    DialogBox(getResources().getString(R.string.kot_not_print));
                }else{
                    DialogBox(getResources().getString(R.string.order_not_place));
                }
            }else{
                if (table_food_status.equals("2") || table_bar_status.equals("2")){
                    DialogBox(getResources().getString(R.string.bill_printed_for_selected_table));
                }else if (table_food_status.equals("1") || table_bar_status.equals("1")){
                    startActivity(new Intent(getApplicationContext(),CheckKOTActivity.class));
                    finish();
                }else if (table_food_status.equals("0") || table_bar_status.equals("0")){
                    DialogBox(getResources().getString(R.string.kot_not_print));
                }else{
                    DialogBox(getResources().getString(R.string.order_not_place));
                }
            }
        }
    }

    private void DialogBox(String msg) {
        Dialog dialog = new Dialog(TableSelectionActivity.this);
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
        finishAffinity();
        System.exit(0);
    }

    public void OnBarOrderClick(View view) {
        SharedPref.putString(TableSelectionActivity.this,"order_type","BAR");

        if (selectedTableType.equals("")){
            DialogBox(getResources().getString(R.string.select_table));
        }else{
            if (foodStatus.equals("2") || barStatus.equals("2")) {
                DialogBox(getResources().getString(R.string.bill_printed_for_selected_table));
            }else{
                startActivity(new Intent(getApplicationContext(), RestaurantOrderActivity.class));
                finish();
            }
        }
    }

    public void onClearOptionClick(View view) {
        if (selectedTableType.equals("")){
            DialogBox(getResources().getString(R.string.select_table));
        }else{

            String table_food_status = foodStatus;
            String table_bar_status =  barStatus;
            if (table_food_status.equals("") && table_bar_status.equals("")){
                DialogBox(getResources().getString(R.string.order_not_place));
            }else {
                String table_id = selectedTable;

                if (foodStatus.equals("2") || barStatus.equals("2")) {
                    DialogBox(getResources().getString(R.string.bill_printed_for_selected_table));
                }else{

                    if (foodBillId.length()!=0 || barBillId.length()!=0){
                        askForReason();
                    }else{
                        CaptainOrderService.getInstance().sendCommand(Constants.cmdClearOrderTable + SharedPref.getString(TableSelectionActivity.this, "device_id") + "#{'section_id':'" +selectedSection+"','user_id':'"+SharedPref.getString(TableSelectionActivity.this,"user_id")+"','table_id':'"+ table_id+"'}");

                    }

                }
//                }
            }
        }
    }

    private void askForReason() {
        Dialog dialog = new Dialog(TableSelectionActivity.this);
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

                    String user_id = SharedPref.getString(TableSelectionActivity.this,"user_id");

                    CaptainOrderService.getInstance().sendCommand(Constants.cmdClearKOTTable + SharedPref.getString(TableSelectionActivity.this, "device_id") + "#{'section_id':'" +selectedSection+"','user_id':'"+SharedPref.getString(TableSelectionActivity.this,"user_id")+"','table_id':'"+ table_id+"','reason':'" + edt_dialog_msg.getText().toString()+"'}");
                    dialog.dismiss();

                }
            }
        });

        edt_dialog_title.setTypeface(FontStyle.getFontRegular());
        edt_dialog_msg.setTypeface(FontStyle.getFontRegular());
        btn_dialog_cofirm.setTypeface(FontStyle.getFontRegular());
        dialog.show();
    }

    public void onChangeOptionClick(View view) {
        if (selectedTableType.equals("")){
            DialogBox(getResources().getString(R.string.select_table));
        }else{
            String table_food_status = foodStatus;
            String table_bar_status =  barStatus;
            if (table_food_status.equals("") && table_bar_status.equals("")){
                DialogBox(getResources().getString(R.string.order_not_place));
            }else if (table_food_status.equals("2") || table_bar_status.equals("2")){
                DialogBox(getResources().getString(R.string.bill_printed_for_selected_table));
            }else {
                Dialog dialog = new Dialog(TableSelectionActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.view_table_change_dialog);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                Window window = dialog.getWindow();
                assert window != null;
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                AppCompatTextView edt_dialog_title = dialog.findViewById(R.id.edit_title);
//                AppCompatEditText edt_dialog_msg = dialog.findViewById(R.id.edit_msg);
                AppCompatTextView btn_dialog_cofirm = dialog.findViewById(R.id.confirm_button);
                AppCompatSpinner spinner = dialog.findViewById(R.id.table);

                ArrayList<TableListDetails> tableList = new ArrayList<>();
                if (Halltables != null){
                    for (int i=0;i<Halltables.size();i++){

                        String fstatus = "", bstatus = "";

                        if (Halltables.get(i).getFood_data()!= null){
                            if (Halltables.get(i).getFood_data().getStatus()!=null){
                                fstatus = Halltables.get(i).getFood_data().getStatus();
                            }
                        }
                        if (Halltables.get(i).getBar_data()!= null){
                            if (Halltables.get(i).getBar_data().getStatus()!=null){
                                bstatus = Halltables.get(i).getBar_data().getStatus();
                            }
                        }

                        if (fstatus.equals("") && fstatus.equals("") && bstatus.equals("") && bstatus.equals("") ){
                            if (!Halltables.get(i).getCollector_id().equals(selectedTable)){
                                if (!Halltables.get(i).getCollector_name().equals("6") && !Halltables.get(i).getCollector_name().equals("13")){
                                    if (Halltables.get(i).getCollector_status().equals("0")){
                                        tableList.add(Halltables.get(i));
                                    }
                                }
                            }
                        }
                    }
                }

                if (tableList!=null){
                    if (tableList != null && tableList.size() > 0) {
                        TableSpinnerAdapter adapter = new TableSpinnerAdapter(TableSelectionActivity.this,
                                R.layout.spinner_dropdown_item, tableList);
                        adapter.setDropDownViewResource(R.layout.custom_checked_spinner_layout);
                        adapter.notifyDataSetChanged();
                        spinner.setAdapter(adapter);
                    }
                }

                edt_dialog_title.setText(getResources().getString(R.string.app_name));
                edt_dialog_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                        dialog.dismiss();
                    }
                });
                btn_dialog_cofirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tableNo = tableList.get(spinner.getSelectedItemPosition()).getCollector_id();

                        CaptainOrderService.getInstance().sendCommand(Constants.cmdChangeTable + SharedPref.getString(TableSelectionActivity.this, "device_id") + "#{'destination_table':'" + tableNo + "','source_table':'" + selectedTable + "','section_id':'" + selectedSection+"'}");
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                        dialog.dismiss();
                    }
                });

                edt_dialog_title.setTypeface(FontStyle.getFontRegular());
//                edt_dialog_msg.setTypeface(FontStyle.getFontRegular());
                btn_dialog_cofirm.setTypeface(FontStyle.getFontRegular());

                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.show();
            }
        }
    }



    public void OnBackOptionClick(View view) {
        SharedPref.putString(TableSelectionActivity.this,"isLogin","false");
        SharedPref.putString(TableSelectionActivity.this,"user_status","");
        SharedPref.putString(TableSelectionActivity.this,"user_id","");
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }

    @Override
    public void onRefresh() {
        getTableDetails(selectedSection);
    }

    public void onMergeOptionClick(View view) {
        if (selectedTable.equals("")){
            DialogBox(getResources().getString(R.string.select_table));
        }else if (foodStatus.equals("2") || barStatus.equals("2")) {
            DialogBox(getResources().getString(R.string.bill_printed_for_selected_table));
        }else{
            CaptainOrderService.getInstance().sendCommand(Constants.cmdTableUnsplit + SharedPref.getString(TableSelectionActivity.this, "device_id") + "#{'section_id':'" + selectedSection + "','table_id':'" + selectedTable + "'}");
        }
    }

    public void onJoinOptionClick(View view) {
        String table_food_status = foodStatus;
        String table_bar_status =  barStatus;
        if (selectedTable.equals("")){
            DialogBox(getResources().getString(R.string.select_table));
        }else if (table_food_status.equals("") && table_bar_status.equals("")){
            DialogBox(getResources().getString(R.string.order_not_place));
        } else if (foodStatus.equals("2") || barStatus.equals("2")) {
            DialogBox(getResources().getString(R.string.bill_printed_for_selected_table));
        }else {
            Dialog dialog = new Dialog(TableSelectionActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.view_table_merge_dialog);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            Window window = dialog.getWindow();
            assert window != null;
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            AppCompatTextView edt_dialog_title = dialog.findViewById(R.id.edit_title);
            AppCompatTextView msg = dialog.findViewById(R.id.msg);
            AppCompatSpinner spinner = dialog.findViewById(R.id.table);
            AppCompatTextView btn_dialog_cofirm = dialog.findViewById(R.id.confirm_button);

            edt_dialog_title.setText(getResources().getString(R.string.app_name));
            edt_dialog_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            msg.setText("Table "+selectedTableName+" merge KOT with");

            ArrayList<TableListDetails> tableList = new ArrayList<>();
            if (Halltables != null){
                for (int i=0;i<Halltables.size();i++){

                    String fstatus = "", bstatus = "";

                    if (Halltables.get(i).getFood_data()!= null){
                        if (Halltables.get(i).getFood_data().getStatus()!=null){
                            fstatus = Halltables.get(i).getFood_data().getStatus();
                        }
                    }
                    if (Halltables.get(i).getBar_data()!= null){
                        if (Halltables.get(i).getBar_data().getStatus()!=null){
                            bstatus = Halltables.get(i).getBar_data().getStatus();
                        }
                    }

                    if (fstatus.equals("0") || fstatus.equals("1") || bstatus.equals("0") || bstatus.equals("1") ){
                        if (!Halltables.get(i).getCollector_id().equals(selectedTable)){
                            tableList.add(Halltables.get(i));
                        }
                    }
                }
            }


            if (tableList!=null){
                if (tableList != null && tableList.size() > 0) {
                    TableSpinnerAdapter adapter = new TableSpinnerAdapter(TableSelectionActivity.this,
                            R.layout.spinner_dropdown_item, tableList);
                    adapter.setDropDownViewResource(R.layout.custom_checked_spinner_layout);
                    adapter.notifyDataSetChanged();
                    spinner.setAdapter(adapter);
                }
            }

            btn_dialog_cofirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TableListDetails objNewTable = Halltables.get(spinner.getSelectedItemPosition());

                    String tableNo = objNewTable.getCollector_id(); //tableListDetails.get(spinner.getSelectedItemPosition()).getCollector_id();
                    CaptainOrderService.getInstance().sendCommand(Constants.cmdMergeTable + SharedPref.getString(TableSelectionActivity.this, "device_id") + "#{'destination_table':'" + tableNo + "','source_table':'" + selectedTable + "','section_id':'" + selectedSection+"','user_id':'"+SharedPref.getString(TableSelectionActivity.this,"user_id")+"'}");
                    dialog.dismiss();
                }
            });

            edt_dialog_title.setTypeface(FontStyle.getFontRegular());
            msg.setTypeface(FontStyle.getFontRegular());
            btn_dialog_cofirm.setTypeface(FontStyle.getFontRegular());

            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    public void onSplitOptionClick(View view) {
        if (selectedTable.equals("")){
            DialogBox(getResources().getString(R.string.select_table));
        }else if (foodStatus.equals("2") || barStatus.equals("2")) {
            DialogBox(getResources().getString(R.string.bill_printed_for_selected_table));
        }else{
            CaptainOrderService.getInstance().sendCommand(Constants.cmdTableSplit + SharedPref.getString(TableSelectionActivity.this, "device_id") + "#{'section_id':'" + selectedSection + "','table_id':'" + selectedTable + "'}");
        }
    }
}