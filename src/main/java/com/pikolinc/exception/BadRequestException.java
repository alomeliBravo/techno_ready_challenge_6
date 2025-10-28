package com.pikolinc.exception;

public class BadRequestException extends ApiExceptionBase {
    public BadRequestException(String message){
        super(message);
    }

    @Override
    public int getHttpStatus(){
        return 400;
    }
}
