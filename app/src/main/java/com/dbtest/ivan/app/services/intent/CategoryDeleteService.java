package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.logic.api.CategoryApi;
import com.dbtest.ivan.app.logic.db.entities.Category;
import com.dbtest.ivan.app.utils.ExtrasCodes;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Ivan on 22.05.2016.
 */
public class CategoryDeleteService extends IntentService {

    public CategoryDeleteService() {
        super("category delete service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        CategoryApi categoryApi = RetrofitFactory.getInstance().create(CategoryApi.class);
        String categoryName = intent.getStringExtra(ExtrasCodes.CATEGORY_NAME_KEY);
        if (categoryName == null) {
            Log.e("myapp " + this.getClass().getSimpleName(), "service was executed without category");
            return;
        }

        Log.d("myapp " + this.getClass().getSimpleName(), "category from intent : " + categoryName);
        try {
            Call<Category> callCategory = categoryApi.delete(categoryName);
            Response<Category> categoryResponse = callCategory.execute();
            Log.d("myapp", "in delete service");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
