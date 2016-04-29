package com.dbtest.ivan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.abstract_toolbar_activity.AbstractToolbarActivity;
import com.dbtest.ivan.app.services.intent.TokenRegisterIntentService;

public class GCM extends AbstractToolbarActivity {
    public static final int MENU_POSITION = -1;
    @NonNull
    @Override
    protected Integer getBodyResId() {
        return R.layout.activity_gcm;
    }

    @NonNull
    @Override
    public Integer getMenuPosition() {
        return MENU_POSITION;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button getToken = (Button) findViewById(R.id.get_token);
        if (getToken != null) {
            getToken.setOnClickListener(v -> {
                Intent intent = new Intent(GCM.this, TokenRegisterIntentService.class);
                startService(intent);
            });
        }

    }
}
