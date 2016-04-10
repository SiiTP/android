package com.dbtest.ivan.app.model;

/**
 * Created by said on 10.04.16.
 */
public class Friend {
    private long id;
    private String email;
    private String name;
    private long invitionTime;
    private int state;

    public Friend(String email, String name, long invitionTime, int state) {
        this.email = email;
        this.name = name;
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
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getname() {
        return name;
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
