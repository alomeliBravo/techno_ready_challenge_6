package com.pikolinc.exception;

public class ForbiddenException extends ApiExceptionBase{
    public ForbiddenException(String message){
        super(message);
    }

    @Override
    public int getHttpStatus(){
        return 403;
    }
}
