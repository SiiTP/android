//TODO Что должно быть в gitignore?
//TODO 1 меню на все активити?

package com.dbtest.ivan.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dbtest.ivan.app.DrawerMenu.DrawerMenuUtils;
import com.dbtest.ivan.app.R;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerMenuUtils.setDrawerMenuOnActivity(this, toolbar);
    }
}
