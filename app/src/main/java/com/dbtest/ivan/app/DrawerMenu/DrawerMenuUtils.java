package com.dbtest.ivan.app.DrawerMenu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.FriendsActivity;
import com.dbtest.ivan.app.activity.SettingsActivity;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class DrawerMenuUtils {
    private static boolean logged = false;
    private static Drawer drawerMenu = null;

    private static AccountHeader getAccountHeaderLogged(Activity activity) {
        AccountHeaderBuilder accountHeaderBuilder = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.menu_header)
                .withSelectionListEnabled(false)
                .withTextColor(activity.getResources().getColor(R.color.material_drawer_primary_text))
                .addProfiles(
                        new ProfileDrawerItem().withName("IvanS").withEmail("dev.ivansem@gmail.com")
                );
        return accountHeaderBuilder.build();
    }

    public static void setDrawerMenuOnActivity(final Activity activity, Toolbar toolbar) {
        Typeface sectionTypeFace = Typeface.create("", Typeface.BOLD);
        DrawerBuilder builder = new DrawerBuilder(activity)
                .withToolbar(toolbar)
                .withSelectedItemByPosition(2);
//                .withActionBarDrawerToggle(true);
        if (logged) { //if you logged
            builder.withAccountHeader(getAccountHeaderLogged(activity));
        } else { //if you not logged
            builder.withHeader(R.layout.drawer_header_unlogged)
                    .withOnDrawerListener(new AuthHeaderDrawerListener(activity));
        }
        builder.addDrawerItems(
                new SectionDrawerItem().withName("Categories").withTypeface(sectionTypeFace),
                new SecondaryDrawerItem().withName("all"),
                new SecondaryDrawerItem().withName("from friends"),
                new SecondaryDrawerItem().withName("first"),
                new SecondaryDrawerItem().withName("second"),
                new DividerDrawerItem(),
                new PrimaryDrawerItem().withName("Friends").withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
                        Log.d("MY_APP", "click Friends listener");
                        Intent intent = new Intent(activity, FriendsActivity.class);
                        activity.startActivity(intent);
                        return false;
                    }
                }),
                new PrimaryDrawerItem().withName("Settings").withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
                        Log.d("MY_APP", "click Settings listener");
                        Intent intent = new Intent(activity, SettingsActivity.class);
                        activity.startActivity(intent);
                        return false;
                    }
                })
        );
        drawerMenu = builder.build();
    }
}
