package com.appwelt.retailer.captain.printer;

import android.content.Intent;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;

import com.android.print.sdk.PrinterInstance;

public interface IPrinterOpertion {
	public void open(Intent data);
	public void close();
	public void chooseDevice(String PrinterIP, int PortNo);
	public PrinterInstance getPrinter();
	public UsbDeviceConnection getUSBConnection();
	public UsbEndpoint getmUsbEndpoint();
}
