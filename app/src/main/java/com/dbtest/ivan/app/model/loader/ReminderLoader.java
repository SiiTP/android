package com.dbtest.ivan.app.model.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.dbtest.ivan.app.logic.db.OrmHelper;
import com.dbtest.ivan.app.logic.db.entities.Reminder;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReminderLoader extends AsyncTaskLoader<ArrayList<Reminder>> {
    public static final int LOADER_REMINDER_ID = 1; //todo maybe should be in resources

    private OrmHelper mOrmHelper;

    public ReminderLoader(Context context) {
        super(context);
        mOrmHelper = new OrmHelper(context);
    }

    @Override
    public ArrayList<Reminder> loadInBackground() {
        Log.d("myapp", "reminder loader do in background");
        Dao<Reminder, Long> reminderDao = mOrmHelper.getReminderDao();
//        try {
////            Reminder reminder = new Reminder("11.11.2012 10:11", "from db");
////            reminder.setAuthor("My");
////            reminderDao.create(reminder);//todo delete this
////            Log.d("myapp", "id of reminder : " + reminder.getId());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        List<Reminder> reminders = null;
        try {
            reminders = reminderDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (ArrayList<Reminder>)reminders;
    }
}
