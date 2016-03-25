package com.dbtest.ivan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.services.intent.SignUpIntentService;

public class SignUpActivity extends AbstractToolbarActivity {
    private static final int MENU_POSITION = -1; //activity not in menu list

    public static final String SIGNUP_EMAIL = "signup.Email";
    public static final String SIGNUP_PASS = "signup.Password";
    public static final String SIGNUP_USERNAME = "signup.Username";
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

        Button submit = (Button) findViewById(R.id.signup_submit);
        if (submit != null) {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText signUp = (EditText) findViewById(R.id.signup_email);
                    String email = null;
                    if(signUp != null){
                        email = signUp.getText().toString();
                    }
                    signUp = (EditText) findViewById(R.id.signup_username);
                    String username = null;
                    if(signUp != null){
                        username = signUp.getText().toString();
                    }
                    signUp = (EditText) findViewById(R.id.signup_password);
                    String pass = null;
                    if(signUp != null){
                        pass = signUp.getText().toString();
                    }
                    signUp = (EditText) findViewById(R.id.signup_repeat_password);
                    String repeatPass = null;
                    if(signUp != null){
                        repeatPass = signUp.getText().toString();//todo check pass equality
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString(SIGNUP_EMAIL,email);
                    bundle.putString(SIGNUP_PASS,pass);
                    bundle.putString(SIGNUP_USERNAME,username);

                    Intent intent = new Intent(SignUpActivity.this, SignUpIntentService.class);
                    intent.putExtras(bundle);
                    startService(intent);
                }
            });
        }
    }
}
