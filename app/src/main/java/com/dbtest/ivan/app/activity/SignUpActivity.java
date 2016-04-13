package com.dbtest.ivan.app.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.receiver.CustomReceiver;
import com.dbtest.ivan.app.services.intent.SignUpIntentService;

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
        if (submit != null) {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String email = null;
                    if(emailView != null){
                        email = emailView.getText().toString();
                    }

                    String username = null;
                    if(usernameView != null){
                        username = usernameView.getText().toString();
                    }

                    String pass = null;
                    if(passwordView != null){
                        pass = passwordView.getText().toString();
                    }

                    String repeatPass = null;
                    if(repeatPasswordView != null){
                        repeatPass = repeatPasswordView.getText().toString();//todo check pass equality
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString(SIGNUP_EMAIL,email);
                    bundle.putString(SIGNUP_PASS,pass);
                    bundle.putString(SIGNUP_USERNAME, username);

                    IntentFilter filter = new IntentFilter(CustomReceiver.WAITING_ACTION);
                    filter.addCategory(Intent.CATEGORY_DEFAULT);
                    receiver = new CustomReceiver(SignUpActivity.this);
                    LocalBroadcastManager.getInstance(SignUpActivity.this).registerReceiver(receiver, filter);
                    Intent intent = new Intent(SignUpActivity.this, SignUpIntentService.class);
                    intent.putExtras(bundle);
                    startService(intent);
                    setWaiting(true);
//                    submit.startAnimation(AnimationUtils.loadAnimation(SignUpActivity.this,R.anim.fade_out));
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
        if(isWaiting){
            bar.setVisibility(View.VISIBLE);
            bar.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_in));
            submit.setTextColor(Color.GRAY);
        }else{
            bar.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_out));
            bar.setVisibility(View.GONE);
            submit.setTextColor(Color.parseColor("#FF5722"));
        }
        bar.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
        isWaiting = !isWaiting;
        emailView.setFocusableInTouchMode(isWaiting);
        emailView.setEnabled(isWaiting);
        passwordView.setFocusableInTouchMode(isWaiting);
        passwordView.setEnabled(isWaiting);
        usernameView.setEnabled(isWaiting);
        usernameView.setFocusableInTouchMode(isWaiting);
        repeatPasswordView.setEnabled(isWaiting);
        repeatPasswordView.setFocusableInTouchMode(isWaiting);
        submit.setEnabled(isWaiting);
        submit.setFocusableInTouchMode(isWaiting);

    }

    @Override
    public void notifyResult(String result) {
        if(toast == null) {
            toast = Toast.makeText(SignUpActivity.this, "", Toast.LENGTH_LONG);
//            snackbar = Snackbar.make(findViewById(R.id.signup_layout), "None", Snackbar.LENGTH_LONG);
//            TextView textView = (TextView)snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
//            textView.setTextColor(Color.WHITE);
        }
//        snackbar.setText(result);
//        snackbar.show();
        toast.setText(result);
        toast.show();
    }
}
