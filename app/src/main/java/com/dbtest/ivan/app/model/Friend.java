package com.dbtest.ivan.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by said on 10.04.16.
 */
public class Friend implements Parcelable {
    @SerializedName("friendEmail")
    private String email;
    private long invitionTime;
    private int state;
    private long id;

    public Friend(long id, String email, String name, long invitionTime, int state) {
        this.id = id;
        this.email = email;
        this.invitionTime = invitionTime;
        this.state = state;
    }

    public Friend(Parcel in) {
        email = in.readString();
        invitionTime = in.readLong();
        state = in.readInt();
        id = in.readLong();
    }

    public Friend() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setInvitionTime(long time) {
        this.invitionTime = time;
    }

    public long getInvitionTime() {
        return invitionTime;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeLong(invitionTime);
        dest.writeInt(state);
        dest.writeLong(id);
    }

    public static final Parcelable.Creator<Friend> CREATOR = new Parcelable.Creator<Friend>() {

        @Override
        public Friend createFromParcel(Parcel source) {
            return new Friend(source);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };
}
