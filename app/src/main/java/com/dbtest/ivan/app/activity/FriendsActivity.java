package com.dbtest.ivan.app.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.dbtest.ivan.app.R;

public class FriendsActivity extends AbstractToolbarActivity {

    private static final int MENU_POSITION = 1;

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
    }
}
