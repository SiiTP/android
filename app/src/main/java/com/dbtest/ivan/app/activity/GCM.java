package com.dbtest.ivan.app.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.abstract_toolbar_activity.AbstractToolbarActivity;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

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
        String token = getResources().getString(R.string.dev_key);
        if (getToken != null) {
            getToken.setOnClickListener(v -> {
                InstanceID id = InstanceID.getInstance(GCM.this);
                try {
                    String t = id.getToken(getString(R.string.dev_key), GoogleCloudMessaging.INSTANCE_ID_SCOPE,null);
                    Log.d("myapp token",t);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

    }
}
