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
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.FriendsActivity;
import com.dbtest.ivan.app.model.Friend;

import java.util.List;

/**
 * Created by said on 07.04.16.
 */
public class FriendListAdapter extends RecyclerSwipeAdapter<FriendListAdapter.ViewHolder> {

    private List<Friend> friendList;
    FriendsActivity activity;

    public FriendListAdapter(FriendsActivity activity, List<Friend> friendList) {
        this.activity = activity;
        this.friendList = friendList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.name.setText(friendList.get(position).getName());
        holder.deleteBtn.setOnClickListener((v) -> {
            Toast.makeText(activity, "Click delete friend", Toast.LENGTH_SHORT).show();
            mItemManger.closeAllItems();
            activity.showDeleteFriendDialog(new ChooseCallback(position, holder));
        });
        holder.addReminder.setOnClickListener((v) -> {
            activity.openDetailReminderActivity();
        });
        mItemManger.bindView(holder.itemView, position);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.friends_activity_swipe_layout;
    }

    private class ChooseCallback implements MaterialDialog.SingleButtonCallback {

        private int position;
        private ViewHolder holder;

        public ChooseCallback(int position, ViewHolder holder) {
            this.position = position;
            this.holder = holder;
        }

        @Override
        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            if (which == DialogAction.POSITIVE) {
                Toast.makeText(activity, "Positive" + position, Toast.LENGTH_SHORT).show();
                mItemManger.removeShownLayouts(holder.swipeLayout);
                friendList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, friendList.size());
                activity.removeFriend(friendList.get(position).getEmail());
            }
        }
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView name;
        ImageView addReminder;
        TextView deleteBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.friends_activity_swipe_layout);
            name = (TextView) itemView.findViewById(R.id.friend_list_item_friend_name);
            addReminder = (ImageView) itemView.findViewById(R.id.friend_list_item_add_reminder_to_friend);
            deleteBtn = (TextView) itemView.findViewById(R.id.friend_list_item_delete_text);
        }
    }
}
