package com.pikolinc.controller;

import com.pikolinc.config.JsonProvider;
import com.pikolinc.dto.user.UserCreateDTO;
import com.pikolinc.dto.user.UserUpdateDTO;
import com.pikolinc.exception.BadRequestException;
import com.pikolinc.service.impl.UserServiceImpl;
import spark.Request;
import spark.Response;

public class UserController {
    private final UserServiceImpl userService;
    private final JsonProvider jsonProvider;

    public  UserController(UserServiceImpl userService, JsonProvider jsonProvider) {
        this.userService = userService;
        this.jsonProvider = jsonProvider;
    }

    public Object saveUser(Request req, Response res) {
        String body  = req.body();
        if (body == null ||  body.isBlank()) {
            throw new BadRequestException("Body is empty");
        }

        return this.userService.saveUser(jsonProvider.fromJson(body, UserCreateDTO.class));
    }

    public Object findAll(Request req, Response res) {
        return this.jsonProvider.toJson(this.userService.findAll());
    }

    public Object findById(Request req, Response res) {
        long id = Long.parseLong(req.params(":id"));
        return this.jsonProvider.toJson(this.userService.findById(id));
    }

    public Object updateUserById(Request req, Response res) {
        long id = Long.parseLong(req.params(":id"));
        String body = req.body();
        if (body == null ||  body.isBlank()) {
            throw new BadRequestException("Body is empty");
        }
        UserUpdateDTO dto = jsonProvider.fromJson(body, UserUpdateDTO.class);
        return jsonProvider.toJson(this.userService.updateUserById(id,dto));
    }

    public void deleteUserById(Request req, Response res) {
        long id = Long.parseLong(req.params(":id"));
        this.userService.deleteUserById(id);
    }
}
