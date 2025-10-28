package com.pikolinc.exception;

public abstract class ApiExceptionBase extends RuntimeException {
    public ApiExceptionBase(String message) {
        super(message);
    }

    public abstract int getHttpStatus();
}
