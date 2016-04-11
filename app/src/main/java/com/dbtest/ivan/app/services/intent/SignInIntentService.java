package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.dbtest.ivan.app.receiver.CustomReceiver;

/**
 * Created by ivan on 25.03.16.
 */
public class SignInIntentService extends IntentService {
    public static final String PREF_SESSION = "sessionPreference";
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
    static int i = 0;
    @Override
    protected void onHandleIntent(Intent intent) {
       /* Bundle bundle = intent.getExtras();
        String email = bundle.getString(SignInActivity.SIGNIN_EMAIL);
        String password = bundle.getString(SignInActivity.SIGNIN_PASSWORD);

        User user = new User(null,password,email);
        AuthApi authApi = RetrofitFactory.getInstance().create(AuthApi.class);
        Call<User> callUser = authApi.login(user);
        try {
            Response<User> userResponse = callUser.execute();
            Log.d("myapp " + SignUpIntentService.class.toString(), userResponse.headers().toMultimap().toString());
            Log.d("myapp " + SignUpIntentService.class.toString(),userResponse.body().getId() != null? userResponse.body().getId().toString() : "WTF?");
            String session = null;
            if(userResponse.body().getId() != -1) {
                List<String> cookies = userResponse.headers().toMultimap().get("Set-Cookie");
                session = CookieExtractor.getCookie(cookies.get(0), RetrofitFactory.SESSION_COOKIE_NAME);
                RetrofitFactory.setSession(session);
                SharedPreferences preferences = getSharedPreferences(RetrofitFactory.SESSION_STORAGE_NAME,0);
                preferences.edit().putString(RetrofitFactory.SESSION_COOKIE_NAME,session).commit();
                Log.d("myapp " + SignUpIntentService.class.toString(),session);

            }else{
                Log.d("myapp " + SignUpIntentService.class.toString(), "failure login");
            }

            //todo sendbroadcast intent with session & user data??!?!
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Bundle bundle = new Bundle();
        if(i % 2 == 0){
            bundle.putString(CustomReceiver.RESULT,"Successful login");
        }else{
            bundle.putString(CustomReceiver.RESULT,"Wrong email or password");
        }
        i++;
        Intent activityNotify = new Intent(CustomReceiver.WAITING_ACTION);
        activityNotify.addCategory(Intent.CATEGORY_DEFAULT);
        activityNotify.putExtras(bundle);
        LocalBroadcastManager.getInstance(SignInIntentService.this).sendBroadcast(activityNotify);

    }
}
