package com.dbtest.ivan.app.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.logic.adapter.FriendListAdapter;
import com.dbtest.ivan.app.logic.divider.DividerItemDecoration;
import com.dbtest.ivan.app.model.Friend;
import com.dbtest.ivan.app.services.intent.FriendIntentService;

import java.util.ArrayList;
import java.util.List;

public class FriendsActivity extends AbstractToolbarActivity {

    private static final int MENU_POSITION = 1;
    private RecyclerView recyclerView;
    private FriendListAdapter friendListAdapter;
    private List<Friend> exampleList = new ArrayList<>();
    private FriendsWebRequestReceiver receiver;

    @NonNull
    @Override
    protected Integer getBodyResId() {
        return R.layout.activity_friends;
    }

    @NonNull
    @Override
    public Integer getMenuPosition() {
        return MENU_POSITION;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter filter = new IntentFilter(FriendsWebRequestReceiver.PROCESS_RESPONSE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new FriendsWebRequestReceiver();
        registerReceiver(receiver, filter);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        friendListAdapter = new FriendListAdapter(this, exampleList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(friendListAdapter);
        prepareFriendData();
        Intent intent = new Intent(FriendsActivity.this, FriendIntentService.class);
        startService(intent);
    }

    @Override
    public void onDestroy() {
        this.unregisterReceiver(receiver);
        super.onDestroy();
    }

    private void prepareFriendData() {
        for (int i = 0; i < 10; i++) {
            Friend friend = new Friend(i, "aaa@mail.ru" + i, "Eee" + i, 123132, 1);

            exampleList.add(friend);
        }
    }

    public class FriendsWebRequestReceiver extends BroadcastReceiver {

        public static final String PROCESS_RESPONSE = "com.dbtest.ivan.intent.action.PROCESS_RESPONSE";
        @Override
        public void onReceive(Context context, Intent intent) {
            List<Friend> friendList = intent.getParcelableArrayListExtra("FriendsList");
        }
    }
}
