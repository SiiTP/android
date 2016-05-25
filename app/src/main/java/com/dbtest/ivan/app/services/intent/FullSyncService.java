package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;

import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.logic.api.CategoryApi;
import com.dbtest.ivan.app.logic.api.ReminderApi;
import com.dbtest.ivan.app.logic.db.OrmHelper;
import com.dbtest.ivan.app.services.sync.full.CategoryFullSyncHelper;
import com.dbtest.ivan.app.services.sync.full.ReminderFullSyncHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

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

        ReminderFullSyncHelper reminderFullSyncHelper = new ReminderFullSyncHelper(ormHelper.getReminderDao(),reminderApi,ormHelper.getCategoryDao(), this);
        CategoryFullSyncHelper categoryFullSyncHelper = new CategoryFullSyncHelper(ormHelper.getCategoryDao(),categoryApi);

        categoryFullSyncHelper.sync();
        reminderFullSyncHelper.sync();
    }
    @Override
    public void onDestroy() {
        OpenHelperManager.releaseHelper();
        super.onDestroy();
    }
}
