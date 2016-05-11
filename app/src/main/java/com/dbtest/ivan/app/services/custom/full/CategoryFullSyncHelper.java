package com.dbtest.ivan.app.services.custom.full;

import com.dbtest.ivan.app.logic.api.CategoryApi;
import com.dbtest.ivan.app.logic.db.entities.Category;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by ivan on 20.04.16.
 */
public class CategoryFullSyncHelper extends FullSyncHelper<Category> {
    private CategoryApi api;

    public CategoryFullSyncHelper(Dao<Category, Long> dao,CategoryApi api) {
        super(dao);
        this.api = api;
    }

    @Override
    protected Call<ArrayList<Category>> getRequest() {
        return api.get();
    }

    @Override
    protected void syncOne(Category item) throws SQLException {
        List<Category> temp = dao.queryForEq("serverId", item.getServerId());
        if(temp.size() == 0){
            item.setIsSynced(true);
            dao.create(item);
        }
    }
}
