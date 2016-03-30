package com.dbtest.ivan.app.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.logic.db.OrmHelper;
import com.dbtest.ivan.app.logic.entities.Category;
import com.dbtest.ivan.app.logic.entities.Reminder;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DetailReminderActivity extends AbstractToolbarActivity {


    @NonNull
    @Override
    protected Integer getBodyResId() {
        return R.layout.activity_detail_reminder;
    }

    @NonNull
    @Override
    public Integer getMenuPosition() {
        return -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button insert = (Button) findViewById(R.id.details_add);
        if (insert != null) {
            insert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String author = ((EditText) findViewById(R.id.details_author)).getText().toString();
                    String time = ((EditText) findViewById(R.id.details_time)).getText().toString();
                    String text = ((EditText) findViewById(R.id.details_text)).getText().toString();
                    String categoryName = ((EditText) findViewById(R.id.details_category)).getText().toString();
                    Category category = new Category();
                    if(categoryName != null){
                        category.setName(categoryName);
                    }
                    OrmHelper helper = OpenHelperManager.getHelper(getApplicationContext(),OrmHelper.class);
                    Dao<Category,Long> categoryDao = helper.getCategoryDao();
                    try {
                        categoryDao.create(category);
                        Dao<Reminder,Long> reminderDao = helper.getReminderDao();
                        Reminder reminder = new Reminder();
                        reminder.setText(text);
                        reminder.setAuthor(author);
                        reminder.setCategory(category);
                        reminder.setReminderTime(new SimpleDateFormat("yyyyMMdd").parse(time));
                        reminderDao.create(reminder);

                        Where<Reminder, Long> reminderLongQueryBuilder = reminderDao.queryBuilder().where().eq("author", author);
                        Reminder get = reminderLongQueryBuilder.queryForFirst();
                        categoryDao.refresh(get.getCategory());
                        Log.d("myapp " + DetailReminderActivity.class.toString(), get != null ? get.getText() + ' ' + get.getCategory().getName(): "wtf?");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        OpenHelperManager.releaseHelper();
        super.onDestroy();
    }
}
