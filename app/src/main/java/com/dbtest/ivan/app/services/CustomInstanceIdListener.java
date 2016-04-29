package com.dbtest.ivan.app.services;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by ivan on 26.04.16.
 */
public class CustomInstanceIdListener extends InstanceIDListenerService {
    public static final String IS_TOKEN_EXPIRED = "token";

    @Override
    public void onTokenRefresh() {
//        Intent intent = new Intent(this, TokenRegisterIntentService.class);// todo ask when refresh should i delete old tokens?
//        startService(intent);
//        SharedPreferences preferences = getDefaultSharedPreferences(this);
//        preferences.edit().putBoolean(IS_TOKEN_EXPIRED,true).commit();
    }
}
