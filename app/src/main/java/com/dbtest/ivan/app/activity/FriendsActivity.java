package com.dbtest.ivan.app.activity;

import android.os.Bundle;

import com.dbtest.ivan.app.R;

public class FriendsActivity extends AbstractActivity {

    @Override
    protected int getBodyResId() {
        return R.layout.activity_friends;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
    }
}
