package com.appwelt.retailer.captain.utils;

import android.content.Context;
import android.graphics.Typeface;

public class FontStyle {
    private static Typeface font_Light=null;
    private static Typeface font_Regular=null;
    private static Context context = null;


    public static void FontStyle(Context cxt)
    {
        if (context == null)
            context = cxt;
    }
    public static Typeface getFontLight() {
        font_Light = Typeface.createFromAsset(context.getAssets(), "Font Bureau - Interstate-Light.otf");
        return font_Light;
    }
    public static Typeface getFontRegular() {
        font_Regular = Typeface.createFromAsset(context.getAssets(), "Font Bureau - Interstate-Regular.otf");
        return font_Regular;
    }
}
