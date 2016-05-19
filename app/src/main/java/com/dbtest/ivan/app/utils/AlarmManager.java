package com.dbtest.ivan.app.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dbtest.ivan.app.receiver.AlarmReceiver;

import java.util.Date;

public class AlarmManager {
    public static void setAlarm(Context context, int idReminder, long timeInMills) {
        android.app.AlarmManager am = (android.app.AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(context, AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.KEY_ID_REMINDER, idReminder);
        PendingIntent pi = PendingIntent.getBroadcast(context, idReminder, intent, PendingIntent.FLAG_NO_CREATE);

        if (new Date().getTime() < timeInMills) {
            if  (pi == null) {
                pi = PendingIntent.getBroadcast(context, idReminder, intent, 0);
                am.set(android.app.AlarmManager.RTC_WAKEUP, timeInMills, pi);
                Log.i("myapp AlarmReceiver", "alarm setted, id : " + idReminder + " time mills : " + timeInMills + " time now : " + new Date().getTime());
            } else {
                Log.i("myapp AlarmReceiver", "alarm already exists, id : " + idReminder  + " time mills : " + timeInMills + " time now : " + new Date().getTime());
            }
        } else {
            Log.i("myapp AlarmReceiver", "date passed, id : " + idReminder  + " time mills : " + timeInMills + " time now : " + new Date().getTime());

        }
    }

    public static void clearAlarm(Context context, int idReminder) {
        android.app.AlarmManager am = (android.app.AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(context, AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.KEY_ID_REMINDER, idReminder);
        PendingIntent pi = PendingIntent.getBroadcast(context, idReminder, intent, PendingIntent.FLAG_NO_CREATE);
        am.cancel(pi);
    }
}
