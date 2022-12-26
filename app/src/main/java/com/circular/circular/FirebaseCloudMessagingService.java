package com.circular.circular;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

public class FirebaseCloudMessagingService extends FirebaseMessagingService {

    public static final MutableLiveData<String> deviceToken = new MutableLiveData<>();
    private static final String TAG = "FirebaseCloudMessagingS";

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        if (!s.isEmpty()) {
            deviceToken.postValue(s);
            Log.e(TAG, "onNewToken: " + s);
        } else {
            Log.e(TAG, "onNewToken: no new token");
        }
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e(TAG, "From: " + remoteMessage.getFrom());


        if (remoteMessage.getData().size() > 0) {
            final String notiType = remoteMessage.getData().get("type");
            Log.e(TAG, "Data Payload: " + notiType);

            if (notiType != null) {

            }

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleDataMessage(JSONObject json) {
    }


}
