package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.dbtest.ivan.app.activity.SignInActivity;
import com.dbtest.ivan.app.logic.db.entities.User;
import com.dbtest.ivan.app.receiver.CustomReceiver;
import com.dbtest.ivan.app.services.intent.helpers.SignInHelper;

/**
 * Created by ivan on 25.03.16.
 */
public class SignInIntentService extends IntentService {
    public static final String SUCCESS_MSG = "Successful login";
    public static final String FAILURE_MSG = "Wrong email or password";
    public static final String SOMETHING_WRONG_MSG = "Something went wrong";

    public SignInIntentService() {
        super("SignInService");
    }

    @Override
    public void onDestroy() {
//        Log.d(SignInIntentService.class.toString(),"Destroyed()");
        super.onDestroy();
    }

    @Override
    public void onCreate() {
//        Log.d(SignInIntentService.class.toString(),"Created()");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
//        Log.d(SignInIntentService.class.toString(),"Started()");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.d(SignInIntentService.class.toString(),"StartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        String email = bundle.getString(SignInActivity.SIGNIN_EMAIL);
        String password = bundle.getString(SignInActivity.SIGNIN_PASSWORD);

        User user = new User(null,password,email);
        SignInHelper helper = new SignInHelper(this);
        helper.login(user);
        Bundle answer = new Bundle();
        if(helper.isLogged()){
            answer.putString(CustomReceiver.RESULT, SUCCESS_MSG);
            Intent sendToken = new Intent(this,TokenRegisterIntentService.class);
            startService(sendToken);
        }else{
            Log.d("myapp " + SignUpIntentService.class.toString(), "failure login");
            answer.putString(CustomReceiver.RESULT, FAILURE_MSG);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent activityNotify = new Intent(CustomReceiver.WAITING_ACTION);
        activityNotify.addCategory(Intent.CATEGORY_DEFAULT);
        activityNotify.putExtras(answer);
        LocalBroadcastManager.getInstance(SignInIntentService.this).sendBroadcast(activityNotify);

    }
}
