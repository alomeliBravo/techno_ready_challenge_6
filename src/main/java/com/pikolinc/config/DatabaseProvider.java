package com.pikolinc.config;

public interface DatabaseProvider {
    void connect();
    void disconnect();
    Object getConnection();
}
