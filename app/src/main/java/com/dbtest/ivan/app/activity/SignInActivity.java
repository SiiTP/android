package com.dbtest.ivan.app.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.abstract_toolbar_activity.AbstractToolbarActivity;
import com.dbtest.ivan.app.activity.list_activity.ListActivity;
import com.dbtest.ivan.app.receiver.CustomReceiver;
import com.dbtest.ivan.app.services.intent.SignInIntentService;
import com.dbtest.ivan.app.utils.EmailFocusListener;
import com.dbtest.ivan.app.utils.WaitingManager;
import com.dbtest.ivan.app.utils.watchers.MaxLengthTextWatcher;

public class SignInActivity extends AbstractToolbarActivity implements WaitingActivity {
    private static final int MENU_POSITION = -1; //activity not in menu list


    public static final String SIGNIN_EMAIL = "signin.Email";
    public static final String SIGNIN_PASSWORD = "signin.Password";

    private EditText emailView;
    private EditText passwordView;
    private Button submit;
    private ProgressBar bar;
    private CustomReceiver receiver;
    private Toast toast;
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
        super.onCreate(savedInstanceState);
        bar = (ProgressBar) findViewById(R.id.signin_bar);
        emailView = (EditText)findViewById(R.id.signin_email);
        passwordView = (EditText) findViewById(R.id.signin_password);
        submit = (Button) findViewById(R.id.signin_submit);
        bar.setVisibility(View.GONE);

        emailView.setOnFocusChangeListener(new EmailFocusListener((TextInputLayout) findViewById(R.id.signin_email_supp)));
        emailView.addTextChangedListener(new MaxLengthTextWatcher((TextInputLayout) findViewById(R.id.signin_email_supp), 48));
        passwordView.addTextChangedListener(new MaxLengthTextWatcher((TextInputLayout) findViewById(R.id.signin_password_supp),16));
        if (submit != null) {
            submit.setOnClickListener(v -> {
                CharSequence f = ((TextInputLayout) findViewById(R.id.signin_email_supp)).getError();
                if(f == null){
                    f = ((TextInputLayout) findViewById(R.id.signin_password_supp)).getError();
                }
                if(f == null) {
                    String email = null;
                    if (emailView != null) {
                        email = emailView.getText().toString();
                    }
                    String password = null;
                    if (passwordView != null) {
                        password = passwordView.getText().toString();
                    }
                    System.out.println(email + ' ' + password);

                    if(!email.isEmpty() && !password.isEmpty()) {
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
        WaitingManager.makeWaitingProgressBar(this, bar, isWaiting);
        WaitingManager.makeWaitingButton(this, submit, isWaiting);
        WaitingManager.makeWaitingView(passwordView, isWaiting);
        WaitingManager.makeWaitingView(emailView, isWaiting);
    }

    @Override
    public void notifyResult(String result) {
        if(toast == null) {
            toast = Toast.makeText(SignInActivity.this, "", Toast.LENGTH_LONG);
        }
        toast.setText(result);
        toast.show();
        if(result.equals(SignInIntentService.SUCCESS_MSG)){
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
        }
    }

}
