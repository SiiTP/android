package com.dbtest.ivan.app.services.custom;

import com.dbtest.ivan.app.logic.api.ReminderApi;
import com.dbtest.ivan.app.logic.db.entities.Reminder;
import com.j256.ormlite.dao.Dao;

import java.io.IOException;
import java.sql.SQLException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ivan on 06.04.16.
 */
public class ReminderSyncService extends AbstractSyncService<Reminder> {
    private ReminderApi api;
    public ReminderSyncService(Dao<Reminder,Long> categoryDao,ReminderApi api) {
        super(categoryDao);
        this.api = api;
    }

    @Override
    public boolean sync(Reminder item) throws IOException, SQLException {
        if(item.getCategory() != null & !item.getCategory().getIsSynced()){
            return false;
        }
        Call<Reminder> request;

        request = api.create(item);
        Response<Reminder> response = request.execute();
        boolean result = false;
        long serverId = 0;
        if (response.body() != null && response.body().getServerId() != null) {
            serverId = response.body().getServerId();
            if(serverId == -1){//todo add const reminder already exist
                response = api.update(item).execute();
                serverId = response.body().getServerId();
                if(response.body() != null && serverId != -1){
                    result = true;
                }
            }else {
                result = true;
            }
        }
        if(result) {
            item.setServerId(serverId);
            item.setIsSynced(true);
            dao.update(item);
        }

        return result;
    }
}
