package com.dbtest.ivan.app.logic.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.abstractToolbarActivity.AbstractToolbarActivity;
import com.dbtest.ivan.app.logic.db.entities.Reminder;

import java.util.ArrayList;

/**
 * Created by Ivan on 17.04.2016.
 */
public class ReminderListAdapter extends RecyclerView.Adapter<ReminderListAdapter.ReminderViewHolder> {
    AbstractToolbarActivity activity;
    ArrayList<Reminder> reminders;

    public ReminderListAdapter(AbstractToolbarActivity activity, ArrayList<Reminder> reminders) {
        this.reminders = reminders;
        this.activity = activity;
    }

    @Override
    public ReminderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.reminder_swipe_item, null);
        return new ReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReminderViewHolder holder, int position) {
        Reminder reminderItem = reminders.get(position);
        holder.date.setText(reminderItem.getStringReminderDate());
        holder.time.setText(reminderItem.getStringReminderTime());
        holder.text.setText(reminderItem.getText());
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    public class ReminderViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView time;
        TextView text;

        public ReminderViewHolder(View itemView) {
            super(itemView);

            date = (TextView) itemView.findViewById(R.id.reminder_item_date);
            time = (TextView) itemView.findViewById(R.id.reminder_item_time);
            text = (TextView) itemView.findViewById(R.id.reminder_item_text);
        }
    }

    public void setData(ArrayList<Reminder> data) {
        this.reminders = data;
    }

    public void reset() {
        this.reminders = null;
    }

}
