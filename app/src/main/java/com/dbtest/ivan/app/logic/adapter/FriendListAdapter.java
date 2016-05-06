package com.dbtest.ivan.app.logic.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.FriendsActivity;
import com.dbtest.ivan.app.model.Friend;

import java.util.List;

/**
 * Created by said on 07.04.16.
 */
public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {

    private List<Friend> friendList;
    FriendsActivity activity;

    public FriendListAdapter(FriendsActivity activity, List<Friend> friendList) {
        this.activity = activity;
        this.friendList = friendList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_list_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(friendList.get(position).getName());
        holder.addReminder.setTag(position);
        holder.deleteBtn.setTag(position);
    }

    private class ChooseCallback implements MaterialDialog.SingleButtonCallback {

        private int position;

        public ChooseCallback(int position) {
            this.position = position;
        }

        @Override
        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            switch (which) {
                case POSITIVE:
                    Toast.makeText(activity, "Positive" + position, Toast.LENGTH_SHORT).show();
                    //activity.removeFriend(friendList.get(position).getEmail());
                    friendList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, friendList.size());
                case NEGATIVE:
                    Toast.makeText(activity, "Negative" + position, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView addReminder;
        TextView deleteBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.friend_name);
            addReminder = (ImageView) itemView.findViewById(R.id.add_reminder);
            deleteBtn = (TextView) itemView.findViewById(R.id.delete_text);
            deleteBtn.setOnClickListener((v) -> {
                Toast.makeText(activity, "Click delete friend", Toast.LENGTH_SHORT).show();
                int position = (int) v.getTag();
                activity.showDeleteFriendDialog(new ChooseCallback(position));
            });
            addReminder.setOnClickListener((v) -> {
                Toast.makeText(activity, "Click add reminder", Toast.LENGTH_SHORT).show();
            });

        }
    }
}
