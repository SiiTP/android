package com.dbtest.ivan.app.activity;

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
import com.dbtest.ivan.app.activity.drawer_menu.DrawerMenuManager;
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
        Log.v("myapp", "abstract activity on create");
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
        mCategoryLoader.forceLoad(); // загружем категории из базы
        Log.d("myapp", "categories installed");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mDrawer.setSelectionAtPosition(getMenuPosition());
    }

    protected void afterCategoriesLoaded() {
        DrawerMenuManager.setCategoriesSubItems(this, mCategories);
        mDrawer.setSelectionAtPosition(getMenuPosition());
    }



    private class CategoryCallbacks implements LoaderManager.LoaderCallbacks<ArrayList<Category>> {
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
//        Category category = new Category("all");
//        Category category2 = new Category("friends");
//        data.add(category);
//        data.add(category2);
            mCategories = Category.toStringArray(data);
            afterCategoriesLoaded();
            Log.d("myapp" + this.getClass().toString(), "category load finished : " + data.size());
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<Category>> loader) {
            mCategories = null;
        }
    }
}
