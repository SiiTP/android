package com.dbtest.ivan.app.services.sync;

import com.j256.ormlite.dao.Dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ivan on 06.04.16.
 */
public abstract class AbstractSyncToServer<T> implements  SyncDataService<T> {
    protected Dao<T,Long> dao;
    private boolean isSynced = true;
    public AbstractSyncToServer(Dao<T, Long> dao) {
        this.dao = dao;
    }

    @Override
    public void syncAll() throws IOException, SQLException {
        List<T> data = dao.queryForEq("is_synced",false);
        for(T item : data){
            isSynced = sync(item);
            if(!isSynced){
                break;
            }
        }
    }
    public boolean isSynced() {
        return isSynced;
    }
}
