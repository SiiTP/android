package com.dbtest.ivan.app.drawer_menu;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.AbstractToolbarActivity;
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
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class DrawerMenuManager {
    private static boolean logged = false;

    private static AccountHeader getAccountHeaderLogged(Activity activity) {
        AccountHeaderBuilder accountHeaderBuilder = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.menu_header)
                .withSelectionListEnabled(false)
                .withTextColor(ContextCompat.getColor(activity, R.color.material_drawer_primary_text))
                .addProfiles(
                        new ProfileDrawerItem().withName("IvanS").withEmail("dev.ivansem@gmail.com")
                );
        return accountHeaderBuilder.build();
    }

    public static Drawer buildDrawerMenu(final AbstractToolbarActivity activity, Toolbar toolbar) {
        CategoriesDrawerClickListener categoriesClickListener = new CategoriesDrawerClickListener(activity);
        DrawerBuilder builder = new DrawerBuilder(activity)
                .withToolbar(toolbar);

        //position of selected point
        Integer activePosition = activity.getMenuPosition();
        if (activePosition > 0) {
                builder.withSelectedItemByPosition(activePosition);
        }

        if (logged) { //if you logged
            builder.withAccountHeader(getAccountHeaderLogged(activity));
        } else { //if you not logged
            builder.withHeader(R.layout.drawer_header_unlogged)
                    .withOnDrawerListener(new AuthHeaderDrawerClickListener(activity));
        }

        builder.withTranslucentNavigationBarProgrammatically(true);

        builder.addDrawerItems(
                new PrimaryDrawerItem().withName("Friends").withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
                        Intent intent = new Intent(activity, FriendsActivity.class);
                        activity.startActivity(intent);
                        return false;
                    }
                }),
                new PrimaryDrawerItem().withName("Settings").withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
                        Intent intent = new Intent(activity, SettingsActivity.class);
                        activity.startActivity(intent);
                        return false;
                    }
                }),
                new DividerDrawerItem(),
                new PrimaryDrawerItem().withName("Best categories").withSubItems(
                        new SecondaryDrawerItem().withName("all").withOnDrawerItemClickListener(categoriesClickListener),
                        new SecondaryDrawerItem().withName("from friends").withOnDrawerItemClickListener(categoriesClickListener),
                        new SecondaryDrawerItem().withName("first").withOnDrawerItemClickListener(categoriesClickListener),
                        new SecondaryDrawerItem().withName("second").withOnDrawerItemClickListener(categoriesClickListener)
                ).withIsExpanded(true).withSelectable(false)

        );
        return builder.build();
    }
}
