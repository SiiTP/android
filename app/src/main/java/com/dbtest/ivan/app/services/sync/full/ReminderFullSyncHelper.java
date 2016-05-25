package com.dbtest.ivan.app.services.sync.full;

import android.content.Context;

import com.dbtest.ivan.app.logic.api.ReminderApi;
import com.dbtest.ivan.app.logic.db.entities.Category;
import com.dbtest.ivan.app.logic.db.entities.Reminder;
import com.dbtest.ivan.app.utils.ReminderAlarmManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by ivan on 20.04.16.
 */
public class ReminderFullSyncHelper extends AbstractSyncFromServer<Reminder> {
    private ReminderApi api;
    private Dao<Category, Long> categoryDao;
    private Context context;
    public ReminderFullSyncHelper(Dao<Reminder, Long> dao,ReminderApi api,Dao<Category,Long> categoryDao, Context context) {
        super(dao);
        this.api = api;
        this.categoryDao = categoryDao;
        this.context = context;
    }

    @Override
    protected Call<ArrayList<Reminder>> getRequest() {
        return api.get();
    }

    @Override
    protected void syncOne(Reminder item) throws SQLException {
        List<Reminder> temp = dao.queryForEq("serverId", item.getServerId());
        if(temp.size() == 0){
            Category c = categoryDao.queryForEq("name",item.getCategory().getName()).get(0);
            if(c != null){
                item.setCategory(c);
            }
            item.setIsSynced(true);
            dao.create(item);
            ReminderAlarmManager.setAlarm(context,item.getId().intValue(),item.getReminderTime().getTime());
        }
    }
}
