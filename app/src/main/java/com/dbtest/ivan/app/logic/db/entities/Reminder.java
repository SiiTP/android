package com.dbtest.ivan.app.logic.db.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by ivan on 27.03.16.
 */
@DatabaseTable(tableName = "reminder")
public class Reminder {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField(canBeNull = false)
    private String author;
    @DatabaseField(columnName = "reminder_time",canBeNull = false)
    private Date reminderTime;
    @DatabaseField
    private String text;
    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private Category category;
    @DatabaseField(columnName = "is_synced",defaultValue = "false")
    private Boolean isSynced;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getReminderTime() {
        return reminderTime;
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
}
