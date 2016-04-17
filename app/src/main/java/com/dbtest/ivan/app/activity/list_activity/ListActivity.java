//TODO buzz шины
//TODO softReference cache

//TODO ResultReceiver???


package com.dbtest.ivan.app.activity.list_activity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.DetailReminderActivity;
import com.dbtest.ivan.app.activity.abstract_toolbar_activity.AbstractToolbarActivity;
import com.dbtest.ivan.app.logic.adapter.ReminderListAdapter;
import com.dbtest.ivan.app.logic.db.entities.Reminder;
import com.dbtest.ivan.app.logic.divider.DividerItemDecoration;
import com.dbtest.ivan.app.model.loader.ReminderLoader;
import com.dbtest.ivan.app.utils.ExtrasCodes;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.ArrayList;

public class ListActivity extends AbstractToolbarActivity {
    private static final String CURRENT_POSITION_KEY = "currentPosition";
    private static final String CURRENT_CATEGORIES = "currentCategories";
    private int mMenuLastPosition = 4;
    private ReminderLoader mReminderLoader;
    private RecyclerView mRemindersRecyclerView;
    private ReminderListAdapter mRemindersAdapter;
    private FloatingActionButton mButtonAdd;

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

        mButtonAdd =(FloatingActionButton) findViewById(R.id.list_add_reminder);
        if (mButtonAdd != null) {
            mButtonAdd.setOnClickListener(v -> {
                Intent intent1 = new Intent(ListActivity.this, DetailReminderActivity.class);
                startActivity(intent1);
            });
        }
        addRemindersRecyclerView();

        Intent intent = getIntent();
        if (intent != null) {
            mMenuLastPosition = intent.getIntExtra(ExtrasCodes.ACTIVE_MENU_POSITION_CODE, mMenuLastPosition);
            Log.d("myapp", "position from extra : " + mMenuLastPosition);
        }

        if (savedInstanceState != null) {
            mCategories = savedInstanceState.getStringArray(CURRENT_CATEGORIES);
            mMenuLastPosition = savedInstanceState.getInt(CURRENT_POSITION_KEY);
            Log.d("myapp " + this.getClass().toString(), "current position from saved instance state : " + mMenuLastPosition);
        }
        mCategoryLoader.forceLoad();
        mReminderLoader.forceLoad();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OpenHelperManager.releaseHelper();
    }

    @Override
    protected void afterCategoriesLoaded() {
        super.afterCategoriesLoaded();
        renderList();
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
        checkedCategory = getCheckedCategory();
        Log.d("myapp", "rendered list of category : " + checkedCategory);
        TextView textView = (TextView) findViewById(R.id.list_category_name);
        if (textView != null) {
            textView.setText(checkedCategory);
        }
        mReminderLoader.setCategoryLoaded(checkedCategory);
        mReminderLoader.forceLoad();
    }

    private String getCheckedCategory() {
        return  mCategories[mMenuLastPosition - MENU_FIRST_CATEGORY_POSITION];
    }

    private void addRemindersRecyclerView() {
        mReminderLoader = (ReminderLoader) getLoaderManager().initLoader(ExtrasCodes.LOADER_REMINDER_ID, null, new RemindersCallbacks(this));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        mRemindersRecyclerView = (RecyclerView) findViewById(R.id.list_reminders);
        mRemindersRecyclerView.setLayoutManager(layoutManager);
        mRemindersRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRemindersRecyclerView.addOnScrollListener(new OnScrolledListenerReminders(mButtonAdd));
    }

    private class RemindersCallbacks implements LoaderManager.LoaderCallbacks<ArrayList<Reminder>> {
        private AbstractToolbarActivity activity;

        public RemindersCallbacks(AbstractToolbarActivity activity) {
            this.activity = activity;
        }

        @Override
        public Loader<ArrayList<Reminder>> onCreateLoader(int id, Bundle args) {
            ReminderLoader reminderLoader = null;
            if (id == ExtrasCodes.LOADER_REMINDER_ID) {
                reminderLoader = new ReminderLoader(activity);
            } else {
                Log.e("myapp", "unexcept loader id not equal reminder loader");
            }
            return reminderLoader;
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<Reminder>> reminders, ArrayList<Reminder> data) {
            mRemindersAdapter = new ReminderListAdapter(activity, data);
            mRemindersRecyclerView.setAdapter(mRemindersAdapter);
            Log.i("myapp " + this.getClass().toString(), "reminders load finished : " + data.size() + ". Setted in adapter");
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<Reminder>> loader) {
            Log.d("myapp " + this.getClass().toString(), "reminder loader reset");
            mRemindersAdapter.reset();
        }
    }
}
