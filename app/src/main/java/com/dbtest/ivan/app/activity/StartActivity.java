package com.dbtest.ivan.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.list_activity.ListActivity;
import com.dbtest.ivan.app.logic.RetrofitFactory;

public class StartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        SharedPreferences preferences = getSharedPreferences(RetrofitFactory.SESSION_STORAGE_NAME,0);
        RetrofitFactory.setSession(preferences.getString(RetrofitFactory.SESSION_COOKIE_NAME, null));
        final Handler handler = new Handler();
        handler.postDelayed(this::goToList, 2000);
    }

    private void goToList() {
        Intent  intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}
