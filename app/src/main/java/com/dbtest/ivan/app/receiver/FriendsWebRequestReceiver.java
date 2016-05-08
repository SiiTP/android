package com.dbtest.ivan.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dbtest.ivan.app.activity.FriendsActivity;
import com.dbtest.ivan.app.model.Friend;

import java.util.Collections;
import java.util.List;

/**
 * Created by said on 08.05.16.
 */
public class FriendsWebRequestReceiver extends BroadcastReceiver {

    public static final String PROCESS_RESPONSE = "com.dbtest.ivan.intent.action.PROCESS_RESPONSE";
    private FriendsActivity activity;

    public FriendsWebRequestReceiver(FriendsActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        List<Friend> friendList = intent.getParcelableArrayListExtra("FriendsList");

        Collections.sort(friendList);
        activity.setFriendListAdapter(friendList);
    }
}
