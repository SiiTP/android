package com.dbtest.ivan.app.logic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.logic.entities.Reminder;

public class ReminderListAdapter extends ArrayAdapter<Reminder> {

    private LayoutInflater lInflater;

    public ReminderListAdapter(Context context, int resource, Reminder[] objects) {
        super(context, resource, objects);
        this.lInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View reminderRow = convertView;
        Reminder reminderItem = getItem(position);
        if (convertView == null) {
            reminderRow = lInflater.inflate(R.layout.reminder_item, parent, false);
        }

        TextView date = (TextView) reminderRow.findViewById(R.id.reminder_item_date);
        TextView text = (TextView) reminderRow.findViewById(R.id.reminder_item_text);

        date.setText(reminderItem.getStringReminderTime());
        text.setText(reminderItem.getText());

        return reminderRow;
    }
}
