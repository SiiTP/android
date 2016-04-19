package com.dbtest.ivan.app.logic.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.logic.db.entities.Category;
import com.dbtest.ivan.app.logic.db.entities.Reminder;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by ivan on 30.03.16.
 */
public class OrmHelper extends OrmLiteSqliteOpenHelper {
    public static final String DB_NAME = "friend_reminder";
    public static final int DB_VERSION = 36;
    public OrmHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.d("myapp " + OrmHelper.class.toString(), "Creating "+DB_NAME +" database V: "+DB_VERSION);
            TableUtils.createTable(connectionSource, Reminder.class);
            TableUtils.createTable(connectionSource, Category.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.d("myapp " + OrmHelper.class.toString(), "Drop tables in " + DB_NAME
                    + " database old V: " + oldVersion
                    + " new V: " + newVersion);
            TableUtils.dropTable(connectionSource, Reminder.class, true);
            TableUtils.dropTable(connectionSource, Category.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<Reminder,Long> getReminderDao(){
        Dao<Reminder,Long> dao = null;
        try {
            dao = getDao(Reminder.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dao;
    }
    public Dao<Category,Long> getCategoryDao(){
        Dao<Category,Long> dao = null;
        try{
            dao = getDao(Category.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dao;
    }
}
