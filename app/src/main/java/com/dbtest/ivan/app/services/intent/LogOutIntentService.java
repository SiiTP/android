package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.dbtest.ivan.app.activity.abstractToolbarActivity.DrawerMenuManager;
import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.logic.api.AuthApi;
import com.dbtest.ivan.app.logic.db.entities.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;


public class LogOutIntentService extends IntentService {


    public LogOutIntentService() {
        super("LogOutIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AuthApi authApi = RetrofitFactory.getInstance().create(AuthApi.class);
        Call<User> logoutRequest = authApi.logout();
        try {
            Response<User> logoutResp = logoutRequest.execute();
            User body = logoutResp.body();
            if(body.getId() == null){
                Log.d("myapp " + LogOutIntentService.class.getSimpleName(), " wrong logout");
                DrawerMenuManager.setIsLogged(false);
            }else{
                DrawerMenuManager.setIsLogged(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
