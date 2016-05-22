package com.dbtest.ivan.app.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dbtest.ivan.app.services.intent.FullSyncService;
import com.dbtest.ivan.app.utils.JsonParser;
import com.dbtest.ivan.app.utils.NotificationHelper;
import com.google.android.gms.gcm.GcmListenerService;
import com.google.gson.JsonObject;

/**
 * Created by ivan on 26.04.16.
 */
public class CustomGcmListener extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        sendNotification(getApplicationContext(), data.getString("message"));
    }

    private void sendNotification(Context context, String message) {
        JsonObject msgObject = JsonParser.stringToJsonObject(message);
        String msg = msgObject.get("message").getAsString();
        String email = msgObject.get("friend").getAsString();
        String type = msgObject.get("type").getAsString();

        switch (type) {
            case "invite":
                NotificationHelper.sendInviteNotification(context, msg, email);
                break;
            case "reminder":
                NotificationHelper.sendReminderNotification(context, msg);
                Intent syncAll = new Intent(context, FullSyncService.class);
                startService(syncAll);
        }
    }
}
