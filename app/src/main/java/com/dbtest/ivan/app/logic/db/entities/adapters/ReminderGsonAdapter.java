package com.dbtest.ivan.app.logic.db.entities.adapters;

import com.dbtest.ivan.app.logic.db.entities.Reminder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;

/**
 * Created by ivan on 03.04.16.
 */
public class ReminderGsonAdapter extends TypeAdapter<Reminder> {
    @Override
    public void write(JsonWriter out, Reminder value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.beginObject();
        out.name("text").value(value.getText());
        out.name("time").value(value.getReminderTime().getTime());
        out.name("idCategory").value(value.getCategory().getId());
        out.name("friendId").value(value.getFriendId());//todo rename mobileFriendId
        out.name("id").value(value.getId());//todo rename mobileId
        out.endObject();
    }
    @Override
    public Reminder read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        Reminder reminder = new Reminder();
        in.beginObject();
        while (in.hasNext()){
            String name = in.nextName();
            switch (name){
                case "id":
                    reminder.setId(in.nextLong());
                    break;
                case "time":
                    reminder.setReminderTime(new Date(in.nextLong()));
                    break;
                case "author":
                    reminder.setAuthor(in.nextString());
                    break;
                case "text":
                    reminder.setText(in.nextString());
                    break;
            }
        }
        in.endObject();
        return reminder;
    }
}
