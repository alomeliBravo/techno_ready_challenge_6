package com.pikolinc.controller;

import com.pikolinc.config.JsonProvider;
import com.pikolinc.dto.user.UserCreateDTO;
import com.pikolinc.dto.user.UserResponseDTO;
import com.pikolinc.dto.user.UserUpdateDTO;
import com.pikolinc.exception.BadRequestException;
import com.pikolinc.exception.ValidationProvider;
import com.pikolinc.service.impl.UserServiceImpl;
import com.pikolinc.utils.RequestValidator;
import spark.Request;
import spark.Response;

import java.util.Map;

public class UserController {
    private final UserServiceImpl userService;
    private final JsonProvider jsonProvider;

    public  UserController(UserServiceImpl userService, JsonProvider jsonProvider) {
        this.userService = userService;
        this.jsonProvider = jsonProvider;
    }

    public Object saveUser(Request req, Response res) {
        String body  = req.body();
        RequestValidator.validateEmptyBody(body);
        res.status(201);
        UserCreateDTO dto = jsonProvider.fromJson(body, UserCreateDTO.class);
        ValidationProvider.validate(dto);
        UserResponseDTO user = this.userService.saveUser(dto);
        return jsonProvider.toJson(user);
    }

    public Object findAll(Request req, Response res) {
        res.status(200);
        return this.jsonProvider.toJson(this.userService.findAll());
    }

    public Object findById(Request req, Response res) {
        long id = RequestValidator.parseAndValidateId(req.params(":id"));
        res.status(200);
        return this.jsonProvider.toJson(this.userService.findById(id));
    }

    public Object findByEmail(Request req, Response res) {
        res.status(200);
        return this.jsonProvider.toJson(this.userService.findByEmail(req.params(":email")));
    }

    public Object options(Request req, Response res) {
        long id  = RequestValidator.parseAndValidateId(req.params(":id"));
        Boolean userExist = this.userService.userExist(id);
        if(!userExist){
            res.status(400);
            return this.jsonProvider.toJson(Map.of("message","User with id: " + req.params(":id") + " not exists"));
        }
        res.status(200);
        return this.jsonProvider.toJson(Map.of("message","User with id: " + req.params(":id") + " exists"));
    }

    public Object updateUserById(Request req, Response res) {
        long id = RequestValidator.parseAndValidateId(req.params(":id"));
        String body = req.body();
        RequestValidator.validateEmptyBody(body);
        UserCreateDTO dto = jsonProvider.fromJson(body, UserCreateDTO.class);
        ValidationProvider.validate(dto);
        res.status(200);
        return jsonProvider.toJson(this.userService.updateUserById(id,dto));
    }

    public Object patchUserById(Request req, Response res) {
        long id = RequestValidator.parseAndValidateId(req.params(":id"));
        String body = req.body();
        RequestValidator.validateEmptyBody(body);
        UserUpdateDTO dto = jsonProvider.fromJson(body, UserUpdateDTO.class);
        ValidationProvider.validate(dto);
        res.status(200);
        return jsonProvider.toJson(this.userService.patchUserById(id,dto));
    }

    public Object deleteUserById(Request req, Response res) {
        long id = RequestValidator.parseAndValidateId(req.params(":id"));
        this.userService.deleteUserById(id);
        res.status(200);
        return this.jsonProvider.toJson(Map.of("message", "User has been deleted"));
    }
}
