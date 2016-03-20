package com.dbtest.ivan.app.drawer_menu;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.SignInActivity;
import com.dbtest.ivan.app.activity.SignUpActivity;
import com.mikepenz.materialdrawer.Drawer;

public final class AuthHeaderDrawerClickListener implements Drawer.OnDrawerListener {
    private Activity activity;

    AuthHeaderDrawerClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onDrawerOpened(View view) {
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
            Log.e("myapp", "No signin button in menu");
        }

        Button btnSignUp = (Button) activity.findViewById(R.id.h_unlogged_btn_signup);
        if (btnSignUp != null) {
            btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, SignUpActivity.class);
                    activity.startActivity(intent);
                }
            });
        } else {
            Log.e("myapp", "No signup button in menu");
        }
    }

    @Override
    public void onDrawerClosed(View view) {
    }

    @Override
    public void onDrawerSlide(View view, float v) {

    }
}
