package com.pikolinc.controller;

import com.pikolinc.config.JsonProvider;
import com.pikolinc.dto.item.ItemCreateDTO;
import com.pikolinc.dto.item.ItemResponseDTO;
import com.pikolinc.dto.item.ItemUpdateDTO;
import com.pikolinc.exception.ValidationProvider;
import com.pikolinc.service.ItemService;
import com.pikolinc.service.impl.ItemServiceImpl;
import com.pikolinc.utils.RequestValidator;
import spark.Request;
import spark.Response;

import java.util.Map;

public class ItemController {
    private final ItemServiceImpl itemService;
    private final JsonProvider jsonProvider;

    public  ItemController(ItemServiceImpl itemService, JsonProvider jsonProvider) {
        this.itemService = itemService;
        this.jsonProvider = jsonProvider;
    }

    public Object saveItem(Request req, Response res) {
        String body = req.body();
        RequestValidator.validateEmptyBody(body);
        ItemCreateDTO dto = jsonProvider.fromJson(body, ItemCreateDTO.class);
        ValidationProvider.validate(dto);
        ItemResponseDTO itemResponseDTO = itemService.saveItem(dto);
        res.status(201);
        return jsonProvider.toJson(itemResponseDTO);
    }

    public Object findAll(Request req, Response res) {
        res.status(200);
        return this.jsonProvider.toJson(itemService.findAll());
    }

    public Object findById(Request req, Response res) {
        long id = Long.parseLong(req.params(":id"));
        res.status(200);
        return this.jsonProvider.toJson(itemService.findById(id));
    }

    public Object updateItem(Request req, Response res) {
        String body = req.body();
        RequestValidator.validateEmptyBody(body);
        long id = Long.parseLong(req.params(":id"));
        ItemCreateDTO dto = jsonProvider.fromJson(body, ItemCreateDTO.class);
        ValidationProvider.validate(dto);
        res.status(200);
        return jsonProvider.toJson(this.itemService.updateById(id, dto));
    }

    public Object patchItem(Request req, Response res) {
        String body = req.body();
        RequestValidator.validateEmptyBody(body);
        long id = Long.parseLong(req.params(":id"));
        ItemUpdateDTO dto = jsonProvider.fromJson(body, ItemUpdateDTO.class);
        ValidationProvider.validate(dto);
        res.status(200);
        return jsonProvider.toJson(this.itemService.patchById(id, dto));
    }

    public Object itemExist(Request req, Response res) {
        long id = Long.parseLong(req.params(":id"));
        if (!this.itemService.itemExist(id)) {
            res.status(404);
            return this.jsonProvider.toJson(Map.of("message", "Item with id " + id + " does not exist"));
        }
        res.status(200);
        return this.jsonProvider.toJson(Map.of("message", "Item with id " + id + " does not exist"));
    }

    public Object deleteItem(Request req, Response res) {
        long id = Long.parseLong(req.params(":id"));
        this.itemService.deleteById(id);
        res.status(204);
        return this.jsonProvider.toJson(Map.of("message", "Item has been deleted"));
    }
}
