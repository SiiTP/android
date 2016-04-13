package com.dbtest.ivan.app.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.receiver.CustomReceiver;
import com.dbtest.ivan.app.services.intent.SignInIntentService;

public class SignInActivity extends AbstractToolbarActivity implements WaitingActivity {
    private static final int MENU_POSITION = -1; //activity not in menu list


    public static final String SIGNIN_EMAIL = "signin.Email";
    public static final String SIGNIN_PASSWORD = "signin.Password";

    private EditText emailView;
    private EditText passwordView;
    private Button submit;
    private ProgressBar bar;
    private CustomReceiver receiver;
    @NonNull
    @Override
    protected Integer getBodyResId() {
        return R.layout.activity_signin;
    }

    @NonNull
    @Override
    public Integer getMenuPosition() {
        return MENU_POSITION;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//todo постоянное открытие одной и той же аквтивити каждый раз создает новую аквтивити на верх стека??!?
        bar = (ProgressBar) findViewById(R.id.signin_bar);
        emailView = (EditText)findViewById(R.id.signin_email);
        passwordView = (EditText) findViewById(R.id.signin_password);
        submit = (Button) findViewById(R.id.signin_submit);
        bar.setVisibility(View.GONE);
//        toggleWaitingMode();
        if (submit != null) {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = null;
                    if ( emailView != null) {
                        email = emailView.getText().toString();
                    }
                    String password = null;
                    if(passwordView != null){
                        password = passwordView.getText().toString();
                    }
                    System.out.println(email + ' ' + password);

                    Bundle bundle = new Bundle();
                    bundle.putString(SIGNIN_EMAIL, email);
                    bundle.putString(SIGNIN_PASSWORD, password);
                    Intent intent = new Intent(SignInActivity.this, SignInIntentService.class);
                    intent.putExtras(bundle);
                    startService(intent);
                    setWaiting(true);
                    IntentFilter filter = new IntentFilter(CustomReceiver.WAITING_ACTION);
                    filter.addCategory(Intent.CATEGORY_DEFAULT);
                    receiver = new CustomReceiver(SignInActivity.this);
                    LocalBroadcastManager.getInstance(SignInActivity.this).registerReceiver(receiver, filter);
                }
            });
        }

    }

    @Override
    protected void onPause() {
        if(receiver != null) LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onPause();
    }

    @Override
    public void setWaiting(boolean isWaiting) {
        if(isWaiting) {
            bar.setVisibility(View.VISIBLE);
        }else{
            bar.setVisibility(View.GONE);
        }
        bar.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
        isWaiting = !isWaiting;
        submit.setFocusableInTouchMode(isWaiting);
        submit.setEnabled(isWaiting);
        passwordView.setFocusableInTouchMode(isWaiting);
        passwordView.setEnabled(isWaiting);
        emailView.setFocusableInTouchMode(isWaiting);
        emailView.setEnabled(isWaiting);


    }

}
