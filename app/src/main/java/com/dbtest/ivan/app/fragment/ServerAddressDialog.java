package com.dbtest.ivan.app.fragment;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.utils.WaitingManager;

/**
 * Created by ivan on 20.04.16.
 */
public class ServerAddressDialog extends DialogPreference {

    private View dialogView;
    private RadioGroup radioGroup;
    private RadioButton geny;
    private RadioButton local;
    private CheckBox isCustom;
    private EditText custom;
    public ServerAddressDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setDialogLayoutResource(R.layout.server_addr_dialog);
        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);
        setDialogIcon(null);
        setDialogTitle("Select server address...");
    }

    public ServerAddressDialog(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        dialogView = view;

        radioGroup = (RadioGroup)dialogView.findViewById(R.id.addr_radio_grp);
        geny =  ((RadioButton) dialogView.findViewById(R.id.addr_geny));
        local =  ((RadioButton) dialogView.findViewById(R.id.addr_localhost));
        isCustom = (CheckBox)dialogView.findViewById(R.id.addr_custom_check);
        custom = (EditText)dialogView.findViewById(R.id.addr_custom);
        isCustom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCustom.setChecked(isChecked);
                if(isChecked){
                    WaitingManager.makeWaitingView(geny,isChecked);
                    WaitingManager.makeWaitingView(local,isChecked);
                    WaitingManager.makeWaitingView(custom,!isChecked);
                }else{
                    WaitingManager.makeWaitingView(geny,isChecked);
                    WaitingManager.makeWaitingView(local,isChecked);
                    WaitingManager.makeWaitingView(custom,!isChecked);
                }
            }
        });
        isCustom.setChecked(false);
        WaitingManager.makeWaitingView(custom, true);
        String uri = getSharedPreferences().getString(getKey(),null);

        switch (uri){
            case RetrofitFactory.GENYMOTION_URI:
                geny.setChecked(true);
                break;
            case RetrofitFactory.LOCALHOST_URI:
                local.setChecked(true);
                break;
            default:
                ((EditText)dialogView.findViewById(R.id.addr_custom)).setText(uri);
                isCustom.setChecked(true);
        }
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if(positiveResult){
            String uri = getUri();
            persistString(uri);
            RetrofitFactory.setServerURI(uri);
        }

    }

    private String getUri(){
        String uri = "";
        if(!isCustom.isChecked()) {
            int i = radioGroup.getCheckedRadioButtonId();
            switch (i) {
                case R.id.addr_geny:
                    uri = RetrofitFactory.GENYMOTION_URI;
                    break;
                case R.id.addr_localhost:
                    uri = RetrofitFactory.LOCALHOST_URI;
                    break;

            }
        }else {
            uri = ((EditText) dialogView.findViewById(R.id.addr_custom)).getText().toString();
        }
        return uri;
    }
}
