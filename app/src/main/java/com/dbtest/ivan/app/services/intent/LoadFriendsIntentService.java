package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.logic.api.FriendApi;
import com.dbtest.ivan.app.model.Friend;
import com.dbtest.ivan.app.receiver.FriendRequestReceiver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by said on 10.04.16.
 */
public class LoadFriendsIntentService extends IntentService {

    public LoadFriendsIntentService() {
        super("LoadFriendsService");
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
        Bundle answer = new Bundle();
        try {
            List<Friend> friendList = callFriends.execute().body();

            answer.putParcelableArrayList("FriendsList", (ArrayList<Friend>) friendList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent broadcastIntent = new Intent();

        broadcastIntent.setAction(FriendRequestReceiver.PROCESS_RESPONSE);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtras(answer);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }
}
