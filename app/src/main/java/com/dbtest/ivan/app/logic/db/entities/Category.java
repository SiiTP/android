package com.dbtest.ivan.app.logic.db.entities;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ivan on 27.03.16.
 */
@DatabaseTable(tableName = "category")
public class Category {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField(unique = true)
    private String name;
    @DatabaseField
    private String picture;

    @ForeignCollectionField
    private ForeignCollection<Reminder> reminders;

    @DatabaseField(columnName = "is_synced",defaultValue = "false")
    private Boolean isSynced;
    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public ForeignCollection<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(ForeignCollection<Reminder> reminders) {
        this.reminders = reminders;
    }

    public Boolean getIsSynced() {
        return isSynced;
    }

    public void setIsSynced(Boolean isSynced) {
        this.isSynced = isSynced;
    }
}
