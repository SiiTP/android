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
import com.dbtest.ivan.app.drawer_menu.DrawerMenuManager;
import com.dbtest.ivan.app.model.CategoryManager;
import com.mikepenz.materialdrawer.Drawer;

public abstract class AbstractToolbarActivity extends AppCompatActivity {
    protected Toolbar mToolbar;
    protected RelativeLayout mLayout;
    protected Drawer mDrawer;

    @NonNull
    protected abstract Integer getBodyResId();

    @NonNull
    public abstract Integer getMenuPosition();

    public Drawer getDrawer() {
        return mDrawer;
    }

    protected void setToolbarAndMenu() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mDrawer = DrawerMenuManager.buildDrawerMenu(this, mToolbar);
            DrawerMenuManager.setCategoriesSubItems(this, CategoryManager.getBestCategories());
            Log.d("myapp", "categories installed");
        } else {
            Log.e("myapp", "no toolbar");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_abstract, null);
        View addingView = getLayoutInflater().inflate(getBodyResId(), null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );
        layoutParams.addRule(RelativeLayout.BELOW, R.id.toolbar);
        addingView.setLayoutParams(layoutParams);

        mLayout.addView(addingView);
        setContentView(mLayout);
        setToolbarAndMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDrawer.setSelectionAtPosition(getMenuPosition());
    }


}
