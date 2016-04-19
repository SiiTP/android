package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.dbtest.ivan.app.activity.SignInActivity;
import com.dbtest.ivan.app.activity.abstract_toolbar_activity.DrawerMenuManager;
import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.logic.api.AuthApi;
import com.dbtest.ivan.app.logic.db.entities.User;
import com.dbtest.ivan.app.receiver.CustomReceiver;
import com.dbtest.ivan.app.utils.network.CookieExtractor;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ivan on 25.03.16.
 */
public class SignInIntentService extends IntentService {
    public static final String PREF_SESSION = "sessionPreference";
    public static final long ERROR_WRONG_USER_OR_PASS = -1L;
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
        AuthApi authApi = RetrofitFactory.getInstance().create(AuthApi.class);
        Call<User> callUser = authApi.login(user);
        Bundle answer = new Bundle();
        try {
            Response<User> userResponse = callUser.execute();
            Log.d("myapp " + SignUpIntentService.class.toString(), userResponse.headers().toMultimap().toString());
            Log.d("myapp " + SignUpIntentService.class.toString(),userResponse.body().getId() != null? userResponse.body().getId().toString() : "WTF?");
            String session;
            if(userResponse.body().getId() != ERROR_WRONG_USER_OR_PASS) {
                List<String> cookies = userResponse.headers().toMultimap().get("Set-Cookie");
                session = CookieExtractor.getCookie(cookies.get(0), RetrofitFactory.SESSION_COOKIE_NAME);
                RetrofitFactory.setSession(session);

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                preferences.edit().putString(RetrofitFactory.SESSION_COOKIE_NAME,session).commit();
                Log.d("myapp " + SignUpIntentService.class.toString(), session);
                DrawerMenuManager.setIsLogged(true); // TODO после этого надо интентом перекинуть на лист активити чтобы меню пересоздалось и профиль отобразился
                answer.putString(CustomReceiver.RESULT, "Successful login");
            }else{
                Log.d("myapp " + SignUpIntentService.class.toString(), "failure login");
                answer.putString(CustomReceiver.RESULT, "Wrong email or password");
            }

            //todo sendbroadcast intent with session & user data??!?!
        } catch (IOException e) {
            answer.putString(CustomReceiver.RESULT, "Something went wrong");
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
        LocalBroadcastManager.getInstance(SignInIntentService.this).sendBroadcast(activityNotify);

    }
}
