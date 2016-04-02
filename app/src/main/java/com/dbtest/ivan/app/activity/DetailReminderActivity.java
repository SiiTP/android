package com.dbtest.ivan.app.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dbtest.ivan.app.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;

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

                    Log.d("myapp " + DetailReminderActivity.class.toString(), "got " + author + ' ' + time + ' ' + text + ' ' + categoryName);
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
