package com.appwelt.retailer.captain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.appwelt.retailer.captain.BuildConfig;
import com.appwelt.retailer.captain.R;
import com.appwelt.retailer.captain.utils.AskPermissions;
import com.appwelt.retailer.captain.utils.FontStyle;
import com.appwelt.retailer.captain.utils.SharedPref;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private AskPermissions askPermissions;
    AppCompatTextView versionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FontStyle.FontStyle(MainActivity.this);

        versionName = findViewById(R.id.version_name);
        versionName.setTypeface(FontStyle.getFontRegular());
        versionName.setText("Ver. "+ BuildConfig.VERSION_NAME);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            askPermissions = new AskPermissions(MainActivity.this);
            if (askPermissions.checkAndRequestPermissions()) {
                threadCallSession();
            }
        } else {
            threadCallSession();
        }

    }

    private void threadCallSession() {
        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(3 * 1000);
                    checkAppPermissions();
                } catch (Exception e) { e.printStackTrace(); }
            }
        };
        background.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        askPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void checkAppPermissions() {

        Boolean Check = Boolean.valueOf(SharedPref.getString(MainActivity.this, "isLogin"));
        if (Check) {
            startActivity(new Intent(getApplicationContext(), TableSelectionActivity.class));
            finish();
        } else {
            String DATABASE_NAME = "retailerdbv.db";
            SharedPref.putString(MainActivity.this,"database_name",DATABASE_NAME);
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }

}