package com.dbtest.ivan.app.services.intent.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.dbtest.ivan.app.activity.abstractToolbarActivity.DrawerMenuManager;
import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.logic.api.AuthApi;
import com.dbtest.ivan.app.logic.db.entities.User;
import com.dbtest.ivan.app.utils.network.CookieExtractor;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ivan on 29.04.16.
 */
public class SignInHelper {
    public static final long ERROR_WRONG_USER_OR_PASS = -1L;
    private boolean isSuccess = false;
    private Context context;

    public SignInHelper(Context context) {
        this.context = context;
    }

    public void login(User user){
        AuthApi authApi = RetrofitFactory.getInstance().create(AuthApi.class);
        Call<User> callUser = authApi.login(user);
        try {
            Response<User> userResponse = callUser.execute();
            Log.d("myapp " + SignInHelper.class.toString(), userResponse.headers().toMultimap().toString());
            Log.d("myapp " + SignInHelper.class.toString(), userResponse.body().getId() != null ? userResponse.body().getId().toString() : "WTF?");
            String session;
            if (userResponse.body().getId() != ERROR_WRONG_USER_OR_PASS) {
                User responseUserBody = userResponse.body();
                List<String> cookies = userResponse.headers().toMultimap().get("Set-Cookie");
                session = CookieExtractor.getCookie(cookies.get(0), RetrofitFactory.SESSION_COOKIE_NAME);
                RetrofitFactory.setSession(session);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                preferences.edit().putString(RetrofitFactory.SESSION_COOKIE_NAME, session).commit();
                preferences.edit().putString("email", responseUserBody.getEmail()).commit();
                preferences.edit().putString("name", responseUserBody.getUsername()).commit();
                Log.d("myapp " + SignInHelper.class.toString(), session);
                DrawerMenuManager.setIsLogged(true);
                isSuccess = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean isLogged() {
        return  isSuccess;
    }
}
