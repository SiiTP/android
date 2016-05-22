package com.dbtest.ivan.app.activity.reminder;

import android.content.Intent;
import android.os.Bundle;

import com.dbtest.ivan.app.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.dbtest.ivan.app.services.intent.ReminderIntentService.*;

/**
 * Created by ivan on 22.05.16.
 */
public class UpdateDetailReminderActivity extends DetailReminderActivity {

    @Override
    public void onCreate(Bundle restoreBundle){
        super.onCreate(restoreBundle);
        Intent reminderInfo = getIntent();

        if(reminderInfo != null){
            Bundle bundle = reminderInfo.getExtras();
            if(bundle != null) {
                Long id = bundle.getLong(ID);
                String text = bundle.getString(TEXT);
                Long time = bundle.getLong(TIME);
                String categoryName = bundle.getString(CATEGORY);

                if (id != 0) {
                    setId(id);
                }
                if (text != null) {
                    reminderText.setText(text);
                }
                if (time != 0) {
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTimeInMillis(time);
                    onDateTimeSelect(calendar);
                }
                if (categoryName != null) {
                    setSpinnerCategory(categoryName);
                }
            }
        }
    }

    @Override
    public void initSubmitButton() {
        super.initSubmitButton();
        submit.setText(R.string.update_reminder_button);
    }
}
