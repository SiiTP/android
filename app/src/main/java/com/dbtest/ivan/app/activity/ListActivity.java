//TODO Что должно быть в gitignore?
//TODO 1 меню на все активити? метод build в navbaractivity
//TODO drawer странное поведение onOpen onClose
//TODO buzz шины

//TODO ResultReceiver???


package com.dbtest.ivan.app.activity;

import android.os.Bundle;
import android.util.Log;

import com.dbtest.ivan.app.R;

public class ListActivity extends AbstractActivity {

    @Override
    protected int getBodyResId() {
        return R.layout.activity_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("myapp", "onCreate list");
    }
}
