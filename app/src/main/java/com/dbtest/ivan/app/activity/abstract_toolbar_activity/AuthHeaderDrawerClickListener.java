package com.dbtest.ivan.app.activity.abstract_toolbar_activity;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.SignInActivity;
import com.dbtest.ivan.app.activity.SignUpActivity;
import com.mikepenz.materialdrawer.Drawer;

public final class AuthHeaderDrawerClickListener implements Drawer.OnDrawerListener {
    private AbstractToolbarActivity activity;

    AuthHeaderDrawerClickListener(AbstractToolbarActivity activity) {
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
                    goToActivity(activity, intent);
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
                    goToActivity(activity, intent);
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

    private void goToActivity(AbstractToolbarActivity activity, Intent intent) {
        activity.startActivity(intent);
        closeDrawerAfterDelay(activity.getDrawer());
    }

    private void closeDrawerAfterDelay(final Drawer drawer) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                drawer.closeDrawer();
            }
        }, 200);
    }
}
