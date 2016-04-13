package com.dbtest.ivan.app.logic.db.entities;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ivan on 27.03.16.
 */
@DatabaseTable(tableName = "reminder")
public class Reminder {
    public static final String REMINDER_FULL_TIME_FORMAT = "dd.MM.yyyy HH:mm";
    public static final String REMINDER_TIME_FORMAT = "HH:mm";
    public static final String REMINDER_DATE_FORMAT = "dd.MM.yyyy";

    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField
    private String author;
    @DatabaseField(columnName = "reminder_time",canBeNull = false)
    private Date reminderTime;
    @DatabaseField
    private String text;
    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private Category category;
    @DatabaseField(columnName = "is_synced",defaultValue = "false")
    private Boolean isSynced;

    private Long friendId = 0L;
    public Reminder() {

    }

    public Reminder(Long id, String author, Date reminderTime, String text) {
        this.id = id;
        this.author = author;
        this.reminderTime = reminderTime;
        this.text = text;
    }

    public Reminder(Date reminderTime, String text) {
        this.author = author;
        this.reminderTime = reminderTime;
        this.text = text;
    }

    public Reminder(String author, Date reminderTime, String text, Category category) {
        this.author = author;
        this.reminderTime = reminderTime;
        this.text = text;
        this.category = category;
    }

    public Reminder(String date, String text) {
        this.text = text;
        try {
            this.reminderTime = new SimpleDateFormat(REMINDER_FULL_TIME_FORMAT, Locale.US).parse(date);
        } catch (ParseException e) {
            Log.e("myapp " + this.getClass(), "date was not parsed");
            this.reminderTime = new Date();
        }

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getReminderTime() {
        return reminderTime;
    }

    public String getStringReminderTime() {
        return new SimpleDateFormat(REMINDER_TIME_FORMAT, Locale.US).format(reminderTime);
    }

    public String getStringReminderDate() {
        return new SimpleDateFormat(REMINDER_DATE_FORMAT, Locale.US).format(reminderTime);
    }

    public void setReminderTime(Date reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsSynced() {
        return isSynced;
    }

    public void setIsSynced(Boolean isSynced) {
        this.isSynced = isSynced;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }
}
