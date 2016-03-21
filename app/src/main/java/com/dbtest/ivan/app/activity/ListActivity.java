//TODO Что должно быть в gitignore?
//TODO 1 меню на все активити? метод build в navbaractivity
//TODO drawer странное поведение onOpen onClose
//TODO buzz шины

//TODO ResultReceiver???


package com.dbtest.ivan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.model.CategoryManager;
import com.dbtest.ivan.app.utils.ExtrasCodes;

public class ListActivity extends AbstractToolbarActivity {
    private int mMenuLastPosition = 5;

    @NonNull
    @Override
    protected Integer getBodyResId() {
        return R.layout.activity_list;
    }

    @NonNull
    @Override
    public Integer getMenuPosition() {
        return mMenuLastPosition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            int gettedPosition = intent.getIntExtra(ExtrasCodes.ACTIVE_MENU_POSITION_CODE, mMenuLastPosition);
            if (mMenuLastPosition != gettedPosition) {
                mMenuLastPosition = gettedPosition;
                renderList(mMenuLastPosition);
            }
            Log.d("myapp", "position from extra : " + mMenuLastPosition);
            mDrawer.setSelectionAtPosition(mMenuLastPosition);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDrawer.setSelectionAtPosition(mMenuLastPosition);
    }

    public void setMenuLastPosition(int mMenuLastPosition) {
        this.mMenuLastPosition = mMenuLastPosition;
    }

    public void renderList(int position) {
        String checkedCategory = CategoryManager.getCategoryByPosition(position);
        TextView textView = (TextView) findViewById(R.id.list_category_name);
        if (checkedCategory != null) {
            textView.setText(checkedCategory);
        } else {
            textView.setText("not found view by position");
        }
        Log.d("myapp", "rendered list of category : " + checkedCategory);
    }
}
