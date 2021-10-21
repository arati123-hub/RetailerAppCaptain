package com.appwelt.retailer.captain.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConversionClass {
    public String currentDateApoch() {
        String myDate = "";
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            String str = dateFormat.format(cal.getTime());

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = df.parse(str);
            long epoch = date.getTime();
            myDate = String.valueOf(epoch);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return myDate;
    }
    public String dateToApoch(String x) {
        String myDate = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = df.parse(x);
            long epoch = date.getTime();
            myDate = String.valueOf(epoch);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return myDate;
    }
    public String apochToDate(String x) {
        Date date = new Date( Long.parseLong(x) );
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String myDate = format.format(date);
        return myDate;
    }
}
