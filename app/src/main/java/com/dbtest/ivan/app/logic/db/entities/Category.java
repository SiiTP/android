package com.dbtest.ivan.app.logic.db.entities;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;

/**
 * Created by ivan on 27.03.16.
 */
@DatabaseTable(tableName = "category")
public class Category {
    public static final String CATEGORY_ALL_NAME = "all";
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField(unique = true)
    private String name;
    @DatabaseField
    private String picture;
    @DatabaseField(unique = true)
    private Long serverId;
    @ForeignCollectionField
    private ForeignCollection<Reminder> reminders;

    @DatabaseField(columnName = "is_synced",defaultValue = "false")
    private Boolean isSynced = false;
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

    //для drawer menu
    public static String[] toStringArray(ArrayList<Category> categories) {
        String[] strCategories = new String[categories.size()];
        int i = 0;
        for (Category category : categories) {
            strCategories[i] = category.getName();
            i++;
        }
        return strCategories;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }
}
