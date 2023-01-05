package com.circular.circular.local;

import android.util.Log;

import com.circular.circular.CircularApplication;
import com.circular.circular.model.User;
import com.circular.circular.model.data_points.DataPointsItem;

import java.util.ArrayList;
import java.util.List;

public class TinyDbManager {


    public static final String KEY_USER = "key_user";
    public static final String KEY_PASSWORD = "key_password";
    private static final String KEY_DATA_POINTS = "key_data_point";
    private static final String KEY_USER_TYPE = "key_user_type";


    public static void saveUserData(User data) {
        TinyDB tinyDB = new TinyDB(CircularApplication.applicationContext);
        tinyDB.putObject(KEY_USER, data);
        Log.e("USER", "user information saved !");
    }

    public static User getUserInformation() {
        TinyDB tinyDB = new TinyDB(CircularApplication.applicationContext);
        return tinyDB.getObject(KEY_USER, User.class);
    }

    public static void clearUser() {
        TinyDB tinyDB = new TinyDB(CircularApplication.applicationContext);
        tinyDB.remove(KEY_USER);
    }

    public static void saveUserType(String data) {
        TinyDB tinyDB = new TinyDB(CircularApplication.applicationContext);
        tinyDB.putString(KEY_USER_TYPE, data);
        Log.e("user_type", " saved !");
    }

    public static String getUserType() {
        TinyDB tinyDB = new TinyDB(CircularApplication.applicationContext);
        return tinyDB.getString(KEY_USER_TYPE);
    }


    public static void saveDataPoints(DataPointsItem selected_data_points) {
        TinyDB tinyDB = new TinyDB(CircularApplication.applicationContext);
        ArrayList<Object> previousItems = tinyDB.getListObject(KEY_DATA_POINTS, DataPointsItem.class);
        previousItems.add(selected_data_points);
        tinyDB.putListObject(KEY_DATA_POINTS, previousItems);
    }

    public static List<DataPointsItem> getSelectedDatPoints() {

        TinyDB tinyDB = new TinyDB(CircularApplication.applicationContext);
        ArrayList<Object> items = tinyDB.getListObject(KEY_DATA_POINTS, DataPointsItem.class);
        ArrayList<DataPointsItem> Items = new ArrayList<>();
        ;

        if (!items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {

                DataPointsItem item = (DataPointsItem) items.get(i);
                Items.add(item);
            }
        }
        return Items;
    }
}
