package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.dbtest.ivan.app.activity.SignUpActivity;

/**
 * Created by ivan on 25.03.16.
 */
public class SignUpIntentService extends IntentService {

    public SignUpIntentService() {
        super("SIgnUpIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        String email = bundle.getString(SignUpActivity.SIGNUP_EMAIL);
        String password = bundle.getString(SignUpActivity.SIGNUP_PASS);
        String username = bundle.getString(SignUpActivity.SIGNUP_USERNAME);
        System.out.println(email + ' ' + password + ' ' + username);
    }
}
