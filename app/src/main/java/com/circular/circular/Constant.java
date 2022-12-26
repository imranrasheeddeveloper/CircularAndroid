package com.circular.circular;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constant {
    public static final String BASE_URL = "https://staging.circulartestserver.com.ng/public/api/";
    public static final String IMG_PATH = "https://staging.circulartestserver.com.ng/public/images/";
    public static final String SH_KEY_SELECTED_INDUSTRY = "selected_industry";
    public static final String SH_KEY_SELECTED_TEAM_SIZE = "selected_team_size";
    public static final String SH_KEY_SELECTED_BUDGET = "selected_budget";
    public static final String SH_KEY_SELECTED_IMPACTS = "selected_impacts";
    public static final String SH_KEY_SELECTED_REPORT_DATA_FIELDS = "selected_report_data_fields";
    public static final String PROFILE_ACTIVITY_STARTUP_FRAG = "profile_activity_startup_frag";
    public static String DEVICE_KEY = "device_key";
    public static final String UPDATE_PROFILE_STARTUP_ACTION = "update_profile_startup_action";
    public static final int UPDATE_PROFILE_STARTUP_ACTION_REPORT_DATA = 5;
    public  static Constant constant = new Constant();

    public  String getDate(String data) {
        String date;
        String[] date_value = data.split("T");
        date = date_value[0];
        String formatted_date = formatDate(date);
        formatted_date = formatted_date.replaceAll("-", " ");
        return formatted_date;
    }

    public  String getTime(String data) {
        String time;
        String[] time_value = data.split("T");
        time = time_value[1].substring(0, Math.min(time_value[1].length(), 5));
        return time;
    }

    public String formatDate(String earningPeriod) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd MMM, yyyy";
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
