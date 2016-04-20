package com.dbtest.ivan.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import android.support.v4.content.LocalBroadcastManager;
import android.preference.PreferenceManager;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.list_activity.ListActivity;
import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.logic.db.OrmHelper;
import com.dbtest.ivan.app.logic.db.entities.Category;
import com.dbtest.ivan.app.logic.db.entities.Reminder;
import com.dbtest.ivan.app.receiver.AlarmReceiver;
import com.dbtest.ivan.app.receiver.CustomReceiver;
import com.dbtest.ivan.app.utils.AlarmManager;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StartActivity extends Activity {
    private OrmHelper mOrmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        RetrofitFactory.setSession(preferences.getString(RetrofitFactory.SESSION_COOKIE_NAME, null));
        RetrofitFactory.setServerURI(preferences.getString(RetrofitFactory.URI_NAME, null));// use preferences URI from settings

        mOrmHelper = new OrmHelper(this);
        Dao<Category, Long> categoryDao = mOrmHelper.getCategoryDao();

        try {
            List<Category> categories;
            categories = categoryDao.queryForAll();
            if (categories.isEmpty()) {
                categoryDao.create(new Category("all"));
                categoryDao.create(new Category("from friends"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setAlarms();

        final Handler handler = new Handler();
        handler.postDelayed(this::goToList, 2000);
    }

    private void goToList() {
        Intent  intent = new Intent(this, ListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        OpenHelperManager.releaseHelper();
        super.onDestroy();
    }

    public void setAlarms() {
        IntentFilter filter = new IntentFilter(CustomReceiver.WAITING_ACTION);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        AlarmReceiver alarmReceiver = new AlarmReceiver();
        LocalBroadcastManager.getInstance(StartActivity.this).registerReceiver(alarmReceiver, filter);

        Dao<Reminder, Long> reminderDao = mOrmHelper.getReminderDao();
        List<Reminder> reminders = new ArrayList<>();
        try {
            reminders = reminderDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!reminders.isEmpty()) {
            for (Reminder reminder : reminders) {
                int idRem = (int)(long)reminder.getId(); //TODO unsafe parse long to int
                long timeInMills = reminder.getReminderTime().getTime();
                AlarmManager.setAlarm(this, idRem, timeInMills);
            }
        }
    }
}
