package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.dbtest.ivan.app.receiver.CustomReceiver;

/**
 * Created by ivan on 25.03.16.
 */
public class SignUpIntentService extends IntentService {

    public SignUpIntentService() {
        super("SIgnUpIntentService");
    }
    static int i = 0;
    @Override
    protected void onHandleIntent(Intent intent) {
//        Bundle bundle = intent.getExtras();
//        String email = bundle.getString(SignUpActivity.SIGNUP_EMAIL);
//        String password = bundle.getString(SignUpActivity.SIGNUP_PASS);
//        String username = bundle.getString(SignUpActivity.SIGNUP_USERNAME);
//        User user = new User(username,password,email);
//        Retrofit retrofit = RetrofitFactory.getInstance();
//        AuthApi authApi = retrofit.create(AuthApi.class);
//        Call<User> userCall = authApi.register(user);
//        try {
//            Response<User> userResponse = userCall.execute();
//            System.out.println(userResponse.headers().toMultimap().toString());
//            Log.d("myapp " + SignUpIntentService.class.toString(), userResponse.body().getId().toString());
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Bundle bundle = new Bundle();
        if(i % 2 == 0){
            bundle.putString(CustomReceiver.RESULT,"You are registered");
        }else{
            bundle.putString(CustomReceiver.RESULT,"Such user already exist");
        }
        i++;
        Intent activityNotify = new Intent(CustomReceiver.WAITING_ACTION);
        activityNotify.addCategory(Intent.CATEGORY_DEFAULT);
        activityNotify.putExtras(bundle);
        LocalBroadcastManager.getInstance(SignUpIntentService.this).sendBroadcast(activityNotify);
    }
}
