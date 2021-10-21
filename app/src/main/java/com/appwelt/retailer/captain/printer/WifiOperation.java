package com.appwelt.retailer.captain.printer;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;

import com.android.print.sdk.PrinterInstance;

import java.util.Timer;
import java.util.TimerTask;

public class WifiOperation implements IPrinterOpertion {
	private Context mContext;
	private Handler mHandler;
	private PrinterInstance mPrinter;
	private WifiManager wifiManager;
	private Timer timer;
	private int errorNumber;
	private String mPrinterIP;// = "192.168.123.100";
	private int mPortNo;// = 9100;

	public WifiOperation(Context context, Handler handler, String PrinterIP, String PrtNo) {
		mContext = context;
		mHandler = handler;
		mPrinterIP = PrinterIP;
		mPortNo = Integer.valueOf(PrtNo);
		wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		timer = new Timer();
	}

	public void close() {
		if (mPrinter != null) {
			mPrinter.closeConnection();
			mPrinter = null;
		}
	}

	//每隔10s检查一次状态。能检查到就证明连接正常。否则再查一次。还不行就断开连接
	private TimerTask myTask = new TimerTask() {
		byte[] writeData = new byte[]{0x10, 0x04, 0x01};
		@Override
		public void run() {
			if (mPrinter == null) {
				timer.cancel();
				return;
			}
			mPrinter.sendByteData(writeData);

			if (mPrinter.read() != null) {
				System.out.println("wifi connection is alive..");
			}else{
				errorNumber++;
				if (errorNumber == 2) {
					errorNumber = 0;
					close();
				}
			}
		}
	};

	public PrinterInstance getPrinter() {
		if (mPrinter != null && mPrinter.isConnected()) {
			timer.schedule(myTask, 5000, 10000);
		}
		return mPrinter;
	}

	public UsbDeviceConnection getUSBConnection()
	{
		return null;
	}
	public UsbEndpoint getmUsbEndpoint()
	{
		return null;
	}

	public void open(Intent data) {
//		String ipAddress = data.getStringExtra("ip_address");
//		String ipAddress = SharedPref.getString(mContext,"wifi_printer");
//		String port = SharedPref.getString(mContext,"wifi_printer_port");
//		int portNo = Integer.parseInt(port);
		Log.i("TAG", "WIFI_CREDENTIALS : "+mPrinterIP);
		Log.i("TAG", "WIFI_CREDENTIALS : "+mPortNo);
		mPrinter = new PrinterInstance(mPrinterIP, mPortNo, mHandler);
		//default is gbk...
		//mPrinter.setEncoding("gbk");

		//Toast.makeText((Activity)mContext, ipAddress + "- 9100", Toast.LENGTH_SHORT).show();
		mPrinter.openConnection();
	}

	@Override
	public void chooseDevice(String PrinterIP, int PortNo) {
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
			mPrinterIP = PrinterIP;
			mPortNo = PortNo;
		}
	}
}
