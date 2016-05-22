package com.dbtest.ivan.app.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * Created by said on 22.05.16.
 */
public class JsonParser {
    public static JsonObject stringToJsonObject(String str) {
        JsonObject jsonObject;

        try {
            jsonObject  = new Gson().fromJson(str, JsonObject.class);
        } catch (JsonSyntaxException e) {
            return null;
        }
        return jsonObject;
    }
}
