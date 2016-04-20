package com.dbtest.ivan.app.utils;

import android.support.design.widget.TextInputLayout;
import android.view.View;

/**
 * Created by ivan on 19.04.16.
 */
public class EmailFocusListener implements View.OnFocusChangeListener {
    private TextInputLayout layout;
    public static final String WRONG_EMAIL = "enter valid email ";
    public EmailFocusListener(TextInputLayout layout) {
        this.layout = layout;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus && layout != null){
            if(!layout.getEditText().getText().toString().matches(".+@.+\\..+") && !layout.getEditText().getText().toString().isEmpty()){
                layout.setError(WRONG_EMAIL);
            }else{
                if(layout.getError() != null && layout.getError().equals(WRONG_EMAIL)) {
                    layout.setErrorEnabled(false);
                    layout.setError(null);
                }
            }
        }
    }
}
