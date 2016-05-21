package com.dbtest.ivan.app.activity.reminder;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.WaitingActivity;
import com.dbtest.ivan.app.activity.abstractToolbarActivity.AbstractToolbarActivity;
import com.dbtest.ivan.app.fragment.DateTimePickerDialog;
import com.dbtest.ivan.app.receiver.CustomReceiver;
import com.dbtest.ivan.app.utils.WaitingManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class ReminderActivity extends AbstractToolbarActivity implements DateTimePickerDialog.DateTimeDialogListener, WaitingActivity {
    public static final String PICKED_DATE = "pick_date";
    protected Calendar reminderDate;
    protected Button pickButton;
    protected TextInputLayout textLayout;
    protected ProgressBar bar;
    protected Button submit;
    protected EditText reminderText;
    protected TextView dateTextView;
    private BroadcastReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWaitBar();
        initPickDateButton();
        initDateTextView();
        initReminderText();
        initSubmitButton();
        if(savedInstanceState != null){
            String savedDate = savedInstanceState.getString(PICKED_DATE);
            if(savedDate != null && dateTextView != null){
                dateTextView.setText(savedDate);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PICKED_DATE,dateTextView.getText().toString());

    }

    public void startWaitingReceiver(){
        setWaiting(true);
        IntentFilter filter = new IntentFilter(CustomReceiver.WAITING_ACTION);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new CustomReceiver(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }
    public void stopWaitingReceiver(){
        if(receiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        }
    }
    public void initWaitBar(){
        bar = (ProgressBar) findViewById(R.id.details_bar);
        bar.setVisibility(View.GONE);
    }
    public void initDateTextView(){
        dateTextView = (TextView) findViewById(R.id.details_picked_date);
    }
    public void initPickDateButton(){
        pickButton = (Button) findViewById(R.id.pick_date_button);
        if (pickButton != null) {
            pickButton.setOnClickListener(v -> showDateTimeDialog());
        }
    }
    public abstract void initSubmitButton();
    public void initReminderText(){
        reminderText = ((EditText) findViewById(R.id.details_text));
        textLayout = ((TextInputLayout) findViewById(R.id.details_text_supp));
        textLayout.setCounterEnabled(true);
    }
    public void showDateTimeDialog(){
        DateTimePickerDialog dateTimePickerDialog = new DateTimePickerDialog();
        dateTimePickerDialog.show(getFragmentManager(),"Choose date");
    }
    @Override
    protected void onPause() {
        stopWaitingReceiver();
        super.onPause();
    }

    @Override
    public void setWaiting(boolean isWaiting) {
        WaitingManager.makeWaitingButton(this,pickButton,isWaiting);
        WaitingManager.makeWaitingButton(this,submit,isWaiting);
        WaitingManager.makeWaitingProgressBar(this,bar,isWaiting);
        WaitingManager.makeWaitingView(reminderText,isWaiting);
    }


    @Override
    public void onDateTimeSelect(Calendar calendar) {
        if (dateTextView != null) {
            reminderDate = calendar;
            Date d = new Date(calendar.getTimeInMillis());
            SimpleDateFormat format = new SimpleDateFormat("EEEE dd.MM.yyyy HH:mm");
            dateTextView.setText(format.format(d));
        }
    }
}
