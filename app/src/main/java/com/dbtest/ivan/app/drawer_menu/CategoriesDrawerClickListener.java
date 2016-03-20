package com.dbtest.ivan.app.drawer_menu;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.dbtest.ivan.app.activity.ListActivity;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class CategoriesDrawerClickListener implements Drawer.OnDrawerItemClickListener {
    private Activity activity;

    public CategoriesDrawerClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        Log.d("myapp", "sub item click, position : " + position);
        drawerItem.withSetSelected(true);
        if ((activity instanceof ListActivity)) {
            Log.d("myapp", "activity is the ListActivity");
            ((ListActivity)activity).renderList("all");
        } else {
            Log.d("myapp", "activity is not instance of ListActivity");
            Intent i = new Intent(activity, ListActivity.class);
            activity.startActivity(i);
        }
        return false;
    }
}
