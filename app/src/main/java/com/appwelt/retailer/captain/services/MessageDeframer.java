package com.appwelt.retailer.captain.services;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.appwelt.retailer.captain.utils.Constants;
import com.appwelt.retailer.captain.utils.FileTools;
import com.appwelt.retailer.captain.utils.SharedPref;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MessageDeframer {

    static Context context;
    /**
     * Context handler for message event raise.
     */
    static Handler objhandler;
    private OnMessageListener MessageListener;
    private static MessageDeframer MessageDeframerInstance;

    //private static NotificationUtils utilNotification;

    static String responseCommand = "";
    static String responseData = "";

    static ScheduledExecutorService scheduleTaskExecutorForResponse;

    private static void ResponseProcessExecutor()
    {
        scheduleTaskExecutorForResponse = Executors.newScheduledThreadPool(5);
        // This schedule a runnable task every 2 minutes
        scheduleTaskExecutorForResponse.scheduleAtFixedRate(new Runnable() {
            public void run() {

                if (ResponseQueue.size() > 0) {
                    ResponseObject obj;
                    synchronized (ResponseQueue) {
                        obj = ResponseQueue.get(0);
                        ResponseQueue.remove(0);
                    }

                   //MessageDataDeframe(obj.ResponseHeader, obj.ResponseData, obj.iResponsePayLoad);
                }

            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }


    public static String MsgTypeLogprefix = "UNKNOWN";

    private final static Object ResponseQueueLock = new Object();

    public static ArrayList<ResponseObject> ResponseQueue = new ArrayList<ResponseObject>();

    public static class ResponseObject
    {
        private ResponseObject(byte[] MsgHeader, byte[] pMsgData, int iPayLoad)
        {
            ResponseHeader = MsgHeader;
            ResponseData = pMsgData;
            iResponsePayLoad = iPayLoad;
        }

        public byte[] ResponseHeader;
        public byte[] ResponseData;
        public int iResponsePayLoad;
    }


    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (responseCommand.equals("RECONNECT"))
            {
                MessageListener.onReconnect();
            }
            else {
                MessageListener.onMessageReceived(responseCommand, responseData);
            }
        }
    };


    public void MessageDeframer(Context pcontext)
    {
        context = pcontext;
        this.objhandler = handler;
    }

    /**
     * Public method to setup the Instance Context as per front activity. Called from each activity.
     * @param pcontext Context
     * @param pMessageListener OnSOCMessageListener
     */
    public void SetInstanceContext(Context pcontext, OnMessageListener pMessageListener)
    {
        MessageDeframerInstance.context = pcontext;
        MessageDeframerInstance.MessageListener = pMessageListener;
        this.objhandler = handler;
    }

    /**
     * Handle the Single Instance of the class
     * @return ProcessMessage Instance
     */
    public static MessageDeframer getInstance() {
        if (MessageDeframerInstance == null) { //if there is no instance available... create new one
            MessageDeframerInstance = new MessageDeframer();
            //ResponseProcessExecutor();
        }

        return MessageDeframerInstance;
    }

    public static int MessageHeaderDeframe(byte[] MsgHeader)
   {
       int iPayLoad = 0;

       byte MessageType = MsgHeader[2];
       byte MessageVCSID = MsgHeader[4];

       boolean MessageTypeValid = false;
//       if (MessageType == ICSBConstants.Byte_MessageTypes.INFORMATION) {
//           MsgTypeLogprefix = "INFOMSG";
//           MessageTypeValid = true;
//       } else if (MessageType == ICSBConstants.Byte_MessageTypes.DATA) {
//           MsgTypeLogprefix = "DATAMSG";
//           MessageTypeValid = true;
//       } else if (MessageType == ICSBConstants.Byte_MessageTypes.ACKNOWLEDGEMENT) {
//           MsgTypeLogprefix = "ACKMSG";
//           MessageTypeValid = true;
//       }

       //Utils.writeCommandLog(context, "WiFi", MsgTypeLogprefix + "_HEAD: ", Utils.encodeHexString(MsgHeader), true);

       if (MessageTypeValid == true) {

           byte[] PAYLEN = new byte[2];
           PAYLEN[0] = MsgHeader[6];
           PAYLEN[1] = MsgHeader[7];
           //iPayLoad = Utils.GetDataLength(PAYLEN);

       }
       else
       {
           FileTools.writeCommandLog(context, "WiFi", MsgTypeLogprefix + "_HEAD: ", "Discarded.", "", false);
       }

       return iPayLoad;

   }

   public static void MessageAddQueue(byte[] MsgHeader, byte[] pMsgData, int iPayLoad)
   {
       ResponseObject obj = new ResponseObject(MsgHeader, pMsgData, iPayLoad);

       synchronized (ResponseQueueLock) {
           ResponseQueue.add(obj);
       }
   }

   public static void MessageDataDeframe(String pMsgData, int iPayLoad)
   {
       //Header part for MsgType and VCS
       //byte MessageType = MsgHeader[2];

       String MsgData = pMsgData;

       FileTools.writeCommandLog(context, "1", "1", MsgData, "", false);


       if (MsgData.startsWith(Constants.cmdDayClose)) {
           responseData = "";
           responseCommand = Constants.cmdDayClose;
       }
       else   if (MsgData.startsWith(Constants.cmdLogin)) {
           MsgData = MsgData.replace(Constants.cmdLogin, "");
           String Device_id = MsgData.substring(0, MsgData.indexOf("#"));
           MsgData = MsgData.replace(Device_id + "#", "");

           MsgData = MsgData.replace("~END~", "");
           if (Device_id.equals(SharedPref.getString(context,"device_id"))) {
               responseData = MsgData;
                responseCommand = Constants.cmdLogin;
            }
           else {
                responseData = "";
                responseCommand = "";
            }
       }
       else  if (MsgData.startsWith(Constants.cmdListTables)) {
           MsgData = MsgData.replace(Constants.cmdListTables, "");
           String Device_id = MsgData.substring(0, MsgData.indexOf("#"));
           MsgData = MsgData.replace(Device_id + "#", "");
           MsgData = MsgData.replace("~END~","");

           if (Device_id.equals(SharedPref.getString(context,"device_id"))) {
               responseData = MsgData;
               responseCommand = Constants.cmdListTables;
           }
           else {
                responseData = "";
                responseCommand = "";
           }

       }else  if ((MsgData.startsWith(Constants.cmdClearOrderTable)) ) {
           MsgData = MsgData.replace(Constants.cmdClearOrderTable, "");
           String Device_id = MsgData.substring(0, MsgData.indexOf("#"));
           MsgData = MsgData.replace(Device_id + "#", "");
           MsgData = MsgData.replace("~END~","");

           if (Device_id.equals(SharedPref.getString(context,"device_id"))) {
               responseData = MsgData;
               responseCommand = Constants.cmdClearOrderTable;
           }
           else {
               responseData = "";
               responseCommand = "";
           }

       }else  if ((MsgData.startsWith(Constants.cmdClearKOTTable)) ) {
           MsgData = MsgData.replace(Constants.cmdClearKOTTable, "");
           String Device_id = MsgData.substring(0, MsgData.indexOf("#"));
           MsgData = MsgData.replace(Device_id + "#", "");
           MsgData = MsgData.replace("~END~","");

           if (Device_id.equals(SharedPref.getString(context,"device_id"))) {
               responseData = MsgData;
               responseCommand = Constants.cmdClearKOTTable;
           }
           else {
               responseData = "";
               responseCommand = "";
           }

       }else  if ((MsgData.startsWith(Constants.cmdChangeTable)) ) {
           MsgData = MsgData.replace(Constants.cmdChangeTable, "");
           String Device_id = MsgData.substring(0, MsgData.indexOf("#"));
           MsgData = MsgData.replace(Device_id+"#", "");
           MsgData = MsgData.replace("~END~","");

           if (Device_id.equals(SharedPref.getString(context,"device_id"))) {
               responseData = MsgData;
               responseCommand = Constants.cmdChangeTable;
           }
           else {
               responseData = "";
               responseCommand = "";
           }

       }else  if ((MsgData.startsWith(Constants.cmdMergeTable)) ) {
           MsgData = MsgData.replace(Constants.cmdMergeTable, "");
           String Device_id = MsgData.substring(0, MsgData.indexOf("#"));
           MsgData = MsgData.replace(Device_id+"#", "");
           MsgData = MsgData.replace("~END~","");

           if (Device_id.equals(SharedPref.getString(context,"device_id"))) {
               responseData = MsgData;
               responseCommand = Constants.cmdMergeTable;
           }
           else {
               responseData = "";
               responseCommand = "";
           }

       }else  if ((MsgData.startsWith(Constants.cmdTableOrder)) ) {
           MsgData = MsgData.replace(Constants.cmdTableOrder, "");
           String Device_id = MsgData.substring(0, MsgData.indexOf("#"));
           MsgData = MsgData.replace(Device_id+"#", "");
           MsgData = MsgData.replace("~END~","");

           if (Device_id.equals(SharedPref.getString(context,"device_id"))) {
               responseData = MsgData;
               responseCommand = Constants.cmdTableOrder;
           }
           else {
               responseData = "";
               responseCommand = "";
           }

       }else  if ((MsgData.startsWith(Constants.cmdPrintKKOT)) ) {
           MsgData = MsgData.replace(Constants.cmdPrintKKOT, "");
           String Device_id = MsgData.substring(0, MsgData.indexOf("#"));
           MsgData = MsgData.replace(Device_id+"#", "");
           MsgData = MsgData.replace("~END~","");

           if (Device_id.equals(SharedPref.getString(context,"device_id"))) {
               responseData = MsgData;
               responseCommand = Constants.cmdPrintKKOT;
           }
           else {
               responseData = "";
               responseCommand = "";
           }

       }else  if ((MsgData.startsWith(Constants.cmdTableKOTData)) ) {
           MsgData = MsgData.replace(Constants.cmdTableKOTData, "");
           String Device_id = MsgData.substring(0, MsgData.indexOf("#"));
           MsgData = MsgData.replace(Device_id+"#", "");
           MsgData = MsgData.replace("~END~","");

           if (Device_id.equals(SharedPref.getString(context,"device_id"))) {
               responseData = MsgData;
               responseCommand = Constants.cmdTableKOTData;
           }
           else {
               responseData = "";
               responseCommand = "";
           }

       }else  if ((MsgData.startsWith(Constants.cmdPrintBill)) ) {
           MsgData = MsgData.replace(Constants.cmdPrintBill, "");
           String Device_id = MsgData.substring(0, MsgData.indexOf("#"));
           MsgData = MsgData.replace(Device_id+"#", "");
           MsgData = MsgData.replace("~END~","");

           if (Device_id.equals(SharedPref.getString(context,"device_id"))) {
               responseData = MsgData;
               responseCommand = Constants.cmdPrintBill;
           }
           else {
               responseData = "";
               responseCommand = "";
           }

       }else  if ((MsgData.startsWith(Constants.cmdPrintCheckKOT)) ) {
           MsgData = MsgData.replace(Constants.cmdPrintCheckKOT, "");
           String Device_id = MsgData.substring(0, MsgData.indexOf("#"));
           MsgData = MsgData.replace(Device_id+"#", "");
           MsgData = MsgData.replace("~END~","");

           if (Device_id.equals(SharedPref.getString(context,"device_id"))) {
               responseData = MsgData;
               responseCommand = Constants.cmdPrintCheckKOT;
           }
           else {
               responseData = "";
               responseCommand = "";
           }
       }
       else if (MsgData.startsWith(Constants.cmdListProducts)) {
           MsgData = MsgData.replace(Constants.cmdListProducts, "");
           String Device_id = MsgData.substring(0, MsgData.indexOf("#"));
           MsgData = MsgData.replace(Device_id + "#", "");

           MsgData = MsgData.replace("~END~", "");
           if (Device_id.equals(SharedPref.getString(context,"device_id"))) {
               responseData = MsgData;
               responseCommand = Constants.cmdLogin;
           }
           else {
               responseData = "";
               responseCommand = "";
           }
       }else if (MsgData.startsWith(Constants.cmdTableSplit)) {
           MsgData = MsgData.replace(Constants.cmdTableSplit, "");
           String Device_id = MsgData.substring(0, MsgData.indexOf("#"));
           MsgData = MsgData.replace(Device_id + "#", "");

           MsgData = MsgData.replace("~END~", "");
           if (Device_id.equals(SharedPref.getString(context,"device_id"))) {
               responseData = MsgData;
               responseCommand = Constants.cmdTableSplit;
           }
           else {
               responseData = "";
               responseCommand = "";
           }
       }else if (MsgData.startsWith(Constants.cmdTableUnsplit)) {
           MsgData = MsgData.replace(Constants.cmdTableUnsplit, "");
           String Device_id = MsgData.substring(0, MsgData.indexOf("#"));
           MsgData = MsgData.replace(Device_id + "#", "");

           MsgData = MsgData.replace("~END~", "");
           if (Device_id.equals(SharedPref.getString(context,"device_id"))) {
               responseData = MsgData;
               responseCommand = Constants.cmdTableUnsplit;
           }
           else {
               responseData = "";
               responseCommand = "";
           }
       }else  if (MsgData.startsWith(Constants.cmdNoAvailable)) {
           responseData = MsgData;
           responseCommand = Constants.cmdNoAvailable;
       }

       if (objhandler != null ) {
           if (!responseCommand.equals("")) {
               Message msg = objhandler.obtainMessage();
               objhandler.sendMessage(msg);
           }
       }
   }


}
