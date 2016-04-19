package com.dbtest.ivan.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.list_activity.ListActivity;
import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.logic.db.OrmHelper;
import com.dbtest.ivan.app.logic.db.entities.Category;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class StartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        RetrofitFactory.setSession(preferences.getString(RetrofitFactory.SESSION_COOKIE_NAME, null));
        RetrofitFactory.setServerURI(preferences.getString(RetrofitFactory.URI_NAME, null));// use preferences URI from settings

        OrmHelper ormHelper = new OrmHelper(this);
        Dao<Category, Long> categoryDao = ormHelper.getCategoryDao();

        try {
            List<Category> categories;
            categories = categoryDao.queryForAll();
            if (categories.isEmpty()) {
                categoryDao.create(new Category("all"));
                categoryDao.create(new Category("from friends"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final Handler handler = new Handler();
        handler.postDelayed(this::goToList, 2000);
    }

    private void goToList() {
        Intent  intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}
