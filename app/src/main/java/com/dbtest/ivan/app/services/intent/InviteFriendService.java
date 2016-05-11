package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.logic.api.FriendApi;
import com.dbtest.ivan.app.model.Friend;
import com.dbtest.ivan.app.model.RemoveFriendResponse;

import java.io.IOException;

import retrofit2.Call;

/**
 * Created by said on 07.05.16.
 */
public class InviteFriendService extends IntentService {

    public InviteFriendService() {
        super("InviteFriendService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        FriendApi api = RetrofitFactory.getInstance().create(FriendApi.class);
        String email = bundle.getString("email");
        Friend friend = new Friend();
        friend.setEmail(email);
        friend.setState(0);
        Call<Friend> call = api.inviteFriend(friend);
        try {
            Friend response = call.execute().body();
            Log.d("Ivite", "Yes");
            //TODO: return result to activity????
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
