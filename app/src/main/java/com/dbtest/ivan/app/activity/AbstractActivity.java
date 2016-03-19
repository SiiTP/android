package com.dbtest.ivan.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.drawer_menu.DrawerMenuUtils;

public abstract class AbstractActivity extends AppCompatActivity {
    protected Toolbar toolbar;
    RelativeLayout rel;

    protected abstract int getBodyResId();

    protected void setToolbarAndMenu() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            DrawerMenuUtils.setDrawerMenuOnActivity(this, toolbar);
        } else {
            Log.e("myapp", "no toolbar");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("myapp", "on create abstract");

        rel = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_abstract, null);
        View addingView = getLayoutInflater().inflate(getBodyResId(), null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.BELOW, R.id.toolbar);

        addingView.setLayoutParams(layoutParams);

        rel.addView(addingView);
        setContentView(rel);
        setToolbarAndMenu();

    }
}
