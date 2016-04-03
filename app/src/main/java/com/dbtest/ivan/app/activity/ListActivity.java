//TODO buzz шины
//TODO softReference cache

//TODO ResultReceiver???


package com.dbtest.ivan.app.activity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.fragment.ReminderListFragment;
import com.dbtest.ivan.app.logic.db.entities.Reminder;
import com.dbtest.ivan.app.model.CategoryManager;
import com.dbtest.ivan.app.model.loader.ReminderLoader;
import com.dbtest.ivan.app.utils.ExtrasCodes;

import java.util.ArrayList;

public class ListActivity extends AbstractToolbarActivity
                          implements ReminderListFragment.OnItemSelectedListener {
    private int mMenuLastPosition = 4;

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
        final Intent intent = getIntent();
        if (intent != null) {
            int gettedPosition = intent.getIntExtra(ExtrasCodes.ACTIVE_MENU_POSITION_CODE, mMenuLastPosition);
            if (mMenuLastPosition != gettedPosition) {
                mMenuLastPosition = gettedPosition;
            }
            renderList(mMenuLastPosition);
            Log.d("myapp", "position from extra : " + mMenuLastPosition);
            mDrawer.setSelectionAtPosition(mMenuLastPosition);
        }

//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        ReminderListFragment fragment = new ReminderListFragment();
//        transaction.add(R.id.list_reminders, fragment);
//        transaction.commit();

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
    }

    public void setMenuLastPosition(int mMenuLastPosition) {
        this.mMenuLastPosition = mMenuLastPosition;
    }

    public void renderList(int position) {
        String checkedCategory = CategoryManager.getCategoryByPosition(position);
        TextView textView = (TextView) findViewById(R.id.list_category_name);

        Log.d("myapp", "rendered list of category : " + checkedCategory);
        if (textView != null) {
            textView.setText(checkedCategory);
        }
    }

    @Override
    public void onReminderSelected(int position) {
        Log.d("myapp", "reminder selected");
    }


}
