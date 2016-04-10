package com.dbtest.ivan.app.activity.drawer_menu;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.AbstractToolbarActivity;
import com.dbtest.ivan.app.activity.FriendsActivity;
import com.dbtest.ivan.app.activity.ListActivity;
import com.dbtest.ivan.app.activity.SettingsActivity;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class DrawerMenuManager {
    private static boolean logged = false;
    public static final int ID_CATEGORIES_ITEM = 1;
    private static final int CATEGORY_SUBITEMS_LEVEL = 2;

    public static Drawer buildDrawerMenu(final AbstractToolbarActivity activity, Toolbar toolbar) {
        DrawerBuilder builder = new DrawerBuilder(activity)
                .withToolbar(toolbar);

        if (logged) { //if you logged
            builder.withAccountHeader(getAccountHeaderLogged(activity));
        } else { //if you not logged
            builder.withHeader(R.layout.drawer_header_unlogged)
                    .withOnDrawerListener(new AuthHeaderDrawerClickListener(activity));
        }

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
                new PrimaryDrawerItem().withName("Categories").withIdentifier(ID_CATEGORIES_ITEM)
                        .withIcon(R.drawable.ic_action_refresh)
                        .withIconTintingEnabled(true)
                        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                            @Override
                            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                Log.d("myapp", "clicked");
                                return true;
                            }
                        })
                        .withSelectable(false),
                new SecondaryDrawerItem().withName("Create").withIcon(R.drawable.ic_action_new_label).withLevel(CATEGORY_SUBITEMS_LEVEL)
        );
        return builder.build();
    }

    private static void eraseCategorieSubItems(Drawer drawer) {
        while (drawer.getDrawerItems().size() > ListActivity.MENU_FIRST_CATEGORY_POSITION - 1) {
            drawer.removeItemByPosition(ListActivity.MENU_FIRST_CATEGORY_POSITION);

        }
    }

    public static void setCategoriesSubItems(AbstractToolbarActivity activity, String[] categories) {
        Drawer drawer = activity.getDrawer();
        eraseCategorieSubItems(drawer);
        CategoriesDrawerClickListener categoriesClickListener = new CategoriesDrawerClickListener(activity);
        int iter = 0;
        for (String category : categories) {
            SecondaryDrawerItem item = new SecondaryDrawerItem().withName(category)
                    .withLevel(CATEGORY_SUBITEMS_LEVEL)
                    .withOnDrawerItemClickListener(categoriesClickListener);
            drawer.addItemAtPosition(item, ListActivity.MENU_FIRST_CATEGORY_POSITION + iter);
            iter++;
        }
    }

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


}
