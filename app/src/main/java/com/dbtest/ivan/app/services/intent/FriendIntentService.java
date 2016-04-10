package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.logic.api.FriendApi;
import com.dbtest.ivan.app.model.Friend;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by said on 10.04.16.
 */
public class FriendIntentService extends IntentService {

    public FriendIntentService() {
        super("FriendService");
    }

    @Override
    public void onDestroy() {
    //    Log.d("Friend","Destroyed()");
        super.onDestroy();
    }

    @Override
    public void onCreate() {
    //    Log.d("Friend","Created()");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
    //   Log.d("Friend", "Started()");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    //    Log.d("Friend","StartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        FriendApi friendApi = RetrofitFactory.getInstance().create(FriendApi.class);
        Call<List<Friend>> callFriends = friendApi.getFriends();
        try {
            List<Friend> friendList = callFriends.execute().body();
            Log.d("Friend", friendList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
