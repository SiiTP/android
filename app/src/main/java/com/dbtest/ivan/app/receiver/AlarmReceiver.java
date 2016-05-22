//shell am broadcast -a android.intent.action.BOOT_COMPLETED -c android.intent.category.HOME -n receiver/AlarmReceiver

package com.dbtest.ivan.app.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
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
import com.dbtest.ivan.app.activity.listActivity.ListActivity;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String KEY_ID_REMINDER = "reminder_id";
    public static final String KEY_TEXT_REMINDER = "reminder_text";
    public static final String KEY_CATEGORY_REMINDER = "reminder_category";

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra(KEY_ID_REMINDER, -1);
        Log.d("myapp alarm receiver", "in on receive, id : " + id);
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

        Intent notificationIntent = new Intent(context, ListActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent intent = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_app_start_activity)
                .setContentTitle(context.getString(R.string.alarm_notification_title))
                .setContentInfo(category)
                .setContentText(text)
                .setContentIntent(intent);



//        notification.setLatestEventInfo(context, title, message, intent);
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(id, builder.build());
    }
}
