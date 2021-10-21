package com.appwelt.retailer.captain.printer;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.print.sdk.PrinterInstance;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public class UsbGenericPrinter implements IPrinterOpertion {
	private final static String TAG = "UsbGenericPrinter";
	private Context mContext;
	private Handler mHandler;
	private PrinterInstance mPrinter;
	private UsbDevice mDevice;
	private UsbInterface mUsbInterface;
	private UsbEndpoint mUsbEndPoint;
	private UsbDeviceConnection mUsbConnection;
	private boolean hasRegDisconnectReceiver;
	private IntentFilter filter;
	private static String ACTION_USB_PERMISSION = "com.android.print.demo.USB_PERMISSION";


	public UsbGenericPrinter(Context context, Handler handler) {
		mContext = context;
		mHandler = handler;
		hasRegDisconnectReceiver = false;
		mUsbConnection = null;
		mUsbInterface = null;

		filter = new IntentFilter();
		filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
		filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
	}

	public void open(Intent data) {
		//mDevice = data.getParcelableExtra(UsbManager.EXTRA_DEVICE);
		//mPrinter = new PrinterInstance(mContext, mDevice, mHandler);
		// default is gbk...
		// mPrinter.setEncoding("gbk");
		//mPrinter.openConnection();
		UsbManager usbManager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
		try {
			mUsbEndPoint = getOutputEndPointOfPrinter(mUsbInterface);
			if (mUsbEndPoint == null) {
				Toast.makeText(mContext, "Sorry, Unable to locate output end point of your printer", Toast.LENGTH_SHORT).show();
				new Thread(new Runnable(){
					public void run() {
						if (mHandler != null)
							mHandler.obtainMessage(102).sendToTarget();
					}
				}).start();
			}
			else {
				mUsbConnection = usbManager.openDevice(mDevice);
				mUsbConnection.claimInterface(mUsbInterface, true);
				new Thread(new Runnable(){
					public void run() {
						if (mHandler != null)
							mHandler.obtainMessage(101).sendToTarget();
					}
				}).start();

			}
		}
		catch (Exception e) {
			e.printStackTrace();
			new Thread(new Runnable(){
				public void run() {
					if (mHandler != null)
						mHandler.obtainMessage(102).sendToTarget();
				}
			}).start();
		} finally {

		}
	}

	public void close() {
//		if (mPrinter != null) {
//			mPrinter.closeConnection();
//			mPrinter = null;
//		}

		try {
			if (mUsbConnection != null) {
				mUsbConnection.close();
				new Thread(new Runnable(){
					public void run() {
						if (mHandler != null)
							mHandler.obtainMessage(103).sendToTarget();
					}
				}).start();

			}
		} catch (Exception e) {
			Log.e("Exception", "Unable to release resources because : " + e.getLocalizedMessage());
			new Thread(new Runnable(){
				public void run() {
					if (mHandler != null)
						mHandler.obtainMessage(102).sendToTarget();
				}
			}).start();
		}

		if (hasRegDisconnectReceiver) {
			mContext.unregisterReceiver(myReceiver);
			hasRegDisconnectReceiver = false;
		}
	}

	private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.i(TAG, "receiver is: " + action);
			if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
				// xxxxx
			} else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
				UsbDevice device = (UsbDevice) intent
						.getParcelableExtra(UsbManager.EXTRA_DEVICE);
				if (device != null && mPrinter != null
						&& mPrinter.isConnected() && device.equals(mDevice)) {
					close();
				}
			}
			else if (ACTION_USB_PERMISSION.equals(action)) {
				//startLockTask();
				{
					UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
					if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
						if (device != null) {
							mDevice = device;
						}
					} else {

					}
				}
			}
		}
	};

	public PrinterInstance getPrinter() {
		if (mPrinter != null && mPrinter.isConnected()) {
			if (!hasRegDisconnectReceiver) {
				mContext.registerReceiver(myReceiver, filter);
				hasRegDisconnectReceiver = true;
			}
		}
		return mPrinter;
	}

	public UsbDeviceConnection getUSBConnection() {
		if (mUsbConnection != null) {
			if (!hasRegDisconnectReceiver) {
				mContext.registerReceiver(myReceiver, filter);
				hasRegDisconnectReceiver = true;
			}
		}
		return mUsbConnection;
	}

	public UsbEndpoint getmUsbEndpoint()
	{
		return mUsbEndPoint;
	}

	@Override
	public void chooseDevice(String PrinterIP, int PortNo) {
		try {
			UsbManager usbManager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
			PendingIntent mPermissionIntent = PendingIntent.getBroadcast(mContext, 0, new Intent(
					ACTION_USB_PERMISSION), 0);
			IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
			mContext.registerReceiver(myReceiver, filter);

			for (UsbDevice objDevice : usbManager.getDeviceList().values()) {
				if (objDevice != null) {
					for (int j = 0; j < objDevice.getInterfaceCount(); j++) {
						if (objDevice.getInterface(j).getInterfaceClass() == UsbConstants.USB_CLASS_PRINTER) {
							if (objDevice.getDeviceName().equals(PrinterIP)){
								mUsbInterface = objDevice.getInterface(j);
								mDevice = objDevice;
								if (!usbManager.hasPermission(mDevice)) {
									usbManager.requestPermission(mDevice, mPermissionIntent);
									break; //For Loop
								} else {
									break; //For Loop
								}
							}
							//obj.getDeviceName== device name selectd by user in config screen ()207
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}


	private UsbEndpoint getOutputEndPointOfPrinter(UsbInterface printerInterface ) {
		UsbEndpoint usbendpoint = null;
		for (int i=0; i<printerInterface.getEndpointCount(); i++)
		{
			usbendpoint = printerInterface.getEndpoint(i);
			if (usbendpoint.getDirection() == UsbConstants.USB_DIR_OUT)
				break;
			else
				usbendpoint = null;
		}

		return usbendpoint;
	}

	private UsbEndpoint getInputEndPointOfPrinter(UsbInterface printerInterface ) {
		UsbEndpoint usbendpoint = null;
		for (int i=0; i<printerInterface.getEndpointCount(); i++)
		{
			usbendpoint = printerInterface.getEndpoint(i);
			if (usbendpoint.getDirection() == UsbConstants.USB_DIR_IN)
				break;
			else
				usbendpoint = null;
		}

		return usbendpoint;
	}
}
