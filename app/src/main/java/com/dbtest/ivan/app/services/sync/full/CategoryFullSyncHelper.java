package com.dbtest.ivan.app.services.sync.full;

import com.dbtest.ivan.app.logic.api.CategoryApi;
import com.dbtest.ivan.app.logic.db.entities.Category;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by ivan on 20.04.16.
 */
public class CategoryFullSyncHelper extends AbstractSyncFromServer<Category> {
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
        List<Category> temp = null;
//            temp = dao.queryForEq("serverId", item.getServerId());
            PreparedQuery<Category> preparedQuery = dao.queryBuilder().where().eq("name",item.getName()).or().eq("serverId", item.getServerId()).prepare();
            temp = dao.query(preparedQuery);
        if(temp != null){
            if(temp.size() != 0){
                Category stored = temp.get(0);
                if(stored.getServerId() == null){
                    stored.setServerId(item.getServerId());
                    stored.setIsSynced(true);
                    dao.update(stored);
                }
            }else{
                item.setIsSynced(true);
                dao.create(item);
            }

        }
    }
}
