package com.pikolinc.config.json;

import com.google.gson.Gson;
import com.pikolinc.config.JsonProvider;

public class GsonProvider implements JsonProvider {
    private final Gson gson = new Gson();

    @Override
    public <T> T fromJson(String json, Class<T> type) {
        return gson.fromJson(json, type);
    }

    @Override
    public String toJson(Object object) {
        return gson.toJson(object);
    }
}
