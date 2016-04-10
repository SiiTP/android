package com.dbtest.ivan.app.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dbtest.ivan.app.activity.AbstractToolbarActivity;

public class CategoryManager {
    private static String[] bestCategories = {"all", "from friends"}; //get from db


    @NonNull
    public static String[] getBestCategories() {
        return bestCategories;
    }

    @Nullable
    public static String getCategoryByPosition(int position) {
        if (AbstractToolbarActivity.MENU_FIRST_CATEGORY_POSITION <= position &&
                position < AbstractToolbarActivity.MENU_FIRST_CATEGORY_POSITION + bestCategories.length) {
            return bestCategories[position - AbstractToolbarActivity.MENU_FIRST_CATEGORY_POSITION];
        } else {
            Log.e("myapp", "No category with position : " + position);
            return null;
        }
    }
}
