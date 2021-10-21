package com.appwelt.retailer.captain.services;

/**
 * This is the SOC message Listener used to pass the message data to activity context.
 * Each message has to be shown on UI or need the update on UI side and our SOC Socket is a background thread process. To avoid UI thread update exception we need to use this interface.
 * This interface is common for all types of messages: Acknowledge, Information, Alert, Log, Data
 */

public interface OnMessageListener {

    /**
     * All Acknowlegde messages will be raised using this event.
     */
    void onMessageReceived(String strCommand, String strData);

    void onReconnect();

}
