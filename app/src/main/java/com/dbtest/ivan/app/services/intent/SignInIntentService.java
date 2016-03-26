package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dbtest.ivan.app.activity.SignInActivity;
import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.logic.api.AuthApi;
import com.dbtest.ivan.app.logic.entities.User;
import com.dbtest.ivan.app.utils.network.CookieExtractor;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ivan on 25.03.16.
 */
public class SignInIntentService extends IntentService {

    public SignInIntentService() {
        super("SignInService");
    }

    private static String SESSION_COOKIE_NAME = "JSESSIONID";

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
        String email = bundle.getString(SignInActivity.SIGNIN_EMAIL);
        String password = bundle.getString(SignInActivity.SIGNIN_PASSWORD);

        User user = new User(null,password,email);
        AuthApi authApi = RetrofitFactory.getInstance().create(AuthApi.class);
        Call<User> callUser = authApi.login(user);
        try {
            Response<User> userResponse = callUser.execute();
            Log.d(SignUpIntentService.class.toString(), userResponse.headers().toMultimap().toString());
            Log.d(SignUpIntentService.class.toString(),userResponse.body().getId() != null? userResponse.body().getId().toString() : "WTF?");
            List<String> cookies = userResponse.headers().toMultimap().get("Set-Cookie");
            String session = CookieExtractor.getCookie(cookies.get(0), SESSION_COOKIE_NAME);
            Log.d(SignUpIntentService.class.toString(),session);
            //todo sendbroadcast intent with session & user data??!?!
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
