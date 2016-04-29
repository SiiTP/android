package com.dbtest.ivan.app.services;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by ivan on 26.04.16.
 */
public class CustomGcmListener extends GcmListenerService {
    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        System.out.println(from + ' ' + data);

    }

}
