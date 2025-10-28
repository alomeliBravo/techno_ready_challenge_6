package com.pikolinc.exception;

public class AlredyExistException extends ApiExceptionBase{
    public AlredyExistException(String message){
        super(message);
    }

    @Override
    public int getHttpStatus(){
        return 409;
    }
}
