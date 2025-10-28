package com.pikolinc.config;

public interface JsonProvider {
    <T> T fromJson(String json, Class<T> type);
    String toJson(Object object);
}
