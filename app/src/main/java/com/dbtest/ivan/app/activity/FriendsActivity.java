package com.dbtest.ivan.app.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.abstract_toolbar_activity.AbstractToolbarActivity;
import com.dbtest.ivan.app.logic.adapter.FriendListAdapter;
import com.dbtest.ivan.app.logic.divider.DividerItemDecoration;
import com.dbtest.ivan.app.model.Friend;
import com.dbtest.ivan.app.services.intent.LoadFriendsIntentService;
import com.dbtest.ivan.app.services.intent.RemoveFriendIntentService;

import java.util.Collections;
import java.util.List;

public class FriendsActivity extends AbstractToolbarActivity {

    private static final int MENU_POSITION = 1;
    private RecyclerView recyclerView;
    private FriendListAdapter friendListAdapter;
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

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("myapp", "scroll state changed, new state : " + newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("myapp", "onScrolled, dx, dy : " + dx + " " + dy);
            }
        });
        IntentFilter filter = new IntentFilter(FriendsWebRequestReceiver.PROCESS_RESPONSE);
        receiver = new FriendsWebRequestReceiver(this);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
        Intent intent = new Intent(FriendsActivity.this, LoadFriendsIntentService.class);
        startService(intent);
    }

    @Override
    public void onDestroy() {
        if (receiver != null) {
            this.unregisterReceiver(receiver);
        }
        super.onDestroy();
    }

    public void removeFriend(String friendEmail) {
        Bundle bundle = new Bundle();

        bundle.putString("email", friendEmail);
        Intent intent = new Intent(FriendsActivity.this, RemoveFriendIntentService.class);

        intent.putExtras(bundle);
        startService(intent);
    }

    public void setFriendListAdapter(List<Friend> friendsList) {
        if (friendListAdapter == null) {
            friendListAdapter = new FriendListAdapter(this, friendsList);
            recyclerView.setAdapter(friendListAdapter);
        }
    }

    public void showDeleteFriendDialog(MaterialDialog.SingleButtonCallback callback) {
        new MaterialDialog.Builder(this)
                .content("Are you sure?")
                .positiveText("Agree")
                .onPositive(callback)
                .negativeText("Disagree")
                .onNegative(callback)
                .show();
    }

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
}
