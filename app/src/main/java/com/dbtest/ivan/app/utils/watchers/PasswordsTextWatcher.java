package com.dbtest.ivan.app.utils.watchers;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by ivan on 18.04.16.
 */
public class PasswordsTextWatcher implements TextWatcher {
    private TextInputLayout layout;
    private EditText passwordView;
    public static final String PASS_NOT_EQUALS = "passwords don`t match";
    public PasswordsTextWatcher(TextInputLayout layout,EditText passwordView) {
        this.layout = layout;
        this.passwordView = passwordView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(!passwordView.getText().toString().isEmpty()) {
            if (!s.toString().equals(passwordView.getText().toString()) && !s.toString().isEmpty()) {
                layout.setError(PASS_NOT_EQUALS);
            } else {
                if (layout.getError() != null && layout.getError().equals(PASS_NOT_EQUALS)) {
                    layout.setErrorEnabled(false);
                    layout.setError(null);
                }
            }
        }
    }
}
