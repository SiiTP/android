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

        //if you logged
//        AccountHeader accHeader = DrawerMenuUtils.getAccountHeaderLogged(this);

        DrawerMenuUtils.setDrawerMenuOnActivity(this, toolbar);
//        Typeface sectionTypeFace = Typeface.create("", Typeface.BOLD);
//        DrawerBuilder builder = new DrawerBuilder(this)
//                .withToolbar(toolbar)
//                .withActionBarDrawerToggle(true)
////                .withAccountHeader(accHeader)
//                .withHeader(R.layout.drawer_header_unlogged) //if you not logged
//                .withOnDrawerListener(new Drawer.OnDrawerListener() {
//                    @Override
//                    public void onDrawerOpened(View view) {
//                        Log.i("MY_APP", "drawer opened " + view.getId());
//                        Button btnSignIn = (Button) findViewById(R.id.h_unlogged_btn_signin);
//                        if (btnSignIn != null) {
//                            btnSignIn.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Intent intent = new Intent(ListActivity.this, SignInActivity.class);
//                                    startActivity(intent);
//                                }
//                            });
//                        } else {
//                            Log.e("MY_APP", "No signin button in menu");
//                        }
//                    }
//
//                    @Override
//                    public void onDrawerClosed(View view) {
//                        Log.i("MY_APP", "drawer closed " + view.getId());
//                    }
//
//                    @Override
//                    public void onDrawerSlide(View view, float v) {
//
//                    }
//                })
//                .addDrawerItems(
//                        new PrimaryDrawerItem().withName("Reminders"),
//                        new PrimaryDrawerItem().withName("Friends"),
//                        new SectionDrawerItem().withName("Categories").withTypeface(sectionTypeFace),
//                        new SecondaryDrawerItem().withName("first"),
//                        new SecondaryDrawerItem().withName("second")
//                );
//        builder.build();

    }
}
