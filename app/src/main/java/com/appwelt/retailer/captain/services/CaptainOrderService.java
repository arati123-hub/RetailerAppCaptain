package com.appwelt.retailer.captain.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.appwelt.retailer.captain.utils.Constants;
import com.appwelt.retailer.captain.utils.FileTools;
import com.appwelt.retailer.captain.utils.SharedPref;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * This is a background service which will be running all the time on mobile phone.
 * The service makes a Connections Thread and the thread has socket connected over wifi
 * The service has schedule task executor which keeps check for every 2 mins about the Socket status.
 */

public class CaptainOrderService extends Service
{
    private Socket mSocket;
    public ArrayList<byte[]> CommandQueue = new ArrayList<byte[]>();
    private static final String TAG = CaptainOrderService.class.getSimpleName();
    private Context context;
    private static CaptainOrderService CaptainServiceInstance = null;

    private Integer ConnectionMode = 1;

    /**
     * Task Executor: Every 2 min check mConnection == null || mConnection.isConnected == false
     * If any of this is true the socket is closed and we use SocketStart to start the Connection thread.
     */
    ScheduledExecutorService scheduleTaskExecutor;

    /**
     * Socket Connection Thread with Connection Manager
     */
    private WiFiSocketConnectionThread mWiFiConnection;

    private Thread myServerThread;
    private ConnectivityManager mConnMan;

    private OutputStream outputWifi; // = mSocket.getOutputStream();
    private InputStream inputWifi; // = mSocket.getInputStream();

    private final static Object CommandLock = new Object();
    private final static Object ServiceLock = new Object();

    private Boolean mStarted;

    public static CaptainOrderService getInstance() {
        synchronized (ServiceLock) {
            if (CaptainServiceInstance == null) { //if there is no instance available... create new one
                CaptainServiceInstance = new CaptainOrderService();
            }
            return CaptainServiceInstance;
        }
    }

    /**
     * Initiate the service and socket thread for conneciton.
     */
    public void ServiceInitiate() {
        mConnMan = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);

//        final HandlerThread thread = new HandlerThread("IntentService[KeepAliveService]");
//        thread.start();

//        mServiceLooper = thread.getLooper();
//        mServiceHandler = new AegisService.ServiceHandler(mServiceLooper);


        SocketStart();

    }

    /**
     * Used to check the network is available or not.
     * @return true or false
     */
    private boolean isNetworkAvailable() {
        final NetworkInfo info = mConnMan.getActiveNetworkInfo();
        return info != null && info.isConnected();
        //isConnectedOrConnecting decrepted
    }

    /**
     * Used to initiate the Connection Thread for Socket.
     * Read the WiFi_OnOff flag from shared pref.
     * Takes the Device IP and Device Port from Shared pref.
     * if Wifi_OnOff == 1 and both device IP and port are available then start connection thread.
     * It also starts teh Socket Tracker which check the connection state and used to reconnect.
     */
    public void SocketStart()
    {
        SharedPref.setApplicationContext(context);

        if (ConnectionMode == 1) {
            String server_ip = SharedPref.getString(context, "server_ip"); //"192.168.15.137";  //
            String server_port = SharedPref.getString(context, "server_port"); //1600
            if (!server_ip.equals("") && !server_port.equals("")) {
                if (isNetworkAvailable()) {
                    synchronized (ServiceLock) {
                        if (myServerThread == null) {
                            mWiFiConnection = new WiFiSocketConnectionThread(server_ip, Integer.parseInt(server_port));
                            mWiFiConnection.start();
                        }
                    }
                }
            }
        }
        else if (ConnectionMode == 2) {
            //Bluethooth Mode

        }

        SocketTrackerStart();

    }

    public void StartWiFiConnection()
    {
        SocketStop();

        SharedPref.setApplicationContext(context);

        SocketStart();
    }

    /**
     * Not in use, as we are not closing the socket.
     * It will be used when we want to switch the connection mode.
     */
    private void SocketStop()
    {
        synchronized (ServiceLock) {
            if (mWiFiConnection != null && mWiFiConnection.isConnected()) {
                mWiFiConnection.closeSocket();
            }
            mWiFiConnection = null;
        }
    }

    /**
     * This fucntion get called using the service instance.
     * The purpose of this is to add the command in queue. The Queue scheduler will take commands using FIFO
     * @param pCommand to be send as Byte array
     */
    public synchronized void sendCommand(String pCommand)
    {
        if (mWiFiConnection != null) {
            mWiFiConnection.addCommandtoQueue(pCommand);
        }
    }

    /**
     * Service Constructor set the BaseContext to service context
     */
    public CaptainOrderService() {
        this.context = getBaseContext();
    }

    /**
     * Used to set the Context shared by the activity to service context
     * @param context object
     */
    public void SetCaptainOrderServiceContext(Context context) {
        this.context = context;
    }


    /**
     * Bind Intent method for service override
     * @param intent object
     * @return IBinder interface
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     *  onCreate for service override
     */
    @Override
    public void onCreate() {
        super.onCreate();
        SocketServiceStart();
    }

    /**
     *  Used to start the service in foreground mode as per the OS version
     *  Push the notification if the SDK version is greater than 26
     */
    private void SocketServiceStart()
    {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());
    }

    /**
     * Service Notification for the SDK greater than 26
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        String NOTIFICATION_CHANNEL_ID = "Retailer.service";
        String channelName = "Retailer Captain Connection Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("Retailer app service is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }

    /**
     * Not in Use
     * can be used to send the notificaiton on alert/error received
     * @param strMessage String
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void NotificationShow(String strMessage)
    {
        try {
            String NOTIFICATION_CHANNEL_ID = "Aegis.service";
            String channelName = "Aegis Connection Service";
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
            Notification notification = notificationBuilder.setOngoing(true)
                    .setContentTitle(strMessage)
                    .setPriority(NotificationManager.IMPORTANCE_MIN)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build();

            startForeground(3, notification);

        }
        catch (Exception e)
        {
            e.getStackTrace();
        }

    }

    /**
     * Service Start Command for Service Override
     * Standard parameters to the start command
     * Service Started as Sticky
     * @param intent, flags and startId
     * @return int processId
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        SocketStart();

        return START_STICKY;
    }

    /**
     * Scheduled task executor for service to check and start the socket thread
     * It check the connection object and  Socket state and then start socket or reset socket.
     * It does the check after every 2 mins and the task is fixed for this interval.
     */
    private void SocketTrackerStart()
    {
        scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
        // This schedule a runnable task every 2 minutes
        scheduleTaskExecutor.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                SharedPref.setApplicationContext(context);

                synchronized (ServiceLock) {
                    if (ConnectionMode == 1) {
                        if (mWiFiConnection == null || !mWiFiConnection.isConnected()) {
                            SocketStart();
                        }
                    }
                    else if (ConnectionMode == 2) {
                       //Bluethooth
                    }

                }
            }
        }, 1, 2, TimeUnit.MINUTES);
    }

    /**
     * Service onDestroy for Service Override
     * We do not need to shutdown the scheduled task. We want to keep it running.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (scheduleTaskExecutor != null) {
//            scheduleTaskExecutor.shutdown();
//        }
    }


    /**
     * Service onTaskRemove for Service Override
     * Standard parameters to the start command
     * It start the broadcast intetnt to so that the app receive the SocketRestarterBroadcastRecevier.
     * @param rootIntent
     */
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Intent broadcastIntent = new Intent(context, SocketRestarterBroadcastReceiver.class);
        sendBroadcast(broadcastIntent);
    }


    /**
     * Socket Connection Thead hold the socket and keep it in loop
     * Incase the socket breaks traps the error and log it.
     * Thead get closed when the socket lost the connection.
     * Has the Message Processor Object Instance for message data handler.
     */
    protected class WiFiSocketConnectionThread extends Thread {


        private final String mHost;
        private final int mPort;

        public boolean isConnected()
        {
            return mSocket != null && mSocket.isConnected();
        }

//        public ArrayList<byte[]> CommandQueue = new ArrayList<byte[]>();

        /**
         * Pull the next command from the Queue and pass to connection thread for SOC
         * If command Queue is not null and its size is > 0 then there is pending command for SOC.
         * Make the command buffer ready to send over socket.
         */
        public void addCommandtoQueue(String pCommand)
        {
            synchronized (CommandLock) {
                CommandQueue.add(pCommand.getBytes());
            }
            FileTools.writeCommandLog(getBaseContext(), "WiFi", "Sending: ", pCommand, "",false);
        }

        /**
         * Thread consructor which host and port parameter
         */
        public WiFiSocketConnectionThread(final String host, final int port) {
            mHost = host;
            mPort = port;
            mSocket = new Socket();
        }

        /**
         *  Set Connection status check variable to break the loop
         */
        public void closeSocket()
        {
            if (isConnected()) {
                try {
                    mSocket.close();
                } catch (Exception e) {

                }
                synchronized (ServiceLock) {
                    mWiFiConnection = null;
                }
           }
        }

        /**
         * Thread run method connect the socket and keep it in while loop.
         * Accepts the commands from outside and send over the socket
         * Also read socket data and check the header
         * Read message data from socket
         * As per message header call the message processor class for handle the message data.
         */
        @Override
        public void run() {
            WifiOutputStream OutputWifiStream; WifiInputStream InputWifiStream;

            try {
                // Now we can say that the service is started.
                //setStarted(true);

                // Connect to server.
                mSocket.connect(new InetSocketAddress(mHost, mPort), 10000); //300000

                //Log.i(TAG, "Connection established to " + mSocket.getInetAddress() + ":" + mPort);

                if (isConnected()) {

                    outputWifi = mSocket.getOutputStream();
                    inputWifi = mSocket.getInputStream();

//                    outputWifi = new DataOutputStream(mSocket.getOutputStream());
//                    inputWifi = new DataInputStream(mSocket.getInputStream());

                    OutputWifiStream = new WifiOutputStream(context);
                    OutputWifiStream.start();

                    InputWifiStream = new WifiInputStream(context);
                    InputWifiStream.start();
                }

            } catch (final IOException e) {
                //Utils.writeCommandLog(getBaseContext(), "WiFi", "Unexpected I/O error." + e.toString(), "");
                Log.e(TAG, "Unexpected I/O error.", e);
            } catch (final Exception e) {
                //Utils.writeCommandLog(getBaseContext(), "WiFi", "Exception occurred." + e.toString(), "");
                Log.e(TAG, "Exception occurred.", e);
            } finally {


                //closeSocket();
            }


            }
        }


    protected class WifiOutputStream extends Thread {
        Context context;

        public WifiOutputStream(final Context pcontext) {
            this.context = pcontext;
        }

        private boolean isConnected()
        {
            return mSocket != null && mSocket.isConnected();
        }

        @Override
        public void run() {

            while (isConnected()) {

                boolean hasCommandToWrite = false;

                synchronized (CommandLock) {
                    hasCommandToWrite = (mWiFiConnection != null) && (CommandQueue != null) && (CommandQueue.size() > 0);
                }
                    if (hasCommandToWrite) {

                        try {

                            outputWifi.write(CommandQueue.get(0));
                            outputWifi.flush();

                            synchronized (CommandLock) {
                                if (CommandQueue.size() > 0)
                                    CommandQueue.remove(0);
                            }

                        } catch (IOException ex) {
                            Log.e(TAG, "Unexpected I/O error.", ex);
                            mWiFiConnection.closeSocket();
                        }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Log.e(TAG, "Thread.", ex);
                    }
                }
            }
        }
    }


    protected class  WifiInputStream extends Thread {
        Context context;

        private int iavailable = 0;

        private String FullMsgData ="";
        private  String responseData = "";
        private String bZipFile = "";
        private File zipfile;

        private boolean isConnected()
        {
            return mSocket != null && mSocket.isConnected();
        }

        public WifiInputStream(final Context pcontext) {
            this.context = pcontext;
        }

        @Override
        public void run() {

            try {
                while (isConnected())
                {
                    byte[] readMsgData;
                    iavailable = inputWifi.available();

                    if (iavailable > 0) {
                        readMsgData = new byte[iavailable];
                        int count = inputWifi.read(readMsgData, 0, iavailable);
                        String strData = "";

                        for(int i=0; i<count; i++)
                            strData = strData + (char) readMsgData[i];

//                        if (bZipFile.equals("")) {
//                            FullMsgData = FullMsgData + strData;
//
//                            if (FullMsgData.startsWith(Constants.cmdListImages)) {
//                                FullMsgData = FullMsgData.replace(Constants.cmdListImages, "");
//                                String Device_id = FullMsgData.substring(0, FullMsgData.indexOf("#"));
//                                FullMsgData = FullMsgData.replace(Device_id + "#", "");
//                                FullMsgData = FullMsgData.replace("~END~", "");
//
//                                bZipFile = FullMsgData;
//
//                                File zipFolder = new File(Environment.getExternalStorageDirectory().getPath() + "/" + Constants.FOLDER_NAME);
//
//                                zipfile = new File(zipFolder, bZipFile);
//                                if (zipfile.exists()) {
//                                    zipfile.delete();
//                                }
//                                try {
//                                    Log.e("File created ", "File created.");
//                                    zipfile.createNewFile();
//                                } catch (IOException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                }
//                                zipfile = null;
//                            }
//                        }
//                        else
//                        {
//                            try {
//                                FileOutputStream out = new FileOutputStream(zipfile, true);
//                                out.write(readMsgData, 0, readMsgData.length);
//                                out.close();
//                            } catch (FileNotFoundException e) {
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        if (bZipFile.equals("")) {
                            if (strData.contains("~END~")) {
                                responseData = FullMsgData;
                                FullMsgData = "";
                            }

                            FileTools.writeCommandLog(context, "WiFi", "Receive:", strData, "", false);

                            if (!responseData.equals("")) {
                                /*
                                 * Executing the command defragmer on different thead than the Service
                                 */
                                final String MsgData = responseData;
                                final int PayLoad = responseData.length();
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //ICSBMessageDeframer.MessageAddQueue(MsgHeader, MsgData, iPayLoad);
                                        MessageDeframer.MessageDataDeframe(MsgData, PayLoad);
                                    }
                                });
                            }
                        //} Zip

                        strData = "";


                    }
                }
            }
            catch (final IOException e) {
                Log.e(TAG, "Unexpected I/O error.", e);
             } catch (final Exception e) {
                Log.e(TAG, "Exception occurred.", e);
            }
            finally {
                Log.d(TAG, "Exception occurred.");
            }


        }
    }





}
