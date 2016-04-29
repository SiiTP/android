package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.utils.TokenHelper;

/**
 * Created by ivan on 26.04.16.
 */
public class TokenRegisterIntentService extends IntentService {

    public TokenRegisterIntentService() {
        super("Token register intent service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        TokenHelper.getToken(getApplicationContext(),getString(R.string.proj_numb));
    }
}
