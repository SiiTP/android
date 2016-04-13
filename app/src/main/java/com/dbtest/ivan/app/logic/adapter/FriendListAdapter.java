package com.dbtest.ivan.app.logic.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.FriendsActivity;
import com.dbtest.ivan.app.model.Friend;
import com.dbtest.ivan.app.services.intent.LoadFriendsIntentService;
import com.dbtest.ivan.app.services.intent.RemoveFriendIntentService;

import java.util.List;

/**
 * Created by said on 07.04.16.
 */
public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {

    private List<Friend> friendList;
    LayoutInflater layoutInflater;
    AppCompatActivity activity;

    public FriendListAdapter(AppCompatActivity activity, List<Friend> friendList) {
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
        String name = friendList.get(position).getName();
        holder.name.setText(friendList.get(position).getName());
        holder.deleteBtn.setTag(position);
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        ImageButton deleteBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.friend_name);
            deleteBtn = (ImageButton) itemView.findViewById(R.id.delete_btn);
            deleteBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            ((FriendsActivity) activity).removeFriend(friendList.get(position).getEmail());
            friendList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, friendList.size());
        }
    }
}
