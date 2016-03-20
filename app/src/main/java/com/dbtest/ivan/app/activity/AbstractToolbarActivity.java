package com.dbtest.ivan.app.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.drawer_menu.DrawerMenuUtils;

public abstract class AbstractToolbarActivity extends AppCompatActivity {
    protected Toolbar toolbar;
    RelativeLayout layout;

    @NonNull
    protected abstract Integer getBodyResId();

    @NonNull
    protected abstract Integer getMenuPosition();

    protected void setToolbarAndMenu() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            DrawerMenuUtils.setDrawerMenuOnActivity(this, toolbar, getMenuPosition());
        } else {
            Log.e("myapp", "no toolbar");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layout = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_abstract, null);
        View addingView = getLayoutInflater().inflate(getBodyResId(), null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.BELOW, R.id.toolbar);

        addingView.setLayoutParams(layoutParams);

        layout.addView(addingView);
        setContentView(layout);
        setToolbarAndMenu();

    }
}
