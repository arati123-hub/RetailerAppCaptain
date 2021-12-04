package com.appwelt.retailer.captain.app;

import android.app.Application;

import com.appwelt.retailer.captain.activities.MainActivity;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;

public class RetailerCaptainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        installCustomActivityOnCrash();
    }

    private void installCustomActivityOnCrash() {
        //This enables CustomActivityOnCrash
        CustomActivityOnCrash.install(this);

        //This makes the library not launch the error activity when the app crashes while it is in background.
        CustomActivityOnCrash.setLaunchActivityEvenIfInBackground(false);

        //This sets the restart activity. If you don't do this, the "Restart app" button will change to "Close app".
        CustomActivityOnCrash.setRestartActivityClass(MainActivity.class);

        // Only show the errors when on debug
        // TODO CustomActivityOnCrash.setShowErrorDetails(BuildConfig.DEBUG);
    }
}
