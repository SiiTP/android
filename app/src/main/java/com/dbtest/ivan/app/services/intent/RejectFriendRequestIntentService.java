package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.logic.api.FriendApi;
import com.dbtest.ivan.app.model.Friend;

import java.io.IOException;

import retrofit2.Call;

/**
 * Created by said on 07.05.16.
 */
public class RejectFriendRequestIntentService extends IntentService {

    public RejectFriendRequestIntentService() {
        super("RejectFriendRequestIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String email = intent.getStringExtra("email");
        FriendApi friendApi = RetrofitFactory.getInstance().create(FriendApi.class);
        Friend friend = new Friend();
        friend.setEmail(email);
        friend.setState(2);
        Call<Friend> call = friendApi.confirm(friend);
        try {
            Friend response = call.execute().body();
            Log.d("Reject", "YES");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("Reject", "service");
    }
}
