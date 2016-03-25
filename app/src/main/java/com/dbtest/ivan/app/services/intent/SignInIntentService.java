package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dbtest.ivan.app.activity.SignInActivity;

/**
 * Created by ivan on 25.03.16.
 */
public class SignInIntentService extends IntentService {

    public SignInIntentService() {
        super("SignInService");
    }


    @Override
    public void onDestroy() {
        Log.d(SignInIntentService.class.toString(),"Destroyed()");
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        Log.d(SignInIntentService.class.toString(),"Created()");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d(SignInIntentService.class.toString(),"Started()");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(SignInIntentService.class.toString(),"StartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        System.out.println(bundle.get(SignInActivity.SIGNIN_EMAIL));
        System.out.println(bundle.get(SignInActivity.SIGNIN_PASSWORD));
    }
}
