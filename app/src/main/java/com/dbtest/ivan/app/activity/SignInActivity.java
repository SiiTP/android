package com.dbtest.ivan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.services.intent.SignInIntentService;

public class SignInActivity extends AbstractToolbarActivity {
    private static final int MENU_POSITION = -1; //activity not in menu list


    public static final String SIGNIN_EMAIL = "signin.Email";
    public static final String SIGNIN_PASSWORD = "signin.Password";
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

        Button signinButton = (Button) findViewById(R.id.signin_submit);
        if (signinButton != null) {
            signinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    EditText formView = (EditText)findViewById(R.id.signin_email);
                    String email = null;
                    if ( formView != null) {

                        email = formView.getText().toString();
                    }
                    formView = (EditText) findViewById(R.id.signin_password);
                    String password = null;
                    if(formView != null){
                        password = formView.getText().toString();
                    }
                    System.out.println(email + ' ' + password);
                    Bundle bundle = new Bundle();
                    bundle.putString(SIGNIN_EMAIL, email);
                    bundle.putString(SIGNIN_PASSWORD, password);
                    Intent intent = new Intent(SignInActivity.this, SignInIntentService.class);
                    intent.putExtras(bundle);
                    startService(intent);
                }
            });
        }

    }
}
