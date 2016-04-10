package com.dbtest.ivan.app.model;

/**
 * Created by said on 10.04.16.
 */
public class Friend {
    private String friendEmail;
    private long invitionTime;
    private int state;
    private long id;

    public Friend(long id, String email, String name, long invitionTime, int state) {
        this.id = id;
        this.friendEmail = email;
        this.invitionTime = invitionTime;
        this.state = state;
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
        this.friendEmail = email;
    }

    public String getEmail() {
        return friendEmail;
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
}
