package com.dbtest.ivan.app.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
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
import com.dbtest.ivan.app.activity.abstractToolbarActivity.AbstractToolbarActivity;
import com.dbtest.ivan.app.activity.listActivity.ListActivity;
import com.dbtest.ivan.app.receiver.CustomReceiver;
import com.dbtest.ivan.app.services.intent.SignUpIntentService;
import com.dbtest.ivan.app.utils.EmailFocusListener;
import com.dbtest.ivan.app.utils.WaitingManager;
import com.dbtest.ivan.app.utils.watchers.MaxLengthTextWatcher;
import com.dbtest.ivan.app.utils.watchers.PasswordsTextWatcher;

public class SignUpActivity extends AbstractToolbarActivity implements WaitingActivity {
    private static final int MENU_POSITION = -1; //activity not in menu list

    public static final String SIGNUP_EMAIL = "signup.Email";
    public static final String SIGNUP_PASS = "signup.Password";
    public static final String SIGNUP_USERNAME = "signup.Username";


    private EditText emailView;
    private EditText passwordView;
    private EditText usernameView;
    private EditText repeatPasswordView;
    private Button submit;
    private BroadcastReceiver receiver;
    private ProgressBar bar;
    private Toast toast;
    @NonNull
    @Override
    protected Integer getBodyResId() {
        return R.layout.activity_signup;
    }

    @NonNull
    @Override
    public Integer getMenuPosition() {
        return MENU_POSITION;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bar = (ProgressBar) findViewById(R.id.signup_bar);
        bar.setVisibility(View.GONE);
        emailView = (EditText) findViewById(R.id.signup_email);
        usernameView = (EditText) findViewById(R.id.signup_username);
        passwordView = (EditText) findViewById(R.id.signup_password);
        repeatPasswordView = (EditText) findViewById(R.id.signup_repeat_password);
        submit = (Button) findViewById(R.id.signup_submit);

        repeatPasswordView.addTextChangedListener(new PasswordsTextWatcher((TextInputLayout)findViewById(R.id.signup_repeat_password_supp),passwordView));
        passwordView.addTextChangedListener(new MaxLengthTextWatcher((TextInputLayout)findViewById(R.id.signup_password_supp),16));
        passwordView.addTextChangedListener(new PasswordsTextWatcher((TextInputLayout)findViewById(R.id.signup_repeat_password_supp),repeatPasswordView));
        usernameView.addTextChangedListener(new MaxLengthTextWatcher((TextInputLayout)findViewById(R.id.signup_username_supp),32));
        emailView.addTextChangedListener(new MaxLengthTextWatcher((TextInputLayout) findViewById(R.id.signup_email_supp), 48));
        emailView.setOnFocusChangeListener(new EmailFocusListener((TextInputLayout) findViewById(R.id.signup_email_supp)));

        if (submit != null) {
            submit.setOnClickListener(v -> {

                CharSequence f  = ((TextInputLayout)findViewById(R.id.signup_repeat_password_supp)).getError();
                if(f == null) {
                    f = ((TextInputLayout) findViewById(R.id.signup_password_supp)).getError();
                }
                if(f == null){
                    f = ((TextInputLayout) findViewById(R.id.signup_email_supp)).getError();
                }
                if(f == null){
                    f = ((TextInputLayout) findViewById(R.id.signup_username_supp)).getError();
                }


                if(f == null) {
                    String email = null;
                    if (emailView != null) {
                        email = emailView.getText().toString();
                    }

                    String username = null;
                    if (usernameView != null) {
                        username = usernameView.getText().toString();
                    }

                    String pass = null;
                    if (passwordView != null) {
                        pass = passwordView.getText().toString();
                    }
                    if((!email.isEmpty() && !pass.isEmpty()) && !username.isEmpty()) {
                        Bundle bundle = new Bundle();
                        bundle.putString(SIGNUP_EMAIL, email);
                        bundle.putString(SIGNUP_PASS, pass);
                        bundle.putString(SIGNUP_USERNAME, username);

                        IntentFilter filter = new IntentFilter(CustomReceiver.WAITING_ACTION);
                        filter.addCategory(Intent.CATEGORY_DEFAULT);
                        receiver = new CustomReceiver(SignUpActivity.this);
                        LocalBroadcastManager.getInstance(SignUpActivity.this).registerReceiver(receiver, filter);
                        Intent intent = new Intent(SignUpActivity.this, SignUpIntentService.class);
                        intent.putExtras(bundle);
                        startService(intent);
                        setWaiting(true);
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
        WaitingManager.makeWaitingView(repeatPasswordView, isWaiting);
        WaitingManager.makeWaitingView(usernameView, isWaiting);
        WaitingManager.makeWaitingView(passwordView, isWaiting);
        WaitingManager.makeWaitingView(emailView, isWaiting);
        WaitingManager.makeWaitingButton(this, submit, isWaiting);
        WaitingManager.makeWaitingProgressBar(this, bar, isWaiting);
    }
    @Override
    public void notifyResult(String result) {
        if(toast == null) {
            toast = Toast.makeText(SignUpActivity.this, "", Toast.LENGTH_LONG);
        }
        toast.setText(result);
        toast.show();
        Class<? extends Activity> nextActivity;
        switch (result) {
            case SignUpIntentService.SUCCESS_MSG:
                nextActivity = ListActivity.class;
                break;
            case SignUpIntentService.HALF_SUCCESS_MSG:
                nextActivity = SignInActivity.class;
            default:
                nextActivity = null;
        }
        if(nextActivity != null) {
            Intent intent = new Intent(this, nextActivity);
            startActivity(intent);
        }
    }
}
