//TODO Что должно быть в gitignore?
//TODO 1 меню на все активити? метод build в navbaractivity
//TODO drawer странное поведение onOpen onClose
//TODO buzz шины

//TODO ResultReceiver???


package com.dbtest.ivan.app.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dbtest.ivan.app.R;

public class ListActivity extends AbstractToolbarActivity {
    public static final int MENU_POSITION = 4;

    @NonNull
    @Override
    protected Integer getBodyResId() {
        return R.layout.activity_list;
    }

    @NonNull
    @Override
    protected Integer getMenuPosition() {
        return MENU_POSITION;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void renderList(String category) {
        Log.d("myapp", "rendered list of category : " + category);
    }
}
