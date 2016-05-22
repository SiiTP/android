package com.dbtest.ivan.app.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.abstractToolbarActivity.AbstractToolbarActivity;
import com.dbtest.ivan.app.activity.reminder.DetailReminderActivity;
import com.dbtest.ivan.app.logic.adapter.FriendListAdapter;
import com.dbtest.ivan.app.logic.divider.DividerItemDecoration;
import com.dbtest.ivan.app.model.Friend;
import com.dbtest.ivan.app.receiver.FriendsWebRequestReceiver;
import com.dbtest.ivan.app.services.intent.InviteFriendService;
import com.dbtest.ivan.app.services.intent.LoadFriendsIntentService;
import com.dbtest.ivan.app.services.intent.RemoveFriendIntentService;
import com.dbtest.ivan.app.utils.NotificationHelper;

import java.util.List;

public class FriendsActivity extends AbstractToolbarActivity {

    private static final int MENU_POSITION = 1;
    private RecyclerView recyclerView;
    private FriendListAdapter friendListAdapter;
    private FriendsWebRequestReceiver receiver;
    private ImageButton button;
    private EditText emailView;

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

        emailView = (EditText) findViewById(R.id.find_friend).findViewById(R.id.friend_email);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        button = (ImageButton) findViewById(R.id.add_friend);
        if (button != null) {
            button.setOnClickListener((v)-> {
                String email = emailView.getText().toString();

                if (!email.isEmpty()) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(FriendsActivity.this, InviteFriendService.class);

                    bundle.putString("email", email);
                    intent.putExtras(bundle);
                    startService(intent);
                }
            });
        }
        IntentFilter filter = new IntentFilter(FriendsWebRequestReceiver.PROCESS_RESPONSE);
        Intent intent = new Intent(FriendsActivity.this, LoadFriendsIntentService.class);

        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new FriendsWebRequestReceiver(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
        startService(intent);
    }

    @Override
    public void onDestroy() {
        if (receiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        }
        super.onDestroy();
    }

    public void openDetailReminderActivity() {
        Intent intent = new Intent(FriendsActivity.this, DetailReminderActivity.class);
        startActivity(intent);
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
                .positiveText("Yes")
                .onPositive(callback)
                .negativeText("No")
                .onNegative(callback)
                .show();
    }
}
