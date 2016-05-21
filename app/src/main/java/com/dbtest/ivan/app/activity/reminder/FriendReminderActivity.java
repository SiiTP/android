package com.dbtest.ivan.app.activity.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.services.intent.FriendReminderIntentService;

/**
 * Created by ivan on 21.05.16.
 */
public class FriendReminderActivity extends ReminderActivity {
    public static final String MAIL = "email";
    private TextView friendEmail;
    @NonNull
    @Override
    protected Integer getBodyResId() {
        return R.layout.activity_friend_reminder;
    }

    @NonNull
    @Override
    public Integer getMenuPosition() {
        return -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent startIntent = getIntent();
        friendEmail = (TextView) findViewById(R.id.friend_reminder_email);
        if(startIntent != null){
            String temp = startIntent.getStringExtra(MAIL);
            if(temp != null && friendEmail != null){
                friendEmail.setText(temp);
            }
        }
    }

    @Override
    public void initSubmitButton() {
        submit = (Button) findViewById(R.id.friend_reminder_send_button);
        submit.setOnClickListener(v -> {
            String email = friendEmail.getText().toString();
            Long date = reminderDate.getTimeInMillis();
            String text = reminderText.getText().toString();

            Bundle bundle = new Bundle();
            bundle.putString(FriendReminderIntentService.FRIEND_EMAIL,email);
            bundle.putString(FriendReminderIntentService.FRIEND_TEXT,text);
            bundle.putLong(FriendReminderIntentService.FRIEND_DATE,date);

            Intent intent = new Intent(FriendReminderActivity.this,FriendReminderIntentService.class);
            intent.putExtras(bundle);
            startService(intent);
            startWaitingReceiver();
        });
    }

    @Override
    public void notifyResult(String result) {
        Toast.makeText(FriendReminderActivity.this, result, Toast.LENGTH_LONG).show();
    }
}
