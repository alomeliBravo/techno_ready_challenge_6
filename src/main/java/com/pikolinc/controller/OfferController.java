package com.pikolinc.controller;

import com.pikolinc.config.JsonProvider;
import com.pikolinc.dto.offer.OfferCreateDTO;
import com.pikolinc.dto.offer.OfferResponseDTO;
import com.pikolinc.enums.OfferStatus;
import com.pikolinc.exception.ValidationProvider;
import com.pikolinc.service.OfferService;
import com.pikolinc.utils.RequestValidator;
import spark.Request;
import spark.Response;

import java.util.Map;

public class OfferController {
    private final OfferService offerService;
    private final JsonProvider jsonProvider;

    public OfferController(OfferService offerService, JsonProvider jsonProvider) {
        this.offerService = offerService;
        this.jsonProvider = jsonProvider;
    }

    public Object saveOffer(Request req, Response res) {
        String body = req.body();
        RequestValidator.validateEmptyBody(body);
        OfferCreateDTO dto = jsonProvider.fromJson(body, OfferCreateDTO.class);
        ValidationProvider.validate(dto);
        OfferResponseDTO offerResponse = this.offerService.saveOffer(dto);
        res.status(201);
        return jsonProvider.toJson(offerResponse);
    }

    public Object findAll(Request req, Response res) {
        res.status(200);
        return jsonProvider.toJson(this.offerService.findAllOffers());
    }

    public Object findById(Request req, Response res) {
        long id = RequestValidator.parseAndValidateId(req.params(":id"));
        res.status(200);
        return jsonProvider.toJson(this.offerService.findOfferById(id));
    }

    public Object findAllOfferByUserId(Request req, Response res) {
        long userId = RequestValidator.parseAndValidateId(req.params(":userId"));
        res.status(200);
        return jsonProvider.toJson(this.offerService.findAllOffersByUserId(userId));
    }

    public Object findAllOffersByItemId(Request req, Response res) {
        long itemId =  RequestValidator.parseAndValidateId(req.params(":itemId"));
        res.status(200);
        return jsonProvider.toJson(this.offerService.findAllOffersByItemId(itemId));
    }

    public Object updateOfferById(Request req, Response res) {
        long id = RequestValidator.parseAndValidateId(req.params(":id"));
        String body = req.body();
        RequestValidator.validateEmptyBody(body);
        OfferCreateDTO dto = jsonProvider.fromJson(body, OfferCreateDTO.class);
        ValidationProvider.validate(dto);
        res.status(200);
        return jsonProvider.toJson(this.offerService.updateOfferById(id, dto));
    }

    public Object updateOfferStatus(Request req, Response res) {
        long id = RequestValidator.parseAndValidateId(req.params(":id"));
        String statusParam = req.params(":status");
        OfferStatus status = OfferStatus.valueOf(statusParam.toUpperCase());
        res.status(200);
        return jsonProvider.toJson(this.offerService.updateOfferStatus(id, status));
    }

    public Object offerExist(Request req, Response res) {
        long id =  RequestValidator.parseAndValidateId(req.params(":id"));
        if (!this.offerService.offerExist(id)) {
            res.status(404);
            return jsonProvider.toJson(Map.of("message", "Offer with id: " + id + " not found"));
        }
        res.status(200);
        return jsonProvider.toJson(Map.of("message", "Offer with id: " + id + " exists"));
    }

    public Object deleteOfferById(Request req, Response res) {
        long id = RequestValidator.parseAndValidateId(req.params(":id"));
        if(!this.offerService.deleteOfferById(id)) {
            res.status(404);
            return jsonProvider.toJson(Map.of("message", "Offer with id: " + id + " not found"));
        }
        res.status(200);
        return jsonProvider.toJson(Map.of("message", "Offer with id: " + id + " deleted"));
    }
}
