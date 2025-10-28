package com.pikolinc.exception;

public class NotFoundException extends ApiExceptionBase{
    public NotFoundException(String message){
        super(message);
    }

    @Override
    public int getHttpStatus(){
        return 404;
    }
}
