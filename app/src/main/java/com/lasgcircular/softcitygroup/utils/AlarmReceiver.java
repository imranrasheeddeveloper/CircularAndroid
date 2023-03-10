package com.lasgcircular.softcitygroup.utils;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;

import com.lasgcircular.softcitygroup.MainActivity;
import com.lasgcircular.softcitygroup.R;

public class AlarmReceiver extends WakefulBroadcastReceiver {
    AlarmManager mAlarmManager;
    PendingIntent mPendingIntent;

    @Override
    public void onReceive(Context context, Intent intent) {


        // Create intent to open ReminderEditActivity on notification click
        Intent editIntent = new Intent(context, MainActivity.class);
        PendingIntent mClick = PendingIntent.getActivity(context, 0, editIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create Notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.drawable.logo_2)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setTicker("Ticker")
                .setContentText("Content Text")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(mClick)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true);

        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(0, mBuilder.build());
    }

//    public void setAlarm(Context context, Calendar calendar, int ID) {
//        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//        // Put Reminder ID in Intent Extra
//        Intent intent = new Intent(context, AlarmReceiver.class);
//        intent.putExtra(ReminderEditActivity.EXTRA_REMINDER_ID, Integer.toString(ID));
//        mPendingIntent = PendingIntent.getBroadcast(context, ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//
//        // Calculate notification time
//        Calendar c = Calendar.getInstance();
//        long currentTime = c.getTimeInMillis();
//        long diffTime = calendar.getTimeInMillis() - currentTime;
//
//        // Start alarm using notification time
//        mAlarmManager.set(AlarmManager.ELAPSED_REALTIME,
//                SystemClock.elapsedRealtime() + diffTime,
//                mPendingIntent);
//
//        // Restart alarm if device is rebooted
//        ComponentName receiver = new ComponentName(context, BootReceiver.class);
//        PackageManager pm = context.getPackageManager();
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP);
//    }
//
//    public void setRepeatAlarm(Context context, Calendar calendar, int ID, long RepeatTime) {
//        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//        // Put Reminder ID in Intent Extra
//        Intent intent = new Intent(context, AlarmReceiver.class);
//        intent.putExtra(ReminderEditActivity.EXTRA_REMINDER_ID, Integer.toString(ID));
//        mPendingIntent = PendingIntent.getBroadcast(context, ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//
//        // Calculate notification timein
//        Calendar c = Calendar.getInstance();
//        long currentTime = c.getTimeInMillis();
//        long diffTime = calendar.getTimeInMillis() - currentTime;
//
//        // Start alarm using initial notification time and repeat interval time
//        mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
//                SystemClock.elapsedRealtime() + diffTime,
//                RepeatTime , mPendingIntent);
//
//        // Restart alarm if device is rebooted
//        ComponentName receiver = new ComponentName(context, BootReceiver.class);
//        PackageManager pm = context.getPackageManager();
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP);
//    }
//
//    public void cancelAlarm(Context context, int ID) {
//        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//        // Cancel Alarm using Reminder ID
//        mPendingIntent = PendingIntent.getBroadcast(context, ID, new Intent(context, AlarmReceiver.class), 0);
//        mAlarmManager.cancel(mPendingIntent);
//
//        // Disable alarm
//        ComponentName receiver = new ComponentName(context, BootReceiver.class);
//        PackageManager pm = context.getPackageManager();
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                PackageManager.DONT_KILL_APP);
//    }
}

