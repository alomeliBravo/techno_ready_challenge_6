package com.pikolinc.config.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonParseException;
import com.pikolinc.config.JsonProvider;
import com.pikolinc.enums.OfferStatus;

public class GsonProvider implements JsonProvider {
    private final Gson gson;

    public GsonProvider() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(OfferStatus.class, (JsonDeserializer<OfferStatus>) (json, typeOfT, context) -> {
                    String value = json.getAsString();
                    if (value == null) return null;
                    try {
                        return OfferStatus.valueOf(value.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        throw new JsonParseException("Invalid OfferStatus: " + value + " OfferStatus accepted: ACCEPTED, PENDING, REJECTED");
                    }
                })
                .create();
    }

    @Override
    public <T> T fromJson(String json, Class<T> type) {
        return gson.fromJson(json, type);
    }

    @Override
    public String toJson(Object object) {
        return gson.toJson(object);
    }
}
