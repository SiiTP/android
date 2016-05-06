package com.dbtest.ivan.app.utils.watchers;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by ivan on 18.04.16.
 */
public class MaxLengthTextWatcher implements TextWatcher {
    private TextInputLayout layout;
    private long maxLength;

    public MaxLengthTextWatcher(TextInputLayout layout, long maxLength) {
        this.layout = layout;
        this.maxLength = maxLength;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String errorMessage = "max length " + maxLength;
        if(s.toString().length() >= maxLength){
            layout.setError(errorMessage);
        }else{
            if(layout.getError() != null && layout.getError().equals(errorMessage)) {
                layout.setErrorEnabled(false);
                layout.setError(null);
            }
        }
    }
}
