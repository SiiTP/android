package com.dbtest.ivan.app.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.FriendsActivity;
import com.dbtest.ivan.app.services.intent.AcceptFriendRequestIntentService;
import com.dbtest.ivan.app.services.intent.RejectFriendRequestIntentService;
import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by ivan on 26.04.16.
 */
public class CustomGcmListener extends GcmListenerService {

    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        sendNotification(getApplicationContext(), data.getString("message"));
    }

    private void sendNotification(Context context, String msg) {
        String email = null;
        if(msg.contains(" ")) {
            email = msg.substring(0, msg.indexOf(" "));
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(true);
        builder.setContentTitle("Basic notification");
        builder.setContentText(msg);
        builder.setSmallIcon(R.drawable.ic_launcher);

        Intent acceptIntent = new Intent(context, AcceptFriendRequestIntentService.class);
        acceptIntent.putExtra("email", email);
        PendingIntent acceptPenIntent = PendingIntent.getService(context, 0, acceptIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent rejectIntent = new Intent(context, RejectFriendRequestIntentService.class);
        rejectIntent.putExtra("email", email);
        PendingIntent rejectPenIntent = PendingIntent.getService(context, 0, rejectIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.addAction(R.drawable.ic_launcher, "Accept", acceptPenIntent);
        builder.addAction(R.drawable.ic_launcher, "Dismiss", rejectPenIntent);

        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, notification);
    }
}
