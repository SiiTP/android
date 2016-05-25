package com.dbtest.ivan.app.logic.adapter;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.listActivity.ListActivity;
import com.dbtest.ivan.app.activity.reminder.UpdateDetailReminderActivity;
import com.dbtest.ivan.app.logic.db.entities.Reminder;
import com.dbtest.ivan.app.receiver.CustomReceiver;
import com.dbtest.ivan.app.services.intent.ReminderDeleteService;
import com.dbtest.ivan.app.services.intent.ReminderIntentService;
import com.dbtest.ivan.app.utils.ExtrasCodes;

import java.util.ArrayList;

public class ReminderListAdapter extends RecyclerSwipeAdapter<ReminderListAdapter.ReminderViewHolder> {
    ListActivity activity;
    ArrayList<Reminder> reminders;

    public ReminderListAdapter(ListActivity activity, ArrayList<Reminder> reminders) {
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

        holder.date.setTextColor(ContextCompat.getColor(activity, R.color.md_black_1000));
        if (reminderItem.isReminderToday()) {
            holder.date.setTextColor(ContextCompat.getColor(activity, R.color.app_accent));
        }
        if (reminderItem.isReminderPassed()) {
            holder.date.setTextColor(ContextCompat.getColor(activity, R.color.app_disabled));
        }

        holder.date.setText(reminderItem.getStringReminderDate());
        holder.time.setText(reminderItem.getStringReminderTime());
        holder.text.setText(reminderItem.getText());

        holder.mBtnEdit.setOnClickListener((v -> {
            Reminder reminder = reminders.get(position);
            Intent intent = new Intent(activity, UpdateDetailReminderActivity.class);
            intent.putExtra(ReminderIntentService.ID, reminder.getId());
            intent.putExtra(ReminderIntentService.CATEGORY, reminder.getCategory().getName());
            intent.putExtra(ReminderIntentService.TEXT, reminder.getText());
            intent.putExtra(ReminderIntentService.TIME, reminder.getReminderTime().getTime());
            activity.startActivity(intent);
        }));
        holder.mBtnDelete.setOnClickListener((v -> {
            Log.d("myapp", "delete clicked : " + reminders.get(position).getId());
            Intent intent = new Intent(activity, ReminderDeleteService.class);
            intent.putExtra(ExtrasCodes.REMINDER_ID_KEY, reminders.get(position).getId());
            activity.startService(intent);

            activity.setWaiting(true);
            IntentFilter filter = new IntentFilter(CustomReceiver.WAITING_ACTION);
            filter.addCategory(Intent.CATEGORY_DEFAULT);
            CustomReceiver receiver = activity.getReceiver();
            LocalBroadcastManager.getInstance(activity).registerReceiver(receiver, filter);
        }));
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.reminder_swipe_item;
    }

    public class ReminderViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView time;
        TextView text;

        ImageButton mBtnDelete;
        ImageButton mBtnEdit;

        public ReminderViewHolder(View itemView) {
            super(itemView);

            date = (TextView) itemView.findViewById(R.id.reminder_item_date);
            time = (TextView) itemView.findViewById(R.id.reminder_item_time);
            text = (TextView) itemView.findViewById(R.id.reminder_item_text);

            mBtnDelete = (ImageButton) itemView.findViewById(R.id._reminder_s_item_delete_btn);
            mBtnEdit   = (ImageButton) itemView.findViewById(R.id._reminder_s_item_edit_btn);
        }
    }

    public void setData(ArrayList<Reminder> data) {
        this.reminders = data;
    }

    public void reset() {
        this.reminders = null;
    }

}
