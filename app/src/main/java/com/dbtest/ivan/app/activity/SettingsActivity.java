package com.dbtest.ivan.app.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.abstract_toolbar_activity.AbstractToolbarActivity;

public class SettingsActivity extends AbstractToolbarActivity {
    private static final int MENU_POSITION = 2;

    @NonNull
    @Override
    protected Integer getBodyResId() {
        return R.layout.activity_settings;
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
