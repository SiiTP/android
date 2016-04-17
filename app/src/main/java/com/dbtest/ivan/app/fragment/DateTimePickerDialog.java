package com.dbtest.ivan.app.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.dbtest.ivan.app.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by ivan on 18.04.16.
 */
public class DateTimePickerDialog extends DialogFragment {
    private View dateTimeView;
    private DateTimeDialogListener listener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (DateTimeDialogListener) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dateTimeView = View.inflate(getActivity(), R.layout.date_time_picker,null);
        dateTimeView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePicker timePicker = (TimePicker) dateTimeView.findViewById(R.id.time_picker);
                DatePicker datePicker = (DatePicker) dateTimeView.findViewById(R.id.date_picker);

                Calendar reminderDateTime = new GregorianCalendar(
                        datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());
                listener.onDateTimeSelect(reminderDateTime);
                DateTimePickerDialog.this.dismiss();
            }
        });
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.setView(dateTimeView);
        return dialog;
    }
    public interface DateTimeDialogListener{
        void onDateTimeSelect(Calendar calendar);
    }
}
