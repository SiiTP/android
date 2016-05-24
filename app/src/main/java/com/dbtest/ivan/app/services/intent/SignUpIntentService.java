package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.dbtest.ivan.app.activity.SignUpActivity;
import com.dbtest.ivan.app.logic.db.entities.User;
import com.dbtest.ivan.app.receiver.CustomReceiver;
import com.dbtest.ivan.app.services.intent.helpers.SignInHelper;
import com.dbtest.ivan.app.services.intent.helpers.SignUpHelper;


public class SignUpIntentService extends IntentService {
    public static final String SUCCESS_MSG = "Successful registration & login";
    public static final String HALF_SUCCESS_MSG = "Successful registration";
    public static final String FAILURE_MSG = "Such user already exist";
    public static final String SOMETHING_WRONG_MSG = "Something went wrong";

    public SignUpIntentService() {
        super("SIgnUpIntentService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        String email = bundle.getString(SignUpActivity.SIGNUP_EMAIL);
        String password = bundle.getString(SignUpActivity.SIGNUP_PASS);
        String username = bundle.getString(SignUpActivity.SIGNUP_USERNAME);
        User user = new User(username,password,email);
        SignUpHelper helper = new SignUpHelper();
        helper.register(user);
        Bundle answer = new Bundle();
        if(helper.isRegistered()) {
//            answer.putString(CustomReceiver.RESULT, SUCCESS_MSG);
            SignInHelper signInHelper = new SignInHelper(this);
            signInHelper.login(user);
            if(signInHelper.isLogged()){
                answer.putString(CustomReceiver.RESULT, SUCCESS_MSG);
                Intent sendToken = new Intent(this,TokenRegisterIntentService.class);
                startService(sendToken);
            }else{
                answer.putString(CustomReceiver.RESULT, HALF_SUCCESS_MSG);
            }
        } else {
            answer.putString(CustomReceiver.RESULT,FAILURE_MSG);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent activityNotify = new Intent(CustomReceiver.WAITING_ACTION);
        activityNotify.addCategory(Intent.CATEGORY_DEFAULT);
        activityNotify.putExtras(answer);
        LocalBroadcastManager.getInstance(SignUpIntentService.this).sendBroadcast(activityNotify);
    }
}
