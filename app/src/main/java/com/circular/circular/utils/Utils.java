package com.circular.circular.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;

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
}
