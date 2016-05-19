package com.dbtest.ivan.app.model.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.dbtest.ivan.app.logic.db.OrmHelper;
import com.dbtest.ivan.app.logic.db.entities.Category;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 10.04.2016.
 */
public class CategoryLoader extends AsyncTaskLoader<ArrayList<Category>> {
    OrmHelper mOrmHelper;
    public CategoryLoader(Context context) {
        super(context);
        mOrmHelper = new OrmHelper(context);
    }

    @Override
    public ArrayList<Category> loadInBackground() {
        Dao<Category, Long> categoryDao = mOrmHelper.getCategoryDao();
        List<Category> categories = null;
        try {
            categories = categoryDao.queryForAll();
            Log.d("myapp", "categories loaded, count : " + categories.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (ArrayList<Category>)categories;
    }
}
