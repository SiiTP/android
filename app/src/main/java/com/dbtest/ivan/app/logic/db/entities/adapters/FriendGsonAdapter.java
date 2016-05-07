package com.dbtest.ivan.app.logic.db.entities.adapters;

import com.dbtest.ivan.app.logic.db.entities.Category;
import com.dbtest.ivan.app.model.Friend;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by said on 07.05.16.
 */
public class FriendGsonAdapter extends TypeAdapter<Friend> {

    @Override
    public void write(JsonWriter out, Friend value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.beginObject();
        out.name("friendEmail").value(value.getEmail());
        out.name("state").value(value.getState());
        out.endObject();
    }

    @Override
    public Friend read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        Friend friend = new Friend();
        in.beginObject();
        while (in.hasNext()){
            String name = in.nextName();
            switch (name){
                case "id":
                    friend.setId(in.nextLong());
                    break;
                case "friendEmail":
                    friend.setEmail(in.nextString());
                    break;
                case "friendName":
                    friend.setName(in.nextString());
                    break;
                case "invitionTime":
                    friend.setInvitionTime(Long.valueOf(in.nextString()));
                    break;
                case "state":
                    friend.setState(Integer.valueOf(in.nextString()));
                    break;
            }
        }
        in.endObject();
        return friend;
    }
}
