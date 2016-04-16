package com.dbtest.ivan.app.logic.db.entities.adapters;

import com.dbtest.ivan.app.logic.db.entities.Category;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by ivan on 03.04.16.
 */
public class CategoryGsonAdapter extends TypeAdapter<Category> {
    @Override
    public void write(JsonWriter out, Category value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.beginObject();
        out.name("id").value(value.getServerId());
        out.name("name").value(value.getName());
        out.name("picture").value(value.getPicture());
        out.endObject();
    }
    @Override
    public Category read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        Category category = new Category();
        in.beginObject();
        while (in.hasNext()){
            String name = in.nextName();
            switch (name){
                case "id":
                    category.setServerId(in.nextLong());
                    break;
                case "name":
                    category.setName(in.nextString());
                    break;
                case "picture":
                    category.setPicture(in.nextString());
                    break;
            }
        }
        in.endObject();
        return category;
    }
}
