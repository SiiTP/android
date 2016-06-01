package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.logic.api.FriendApi;
import com.dbtest.ivan.app.logic.db.entities.Reminder;
import com.dbtest.ivan.app.receiver.CustomReceiver;

import java.io.IOException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;

public class FriendReminderIntentService extends IntentService {

    public static final String FRIEND_EMAIL = "friend.email";
    public static final String FRIEND_TEXT = "friend.text";
    public static final String FRIEND_DATE = "friend.date";
    public FriendReminderIntentService() {
        super("FriendReminderIntentService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            String text = bundle.getString(FRIEND_TEXT);
            Long time = bundle.getLong(FRIEND_DATE);
            String friendEmail = bundle.getString(FRIEND_EMAIL);
            Reminder reminder = new Reminder(new Date(time),text);
            FriendApi api = RetrofitFactory.getInstance().create(FriendApi.class);
            Call<Reminder> sendReminder = api.sendReminder(friendEmail,reminder);
            Log.d("myapp " + FriendReminderIntentService.class.toString(),text+ ' ' + time + ' ' + friendEmail);
            try {
                Response<Reminder> reminderResponse = sendReminder.execute();

            } catch (IOException e) {
                e.printStackTrace();
            }
            Bundle answer = new Bundle();
            answer.putString(CustomReceiver.RESULT,"Reminder sent");
            Intent activityNotify = new Intent(CustomReceiver.WAITING_ACTION);
            activityNotify.addCategory(Intent.CATEGORY_DEFAULT);
            activityNotify.putExtras(answer);
            LocalBroadcastManager.getInstance(FriendReminderIntentService.this).sendBroadcast(activityNotify);

        }
    }
}
