package com.dbtest.ivan.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.logic.adapter.ReminderListAdapter;
import com.dbtest.ivan.app.logic.entities.Reminder;

public class ReminderListFragment extends ListFragment {
    private Reminder[] reminders = new Reminder[3];

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mItemSelectedListener.onReminderSelected(position);
    }

    OnItemSelectedListener mItemSelectedListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        reminders[0] = new Reminder("17:11:1996 12:12", "description 2");
        reminders[1] = new Reminder("17:11:1995 11:11", "description 1");
        reminders[2] = new Reminder("17:11:1997 10:10", "description 3");
        ReminderListAdapter adapter = new ReminderListAdapter(inflater.getContext(),
                R.layout.reminder_item, reminders);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mItemSelectedListener = (OnItemSelectedListener) context; //TODO error if not implemented
    }



    public interface OnItemSelectedListener {
        public void onReminderSelected(int position);
    }
}
