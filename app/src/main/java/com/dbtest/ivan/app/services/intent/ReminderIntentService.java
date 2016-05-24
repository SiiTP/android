package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.dbtest.ivan.app.logic.db.OrmHelper;
import com.dbtest.ivan.app.logic.db.entities.Category;
import com.dbtest.ivan.app.logic.db.entities.Reminder;
import com.dbtest.ivan.app.receiver.CustomReceiver;
import com.dbtest.ivan.app.utils.ReminderAlarmManager;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public class ReminderIntentService extends IntentService {
    public static final String TIME = "TIME";
    public static final String TEXT = "TEXT";
    public static final String CATEGORY = "CATEGORY";
    public static final String ID = "ID";
    public static final long CREATE_ID = -1;
    public ReminderIntentService() {
        super("reminder intent service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        Long id = bundle.getLong(ID);
        String text = bundle.getString(TEXT);
        Long time = bundle.getLong(TIME);
        String categoryName = bundle.getString(CATEGORY);
        Date date = null;

        OrmHelper helper = OpenHelperManager.getHelper(this, OrmHelper.class);
        Dao<Reminder,Long> reminderDao = helper.getReminderDao();
        Dao<Category,Long> categoryDao = helper.getCategoryDao();
        Reminder reminder;
        try {
            if(time != 0) {
                date = new Date(time);
            }
            if(id != CREATE_ID){
                reminder = reminderDao.queryForId(id);
                if(text != null && !text.isEmpty()){
                    reminder.setText(text);
                }
                if(date != null){
                    reminder.setReminderTime(date);
                }
            }else{
                reminder = new Reminder(date,text);
            }
            Category category = getCategory(categoryDao, categoryName);
            if(category == null){
                category = createCategory(categoryDao,categoryName);
            }
            reminder.setCategory(category);
            if(id != CREATE_ID){
                reminder.setIsSynced(false);
                reminderDao.update(reminder);
            } else {
                reminder.setAuthor("ME");
                reminderDao.create(reminder);
            }
            int idReminder = (int) (long) reminder.getId();
            ReminderAlarmManager.setAlarm(this.getApplicationContext(), idReminder, reminder.getReminderTime().getTime());
            Intent sync = new Intent(this,SynchronizeIntentService.class);
            startService(sync);

            Bundle answer = new Bundle();
            answer.putString(CustomReceiver.RESULT,"Reminder created");
            try { //TODO зачем это? Ответ: чтобы показать прогрес бар, на локалхосте взаимодействие с сервером очень быстрое
                Thread.sleep(1500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent activityNotify = new Intent(CustomReceiver.WAITING_ACTION);
            activityNotify.addCategory(Intent.CATEGORY_DEFAULT);
            activityNotify.putExtras(answer);
            LocalBroadcastManager.getInstance(ReminderIntentService.this).sendBroadcast(activityNotify);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        OpenHelperManager.releaseHelper();
    }
    @Nullable
    private Category getCategory(Dao<Category,Long> dao,String categoryName) throws SQLException, IOException {
        List<Category> categoryList = dao.queryForEq("name", categoryName);
        Category category = null;
        if (categoryList.size() != 0) {
            category = categoryList.get(0);
        }
        return category;
    }
    protected Category createCategory(Dao<Category,Long> dao,String categoryName) throws SQLException, IOException {
        Category category;
        category = new Category(categoryName);
        dao.create(category);
        return category;
    }
}
