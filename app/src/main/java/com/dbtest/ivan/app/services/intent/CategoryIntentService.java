package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dbtest.ivan.app.logic.db.OrmHelper;
import com.dbtest.ivan.app.logic.db.entities.Category;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;

/**
 * Created by ivan on 18.04.16.
 */
public class CategoryIntentService extends IntentService {
    public static final String CATEGORY_NAME = "NAME";
    public static final String CATEGORY_PICTURE = "PICTURE";

    public CategoryIntentService() {
        super("category intent service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        Category category = new Category();
        category.setName(bundle.getString(CATEGORY_NAME));
        category.setPicture(bundle.getString(CATEGORY_PICTURE));
        OrmHelper ormHelper = OpenHelperManager.getHelper(this,OrmHelper.class);
        try {
            ormHelper.getCategoryDao().create(category);
            Log.d("myapp " + CategoryIntentService.class.toString(), "category created id: " + category.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        OpenHelperManager.releaseHelper();
        super.onDestroy();
    }
}
