package com.dbtest.ivan.app.model.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.dbtest.ivan.app.logic.db.OrmHelper;
import com.dbtest.ivan.app.logic.db.entities.Category;
import com.dbtest.ivan.app.logic.db.entities.Reminder;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReminderLoader extends AsyncTaskLoader<ArrayList<Reminder>> {

    private OrmHelper ormHelper;

    private String categoryLoaded;

    public ReminderLoader(Context context) {
        super(context);
        ormHelper = new OrmHelper(context);
        categoryLoaded = Category.CATEGORY_ALL_NAME;
    }

    @Override
    public ArrayList<Reminder> loadInBackground() {
        Log.d("myapp", "reminder loader do in background");
        Dao<Reminder, Long> reminderDao = ormHelper.getReminderDao();
        Dao<Category, Long> categoryDao = ormHelper.getCategoryDao();

        List<Reminder> reminders = new ArrayList<>();
        try {
            if (categoryLoaded.equals(Category.CATEGORY_ALL_NAME)) {
                reminders = reminderDao.queryForAll();
            } else {
                Category category = categoryDao.queryForEq("name", categoryLoaded).get(0); //TODO get unique
                reminders = reminderDao.queryForEq("category_id", category.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d("myapp", "reminder return from doInBackground size : " + (!reminders.isEmpty() ? reminders.size() : 0));
        return (ArrayList<Reminder>)reminders;
    }

    public void setCategoryLoaded(String categoryLoaded) {
        this.categoryLoaded = categoryLoaded;
    }


}
