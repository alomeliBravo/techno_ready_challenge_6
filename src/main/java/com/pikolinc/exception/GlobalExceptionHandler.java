package com.pikolinc.exception;

import com.google.gson.JsonSyntaxException;
import com.pikolinc.config.JsonProvider;
import com.pikolinc.config.json.GsonProvider;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import spark.Spark;

import java.util.List;
import java.util.stream.Collectors;

public class GlobalExceptionHandler {

    private static final JsonProvider jsonProvider = new GsonProvider();

    public static void init() {
        Spark.exception(NotFoundException.class, (ex, req, res) -> {
            res.type("application/json");
            res.status(ex.getHttpStatus());
            res.body(jsonProvider.toJson(new ErrorResponse(ex.getHttpStatus(), ex.getMessage())));
        });

        Spark.exception(ForbiddenException.class, (ex, req, res) -> {
            res.type("application/json");
            res.status(ex.getHttpStatus());
            res.body(jsonProvider.toJson(new ErrorResponse(ex.getHttpStatus(), ex.getMessage())));
        });

        Spark.exception(AlredyExistException.class, (ex, req, res) -> {
            res.type("application/json");
            res.status(ex.getHttpStatus());
            res.body(jsonProvider.toJson(new ErrorResponse(ex.getHttpStatus(), ex.getMessage())));
        });

        Spark.exception(ConstraintViolationException.class, (ex, req, res) -> {
            res.type("application/json");
            res.status(400);
            List<String> errors = ex.getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            res.body(jsonProvider.toJson(new ErrorResponse(400, String.join(" | ", errors))));
        });

        Spark.exception(Exception.class, (ex, req, res) -> {
            res.type("application/json");
            res.status(500);
            res.body(jsonProvider.toJson(new ErrorResponse(500, "Sorry. An unexpected error has occurred. Try again later")));
        });

        Spark.exception(BadRequestException.class, (ex, req, res) -> {
            res.type("application/json");
            res.status(400);
            res.body(jsonProvider.toJson(new ErrorResponse(400, ex.getMessage())));
        });

        Spark.exception(JsonSyntaxException.class, (ex, req, res) -> {
            res.type("application/json");
            res.status(400);
            res.body(jsonProvider.toJson(new ErrorResponse(400, "Json body must be valid")));
        });
    }
}
