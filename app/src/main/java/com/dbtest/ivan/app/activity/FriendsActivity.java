package com.dbtest.ivan.app.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.abstractToolbarActivity.AbstractToolbarActivity;
import com.dbtest.ivan.app.activity.reminder.DetailReminderActivity;
import com.dbtest.ivan.app.logic.adapter.FriendListAdapter;
import com.dbtest.ivan.app.logic.divider.DividerItemDecoration;
import com.dbtest.ivan.app.model.Friend;
import com.dbtest.ivan.app.receiver.FriendRequestReceiver;
import com.dbtest.ivan.app.services.intent.InviteFriendService;
import com.dbtest.ivan.app.services.intent.LoadFriendsIntentService;
import com.dbtest.ivan.app.services.intent.RemoveFriendIntentService;

import java.util.List;

public class FriendsActivity extends AbstractToolbarActivity {

    private static final int MENU_POSITION = 1;
    private RecyclerView friendsListRecyclerView;
    private FriendListAdapter friendListAdapter;
    private FriendRequestReceiver friendRequestReceiver;
    private ImageButton addFriendButton;
    private EditText emailView;
    public static final String inviteMessage = "invite";
    public static final String reminderMessage = "reminder";

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

        emailView = (EditText) findViewById(R.id.friends_activity_find_friend_view).findViewById(R.id.friend_email);
        friendsListRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        friendsListRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        friendsListRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        addFriendButton = (ImageButton) findViewById(R.id.add_friend);
        if (addFriendButton != null) {
            addFriendButton.setOnClickListener((v)-> {
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
        IntentFilter filter = new IntentFilter(FriendRequestReceiver.PROCESS_RESPONSE);
        Intent intent = new Intent(FriendsActivity.this, LoadFriendsIntentService.class);

        filter.addCategory(Intent.CATEGORY_DEFAULT);
        friendRequestReceiver = new FriendRequestReceiver(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(friendRequestReceiver, filter);
        startService(intent);
    }

    @Override
    public void onDestroy() {
        if (friendRequestReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(friendRequestReceiver);
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
            friendsListRecyclerView.setAdapter(friendListAdapter);
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
