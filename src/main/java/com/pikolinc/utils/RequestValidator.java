package com.pikolinc.utils;

import com.pikolinc.exception.BadRequestException;

public class RequestValidator {
    public static long parseAndValidateId(String idParam){
        if (idParam == null || idParam.isBlank()){
            throw new BadRequestException("Missing or Empty id parammeter");
        }

        try {
            return Long.parseLong(idParam);
        } catch (NumberFormatException e) {
            throw  new BadRequestException("Invalid id: " + idParam + " id must be an integer");
        }
    }

    public static void validateEmptyBody(String requestBody){
        if (requestBody == null || requestBody.isBlank()){
            throw  new BadRequestException("Request body is empty");
        }
    }
}
