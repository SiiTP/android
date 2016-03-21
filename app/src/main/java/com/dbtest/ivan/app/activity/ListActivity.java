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

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.utils.ExtrasCodes;

public class ListActivity extends AbstractToolbarActivity {
    private static int sMenuLastPosition = 5;

    @NonNull
    @Override
    protected Integer getBodyResId() {
        return R.layout.activity_list;
    }

    @NonNull
    @Override
    public Integer getMenuPosition() {
        return sMenuLastPosition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            sMenuLastPosition = intent.getIntExtra(ExtrasCodes.ACTIVE_MENU_POSITION_CODE, sMenuLastPosition);
            Log.d("myapp", "position from extra : " + sMenuLastPosition);
            mDrawer.setSelectionAtPosition(sMenuLastPosition);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDrawer.setSelectionAtPosition(sMenuLastPosition);
    }

    public void renderList(String category) {
        Log.d("myapp", "rendered list of category : " + category);
    }
}
