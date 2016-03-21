package com.dbtest.ivan.app.drawer_menu;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.dbtest.ivan.app.activity.AbstractToolbarActivity;
import com.dbtest.ivan.app.activity.ListActivity;
import com.dbtest.ivan.app.utils.ExtrasCodes;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public final class CategoriesDrawerClickListener implements Drawer.OnDrawerItemClickListener {
    private AbstractToolbarActivity activity;

    public CategoriesDrawerClickListener(AbstractToolbarActivity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        Log.d("myapp", "sub item click, position : " + position);
        drawerItem.withSetSelected(true);
        if ((activity instanceof ListActivity)) {
            ((ListActivity)activity).renderList(position);
            ((ListActivity)activity).setMenuLastPosition(position);
        } else {
            Intent i = new Intent(activity, ListActivity.class);
            i.putExtra(ExtrasCodes.ACTIVE_MENU_POSITION_CODE, position);
            activity.startActivity(i);
        }
        return false;
    }
}
