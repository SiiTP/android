package com.dbtest.ivan.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.logic.RetrofitFactory;

public class StartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        RetrofitFactory.setSession(preferences.getString(RetrofitFactory.SESSION_COOKIE_NAME, null));
        RetrofitFactory.setServerURI(preferences.getString(RetrofitFactory.URI_NAME, null));// use preferences URI from settings
        final Handler handler = new Handler();
        handler.postDelayed(this::goToList, 2000);
    }

    private void goToList() {
        Intent  intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
