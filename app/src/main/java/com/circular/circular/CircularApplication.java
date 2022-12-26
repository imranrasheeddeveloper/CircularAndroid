package com.circular.circular;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import com.google.firebase.messaging.Constants;
import com.google.firebase.messaging.FirebaseMessaging;

public class CircularApplication extends Application {
    public static Typeface mTfMainBold;
    public static Typeface mTfMainRegular;
    public static Context applicationContext = null;

    @Override
    public void onCreate(){
        super.onCreate();

        if (applicationContext == null) {
            applicationContext = this;
        }
        mTfMainBold = Typeface.createFromAsset(getAssets(), "fontmain_bold.otf");
        mTfMainRegular = Typeface.createFromAsset(getAssets(), "fontmain_regular.otf");

        getDeviceToken();

    }

    private void getDeviceToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e("FCM TOKEN", "onComplete: Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    // Get new FCM registration token
                    Constant.DEVICE_KEY = task.getResult();
                    Log.e("FCM TOKEN", Constant.DEVICE_KEY);
                });
    }
}
