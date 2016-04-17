package com.dbtest.ivan.app.activity.abstract_toolbar_activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.dbtest.ivan.app.services.intent.SynchronizeIntentService;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class ReminderUpdateClickListener implements Drawer.OnDrawerItemClickListener {
    private Activity mActivity;

    public ReminderUpdateClickListener(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        Log.i("myapp", "clicked update reminders");
        Intent intent = new Intent(mActivity, SynchronizeIntentService.class);
        mActivity.startService(intent);
        return true;
    }
}
