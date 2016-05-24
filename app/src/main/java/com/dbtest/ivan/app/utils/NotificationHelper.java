package com.dbtest.ivan.app.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.services.intent.AcceptFriendRequestIntentService;
import com.dbtest.ivan.app.services.intent.RejectFriendRequestIntentService;
import com.google.gson.JsonObject;

import java.util.Random;

/**
 * Created by said on 22.05.16.
 */
public class NotificationHelper {
    private static final String inviteTitle = "invites you";
    private static final String reminderTitle = "New reminder from";

    private static NotificationCompat.Builder initNotification(Context context, String msg, String title) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setContentTitle(title);
        builder.setContentText(msg);
        builder.setSmallIcon(R.drawable.ic_launcher);

        return builder;
    }

    private static void addButtonToNotification(NotificationCompat.Builder builder, Context context, int id, String email) {
        Intent acceptIntent = new Intent(context, AcceptFriendRequestIntentService.class);
        Intent rejectIntent = new Intent(context, RejectFriendRequestIntentService.class);
        Bundle bundle = new Bundle();

        bundle.putString("email", email);
        bundle.putInt("notificationID", id);

        acceptIntent.putExtras(bundle);
        rejectIntent.putExtras(bundle);

        PendingIntent acceptPenIntent = PendingIntent.getService(context, 0, acceptIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent rejectPenIntent = PendingIntent.getService(context, 0, rejectIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.addAction(R.drawable.ic_check_circle, "Accept", acceptPenIntent);
        builder.addAction(R.drawable.ic_remove_circle, "Reject", rejectPenIntent);
    }

    public static void sendInviteNotification(Context context, String msg, String email) {
        int id = new Random().nextInt(32);
        NotificationCompat.Builder builder = initNotification(context, msg, email + inviteTitle);

        builder.setOngoing(true);
        addButtonToNotification(builder, context, id, email);

        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(id, notification);
    }

    public static void sendReminderNotification(Context context, String msg) {
        int id = new Random().nextInt(32);
        NotificationCompat.Builder builder = initNotification(context, msg, reminderTitle);
        Notification notification = builder.build();
        builder.setAutoCancel(true);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(id, notification);
    }

    public static void cancelNotification(Context context, int id) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(id);
    }
}
