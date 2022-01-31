package com.circular.circular;

import android.app.Application;
import android.graphics.Typeface;

public class CircularApplication extends Application {
    public static Typeface mTfMainBold;
    public static Typeface mTfMainRegular;

    @Override
    public void onCreate(){
        super.onCreate();
        mTfMainBold = Typeface.createFromAsset(getAssets(), "fontmain_bold.otf");
        mTfMainRegular = Typeface.createFromAsset(getAssets(), "fontmain_regular.otf");
    }
}
