package com.dbtest.ivan.app.services.intent.helpers;

import android.util.Log;

import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.logic.api.AuthApi;
import com.dbtest.ivan.app.logic.db.entities.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ivan on 29.04.16.
 */
public class SignUpHelper {
    public static final long ERROR_SUCH_USER_EXIST = -1L;
    private boolean isRegistered = false;
    public void register(User user){
        Retrofit retrofit = RetrofitFactory.getInstance();
        AuthApi authApi = retrofit.create(AuthApi.class);
        Call<User> userCall = authApi.register(user);
        try {
            Response<User> userResponse = userCall.execute();
            System.out.println(userResponse.headers().toMultimap().toString());
            long id = userResponse.body().getId();
            Log.d("myapp " + SignUpHelper.class.toString(), Long.toString(id));
            if(id != ERROR_SUCH_USER_EXIST) {
                isRegistered = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isRegistered() {
        return isRegistered;
    }
}
