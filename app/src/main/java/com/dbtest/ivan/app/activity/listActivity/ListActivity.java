//TODO buzz шины
//TODO softReference cache

//TODO ResultReceiver???


package com.dbtest.ivan.app.activity.listActivity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.WaitingActivity;
import com.dbtest.ivan.app.activity.abstractToolbarActivity.AbstractToolbarActivity;
import com.dbtest.ivan.app.activity.reminder.DetailReminderActivity;
import com.dbtest.ivan.app.logic.adapter.ReminderListAdapter;
import com.dbtest.ivan.app.logic.db.entities.Category;
import com.dbtest.ivan.app.logic.db.entities.Reminder;
import com.dbtest.ivan.app.logic.divider.DividerItemDecoration;
import com.dbtest.ivan.app.model.loader.CategoryLoader;
import com.dbtest.ivan.app.model.loader.ReminderLoader;
import com.dbtest.ivan.app.receiver.CustomReceiver;
import com.dbtest.ivan.app.services.intent.CategoryDeleteService;
import com.dbtest.ivan.app.services.intent.ReminderDeleteService;
import com.dbtest.ivan.app.utils.ExtrasCodes;
import com.dbtest.ivan.app.utils.WaitingManager;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.ArrayList;

public class ListActivity extends AbstractToolbarActivity implements WaitingActivity {
    private static final String CURRENT_POSITION_KEY = "currentPosition";
    private static final String CURRENT_CATEGORIES = "currentCategories";
    public static final String CURRENT_CATEGORY = "currentCategory";
    private int mMenuLastPosition = 4;
    private ReminderLoader mReminderLoader;
    private RecyclerView mRemindersRecyclerView;
    private ReminderListAdapter mRemindersAdapter;
    private FloatingActionButton mButtonAdd;
    private CategoryLoader mCategoryNotificationLoader;
    private ImageButton mButtonDelete;
    private ProgressBar mBar;
    private TextSwitcher mCategoryName;
    private CustomReceiver mReceiver;

    public CustomReceiver getReceiver() {
        return mReceiver;
    }

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
        mReceiver = new CustomReceiver(this);

        mCategoryName = (TextSwitcher) findViewById(R.id.list_category_name);
        if (mCategoryName != null) {
            mCategoryName.setInAnimation(this, R.anim.popup_enter);
            mCategoryName.setOutAnimation(this, R.anim.popup_exit);
            TextView categoryNameTextView = (TextView) getLayoutInflater().inflate(R.layout.list_category_name, null);
            TextView categoryNameTextView2 = (TextView) getLayoutInflater().inflate(R.layout.list_category_name, null);
            mCategoryName.addView(categoryNameTextView);
            mCategoryName.addView(categoryNameTextView2);
        }

        mBar = (ProgressBar) findViewById(R.id.list_bar);
        if (mBar != null) {
            mBar.setVisibility(View.INVISIBLE);
        }

        mButtonAdd = (FloatingActionButton) findViewById(R.id.list_add_reminder);
        if (mButtonAdd != null) {
            mButtonAdd.setOnClickListener(v -> {
                Intent intent1 = new Intent(ListActivity.this, DetailReminderActivity.class);
                startActivity(intent1);
            });
        }

        mButtonDelete = (ImageButton) findViewById(R.id.list_btn_category_delete);
        if (mButtonDelete != null) {
            mButtonDelete.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.dialog_delete_category_title))
                        .setMessage(getString(R.string.dialog_delete_category_message))
                        .setPositiveButton(getString(R.string.dialog_delete_category_btn_positive), (dialog, which) -> {
                            Intent intent = new Intent(this, CategoryDeleteService.class);
                            intent.putExtra(ExtrasCodes.CATEGORY_NAME_KEY, getCheckedCategory());
                            startService(intent);
                            setWaiting(true);
                            IntentFilter filter = new IntentFilter(CustomReceiver.WAITING_ACTION);
                            filter.addCategory(Intent.CATEGORY_DEFAULT);
                            LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, filter);
                        })
                        .setNegativeButton(getString(R.string.dialog_delete_category_btn_negative), (dialog1, which1) -> {
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            });
        }

        addRemindersRecyclerView();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mMenuLastPosition = bundle.getInt(ExtrasCodes.ACTIVE_MENU_POSITION_CODE, mMenuLastPosition);
        }

        if (savedInstanceState != null) {
            mCategories = savedInstanceState.getStringArray(CURRENT_CATEGORIES);
            mMenuLastPosition = savedInstanceState.getInt(CURRENT_POSITION_KEY);
            mDrawer.setSelectionAtPosition(mMenuLastPosition);
        }
        mReminderLoader.forceLoad();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mMenuLastPosition = bundle.getInt(ExtrasCodes.ACTIVE_MENU_POSITION_CODE, mMenuLastPosition);
            String currentCategory = bundle.getString(CURRENT_CATEGORY);
            if (currentCategory != null) {
                CategoryNotificationCallbacks notificationCallbacks = new CategoryNotificationCallbacks(this);
                notificationCallbacks.setCurrentCategory(currentCategory);
                mCategoryNotificationLoader = (CategoryLoader) getLoaderManager().initLoader(ExtrasCodes.LOADER_CATEGORY_NOTIFICATION_ID, null, notificationCallbacks);
                mCategoryLoader.stopLoading();
                Log.d("myapp", "forceload when resume soon");
                mCategoryNotificationLoader.forceLoad();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
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
        if (checkedCategory.equals(Category.CATEGORY_ALL_NAME) || checkedCategory.equals(Category.CATEGORY_FRIENDS_NAME)) {
            mButtonDelete.setVisibility(View.INVISIBLE);
        } else {
            mButtonDelete.setVisibility(View.VISIBLE);
        }

        if (mCategoryName != null) {
            System.out.println("!");
            mCategoryName.setText(checkedCategory);
        }
        mReminderLoader.setCategoryLoaded(checkedCategory);
        mReminderLoader.forceLoad();
    }

    private String getCheckedCategory() {
        return mCategories[mMenuLastPosition - MENU_FIRST_CATEGORY_POSITION];
    }

    private void addRemindersRecyclerView() {
        mReminderLoader = (ReminderLoader) getLoaderManager().initLoader(ExtrasCodes.LOADER_REMINDER_ID, null, new RemindersCallbacks(this));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        mRemindersRecyclerView = (RecyclerView) findViewById(R.id.list_reminders);
        mRemindersRecyclerView.setLayoutManager(layoutManager);
        mRemindersRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRemindersRecyclerView.addOnScrollListener(new OnScrolledListenerReminders(mButtonAdd));
    }

    @Override
    public void setWaiting(boolean isWaiting) {
        WaitingManager.makeWaitingProgressBar(this, mBar, isWaiting);
    }

    @Override
    public void notifyResult(String result) {
        Log.d("myapp", "result : " + result);

        if (result.equals(CategoryDeleteService.SUCCESS_MSG)) {
            goToAllCategory();
            mCategoryLoader.forceLoad();
            Toast.makeText(this, "category was deleted", Toast.LENGTH_LONG).show();
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        }

        if (result.equals(ReminderDeleteService.SUCCESS_MSG)) {
            renderList();
            Toast.makeText(this, "reminder was deleted", Toast.LENGTH_LONG).show();
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        }
    }

    private void goToAllCategory() {
        mMenuLastPosition = MENU_FIRST_CATEGORY_POSITION;
        renderList();
    }

    private class RemindersCallbacks implements LoaderManager.LoaderCallbacks<ArrayList<Reminder>> {
        private ListActivity activity;

        public RemindersCallbacks(ListActivity activity) {
            this.activity = activity;
        }

        @Override
        public Loader<ArrayList<Reminder>> onCreateLoader(int id, Bundle args) {
            ReminderLoader reminderLoader = null;
            if (id == ExtrasCodes.LOADER_REMINDER_ID) {
                reminderLoader = new ReminderLoader(activity);
            } else {
                Log.e("myapp", "unexcept, loader id not equal reminder loader");
            }
            return reminderLoader;
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<Reminder>> reminders, ArrayList<Reminder> data) {
            mRemindersAdapter = new ReminderListAdapter(activity, data);
            mRemindersRecyclerView.setAdapter(mRemindersAdapter);
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<Reminder>> loader) {
            mRemindersAdapter.reset();
        }
    }

    //когда запускаем приложение по клику из нотификации
    private class CategoryNotificationCallbacks extends CategoryCallbacks {

        private String currentCategory;
        private AbstractToolbarActivity activity;

        public CategoryNotificationCallbacks(AbstractToolbarActivity activity) {
            super(activity);
            this.activity = activity;
        }

        @Override
        public Loader<ArrayList<Category>> onCreateLoader(int id, Bundle args) {
            if (id == ExtrasCodes.LOADER_CATEGORY_NOTIFICATION_ID) {
                mCategoryNotificationLoader = new CategoryLoader(activity);
            } else {
                Log.e("myapp", "unexcept loader id not equal category notification loader");
            }
            return mCategoryNotificationLoader;
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<Category>> loader, ArrayList<Category> data) {
            Log.d("myapp", "soon loader finished callback");
            super.onLoadFinished(loader, data);
            int index = getCategoryIndexByName(currentCategory);
            mMenuLastPosition = index + MENU_FIRST_CATEGORY_POSITION;

//            Log.d("myapp", "index : " + index);
//            Log.d("myapp", "current category menu last position : " + mMenuLastPosition);
            renderList();
        }

        public void setCurrentCategory(String category) {
            this.currentCategory = category;
        }
    }
}
