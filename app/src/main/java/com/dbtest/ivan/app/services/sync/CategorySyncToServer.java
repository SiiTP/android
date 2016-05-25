package com.dbtest.ivan.app.services.sync;

import com.dbtest.ivan.app.logic.api.CategoryApi;
import com.dbtest.ivan.app.logic.db.entities.Category;
import com.j256.ormlite.dao.Dao;

import java.io.IOException;
import java.sql.SQLException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ivan on 06.04.16.
 */
public class CategorySyncToServer extends AbstractSyncToServer<Category> {
    private CategoryApi api;
    public CategorySyncToServer(Dao<Category,Long> categoryDao, CategoryApi api) {
        super(categoryDao);
        this.api = api;
    }

    @Override
    public boolean sync(Category item) throws IOException, SQLException {
        Call<Category> request;

        request = api.create(item);

        Response<Category> response = request.execute();
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
        if(result){
            item.setIsSynced(true);
            item.setServerId(serverId);
            dao.update(item);
        }
        return result;
    }
}
