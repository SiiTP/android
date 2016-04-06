package com.dbtest.ivan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.services.intent.ReminderIntentService;

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

        final Button insert = (Button) findViewById(R.id.details_add);
        if (insert != null) {
            insert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String author = ((EditText) findViewById(R.id.details_author)).getText().toString();
                    String time = ((EditText) findViewById(R.id.details_time)).getText().toString();
                    String text = ((EditText) findViewById(R.id.details_text)).getText().toString();
                    String categoryName = ((EditText) findViewById(R.id.details_category)).getText().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString(ReminderIntentService.AUTHOR,author);
                    bundle.putString(ReminderIntentService.TIME,time);
                    bundle.putString(ReminderIntentService.TEXT, text);
                    bundle.putString(ReminderIntentService.CATEGORY, categoryName);
                    bundle.putLong(ReminderIntentService.ID_USER, 0L);
                    bundle.putLong(ReminderIntentService.ID, 4L);
                    Intent intent = new Intent(DetailReminderActivity.this,ReminderIntentService.class);
                    intent.putExtras(bundle);
                    startService(intent);
                }
            });



        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
