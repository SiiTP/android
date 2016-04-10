package com.dbtest.ivan.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dbtest.ivan.app.activity.WaitingActivity;

/**
 * Created by ivan on 10.04.16.
 */
public class CustomReceiver extends BroadcastReceiver {
    public static final String WAITING_ACTION = "wait";
    private WaitingActivity activity;

    public CustomReceiver(WaitingActivity activity) {
        this.activity = activity;
    }

    public CustomReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action){
            case WAITING_ACTION:
                if(activity != null) {
                    activity.setWaiting(false);
                }
                break;
        }
    }
}
