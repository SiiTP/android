package com.dbtest.ivan.app.services;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by ivan on 26.04.16.
 */
public class CustomInstanceIdListener extends InstanceIDListenerService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

    }
}
