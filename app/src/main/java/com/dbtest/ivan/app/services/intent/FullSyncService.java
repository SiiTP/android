package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;

import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.logic.api.CategoryApi;
import com.dbtest.ivan.app.logic.api.ReminderApi;
import com.dbtest.ivan.app.logic.db.OrmHelper;
import com.dbtest.ivan.app.logic.db.entities.Category;
import com.dbtest.ivan.app.logic.db.entities.Reminder;
import com.dbtest.ivan.app.services.custom.full.CategoryFullSyncHelper;
import com.dbtest.ivan.app.services.custom.full.ReminderFullSyncHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import retrofit2.Retrofit;

/**
 * Created by ivan on 20.04.16.
 */
public class FullSyncService extends IntentService{

    public FullSyncService() {
        super("full sync service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Retrofit retrofit = RetrofitFactory.getInstance();
        ReminderApi reminderApi = retrofit.create(ReminderApi.class);
        CategoryApi categoryApi = retrofit.create(CategoryApi.class);

        OrmHelper ormHelper = OpenHelperManager.getHelper(this, OrmHelper.class);

        ReminderFullSyncHelper reminderFullSyncHelper = new ReminderFullSyncHelper(ormHelper.getReminderDao(),reminderApi,ormHelper.getCategoryDao());
        CategoryFullSyncHelper categoryFullSyncHelper = new CategoryFullSyncHelper(ormHelper.getCategoryDao(),categoryApi);

        categoryFullSyncHelper.sync();
        reminderFullSyncHelper.sync();

        try {
            Dao<Category,Long> dao = ormHelper.getCategoryDao();
            Dao<Reminder,Long> rdao = ormHelper.getReminderDao();
            List<Category> l = dao.queryForAll();
            for(Category c : l){
                int f = 5;
            }
            List<Reminder> rr = rdao.queryForAll();
            for(Reminder r : rr){
                int f = 5;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ;
    }
    @Override
    public void onDestroy() {
        OpenHelperManager.releaseHelper();
        super.onDestroy();
    }
}
