package com.dbtest.ivan.app.model.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.dbtest.ivan.app.logic.db.OrmHelper;
import com.dbtest.ivan.app.logic.db.entities.Category;
import com.dbtest.ivan.app.logic.db.entities.Reminder;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

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
        Dao<Reminder, Long> reminderDao = ormHelper.getReminderDao();
        Dao<Category, Long> categoryDao = ormHelper.getCategoryDao();

        List<Reminder> reminders = new ArrayList<>();
        try {
            QueryBuilder<Reminder, Long> queryBuilder = reminderDao.queryBuilder();
            if (!categoryLoaded.equals(Category.CATEGORY_ALL_NAME)) {
                Category category = categoryDao.queryForEq("name", categoryLoaded).get(0); //TODO get unique
                Where<Reminder, Long> where = queryBuilder.where();
                where.eq("category_id", category.getId());
//                where.and();
//                where.between("reminder_time", new Date(), DateUtils.getEndOfDay(new Date()));
                queryBuilder.setWhere(where);
            }
            queryBuilder.orderBy("reminder_time", false);
            reminders = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (ArrayList<Reminder>)reminders;
    }

    public void setCategoryLoaded(String categoryLoaded) {
        this.categoryLoaded = categoryLoaded;
    }


}
