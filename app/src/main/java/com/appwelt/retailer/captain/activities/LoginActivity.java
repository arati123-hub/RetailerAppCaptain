package com.appwelt.retailer.captain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.ImageViewCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appwelt.retailer.captain.BuildConfig;
import com.appwelt.retailer.captain.R;
import com.appwelt.retailer.captain.model.OrganizationDetails;
import com.appwelt.retailer.captain.model.ProfileInfo;
import com.appwelt.retailer.captain.services.CaptainOrderService;
import com.appwelt.retailer.captain.services.MessageDeframer;
import com.appwelt.retailer.captain.services.OnMessageListener;
import com.appwelt.retailer.captain.utils.Constants;
import com.appwelt.retailer.captain.utils.FileTools;
import com.appwelt.retailer.captain.utils.FontStyle;
import com.appwelt.retailer.captain.utils.MyKeyboard;
import com.appwelt.retailer.captain.utils.SharedPref;
import com.appwelt.retailer.captain.utils.sqlitedatabase.DatabaseHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements OnMessageListener  {

    private static final String TAG = "MAIN_ACTIVITY";

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    EditText edt_pin;
    TextView pinTitle,versionName;
    ImageView btnNext;
    MyKeyboard keyboard;
    AppCompatImageView server_connectivity;
    ProgressDialog progressDialog;

    DatabaseHelper dataBaseHelper;

    String server_ip,server_port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FontStyle.FontStyle(LoginActivity.this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        versionName = findViewById(R.id.version_name);

        versionName.setTypeface(FontStyle.getFontRegular());

        versionName.setText("Ver. "+ BuildConfig.VERSION_NAME);

        server_ip = SharedPref.getString(LoginActivity.this,"server_ip");
        server_port = SharedPref.getString(LoginActivity.this,"server_port");

        if (server_ip == null || server_port == null){
            askServerDetails();
        }else if (server_ip.length() == 0 || server_port.length() == 0){
            askServerDetails();
        }

        inti();

        verifyStoragePermissions(LoginActivity.this);

        SharedPref.putString(LoginActivity.this,"device_id", GetDeviceId());


        if (CaptainOrderService.getInstance() == null)
        {
            this.startService(new Intent(this, CaptainOrderService.class));
        }
        CaptainOrderService.getInstance().SetCaptainOrderServiceContext(this);

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

        if (strCommand.equals(Constants.cmdLogin))
        {
            if (strData.startsWith("VALID"))
            {
                strData = strData.replace("VALID#", "");

                try {
                    JSONObject objJSON = new JSONObject(strData);
                    String[] arrData = strData.split("|");
                    SharedPref.putString(LoginActivity.this,"user_id", objJSON.getString("user_id"));
                    SharedPref.putString(LoginActivity.this,"isLogin","true");
                    SharedPref.putString(LoginActivity.this,"user_status", objJSON.getString("user_status"));
                    SharedPref.putString(LoginActivity.this, "user_name", objJSON.getString("user_name"));
                    SharedPref.putString(LoginActivity.this,"section", objJSON.getString("section"));
                    SharedPref.putString(LoginActivity.this,"section_name", objJSON.getString("section_name"));

                    startActivity(new Intent(getApplicationContext(), TableSelectionActivity.class));
                    finish();
                }
                catch (JSONException e)
                {

                }

                //CaptainOrderService.getInstance().sendCommand(Constants.cmdListProducts + SharedPref.getString(LoginActivity.this, "device_id") + "#");

            }
            else if (strData.startsWith("INVALID"))
            {
                strData = strData.replace("INVALID#", "");

                try {
                    JSONObject objJSON = new JSONObject(strData);
                    String msg = objJSON.getString("msg");
                    DialogBox(msg);
                }
                catch (JSONException e)
                {

                }

            }
        }
        else if (strCommand.equals(Constants.cmdListProducts))
        {
            if (strData.startsWith("VALID"))
            {
                strData = strData.replace("VALID#", "");

                File checkFile = new File(Environment.getExternalStorageDirectory().getPath() + "/" + Constants.FOLDER_NAME);
                if (checkFile.exists()) {
                    File ItemList = new File(checkFile.getAbsolutePath() + "/ItemList");
                    if (ItemList.exists()) {
                        ItemList.delete();
                    }

                    try {
                        ItemList.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    FileOutputStream destinationFile = null;
                    try {
                        destinationFile = new FileOutputStream(ItemList);

                        destinationFile.write(strData.getBytes(), 0, strData.length());

                        destinationFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {

                    }
                }

                CaptainOrderService.getInstance().sendCommand(Constants.cmdListImages + SharedPref.getString(LoginActivity.this, "device_id") + "#");

            }
            else if (strData.startsWith("INVALID"))
            {
                DialogBox(getResources().getString(R.string.unable_get_product));
            }
        }
        else if (strCommand.equals(Constants.cmdListImages))
        {
            if (strData.startsWith("VALID"))
            {
                strData = strData.replace("VALID#", "");

                String zipFilePath = Environment.getExternalStorageDirectory().getPath() + "/" + Constants.FOLDER_NAME + "/" + strData;

                String destDir = Environment.getExternalStorageDirectory().getPath() + "/" + Constants.FOLDER_NAME + "/" + Constants.FOLDER_NAME_IMAGES;

                FileTools.unzip(zipFilePath, destDir);

            }
            else if (strData.startsWith("INVALID"))
            {
                DialogBox(getResources().getString(R.string.unable_get_iamges));
            }
        }
    }

    @Override
    public void onReconnect() {

    }

    private String GetDeviceId()
    {
        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId = Settings.Secure.getString(
                    getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = Settings.Secure.getString(
                        getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
        }
        return deviceId;
    }

    private void askServerDetails() {
        Dialog qDialog = new Dialog(LoginActivity.this);
        qDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        qDialog.setContentView(R.layout.view_server_details_dialog);
        qDialog.setCancelable(true);
        qDialog.setCanceledOnTouchOutside(true);
        Window window = qDialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        AppCompatEditText edt_ip1 = qDialog.findViewById(R.id.edt_ip1);
        AppCompatEditText edt_ip2 = qDialog.findViewById(R.id.edt_ip2);
        AppCompatEditText edt_ip3 = qDialog.findViewById(R.id.edt_ip3);
        AppCompatEditText edt_ip4 = qDialog.findViewById(R.id.edt_ip4);
        AppCompatEditText edt_port = qDialog.findViewById(R.id.edt_port);
        AppCompatTextView txt_ip = qDialog.findViewById(R.id.txt_ip);
        AppCompatTextView txt_port = qDialog.findViewById(R.id.txt_port);
        AppCompatTextView note_txt = qDialog.findViewById(R.id.note_txt);
        AppCompatTextView btn_dialog_confirm = qDialog.findViewById(R.id.btn_submit);
        CardView note_card = qDialog.findViewById(R.id.note_card);

        note_txt.setText(getResources().getString(R.string.please_enter_server_details_for_device_connectivity));

        edt_ip1.setText("192");
        edt_ip2.setText("168");
        edt_ip3.setText("0");
        edt_ip4.setText("0");
        edt_port.setText("1600");

        btn_dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String ip1 = edt_ip1.getText().toString();
               String ip2 = edt_ip2.getText().toString();
               String ip3 = edt_ip3.getText().toString();
               String ip4 = edt_ip4.getText().toString();
               String port = edt_port.getText().toString();

               if (ip1.isEmpty()){
                   edt_ip1.setError(getResources().getString(R.string.required));
               }else if (ip2.isEmpty()){
                   edt_ip2.setError(getResources().getString(R.string.required));
               }else if (ip3.isEmpty()){
                    edt_ip3.setError(getResources().getString(R.string.required));
               }else if (ip4.isEmpty()){
                    edt_ip4.setError(getResources().getString(R.string.required));
               }else if (port.isEmpty()){
                   edt_port.setError(getResources().getString(R.string.required));
               }else if (Integer.valueOf(ip1) > 255 || Integer.valueOf(ip1) < 0){
                   edt_ip1.setError(getResources().getString(R.string.invalid));
               }else if (Integer.valueOf(ip2) > 255 || Integer.valueOf(ip2) < 0){
                   edt_ip2.setError(getResources().getString(R.string.invalid));
               }else if (Integer.valueOf(ip3) > 155 || Integer.valueOf(ip3) < -0){
                   edt_ip3.setError(getResources().getString(R.string.invalid));
               }else if (Integer.valueOf(ip4) > 155 || Integer.valueOf(ip4) < -0){
                   edt_ip4.setError(getResources().getString(R.string.invalid));
               }else{
                   SharedPref.putString(LoginActivity.this,"server_ip",ip1+"."+ip2+"."+ip3+"."+ip4);
                   SharedPref.putString(LoginActivity.this,"server_port",port);
                   server_ip = ip1+"."+ip2+"."+ip3+"."+ip4;
                   server_port = port;

                   CaptainOrderService.getInstance().ServiceInitiate();
                   CaptainOrderService.getInstance().sendCommand(Constants.cmdTest + SharedPref.getString(LoginActivity.this, "device_id") + "#");

                   qDialog.dismiss();
               }
            }
        });


        edt_ip1.setTypeface(FontStyle.getFontRegular());
        edt_ip2.setTypeface(FontStyle.getFontRegular());
        edt_ip3.setTypeface(FontStyle.getFontRegular());
        edt_ip4.setTypeface(FontStyle.getFontRegular());
        edt_port.setTypeface(FontStyle.getFontRegular());
        txt_ip.setTypeface(FontStyle.getFontRegular());
        txt_port.setTypeface(FontStyle.getFontRegular());
        note_txt.setTypeface(FontStyle.getFontRegular());
        btn_dialog_confirm.setTypeface(FontStyle.getFontRegular());

        qDialog.setCanceledOnTouchOutside(false);
        qDialog.setCancelable(false);
        qDialog.show();
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void inti() {
//        String DATABASE_NAME = SharedPref.getString(LoginActivity.this,"database_name");
//        if (DATABASE_NAME != null && DATABASE_NAME.length()!=0){
//            dataBaseHelper=new DatabaseHelper(this,DATABASE_NAME);
//        }
//
//        ArrayList<OrganizationDetails> organizationDetails = dataBaseHelper.getOrganizationdetails();
//        if (organizationDetails != null){
//            SharedPref.putString(LoginActivity.this,"organisation_id",organizationDetails.get(0).getOrganisation_id());
//        }


        pinTitle = findViewById(R.id.pin_title);
        server_connectivity = findViewById(R.id.server_connectivity);
        pinTitle.setTypeface(FontStyle.getFontRegular());

        server_connectivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askServerDetails();
            }
        });
        edt_pin = (EditText) findViewById(R.id.edt_pin);
        edt_pin.setFocusable(true);
        edt_pin.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode actionMode, MenuItem item) {
                return false;
            }

            public void onDestroyActionMode(ActionMode actionMode) {
            }
        });

        edt_pin.setLongClickable(false);
        edt_pin.setTextIsSelectable(false);

        edt_pin.setTypeface(FontStyle.getFontRegular());
        btnNext = (ImageView) findViewById(R.id.button_next);
        keyboard = (MyKeyboard) findViewById(R.id.keyboard);

        edt_pin.setRawInputType(InputType.TYPE_CLASS_TEXT);
        edt_pin.setTextIsSelectable(true);

        edt_pin.setShowSoftInputOnFocus(false);
        edt_pin.requestFocus();
        InputConnection ic = edt_pin.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection(ic);
        btnNext.setImageDrawable(getResources().getDrawable(R.drawable.done));
        edt_pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edt_pin.getText().toString().length()==6){
                    if (server_ip == null || server_port == null){
                        askServerDetails();
                    }else if (server_ip.length() == 0 || server_port.length() == 0){
                        askServerDetails();
                    }else{
                        checkLogin();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_pin.getText().toString().isEmpty()){
                    edt_pin.requestFocus();
                }else {
                    if (server_ip == null || server_port == null){
                        askServerDetails();
                    }else if (server_ip.length() == 0 || server_port.length() == 0){
                        askServerDetails();
                    }else{

                        checkLogin();
                    }

                }
            }
        });
    }

    private void checkLogin() {

        CaptainOrderService.getInstance().ServiceInitiate();

        CaptainOrderService.getInstance().sendCommand(Constants.cmdLogin + SharedPref.getString(LoginActivity.this, "device_id") + "#" + edt_pin.getText().toString());

//        File folderStorage = new File(Environment.getExternalStorageDirectory().getPath() + "/" + Constants.FOLDER_NAME );
//        if (folderStorage.exists()){
//            File databaseStorage = new File(Environment.getExternalStorageDirectory().getPath() + "/" + Constants.FOLDER_NAME + "/" +Constants.DATABASE_NAME);
//            if (databaseStorage.exists()){
//                ProfileInfo profileInfo = new ProfileInfo();
//                profileInfo = dataBaseHelper.checkLogin(edt_pin.getText().toString());
//                if (profileInfo != null){
//                    SharedPref.putString(LoginActivity.this,"user_id",profileInfo.getUser_id());
//                    SharedPref.putString(LoginActivity.this,"isLogin","true");
//                    SharedPref.putString(LoginActivity.this,"user_status",profileInfo.getUser_status());
//                    startActivity(new Intent(getApplicationContext(), TableSelectionActivity.class));
//                    finish();
//                }else{
//                    DialogBox(getResources().getString(R.string.invalid_credentials));
//                }
//            }else{
//                DialogBox(getResources().getString(R.string.database_not_found));
//            }
//        }else{
//            DialogBox(getResources().getString(R.string.database_not_found));
//        }

    }

    private void DialogBox(String msg) {
        Dialog dialog = new Dialog(LoginActivity.this);
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
        finishAffinity();
        System.exit(0);
    }

}