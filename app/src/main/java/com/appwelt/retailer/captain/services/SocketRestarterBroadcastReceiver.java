package com.appwelt.retailer.captain.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

/**
 * This is a Braodcast receiver service for Aegis Service.
 * Its helps to get the background serbice started again if it get killed.
 */
public class SocketRestarterBroadcastReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(SocketRestarterBroadcastReceiver.class.getSimpleName(), "Service Stops!");

        context.startService(new Intent(context, CaptainOrderService.class));

        CaptainOrderService.getInstance().SetCaptainOrderServiceContext(context);
        CaptainOrderService.getInstance().ServiceInitiate();
    }

}
