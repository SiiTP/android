package com.dbtest.ivan.app.DrawerMenu;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.SignInActivity;
import com.mikepenz.materialdrawer.Drawer;

public final class AuthHeaderDrawerListener implements Drawer.OnDrawerListener {
    private Activity activity;

    AuthHeaderDrawerListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onDrawerOpened(View view) {
        Log.d("MY_APP", "drawer opened " + view.getId());
        Button btnSignIn = (Button) activity.findViewById(R.id.h_unlogged_btn_signin);
        if (btnSignIn != null) {
            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, SignInActivity.class);
                    activity.startActivity(intent);
                }
            });
        } else {
            Log.e("MY_APP", "No signin button in menu");
        }
    }

    @Override
    public void onDrawerClosed(View view) {
        Log.d("MY_APP", "drawer closed " + view.getId());
    }

    @Override
    public void onDrawerSlide(View view, float v) {

    }
}
