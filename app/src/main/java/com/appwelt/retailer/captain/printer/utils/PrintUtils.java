package com.appwelt.retailer.captain.printer.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbManager;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.android.print.sdk.PrinterConstants.Command;
import com.android.print.sdk.PrinterInstance;
import com.android.print.sdk.Table;
import com.appwelt.retailer.captain.R;
import com.appwelt.retailer.captain.model.BillDetail;
import com.appwelt.retailer.captain.model.BillDetails;
import com.appwelt.retailer.captain.model.BillFormatDetails;
import com.appwelt.retailer.captain.model.BillOrdering;
import com.appwelt.retailer.captain.model.KOTItems;
import com.appwelt.retailer.captain.model.OrderDetails;
import com.appwelt.retailer.captain.model.OrderExtraItem;
import com.appwelt.retailer.captain.utils.DateConversionClass;
import com.appwelt.retailer.captain.utils.GenerateRandom;
import com.appwelt.retailer.captain.utils.SharedPref;
import com.appwelt.retailer.captain.utils.sqlitedatabase.DatabaseHelper;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PrintUtils {

	public static void printText(Resources resources, PrinterInstance mPrinter, String Message) {
		mPrinter.init();
		//mPrinter.printText(resources.getString(R.string.example_text));
		mPrinter.printText(Message);
		// 换行
		// mPrinter.setPrinter(Command.PRINT_AND_NEWLINE);
		mPrinter.setPrinter(Command.PRINT_AND_WAKE_PAPER_BY_LINE, 2); // 换2行
		//切刀
		mPrinter.cutPaper();
	}
	public static boolean printTest(Context context, PrinterInstance mPrinter, String ipAddress, String portN0) {
		mPrinter.init();
		mPrinter.setPrinter(Command.ALIGN, Command.ALIGN_CENTER);
		mPrinter.setCharacterMultiple(1, 1);
		mPrinter.printText("Printer IP Address : "+ipAddress+" and Port No. : "+portN0+"\n");
		mPrinter.printText("Print OK "+"\n");
		mPrinter.setPrinter(Command.PRINT_AND_WAKE_PAPER_BY_LINE, 3);
		mPrinter.cutPaper();
		return true;
	}

	public static boolean printImage(Context context, PrinterInstance mPrinter, String image_str) {
		mPrinter.init();
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.icon);
		mPrinter.printText("Print Image:\n");
		mPrinter.printImage(bitmap);
		mPrinter.setPrinter(Command.PRINT_AND_WAKE_PAPER_BY_LINE, 2);
		mPrinter.cutPaper();
		return true;
	}

	public static boolean printKOT(Context context, PrinterInstance mPrinter, String order_id, ArrayList<OrderDetails> orderDetails) {
		String DATABASE_NAME = SharedPref.getString(context,"database_name");
		DatabaseHelper databaseHelper = new DatabaseHelper(context,DATABASE_NAME);
		String org_name = databaseHelper.selectByID("tbl_organisation","organisation_id", SharedPref.getString(context,"organisation_id"),"organisation_name");
		String username = databaseHelper.selectByID("tbl_users","user_id",SharedPref.getString(context,"user_id"),"user_name")+"\n";
		SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm a");
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date date = null;
		try { date = inputFormat.parse(new DateConversionClass().currentDateApoch()); } catch (ParseException e) { e.printStackTrace(); }
		mPrinter.init();
		mPrinter.setPrinter(Command.ALIGN, Command.ALIGN_CENTER);
		mPrinter.setCharacterMultiple(1, 1);
//		mPrinter.printText(org_name+"\n\n");
		mPrinter.printText("KOT\n\n");

		mPrinter.setCharacterMultiple(0, 0);
		mPrinter.setPrinter(Command.ALIGN, Command.ALIGN_LEFT);
		StringBuffer sb = new StringBuffer();
//		if (SharedPref.getString(context,"bill_type").equals("grocery")){
//			sb.append("Cashier : " + username + "; ");
//			if (username != null){
//				sb.append("");
//			}
//		}else if (SharedPref.getString(context,"bill_type").equals("parcel")){
//			sb.append("Parcel No. : " + SharedPref.getString(context,"table") + "; ");
//			if (username != null){
//				sb.append("Captain : " + username);
//			}
//		}else{
			sb.append("Table No.:" + SharedPref.getString(context,"table") + "; ");
			if (username != null){
				sb.append("Captain:" + username);
			}
//		}
		Table table1 = new Table(sb.toString(), ";", new int[] { 19, 25 });
		mPrinter.printTable(table1);
		sb = new StringBuffer();
		if (order_id != null){
			sb.append("Order No.:" + order_id + "; ");
		}
		sb.append("DateTime:" + outputFormat.format(date));
		Table table2 = new Table(sb.toString(), ";", new int[] { 19, 25 });
		mPrinter.printTable(table2);
		sb = new StringBuffer();
		sb.append("----------------------------------------------\n");
		mPrinter.printText(sb.toString());
		mPrinter.printTable(new Table("Sr; Items; QTY", ";", new int[] { 6, 32, 6 }));
		mPrinter.printText(sb.toString());
		int qty = 0;
		if (orderDetails != null){
			for (int i=0; i<orderDetails.size(); i++){
				qty = qty + Integer.valueOf(orderDetails.get(i).getOrder_details_order_qty());
				String name = databaseHelper.selectByID("tbl_language_text","language_reference_id","PN_"+orderDetails.get(i).getProduct_id(),"language_text");
				if (name != null){
					mPrinter.printTable(new Table(String.valueOf(i+1) + "; " + name + "; " + orderDetails.get(i).getOrder_details_order_qty(), ";", new int[] { 6, 32, 6 }));
					if (orderDetails.get(i).getOrder_details_special_note() != null){
						mPrinter.printTable(new Table(" ; " + "("+orderDetails.get(i).getOrder_details_special_note()+" )" + ";  ", ";", new int[] { 6, 32, 6 }));
					}
				}
			}
		}

		sb = new StringBuffer();
		sb.append("----------------------------------------------\n");
		mPrinter.printText(sb.toString());

		sb = new StringBuffer();
		if (order_id != null){
			sb.append("Total Items:" + orderDetails.size() + "; ");
		}
		sb.append("Total Quantity:" + qty);

		mPrinter.printTable(new Table(sb.toString(), ";", new int[] { 19, 25 }));

		sb = new StringBuffer();
		sb.append("----------------------------------------------\n");
		mPrinter.printText(sb.toString());

		mPrinter.setPrinter(Command.PRINT_AND_WAKE_PAPER_BY_LINE, 3);
		mPrinter.cutPaper();
		return true;
	}

	public static boolean printCheckKOT(Context context, PrinterInstance mPrinter, String order_id, ArrayList<BillDetail> orderDetails) {
		String DATABASE_NAME = SharedPref.getString(context,"database_name");
		DatabaseHelper databaseHelper = new DatabaseHelper(context,DATABASE_NAME);

		ArrayList<OrderExtraItem> extraItems = databaseHelper.BillExtraItem(order_id);

		String org_name = databaseHelper.selectByID("tbl_organisation","organisation_id", SharedPref.getString(context,"organisation_id"),"organisation_name");
		String username = databaseHelper.selectByID("tbl_users","user_id",SharedPref.getString(context,"user_id"),"user_name")+"\n";
		SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm a");
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date date = null;
		try { date = inputFormat.parse(new DateConversionClass().currentDateApoch()); } catch (ParseException e) { e.printStackTrace(); }
		mPrinter.init();
		mPrinter.setPrinter(Command.ALIGN, Command.ALIGN_CENTER);
		mPrinter.setCharacterMultiple(1, 1);
		mPrinter.printText(org_name+"\n\n");
		if (SharedPref.getString(context,"bill_type").equals("parcel")){
			mPrinter.printText("Check KOT (Parcel) \n\n");
		}else{
			mPrinter.printText("Check KOT \n\n");
		}
		mPrinter.setCharacterMultiple(0, 0);
		mPrinter.setPrinter(Command.ALIGN, Command.ALIGN_LEFT);
		StringBuffer sb = new StringBuffer();

		if (SharedPref.getString(context,"bill_type").equals("grocery")){
			sb.append("Cashier : " + username + "; ");
			sb.append("");
		}else if (SharedPref.getString(context,"bill_type").equals("parcel")){
			sb.append("Parcel No. : " + SharedPref.getString(context,"table") + "; ");
			if (username != null){
				sb.append("Captain : " + username);
			}
		}else{
			sb.append("Table No. : " + SharedPref.getString(context,"table") + "; ");
			if (username != null){
				sb.append("Captain : " + username);
			}
		}

		Table table1 = new Table(sb.toString(), ";", new int[] { 19, 25 });
		mPrinter.printTable(table1);
		sb = new StringBuffer();
		if (order_id != null){
			sb.append("Order No.: " + order_id + "; ");
		}
		sb.append("DateTime: " + outputFormat.format(date));
		Table table2 = new Table(sb.toString(), ";", new int[] { 19, 25 });
		mPrinter.printTable(table2);
		sb = new StringBuffer();
		sb.append("----------------------------------------------\n");
		mPrinter.printText(sb.toString());
		mPrinter.printTable(new Table("Sr; Name; QTY", ";", new int[] { 6, 32, 6 }));
		mPrinter.printText(sb.toString());
		if (orderDetails != null){
			for (int i=0; i<orderDetails.size(); i++){
				String name = databaseHelper.selectByID("tbl_language_text","language_reference_id","PN_"+orderDetails.get(i).getBill_detail_product_id(),"language_text");
				if (name != null){
					mPrinter.printTable(new Table(String.valueOf(i+1) + "; " + name + "; " + orderDetails.get(i).getBill_detail_product_quantity(), ";", new int[] { 6, 32, 6 }));
					if (orderDetails.get(i).getBill_detail_product_special_note() != null){
						mPrinter.printTable(new Table(" ; " + "("+orderDetails.get(i).getBill_detail_product_special_note()+" )" + ";  ", ";", new int[] { 6, 32, 6 }));
					}
				}
			}
		}
		if (extraItems != null){
			for (int i=0; i<extraItems.size(); i++){
				mPrinter.printTable(new Table(String.valueOf(i+1) + "; " + extraItems.get(i).getOrder_extra_item_name() + "; " + extraItems.get(i).getOrder_extra_item_qty(), ";", new int[] { 6, 32, 6 }));
//				if (orderDetails.get(i).getBill_detail_product_special_note() != null){
//					mPrinter.printTable(new Table(" ; " + "("+orderDetails.get(i).getBill_detail_product_special_note()+" )" + ";  ", ";", new int[] { 6, 32, 6 }));
//				}
			}
		}
		mPrinter.printText(sb.toString());
		mPrinter.setPrinter(Command.PRINT_AND_WAKE_PAPER_BY_LINE, 3);
		mPrinter.cutPaper();
		return true;
	}

	private static String padLeft(String s, int n) {
		return String.format("%" + n + "s", s);
	}

	public static boolean printBill(Context context, PrinterInstance mPrinter,
									String foodBillId, String barBillId, boolean includeInBill,
									String discount,String tip, String cname, String cno) {
		String DATABASE_NAME = SharedPref.getString(context,"database_name");
		DatabaseHelper databaseHelper = new DatabaseHelper(context,DATABASE_NAME);

		ArrayList<BillOrdering> billOrdering = databaseHelper.getBillOrdering("bill_section_default_order");

		ArrayList<OrderExtraItem> foodExtraItem = databaseHelper.BillExtraItem(foodBillId);
		ArrayList<OrderExtraItem> barExtraItem = databaseHelper.BillExtraItem(barBillId);

		double foodTotal = 0, sgstTotal = 0, cgstTotal = 0;
		double barTotal = 0;
		double grandTotal = 0,finalDiscount =0,finalTip =0;

		ArrayList<BillDetail> orderFoodDetails = databaseHelper.BillDetailsByBillId(foodBillId);
		ArrayList<BillDetail> orderBarDetails = databaseHelper.BillDetailsByBillId(barBillId);
		if (foodBillId.length() != 0){
			if (orderFoodDetails != null){
				for (int i=0;i<orderFoodDetails.size();i++){
					sgstTotal = sgstTotal + Double.parseDouble(orderFoodDetails.get(i).getBill_detail_product_sgst());
					cgstTotal = cgstTotal + Double.parseDouble(orderFoodDetails.get(i).getBill_detail_product_cgst());
					double amount = Double.parseDouble(orderFoodDetails.get(i).getBill_detail_product_quantity()) * Double.parseDouble(orderFoodDetails.get(i).getBill_detail_product_price());
					foodTotal = foodTotal + amount;
				}
			}
			if (foodExtraItem != null){
				for (int i=0;i<foodExtraItem.size();i++){
					double amount = Double.parseDouble(foodExtraItem.get(i).getOrder_extra_item_qty()) * Double.parseDouble(foodExtraItem.get(i).getOrder_extra_item_price());
					foodTotal = foodTotal + amount;
				}
			}
		}

		if (barBillId.length() != 0){
			for (int i=0;i<orderBarDetails.size();i++){
				sgstTotal = sgstTotal + Double.parseDouble(orderBarDetails.get(i).getBill_detail_product_sgst());
				cgstTotal = cgstTotal + Double.parseDouble(orderBarDetails.get(i).getBill_detail_product_cgst());
				double amount = Double.parseDouble(orderBarDetails.get(i).getBill_detail_product_quantity()) * Double.parseDouble(orderBarDetails.get(i).getBill_detail_product_price());
				barTotal = barTotal + amount;
			}
			if (barExtraItem != null){
				for (int i=0;i<barExtraItem.size();i++){
					double amount = Double.parseDouble(barExtraItem.get(i).getOrder_extra_item_qty()) * Double.parseDouble(barExtraItem.get(i).getOrder_extra_item_price());
					barTotal = barTotal + amount;
				}
			}
		}

		String fid,bid;
		if (SharedPref.getString(context,"bill_type").equals("parcel")){
			fid = databaseHelper.selectByTwoID("tbl_parcel_order","parcel_id","parcel_order_type",
					SharedPref.getString(context,"table"),"FOOD","bill_id");
			bid = databaseHelper.selectByTwoID("tbl_parcel_order","parcel_id","parcel_order_type",
					SharedPref.getString(context,"table"),"BAR","bill_id");
		}else if (SharedPref.getString(context,"bill_type").equals("grocery")){
			fid = databaseHelper.selectByID("tbl_grocery_orders","grocery_id",
					SharedPref.getString(context,"table"),"grocery_bill_id");
			bid = "";
		}else{
			fid = databaseHelper.selectByTwoID("tbl_table_order","table_id","order_type",
					SharedPref.getString(context,"table"),"FOOD","bill_id");
			bid = databaseHelper.selectByTwoID("tbl_table_order","table_id","order_type",
					SharedPref.getString(context,"table"),"BAR","bill_id");
		}

		BillDetails billDetails = new BillDetails();
		if (foodBillId.length() !=0){
			billDetails = databaseHelper.getBillDetailsByBillID(foodBillId);
		}else{
			billDetails = databaseHelper.getBillDetailsByBillID(barBillId);
		}
		if (fid != null && !fid.isEmpty()){
			if (fid.length() !=0){
				if (SharedPref.getString(context,"bill_type").equals("parcel")){
					billDetails = databaseHelper.getParcelBillHeaderByBillID(fid);
				}else{
					billDetails = databaseHelper.getBillHeaderByBillID(fid);
				}
			}
		}else if (bid != null && !bid.isEmpty()){
			if (bid.length() !=0){
				if (SharedPref.getString(context,"bill_type").equals("parcel")){
					billDetails = databaseHelper.getParcelBillHeaderByBillID(bid);
				}else{
					billDetails = databaseHelper.getBillHeaderByBillID(bid);
				}
			}
		}else{
			billDetails = null;
		}

		if (billDetails != null){
			if (billDetails.getBill_discount_amount() != null && billDetails.getBill_discount_amount().length()!=0){
				finalDiscount = Double.parseDouble(billDetails.getBill_discount_amount());
			}
			if (billDetails.getBill_tip() != null && billDetails.getBill_tip().length()!=0){
				finalTip = Double.parseDouble(billDetails.getBill_tip());
			}
		}else{
			if (discount != null && discount.length()!=0){
				finalDiscount = Double.parseDouble(discount);
			}
			if (tip != null && tip.length()!=0){
				finalTip = Double.parseDouble(tip);
			}
		}

		BillFormatDetails billFormatDetails = databaseHelper.getBillFormatByOrg(SharedPref.getString(context,"organisation_id"));
		String username = databaseHelper.selectByID("tbl_users","user_id",SharedPref.getString(context,"user_id"),"user_name")+"\n";

		if (username.length()>19)
			username = username.substring(0,18);

		SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date date = null;
		try { date = inputFormat.parse(new GenerateRandom().getCurrentDate()); } catch (ParseException e) { e.printStackTrace(); }

		mPrinter.init();
		mPrinter.setPrinter(Command.ALIGN, Command.ALIGN_CENTER);
		mPrinter.setCharacterMultiple(1, 1);


		for (int i=0; i<billOrdering.size(); i++){
			if (billOrdering.get(i).getBill_section_order().equals("1")){
				if (billFormatDetails.getBill_title() != null && billFormatDetails.getBill_title().length() != 0){
					mPrinter.printText(billFormatDetails.getBill_title()+"\n");
				}
				mPrinter.setCharacterMultiple(0, 0);
			}else if (billOrdering.get(i).getBill_section_order().equals("2")){
				if (billFormatDetails.getBill_gst_no() != null && billFormatDetails.getBill_gst_no().length() != 0){
					mPrinter.printText("GST:"+billFormatDetails.getBill_gst_no()+"\n");
				}
			}else if (billOrdering.get(i).getBill_section_order().equals("3")){
				if (billFormatDetails.getBill_address_line1() != null && billFormatDetails.getBill_address_line1().length() != 0){
					mPrinter.printText(billFormatDetails.getBill_address_line1()+"\n");
				}
				if (billFormatDetails.getBill_address_line2() != null && billFormatDetails.getBill_address_line2().length() != 0){
					mPrinter.printText(billFormatDetails.getBill_address_line2()+"\n");
				}
			}else if (billOrdering.get(i).getBill_section_order().equals("4")){
				mPrinter.printText("Mobile:"+billFormatDetails.getBill_telephone()+"\n");
			}else if (billOrdering.get(i).getBill_section_order().equals("5")){
				if (billFormatDetails.getBill_head() != null && billFormatDetails.getBill_head().length() != 0){
					mPrinter.printText(billFormatDetails.getBill_head()+"\n");
				}
			}else if (billOrdering.get(i).getBill_section_order().equals("6")){
				mPrinter.setPrinter(Command.ALIGN, Command.ALIGN_LEFT);

				StringBuffer sb = new StringBuffer();

				if (SharedPref.getString(context,"bill_type").equals("grocery")){
					sb.append("Cashier:" + username + "; ");
					if (username != null){ sb.append(""); }
				}else{
					if (SharedPref.getString(context,"bill_type").equals("parcel")){
						sb.append("Parcel No.:" + SharedPref.getString(context,"table") + "; ");
					}else{
						sb.append("Table No.:" + SharedPref.getString(context,"table") + "; ");
					}
					if (username != null){ sb.append("Captain:" + username); }
				}
				Table table1 = new Table(sb.toString(), ";", new int[] { 19, 25 });
				mPrinter.printTable(table1);
			}else if (billOrdering.get(i).getBill_section_order().equals("7")){
				if (cname!=null && cno!=null){
					if (cname.length()!=0 && cno.length()!=0){
						StringBuilder sb=new StringBuilder();
						sb.append("Customer:" + cname + "; ");
						sb.append("Mobile No.:" + cno);
						Table table1 = new Table(sb.toString(), ";", new int[] { 19, 25 });
						mPrinter.printTable(table1);
					}
				}else{
					if (cname != null && cname.length() != 0){
						mPrinter.printText("Customer:" + cname+"\n");
					}
					if (cno != null && cno.length() != 0){
						mPrinter.printText("Mobile No.:" + cno+"\n");
					}
				}

				if (cno != null && cno.length() != 0){
					String address = databaseHelper.selectByID("tbl_customer","customer_mobile_no",cno,"customer_address");
					if (address != null && address.length() != 0){
						mPrinter.printText("Address:" + address+"\n");
					}
				}
			}else if (billOrdering.get(i).getBill_section_order().equals("8")){

			}else if (billOrdering.get(i).getBill_section_order().equals("9")){
				if (orderFoodDetails != null){
					StringBuffer sb = new StringBuffer();
					if (foodBillId != null){
						sb.append("Bill No.:" + foodBillId + "; ");
					}
					sb.append("DateTime:" + outputFormat.format(date));
					Table table2 = new Table(sb.toString(), ";", new int[] { 19, 25 });
					mPrinter.printTable(table2);
					sb = new StringBuffer();
					sb.append("----------------------------------------------\n");
					mPrinter.printText(sb.toString());
					mPrinter.printTable(new Table("Sr; Items; QTY; Rate; Amount", ";", new int[] { 3, 24, 5, 6, 6 }));
					mPrinter.printText(sb.toString());

					for (int j=0; j<orderFoodDetails.size(); j++){
						double amount = Double.parseDouble(orderFoodDetails.get(j).getBill_detail_product_quantity()) * Double.parseDouble(orderFoodDetails.get(j).getBill_detail_product_price());
						String name = databaseHelper.selectByID("tbl_language_text","language_reference_id","PN_"+orderFoodDetails.get(j).getBill_detail_product_id(),"language_text");
						if (name != null){
							mPrinter.printTable(new Table(String.valueOf(j+1) + "; " + name + "; " + orderFoodDetails.get(j).getBill_detail_product_quantity()+"; " + orderFoodDetails.get(j).getBill_detail_product_price()+"; " + padLeft(String.format("%.2f",(amount)), 20), ";", new int[] { 3, 24, 5, 6, 6 }));
						}
					}

					if (foodExtraItem!=null){
						for (int k=0; k<foodExtraItem.size(); k++){
							double amount = Double.parseDouble(foodExtraItem.get(k).getOrder_extra_item_qty()) * Double.parseDouble(foodExtraItem.get(k).getOrder_extra_item_price());
							String name = foodExtraItem.get(k).getOrder_extra_item_name();
							if (name != null){
								mPrinter.printTable(new Table(String.valueOf(orderFoodDetails.size()+k+1) + "; " + name + "; " + foodExtraItem.get(k).getOrder_extra_item_qty()+"; " + foodExtraItem.get(k).getOrder_extra_item_price()+"; " + padLeft(String.format("%.2f",(amount)), 20), ";", new int[] { 3, 24, 5, 6, 6 }));
							}
						}
					}

					mPrinter.printText(sb.toString());
					if (SharedPref.getString(context,"bill_type").equals("grocery")){
						mPrinter.printTable(new Table("; ; ; Total Amount:;"+String.format("%.2f",(foodTotal)), ";", new int[] { 2, 8, 8, 21, 5 }));
					}else{
						mPrinter.printTable(new Table("; ; ; Total Food Amount:;"+String.format("%.2f",(foodTotal)), ";", new int[] { 2, 8, 8, 21, 5 }));
					}
					mPrinter.printText(sb.toString());
				}
			}else if (billOrdering.get(i).getBill_section_order().equals("10")){

				if (barBillId.length() != 0){
					StringBuffer sb = new StringBuffer();
					if (barBillId != null){
						sb.append("Bill No.:" + barBillId + "; ");
					}
					sb.append("DateTime:" + outputFormat.format(date));
					Table table2 = new Table(sb.toString(), ";", new int[] { 19, 25 });
					mPrinter.printTable(table2);

					sb = new StringBuffer();
					sb.append("----------------------------------------------\n");
					mPrinter.printText(sb.toString());

					Table table03 = new Table("Sr; Items; QTY; Rate; Amount", ";", new int[] {  3, 24, 5, 6, 6  });
					mPrinter.printTable(table03);
					mPrinter.printText(sb.toString());
					for (int m=0; m<orderBarDetails.size(); m++){
						double amount = Double.parseDouble(orderBarDetails.get(m).getBill_detail_product_quantity()) * Double.parseDouble(orderBarDetails.get(m).getBill_detail_product_price());
						String name = databaseHelper.selectByID("tbl_language_text","language_reference_id","PN_"+orderBarDetails.get(m).getBill_detail_product_id(),"language_text");
						if (name != null){
							mPrinter.printTable(new Table(
									String.valueOf(m+1) + "; " +
											name + "; " + orderBarDetails.get(m).getBill_detail_product_quantity()+"; " +
											orderBarDetails.get(m).getBill_detail_product_price()+"; " +
											String.format("%.2f",(amount)), ";", new int[] { 3, 24, 5, 6, 6 }));
						}
					}

					if (barExtraItem!=null){
						for (int n=0; n<barExtraItem.size(); n++){
							double amount = Double.parseDouble(barExtraItem.get(n).getOrder_extra_item_qty()) * Double.parseDouble(barExtraItem.get(n).getOrder_extra_item_price());
							String name = barExtraItem.get(n).getOrder_extra_item_name();
							if (name != null){
								mPrinter.printTable(new Table(String.valueOf(orderBarDetails.size()+n+1) + "; " + name + "; " + barExtraItem.get(n).getOrder_extra_item_qty()+"; " + barExtraItem.get(n).getOrder_extra_item_price()+"; " + padLeft(String.format("%.2f",(amount)), 20), ";", new int[] { 3, 24, 5, 6, 6 }));
							}
						}
					}
					mPrinter.printText(sb.toString());

					mPrinter.printTable(new Table("; ; ; Total Bar Amount:;"+String.format("%.2f",(barTotal)), ";", new int[] { 2, 8, 8, 20, 6 }));
					mPrinter.printText(sb.toString());
				}
			}else if (billOrdering.get(i).getBill_section_order().equals("11")){
				StringBuffer sb = new StringBuffer();
				if (billDetails != null){
					if (billDetails.getBill_discount_amount() != null && billDetails.getBill_discount_amount().length()!=0){
						finalDiscount = Double.parseDouble(billDetails.getBill_discount_amount());
					}
					if (billDetails.getBill_tip() != null && billDetails.getBill_tip().length()!=0){
						finalTip = Double.parseDouble(billDetails.getBill_tip());
					}
				}else{
					if (discount != null && discount.length()!=0){
						finalDiscount = Double.parseDouble(discount);
					}
					if (tip != null && tip.length()!=0){
						finalTip = Double.parseDouble(tip);
					}
				}
				grandTotal = (foodTotal+barTotal+finalTip+sgstTotal+cgstTotal)-finalDiscount;

				String discountAmount = String.valueOf(finalDiscount);
				if (discountAmount.length()>6)
					discountAmount = discountAmount.substring(0,5);

				String sgstAmount = String.valueOf(sgstTotal);
				if (sgstAmount.length()>6)
					sgstAmount = sgstAmount.substring(0,5);

				String cgstAmount = String.valueOf(cgstTotal);
				if (cgstAmount.length()>6)
					cgstAmount = cgstAmount.substring(0,5);

				String totalAmount = String.valueOf(grandTotal);
				if (totalAmount.length()>6)
					totalAmount = totalAmount.substring(0,5);

				if (SharedPref.getString(context,"bill_type").equals("grocery")){
					mPrinter.printTable(new Table("Total ;Dis. ;SGST ;CGST ;Delivery ;Payable ", ";", new int[] { 7, 7, 7, 7, 9, 7 }));
					mPrinter.printTable(new Table(String.valueOf(foodTotal)+" ;"+discountAmount+" ;"+String.valueOf(sgstAmount)+" ;"+String.valueOf(cgstAmount)+" ;"+String.valueOf(finalTip)+" ;"+String.valueOf(totalAmount), ";", new int[] { 7, 7, 7, 7, 9, 7 }));
				}else{
					mPrinter.printTable(new Table("Food ;Bar ;SGST ;CGST ;Dis. ;Payable ", ";", new int[] { 8, 8, 7, 7, 7, 7 }));
					mPrinter.printTable(new Table(String.valueOf(foodTotal)+" ;"+String.valueOf(barTotal)+" ;"+String.valueOf(sgstAmount)+" ;"+String.valueOf(cgstAmount)+" ;"+discountAmount+" ;"+String.valueOf(totalAmount), ";", new int[] { 8, 8, 7, 7, 7, 7 }));
				}

				sb = new StringBuffer();
				sb.append("----------------------------------------------\n");
				mPrinter.printText(sb.toString());

			}else if (billOrdering.get(i).getBill_section_order().equals("12")){

			}else if (billOrdering.get(i).getBill_section_order().equals("13")){

			}else if (billOrdering.get(i).getBill_section_order().equals("14")){
				mPrinter.setPrinter(Command.ALIGN, Command.ALIGN_CENTER);
				mPrinter.printText("\n");
				if (billFormatDetails.getBill_special_note_line1() != null && billFormatDetails.getBill_special_note_line1().length() !=0){
					mPrinter.printText(billFormatDetails.getBill_special_note_line1()+"\n");
				}
				if (billFormatDetails.getBill_special_note_line2() != null && billFormatDetails.getBill_special_note_line2().length() !=0){
					mPrinter.printText(billFormatDetails.getBill_special_note_line2()+"\n");
				}
				if (billFormatDetails.getBill_thank_you_line() != null && billFormatDetails.getBill_thank_you_line().length() !=0){
					mPrinter.printText(billFormatDetails.getBill_thank_you_line()+"\n");
				}
			}

		}

		mPrinter.setPrinter(Command.PRINT_AND_WAKE_PAPER_BY_LINE, 3);
		mPrinter.cutPaper();
		return true;
	}

	public static boolean reprintBill(Context context, PrinterInstance mPrinter, String foodBillId, String barBillId) {
		String DATABASE_NAME = SharedPref.getString(context,"database_name");
		DatabaseHelper databaseHelper = new DatabaseHelper(context,DATABASE_NAME);

		ArrayList<OrderExtraItem> foodExtraItem = databaseHelper.BillExtraItem(foodBillId);
		ArrayList<OrderExtraItem> barExtraItem = databaseHelper.BillExtraItem(barBillId);

		DecimalFormat df = new DecimalFormat("#.##");
		BillFormatDetails billFormatDetails = databaseHelper.getBillFormatByOrg(SharedPref.getString(context,"organisation_id"));
		String org_name = databaseHelper.selectByID("tbl_organisation","organisation_id", SharedPref.getString(context,"organisation_id"),"organisation_name");
		String username = databaseHelper.selectByID("tbl_users","user_id",SharedPref.getString(context,"user_id"),"user_name")+"\n";
		SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm a");
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date date = null;
		try { date = inputFormat.parse(new DateConversionClass().currentDateApoch()); } catch (ParseException e) { e.printStackTrace(); }

		mPrinter.init();
		mPrinter.setPrinter(Command.ALIGN, Command.ALIGN_CENTER);
		mPrinter.setCharacterMultiple(1, 1);
		mPrinter.printText(org_name+"\n");
		mPrinter.setCharacterMultiple(0, 0);
		if (billFormatDetails.getBill_gst_no() != null && billFormatDetails.getBill_gst_no().length() != 0){
			mPrinter.printText("GSTIN / UIN : "+billFormatDetails.getBill_gst_no()+"\n");
		}
		if (billFormatDetails.getBill_address_line1() != null && billFormatDetails.getBill_address_line1().length() != 0){
			mPrinter.printText(billFormatDetails.getBill_address_line1()+"\n");
		}
		if (billFormatDetails.getBill_address_line2() != null && billFormatDetails.getBill_address_line2().length() != 0){
			mPrinter.printText(billFormatDetails.getBill_address_line2()+"\n");
		}
		mPrinter.printText("Mobile : "+billFormatDetails.getBill_telephone()+"\n");
		mPrinter.setPrinter(Command.ALIGN, Command.ALIGN_LEFT);

		StringBuffer sb = new StringBuffer();
		if (SharedPref.getString(context,"bill_type").equals("grocery")){
			sb.append("Cashier : " + username + "; ");
			if (username != null){ sb.append(""); }
		}else if (SharedPref.getString(context,"bill_type").equals("parcel")){
			sb.append("Parcel No. : " + SharedPref.getString(context,"table") + "; ");
			if (username != null){ sb.append("Captain : " + username); }
		}else{
			sb.append("Table No. : " + SharedPref.getString(context,"table") + "; ");
			if (username != null){ sb.append("Captain : " + username); }
		}
		Table table1 = new Table(sb.toString(), ";", new int[] { 19, 25 });
		mPrinter.printTable(table1);


		BillDetails billDetails = new BillDetails();
		if (foodBillId.length() !=0){
			billDetails = databaseHelper.getBillDetailsByBillID(foodBillId);
		}else{
			billDetails = databaseHelper.getBillDetailsByBillID(barBillId);
		}

		if (billDetails == null){
			if (foodBillId.length() !=0){
				billDetails = databaseHelper.getBillHeaderByBillID(foodBillId);
			}else{
				billDetails = databaseHelper.getBillHeaderByBillID(barBillId);
			}
		}
		if (billDetails != null) {
			String address = databaseHelper.selectByID("tbl_customer","customer_mobile_no",billDetails.getCustomer_mobile_no(),"customer_address");
			if (billDetails.getCustomer_name() != null && billDetails.getCustomer_name().length() != 0){
				mPrinter.printText("Customer: " + billDetails.getCustomer_name()+"\n");
			}
			if (billDetails.getCustomer_mobile_no() != null && billDetails.getCustomer_mobile_no().length() != 0){
				mPrinter.printText("Mobile No.: " + billDetails.getCustomer_mobile_no()+"\n");
			}
			if (address != null && address.length() != 0){
				mPrinter.printText("Address: " + address+"\n");
			}
		}

		ArrayList<BillDetail> foodBill = databaseHelper.BillDetailsByBillId(foodBillId);
		ArrayList<BillDetail> barBill = databaseHelper.BillDetailsByBillId(barBillId);

		int totalQTY = 0;
		double foodTotal = 0;
		double totalFoodAmount = 0,totalBarAmount = 0;
		if (foodBill != null){
			sb = new StringBuffer();
			if (foodBillId != null){
				sb.append("Bill No.: " + foodBillId + "; ");
			}
			sb.append("DateTime: " + outputFormat.format(date));
			Table table2 = new Table(sb.toString(), ";", new int[] { 19, 25 });
			mPrinter.printTable(table2);
			sb = new StringBuffer();
			sb.append("----------------------------------------------\n");
			mPrinter.printText(sb.toString());
			mPrinter.printTable(new Table("Sr; Name; QTY; Rate; Amount", ";", new int[] { 3, 24, 5, 6, 6 }));
			mPrinter.printText(sb.toString());

			for (int i=0; i<foodBill.size(); i++){
				totalQTY = totalQTY + Integer.valueOf(foodBill.get(i).getBill_detail_product_quantity());
				double amount = Double.parseDouble(foodBill.get(i).getBill_detail_product_quantity()) * Double.parseDouble(foodBill.get(i).getBill_detail_product_price());
				totalFoodAmount = totalFoodAmount + amount;
				foodTotal = foodTotal + amount;
				String name = databaseHelper.selectByID("tbl_language_text","language_reference_id","PN_"+foodBill.get(i).getBill_detail_product_id(),"language_text");
				if (name != null){
					mPrinter.printTable(new Table(String.valueOf(i+1) + "; " + name + "; " + foodBill.get(i).getBill_detail_product_quantity()+"; " + String.valueOf(df.format(Integer.valueOf(foodBill.get(i).getBill_detail_product_price())))+"; " + padLeft(String.valueOf(df.format(amount)), 20), ";", new int[] { 3, 24, 5, 6, 6 }));
				}
			}

			if (foodExtraItem!=null){
				for (int i=0; i<foodExtraItem.size(); i++){
					totalQTY = totalQTY + Integer.valueOf(foodExtraItem.get(i).getOrder_extra_item_qty());
					double amount = Double.parseDouble(foodExtraItem.get(i).getOrder_extra_item_qty()) * Double.parseDouble(foodExtraItem.get(i).getOrder_extra_item_price());
					totalFoodAmount = totalFoodAmount + amount;
					foodTotal = foodTotal + amount;
					String name = foodExtraItem.get(i).getOrder_extra_item_name();
					if (name != null){
						mPrinter.printTable(new Table(String.valueOf(i+1) + "; " + name + "; " + foodExtraItem.get(i).getOrder_extra_item_qty()+"; " + String.valueOf(df.format(Integer.valueOf(foodExtraItem.get(i).getOrder_extra_item_price())))+"; " + padLeft(String.valueOf(df.format(amount)), 20), ";", new int[] { 3, 24, 5, 6, 6 }));
					}
				}
			}

			mPrinter.printText(sb.toString());
			if (SharedPref.getString(context,"bill_type").equals("grocery")){
				mPrinter.printTable(new Table("; ; ; Total Amount : ;"+df.format(foodTotal), ";", new int[] { 2, 8, 8, 20, 6 }));
			}else{
				mPrinter.printTable(new Table("; ; ; Total Food Amount : ;"+df.format(foodTotal), ";", new int[] { 2, 8, 8, 20, 6 }));
			}
			mPrinter.printText(sb.toString());
		}
		double barTotal = 0;
		if (barBill != null){
			sb = new StringBuffer();
			if (barBillId != null){
				sb.append("Bill No.: " + barBillId + "; ");
			}
			sb.append("DateTime: " + outputFormat.format(date));
			Table table2 = new Table(sb.toString(), ";", new int[] { 19, 25 });
			mPrinter.printTable(table2);

			sb = new StringBuffer();
			sb.append("----------------------------------------------\n");
			mPrinter.printText(sb.toString());

			Table table03 = new Table("Sr; Name; QTY; Rate; Amount", ";", new int[] {  3, 24, 5, 6, 6  });
			mPrinter.printTable(table03);
			Table table3 = new Table("; ; ; ; ", ";", new int[] {  3, 24, 5, 6, 6  });
			mPrinter.printText(sb.toString());

			for (int i=0; i<barBill.size(); i++){
				totalQTY = totalQTY + Integer.valueOf(barBill.get(i).getBill_detail_product_quantity());
				double amount = Double.parseDouble(barBill.get(i).getBill_detail_product_quantity()) * Double.parseDouble(barBill.get(i).getBill_detail_product_price());
				totalBarAmount = totalBarAmount + amount;
				barTotal = barTotal + amount;
				String name = databaseHelper.selectByID("tbl_language_text","language_reference_id","PN_"+barBill.get(i).getBill_detail_product_id(),"language_text");
				if (name != null){
					table3.addRow(String.valueOf(i+1) + "; " + name + "; " + barBill.get(i).getBill_detail_product_quantity()+"; " + String.valueOf(df.format(Integer.valueOf(barBill.get(i).getBill_detail_product_price())))+"; " + String.valueOf(df.format(amount)));
				}
			}

			if (barExtraItem!=null){
				for (int i=0; i<barExtraItem.size(); i++){
					totalQTY = totalQTY + Integer.valueOf(barExtraItem.get(i).getOrder_extra_item_qty());
					double amount = Double.parseDouble(barExtraItem.get(i).getOrder_extra_item_qty()) * Double.parseDouble(barExtraItem.get(i).getOrder_extra_item_price());
					totalFoodAmount = totalFoodAmount + amount;
					barTotal = barTotal + amount;
					String name = barExtraItem.get(i).getOrder_extra_item_name();
					if (name != null){
						mPrinter.printTable(new Table(String.valueOf(i+1) + "; " + name + "; " + barExtraItem.get(i).getOrder_extra_item_qty()+"; " + String.valueOf(df.format(Integer.valueOf(barExtraItem.get(i).getOrder_extra_item_price())))+"; " + padLeft(String.valueOf(df.format(amount)), 20), ";", new int[] { 3, 24, 5, 6, 6 }));
					}
				}
			}

			mPrinter.printTable(table3);
			mPrinter.printText(sb.toString());

			mPrinter.printTable(new Table("; ; ; Total Bar Amount : ;"+df.format(barTotal), ";", new int[] { 2, 8, 8, 20, 6 }));
			mPrinter.printText(sb.toString());
		}

		if (SharedPref.getString(context,"bill_type").equals("grocery")){
			mPrinter.printTable(new Table("Total ;Discount ;Delivery Charges ;Grand Total ", ";", new int[] { 10, 10, 14, 10 }));

			if (billDetails != null){
				double discount = Double.parseDouble(billDetails.getBill_discount_amount());
				double tip = Double.parseDouble(billDetails.getBill_tip());
				double grandTotal = (totalFoodAmount+totalBarAmount+tip)-discount;
				mPrinter.printTable(new Table(String.valueOf(foodTotal)+" ;"+String.valueOf(discount)+" ;"+String.valueOf(tip)+" ;"+String.valueOf(grandTotal), ";", new int[] { 10, 10, 14, 10 }));
			}else{
				mPrinter.printTable(new Table(String.valueOf(foodTotal)+" ;0 ;0 ;0", ";", new int[] { 10, 10, 14, 10 }));
			}
		}else{
			mPrinter.printTable(new Table("Food ;Bar ;Discount ;Total ", ";", new int[] { 11, 11, 11, 11 }));

			if (billDetails != null){
				double discount = Double.parseDouble(billDetails.getBill_discount_amount());
				double tip = Double.parseDouble(billDetails.getBill_tip());
				double grandTotal = (totalFoodAmount+totalBarAmount+tip)-discount;
				mPrinter.printTable(new Table(String.valueOf(foodTotal)+" ;"+String.valueOf(barTotal)+" ;"+String.valueOf(discount)+" ;"+String.valueOf(grandTotal), ";", new int[] { 11, 11, 11, 11 }));
			}else{
				mPrinter.printTable(new Table(String.valueOf(foodTotal)+" ;"+String.valueOf(barTotal)+" ;0 ;0 ", ";", new int[] { 11, 11, 11, 11 }));
			}

//			mPrinter.printTable(new Table("Food ;Bar ;Discount ;Tip ;Total ", ";", new int[] { 9, 9, 9, 8, 9 }));
//
//			if (billDetails != null){
//				double discount = Double.parseDouble(billDetails.getBill_discount_amount());
//				double tip = Double.parseDouble(billDetails.getBill_tip());
//				double grandTotal = (totalFoodAmount+totalBarAmount+tip)-discount;
//				mPrinter.printTable(new Table(String.valueOf(foodTotal)+" ;"+String.valueOf(barTotal)+" ;"+String.valueOf(discount)+" ;"+String.valueOf(tip)+" ;"+String.valueOf(grandTotal), ";", new int[] { 9, 9, 9, 8, 9 }));
//			}else{
//				mPrinter.printTable(new Table(String.valueOf(foodTotal)+" ;"+String.valueOf(barTotal)+" ;0 ;0 ;0", ";", new int[] { 9, 9, 9, 8, 9 }));
//			}
		}

		//payment details
		sb = new StringBuffer();
		sb.append("----------------------------------------------\n");
		mPrinter.printText(sb.toString());
//		mPrinter.printTable(new Table(" Payment Mode; Payment Amount", ";", new int[] { 22,22 }));
//		mPrinter.printText(sb.toString());
//
//		ArrayList<BillPaymentDetails> multiplePayment = databaseHelper.getOrderBillPayments(foodBillId+"_"+barBillId);
//		ArrayList<BillPaymentDetails> foodPayment = databaseHelper.getOrderBillPayments(foodBillId+"_");
//		ArrayList<BillPaymentDetails> barPayment = databaseHelper.getOrderBillPayments("_"+barBillId);
//
//		ArrayList<BillPaymentDetails> billPaymentDetails = new ArrayList<>();
//
//		if (foodPayment != null && foodPayment.size() != 0){
//			for (int i=0; i<foodPayment.size(); i++){ billPaymentDetails.add(foodPayment.get(i)); }
//		}
//		if (multiplePayment != null && multiplePayment.size() != 0){
//			if (foodBillId.length() != 0 && barBillId.length() != 0){
//				for (int i=0; i<multiplePayment.size(); i++){ billPaymentDetails.add(multiplePayment.get(i)); }
//			}
//		}
//		if (barPayment != null && barPayment.size() != 0){
//			for (int i=0; i<barPayment.size(); i++){ billPaymentDetails.add(barPayment.get(i)); }
//		}
//		double totalPaid = 0;
//		for (int i=0; i<billPaymentDetails.size(); i++){
//			double amount = Double.parseDouble(billPaymentDetails.get(i).getBill_paid_amount());
//			totalPaid = totalPaid + amount;
//			mPrinter.printTable(new Table(billPaymentDetails.get(i).getBill_payment_mode() +" ;" + String.valueOf(df.format(amount)), ";", new int[] { 22,22 }));
//		}
//
//		mPrinter.printText(sb.toString());
//
//		mPrinter.printTable(new Table("; Total Paid Amount : ;"+String.valueOf(df.format(totalPaid)), ";", new int[] { 18, 20, 6 }));
//		mPrinter.printText(sb.toString());

		//payment detail end

		mPrinter.setPrinter(Command.ALIGN, Command.ALIGN_CENTER);
		mPrinter.printText("\n");
		if (billFormatDetails.getBill_special_note_line1() != null && billFormatDetails.getBill_special_note_line1().length() !=0){
			mPrinter.printText(billFormatDetails.getBill_special_note_line1()+"\n");
		}
		if (billFormatDetails.getBill_special_note_line2() != null && billFormatDetails.getBill_special_note_line2().length() !=0){
			mPrinter.printText(billFormatDetails.getBill_special_note_line2()+"\n");
		}
		if (billFormatDetails.getBill_thank_you_line() != null && billFormatDetails.getBill_thank_you_line().length() !=0){
			mPrinter.printText(billFormatDetails.getBill_thank_you_line()+"\n");
		}
		mPrinter.setPrinter(Command.PRINT_AND_WAKE_PAPER_BY_LINE, 3);
		mPrinter.cutPaper();
		return true;
	}

}