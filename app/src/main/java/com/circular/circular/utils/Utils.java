package com.circular.circular.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.WindowManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static void setDialogWidth(Dialog dlg, float fRate){
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dlg.getWindow().getAttributes());
        layoutParams.width = (int)(layoutParams.width * fRate);
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dlg.getWindow().setAttributes(layoutParams);
    }

    public static void setDialogWidth(Dialog dlg, float fRate, Context ctx){
        int width = (int)(ctx.getResources().getDisplayMetrics().widthPixels * fRate); //<-- int width=400;
        int height = (int)(ctx.getResources().getDisplayMetrics().heightPixels*0.50);//<-- int height =300;
        dlg.getWindow().setLayout(width, height);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dlg.getWindow().getAttributes());
        layoutParams.width = width;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dlg.getWindow().setAttributes(layoutParams);
    }

    public static String formatDate(String earningPeriod) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(earningPeriod);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}
