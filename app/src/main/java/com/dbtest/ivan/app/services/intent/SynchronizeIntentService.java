package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;

import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.logic.api.CategoryApi;
import com.dbtest.ivan.app.logic.api.ReminderApi;
import com.dbtest.ivan.app.logic.db.OrmHelper;
import com.dbtest.ivan.app.services.sync.CategorySyncToServer;
import com.dbtest.ivan.app.services.sync.ReminderSyncToServer;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.io.IOException;
import java.sql.SQLException;

import retrofit2.Retrofit;

public class SynchronizeIntentService extends IntentService {
    public static final String FULL_SYNC_ACTION ="sync.full";
    private OrmHelper helper;
    public SynchronizeIntentService() {
        super("SynchronizeIntentService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            helper = OpenHelperManager.getHelper(this, OrmHelper.class);
            Retrofit retrofit = RetrofitFactory.getInstance();
            CategorySyncToServer categorySyncService = new CategorySyncToServer(helper.getCategoryDao(),retrofit.create(CategoryApi.class));
            ReminderSyncToServer reminderSyncService = new ReminderSyncToServer(helper.getReminderDao(),retrofit.create(ReminderApi.class));

            try {
                categorySyncService.syncAll();
                if(categorySyncService.isSynced()) {
                    reminderSyncService.syncAll();
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        OpenHelperManager.releaseHelper();
        super.onDestroy();
    }
    
}
