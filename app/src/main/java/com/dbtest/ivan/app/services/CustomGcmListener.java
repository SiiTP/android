package com.dbtest.ivan.app.services;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dbtest.ivan.app.activity.FriendsActivity;
import com.dbtest.ivan.app.services.intent.FullSyncService;
import com.dbtest.ivan.app.utils.NotificationHelper;
import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by ivan on 26.04.16.
 */
public class CustomGcmListener extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        sendNotification(getApplicationContext(), data);
    }

    private void sendNotification(Context context, Bundle data) {
        String msg = data.getString("message");
        String email = data.getString("email");
        String type = data.getString("type");

        switch (type) {
            case FriendsActivity.inviteMessage:
                NotificationHelper.sendInviteNotification(context, msg, email);
                break;
            case FriendsActivity.reminderMessage:
                NotificationHelper.sendReminderNotification(context, msg);
                Intent syncAll = new Intent(context, FullSyncService.class);
                startService(syncAll);
        }
    }
}
