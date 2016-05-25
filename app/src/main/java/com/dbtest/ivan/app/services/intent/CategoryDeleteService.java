package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.logic.api.CategoryApi;
import com.dbtest.ivan.app.logic.db.OrmHelper;
import com.dbtest.ivan.app.logic.db.entities.Category;
import com.dbtest.ivan.app.logic.db.entities.Reminder;
import com.dbtest.ivan.app.model.RemoveCategoryResponse;
import com.dbtest.ivan.app.receiver.CustomReceiver;
import com.dbtest.ivan.app.utils.ExtrasCodes;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.io.IOException;
import java.sql.SQLException;

import retrofit2.Call;

/**
 * Created by Ivan on 22.05.2016.
 */
public class CategoryDeleteService extends IntentService {

    public static final String SUCCESS_MSG = "successful deleting category";

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
        try {
            Call<RemoveCategoryResponse> callCategory = categoryApi.delete(categoryName);
            RemoveCategoryResponse body = callCategory.execute().body();
            int code = body.getCode();
            if (code == 0) {
                Toast.makeText(getApplicationContext(), "successful deleting", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "unsuccessful deleting : " + code, Toast.LENGTH_LONG).show();
            }
//            Log.d("myapp", "in delete service code : " + categoryResponse.code());
        } catch (IOException e) {
            Log.e("myapp", "network error   ");
        } finally {
            deleteLocalCategory(categoryName);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intentWaiting = new Intent(CustomReceiver.WAITING_ACTION);
            intentWaiting.addCategory(Intent.CATEGORY_DEFAULT);
            intentWaiting.putExtra(CustomReceiver.RESULT, CategoryDeleteService.SUCCESS_MSG);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intentWaiting);
        }
    }

    private void deleteLocalCategory(String categoryName) {
        try {
            OrmHelper ormHelper = new OrmHelper(this);
            Dao<Reminder, Long> reminderDao = ormHelper.getReminderDao();
            Dao<Category, Long> categoryDao = ormHelper.getCategoryDao();
            Category category = categoryDao.queryForEq("name", categoryName).get(0);
            DeleteBuilder<Reminder, Long> reminderDeleteBuilder = reminderDao.deleteBuilder();
            reminderDeleteBuilder.where().eq("category_id", category.getId());
            int rowsDeleted = reminderDeleteBuilder.delete();
            int rowsDeletedCategory = categoryDao.delete(category);
            Log.i("myapp" + this.getClass().getSimpleName(), "rows reminder deleted : " + rowsDeleted);
            Log.i("myapp" + this.getClass().getSimpleName(), "rows categories deleted : " + rowsDeletedCategory);
            OpenHelperManager.releaseHelper();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
