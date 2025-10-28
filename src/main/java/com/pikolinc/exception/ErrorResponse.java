package com.pikolinc.exception;

public record ErrorResponse(
        int status,
        String errorMessage
) {}
