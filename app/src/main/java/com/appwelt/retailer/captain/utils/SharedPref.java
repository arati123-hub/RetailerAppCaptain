package com.appwelt.retailer.captain.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    public static SharedPreferences prefs;

    private static Context context = null;

    public static void setApplicationContext(Context cxt) {
        if (context == null)
            context = cxt;
    }

    public static Context getApplicationContext()
    {
        return context;
    }


    public static void putBoolean(Context ctx, String key, boolean val) {
        prefs = ctx.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        prefs.edit().putBoolean(key, val).commit();

    }

    public static boolean getBoolean(Context ctx, String key, boolean val) {
        prefs = ctx.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        return prefs.getBoolean(key, false);
    }

    public static void putInt(Context ctx, String key, int score) {
        prefs = ctx.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        prefs.edit().putInt(key, score).commit();

    }

    public static int getInt(Context ctx, String key) {
        prefs = ctx.getSharedPreferences("Cache", Context.MODE_PRIVATE);

        return prefs.getInt(key, 0);
    }

    public static void putString(Context ctx, String key, String score) {
        prefs = ctx.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        prefs.edit().putString(key, score).commit();

    }

    public static String getString(Context ctx, String key) {
        prefs = ctx.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        return prefs.getString(key, "");
    }
    public static void putString(Context ctx, String key) {
        prefs = ctx.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        prefs.edit().putString(key,null).commit();

    }

}
