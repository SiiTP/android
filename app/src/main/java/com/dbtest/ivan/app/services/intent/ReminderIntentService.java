package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dbtest.ivan.app.logic.db.OrmHelper;
import com.dbtest.ivan.app.logic.db.entities.Category;
import com.dbtest.ivan.app.logic.db.entities.Reminder;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ivan on 02.04.16.
 */
public class ReminderIntentService extends IntentService {
    public static final String AUTHOR = "AUTHOR";
    public static final String TIME = "TIME";
    public static final String TEXT = "TEXT";
    public static final String CATEGORY = "CATEGORY";
    public static final String ID = "ID";
    public static final String ID_USER = "ID_USER";
    public ReminderIntentService() {
        super("reminder intent service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        Long id = bundle.getLong(ID);
        Long idUser = bundle.getLong(ID_USER);
        String author = bundle.getString(AUTHOR);
        String text = bundle.getString(TEXT);
        String time = bundle.getString(TIME);
        String categoryName = bundle.getString(CATEGORY);
        Date date = null;

        OrmHelper helper = OpenHelperManager.getHelper(this, OrmHelper.class);
        Dao<Reminder,Long> reminderDao = helper.getReminderDao();
        Dao<Category,Long> categoryDao = helper.getCategoryDao();
        Reminder reminder;
        try {
            if(time != null && !time.isEmpty()) {
                date = new SimpleDateFormat("yyyyMMdd").parse(time);
            }

            if(id != -1){
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
            if(id != -1){
                reminder.setIsSynced(false);
                reminderDao.update(reminder);
            } else {
                reminder.setAuthor("ME");
                reminderDao.create(reminder);
            }
            Intent sync = new Intent(this,SynchronizeIntentService.class);
            startService(sync);
        } catch (SQLException | IOException | ParseException e) {
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
    private Category createCategory(Dao<Category,Long> dao,String categoryName) throws SQLException, IOException {
        Category category;
        category = new Category(categoryName);
        dao.create(category);
        return category;
    }
}
