package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.logic.api.FriendApi;
import com.dbtest.ivan.app.model.RemoveFriendResponse;

import java.io.IOException;

import retrofit2.Call;

/**
 * Created by said on 13.04.16.
 */
public class RemoveFriendIntentService extends IntentService {

    public RemoveFriendIntentService() {
        super("RemoveFriendService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        FriendApi api = RetrofitFactory.getInstance().create(FriendApi.class);
        String email = bundle.getString("email");
        Call<RemoveFriendResponse> call = api.removeFriend(email);
        try {
            RemoveFriendResponse response = call.execute().body();
            //TODO: return result to activity????
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
