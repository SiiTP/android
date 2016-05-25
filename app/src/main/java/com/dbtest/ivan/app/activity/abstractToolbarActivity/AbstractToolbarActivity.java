package com.dbtest.ivan.app.activity.abstractToolbarActivity;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.logic.db.entities.Category;
import com.dbtest.ivan.app.model.loader.CategoryLoader;
import com.dbtest.ivan.app.utils.ExtrasCodes;
import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;

public abstract class AbstractToolbarActivity extends AppCompatActivity {
    public static final int MENU_FIRST_CATEGORY_POSITION = 4; //for DrawerMenuManager позиция, начиная с которой категории вставляются

    protected CategoryLoader mCategoryLoader;
    protected String[] mCategories;

    protected Toolbar mToolbar;
    protected Drawer mDrawer;

    protected Category allCategory;

    @NonNull
    protected abstract Integer getBodyResId();

    @NonNull
    public abstract Integer getMenuPosition();

    public Drawer getDrawer() {
        return mDrawer;
    }

    protected void setToolbarAndMenu() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCategoryLoader = (CategoryLoader) getLoaderManager().initLoader(ExtrasCodes.LOADER_CATEGORY_ID, null, new CategoryCallbacks(this));
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mDrawer = DrawerMenuManager.buildDrawerMenu(this, mToolbar);
        } else {
            Log.e("myapp", "no toolbar");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout mLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_abstract, null);
        View addingView = getLayoutInflater().inflate(getBodyResId(), null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
        );
        layoutParams.addRule(RelativeLayout.BELOW, R.id.toolbar);
        addingView.setLayoutParams(layoutParams);

        mLayout.addView(addingView);
        setContentView(mLayout);

        setToolbarAndMenu();

        allCategory = new Category(Category.CATEGORY_ALL_NAME);
        Log.d("myapp", "forceload when create");
        mCategoryLoader.forceLoad();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myapp", "forceload when restart");
        mCategoryLoader.forceLoad();
//        mDrawer.setSelectionAtPosition(getMenuPosition());
    }

    protected void afterCategoriesLoaded() {
        DrawerMenuManager.setCategoriesSubItems(this, mCategories);
        mDrawer.setSelectionAtPosition(getMenuPosition());
    }

    protected int getCategoryIndexByName(String category) {
        for (int categoryIndex = 0; categoryIndex < mCategories.length; categoryIndex++) {
            if (mCategories[categoryIndex].equals(category)) {
                return categoryIndex;
            }
        }
        Log.e("myapp AbstractActivity", "no category with name : " + category);
        return -1;
    }



    protected class CategoryCallbacks implements LoaderManager.LoaderCallbacks<ArrayList<Category>> {
        private AbstractToolbarActivity activity;

        public CategoryCallbacks(AbstractToolbarActivity activity) {
            this.activity = activity;
        }

        @Override
        public Loader<ArrayList<Category>> onCreateLoader(int id, Bundle args) {
            if (id == ExtrasCodes.LOADER_CATEGORY_ID) {
                mCategoryLoader = new CategoryLoader(activity);
            } else {
                Log.e("myapp", "unexcept loader id not equal category loader");
            }
            return mCategoryLoader;
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<Category>> loader, ArrayList<Category> data) {
            Log.d("myapp", "parent loader finished callback");
            if (!data.get(0).getName().equals(allCategory.getName())) {
                data.add(0, allCategory);
            }
            mCategories = Category.toStringArray(data);
            Log.i("myapp", "loaded categories : " + mCategories.length);
            afterCategoriesLoaded();
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<Category>> loader) {
            mCategories = null;
        }
    }
}
