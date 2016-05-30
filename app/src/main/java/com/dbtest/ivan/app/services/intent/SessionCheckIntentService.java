package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;

import com.dbtest.ivan.app.activity.abstractToolbarActivity.DrawerMenuManager;
import com.dbtest.ivan.app.services.intent.helpers.SignInHelper;


public class SessionCheckIntentService extends IntentService {

    public SessionCheckIntentService() {
        super("SessionCheckIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SignInHelper helper = new SignInHelper(this);
        boolean isSessionExpired = helper.isSessionExpired();
        DrawerMenuManager.setIsLogged(!isSessionExpired);
    }
}
