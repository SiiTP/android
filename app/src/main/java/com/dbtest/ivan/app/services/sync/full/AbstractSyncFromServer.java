package com.dbtest.ivan.app.services.sync.full;

import com.j256.ormlite.dao.Dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ivan on 20.04.16.
 */
public abstract class AbstractSyncFromServer<T> {
    protected Dao<T,Long> dao;

    public AbstractSyncFromServer(Dao<T, Long> dao) {
        this.dao = dao;
    }

    protected abstract Call<ArrayList<T>> getRequest();
    protected abstract void syncOne(T item) throws SQLException;
    public void sync(){
        Call<ArrayList<T>> req = getRequest();
        Response<ArrayList<T>> response;
        try{
            response = req.execute();
            for(T r: response.body()){
                syncOne(r);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }
}
