package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.dbtest.ivan.app.activity.SignUpActivity;
import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.logic.api.AuthApi;
import com.dbtest.ivan.app.logic.db.entities.User;
import com.dbtest.ivan.app.receiver.CustomReceiver;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SignUpIntentService extends IntentService {
    public static final String SUCCESS_MSG = "Successful registration";
    public static final String FAILURE_MSG = "Such user already exist";
    public static final String SOMETHING_WRONG_MSG = "Something went wrong";
    public static final long ERROR_SUCH_USER_EXIST = -1L;
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
        Retrofit retrofit = RetrofitFactory.getInstance();
        AuthApi authApi = retrofit.create(AuthApi.class);
        Call<User> userCall = authApi.register(user);
        Bundle answer = new Bundle();
        try {
            Response<User> userResponse = userCall.execute();
            System.out.println(userResponse.headers().toMultimap().toString());
            long id = userResponse.body().getId();
            Log.d("myapp " + SignUpIntentService.class.toString(), Long.toString(id));
            if(id != ERROR_SUCH_USER_EXIST) {
                answer.putString(CustomReceiver.RESULT, SUCCESS_MSG);
            }else{
                answer.putString(CustomReceiver.RESULT,FAILURE_MSG);
            }

        } catch (IOException e) {
            answer.putString(CustomReceiver.RESULT,SOMETHING_WRONG_MSG);
            e.printStackTrace();
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
