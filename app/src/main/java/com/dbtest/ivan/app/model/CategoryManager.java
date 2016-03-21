package com.dbtest.ivan.app.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class CategoryManager {
    public static final int POSITION_OF_FIRST = 5;
    private static String[] bestCategories = {"all", "from friends", "first", "second"}; //get from db

    @NonNull
    public static String[] getBestCategories() {
        return bestCategories;
    }

    @Nullable
    public static String getCategoryByPosition(int position) {
        if (POSITION_OF_FIRST <= position && position < POSITION_OF_FIRST + bestCategories.length) {
            return bestCategories[position - POSITION_OF_FIRST];
        } else {
            Log.e("myapp", "No category with position : " + position);
            return null;
        }
    }
}
