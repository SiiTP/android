package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dbtest.ivan.app.activity.SignUpActivity;
import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.logic.api.AuthApi;
import com.dbtest.ivan.app.logic.entities.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

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
        User user = new User(username,password,email);
        Retrofit retrofit = RetrofitFactory.getInstance();
        AuthApi authApi = retrofit.create(AuthApi.class);
        Call<User> userCall = authApi.register(user);
        try {
            Response<User> userResponse = userCall.execute();
            System.out.println(userResponse.headers().toMultimap().toString());
            Log.d("myapp " + SignUpIntentService.class.toString(), userResponse.body().getId().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
