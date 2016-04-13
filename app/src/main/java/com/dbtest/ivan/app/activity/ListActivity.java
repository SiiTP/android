//TODO buzz шины
//TODO softReference cache

//TODO ResultReceiver???


package com.dbtest.ivan.app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.fragment.ReminderListFragment;
import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.utils.ExtrasCodes;
import com.j256.ormlite.android.apptools.OpenHelperManager;

public class ListActivity extends AbstractToolbarActivity
                          implements ReminderListFragment.OnItemSelectedListener {
    private static final String CURRENT_POSITION_KEY = "currentPosition";
    private static final String CURRENT_CATEGORIES = "currentCategories";
    private int mMenuLastPosition = 5;

    @NonNull
    @Override
    protected Integer getBodyResId() {
        return R.layout.activity_list;
    }

    @NonNull
    @Override
    public Integer getMenuPosition() {
        return mMenuLastPosition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(RetrofitFactory.SESSION_STORAGE_NAME,0);
        RetrofitFactory.setSession(preferences.getString(RetrofitFactory.SESSION_COOKIE_NAME, null));

        final Intent intent = getIntent();
        if (intent != null) {
            int gettedPosition = intent.getIntExtra(ExtrasCodes.ACTIVE_MENU_POSITION_CODE, mMenuLastPosition);
            if (mMenuLastPosition != gettedPosition) {
                mMenuLastPosition = gettedPosition;
            }
            renderList();
            Log.d("myapp", "position from extra : " + mMenuLastPosition);
        }

        Button add =(Button) findViewById(R.id.list_add_reminder);
        if (add != null) {
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(ListActivity.this,DetailReminderActivity.class);
                    startActivity(intent1);
                }
            });
        }
        if (savedInstanceState != null) {
            mCategories = savedInstanceState.getStringArray(CURRENT_CATEGORIES);
            mMenuLastPosition = savedInstanceState.getInt(CURRENT_POSITION_KEY);;
            Log.d("myapp " + this.getClass().toString(), "current position from saved instance state : " + mMenuLastPosition);
            Log.d("myapp " + this.getClass().toString(), "render list, category from saved instance state");
            renderList();
        }
        mDrawer.setSelectionAtPosition(mMenuLastPosition);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OpenHelperManager.releaseHelper();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_POSITION_KEY, getMenuPosition());
        outState.putStringArray(CURRENT_CATEGORIES, mCategories);
    }

    public void setMenuLastPosition(int mMenuLastPosition) {
        this.mMenuLastPosition = mMenuLastPosition;
    }

    public void renderList() {
        String checkedCategory;
        if (mCategories == null) {
            return;
        }
        checkedCategory = mCategories[mMenuLastPosition - MENU_FIRST_CATEGORY_POSITION];

        Log.d("myapp", "rendered list of category : " + checkedCategory);
        Log.d("myapp", "index : " + mMenuLastPosition);
        for (String c : mCategories) {
            Log.d("myapp", "category : " + c);
        }
        TextView textView = (TextView) findViewById(R.id.list_category_name);
        if (textView != null) {
            textView.setText(checkedCategory);
        }
        //TODO render
    }

    @Override
    public void onReminderSelected(int position) {
        Log.d("myapp", "reminder selected");
    }
}
