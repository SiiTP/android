package com.dbtest.ivan.app.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.logic.adapter.FriendListAdapter;
import com.dbtest.ivan.app.model.Friend;

import java.util.ArrayList;
import java.util.List;

public class FriendsActivity extends AbstractToolbarActivity {

    private static final int MENU_POSITION = 1;
    private RecyclerView recyclerView;
    private FriendListAdapter friendListAdapter;
    private List<Friend> exampleList = new ArrayList<>();

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
        friendListAdapter = new FriendListAdapter(this, exampleList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(friendListAdapter);
        prepareFriendData();
    }

    private void prepareFriendData() {
        for (int i = 0; i < 10; i++) {
            Friend friend = new Friend(i, "aaa@mail.ru" + i, "Eee" + i, 123132, 1);

            exampleList.add(friend);
        }
    }
}
