package com.dbtest.ivan.app.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.dbtest.ivan.app.R;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String KEY_ID_REMINDER = "reminder_id";
    public static final String KEY_TEXT_REMINDER = "reminder_text";
    public static final String KEY_CATEGORY_REMINDER = "reminder_category";

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra(KEY_ID_REMINDER, -1);
        String text = intent.getStringExtra(KEY_TEXT_REMINDER);
        String category = intent.getStringExtra(KEY_CATEGORY_REMINDER);
        if (id != -1) {
            startAlarm(context, id, text, category);
        }
    }

    public void startAlarm(Context context, int id, @NonNull String text, @NonNull String category) {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_app_start_activity)
                .setContentTitle("FriendReminder alarm")
                .setContentInfo(category)
                .setContentText(text);
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(id, builder.build());
    }
}
