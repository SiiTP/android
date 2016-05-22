package com.dbtest.ivan.app.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.dbtest.ivan.app.logic.db.OrmHelper;
import com.dbtest.ivan.app.logic.db.entities.Reminder;
import com.dbtest.ivan.app.receiver.AlarmReceiver;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Date;

public class ReminderAlarmManager {
    public static void setAlarm(Context context, int idReminder, long timeInMills) {
        new RegisterReminderAsyncTask(context, timeInMills).execute(idReminder);
    }

    public static void clearAlarm(Context context, int idReminder) {
        android.app.AlarmManager am = (android.app.AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.KEY_ID_REMINDER, idReminder);
        PendingIntent pi = PendingIntent.getBroadcast(context, idReminder, intent, PendingIntent.FLAG_NO_CREATE);
        am.cancel(pi);
    }

    public static class RegisterReminderAsyncTask extends AsyncTask<Integer, Integer, Reminder> {
        private Context context;
        private Intent intentForPendingIntent;
        private long timeInMills;
        private AlarmManager alarmManager;

        public RegisterReminderAsyncTask(Context context, long timeInMills) {
            AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intentForPendingIntent = new Intent(context, AlarmReceiver.class);
            this.context = context;
            this.intentForPendingIntent = intentForPendingIntent;
            this.timeInMills = timeInMills;
            this.alarmManager = am;
        }

        @Override
        protected Reminder doInBackground(Integer... params) {
            OrmHelper ormHelper = new OrmHelper(this.context);
            Dao<Reminder, Long> reminderDao = ormHelper.getReminderDao();
            Reminder reminder;
            try {
                reminder = reminderDao.queryForId(Long.valueOf(params[0]));
            } catch (SQLException e) {
                reminder = null;
            }
            return reminder;
        }

        @Override
        protected void onPostExecute(Reminder reminder) {
            super.onPostExecute(reminder);

            int idReminder =  (int)(long)reminder.getId();
            PendingIntent pi = PendingIntent.getBroadcast(context, idReminder, intentForPendingIntent, PendingIntent.FLAG_NO_CREATE);

            if (new Date().getTime() < timeInMills) {
                if  (pi == null) {
                    intentForPendingIntent.putExtra(AlarmReceiver.KEY_ID_REMINDER, idReminder);
                    intentForPendingIntent.putExtra(AlarmReceiver.KEY_TEXT_REMINDER, reminder.getText());
                    intentForPendingIntent.putExtra(AlarmReceiver.KEY_CATEGORY_REMINDER, reminder.getCategory().getName());
                    pi = PendingIntent.getBroadcast(context, idReminder, intentForPendingIntent, 0);
                    this.alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, timeInMills, pi);

                    Log.i("myapp AlarmReceiver", "alarm setted, id : " + idReminder + " time mills : " + timeInMills + " time now : " + new Date().getTime());
                } else {
                    //TODO переинициализировать с новыми данными... наверное
                    Log.i("myapp AlarmReceiver", "alarm already exists, id : " + idReminder  + " time mills : " + timeInMills + " time now : " + new Date().getTime());
                }
            } else {
                Log.i("myapp AlarmReceiver", "date passed, id : " + idReminder  + " time mills : " + timeInMills + " time now : " + new Date().getTime());

            }
        }
    }
}
