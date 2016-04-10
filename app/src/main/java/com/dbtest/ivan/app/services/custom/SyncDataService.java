package com.dbtest.ivan.app.services.custom;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by ivan on 06.04.16.
 */
public interface SyncDataService<T> {
    void syncAll() throws IOException, SQLException;

    boolean sync(T item) throws IOException, SQLException;
}
