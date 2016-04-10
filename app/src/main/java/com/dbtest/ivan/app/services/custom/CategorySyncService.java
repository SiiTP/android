package com.dbtest.ivan.app.services.custom;

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
public class CategorySyncService extends AbstractSyncService<Category> {
    private CategoryApi api;
    public CategorySyncService(Dao<Category,Long> categoryDao,CategoryApi api) {
        super(categoryDao);
        this.api = api;
    }

    @Override
    public boolean sync(Category item) throws IOException, SQLException {
        Call<Category> request;

        request = api.create(item);

        Response<Category> response = request.execute();
        boolean result = false;
        if (response.body() != null && response.body().getId() != null) {
            item.setIsSynced(true);
            dao.update(item);
            result = true;
        }
        return result;
    }
}
