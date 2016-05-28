package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.dbtest.ivan.app.logic.db.OrmHelper;
import com.dbtest.ivan.app.logic.db.entities.Reminder;
import com.dbtest.ivan.app.receiver.CustomReceiver;
import com.dbtest.ivan.app.utils.ExtrasCodes;
import com.dbtest.ivan.app.utils.ReminderAlarmManager;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class ReminderDeleteService extends IntentService {
    public static final String SUCCESS_MSG = "reminder delete success";

    public ReminderDeleteService() {
        super("reminder delete service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Long reminderId = intent.getLongExtra(ExtrasCodes.REMINDER_ID_KEY, -1);
        try {
            OrmHelper ormHelper = new OrmHelper(this);
            Dao<Reminder, Long> reminderDao = ormHelper.getReminderDao();
            Reminder reminder = reminderDao.queryForId(reminderId);
            ReminderAlarmManager.clearAlarm(this,reminderId.intValue());
            int rowsDeleted = reminderDao.delete(reminder);
            Log.i("myapp" + this.getClass().getSimpleName(), "rows reminder deleted : " + rowsDeleted);
            OpenHelperManager.releaseHelper();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Intent intentWaiting = new Intent(CustomReceiver.WAITING_ACTION);
        intentWaiting.addCategory(Intent.CATEGORY_DEFAULT);
        intentWaiting.putExtra(CustomReceiver.RESULT, ReminderDeleteService.SUCCESS_MSG);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intentWaiting);
    }
}
