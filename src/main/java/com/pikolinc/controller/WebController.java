package com.pikolinc.controller;

import com.pikolinc.config.JsonProvider;
import com.pikolinc.dto.item.ItemResponseDTO;
import com.pikolinc.dto.offer.OfferResponseDTO;
import com.pikolinc.service.ItemService;
import com.pikolinc.service.OfferService;
import com.pikolinc.utils.RequestValidator;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebController {
    private final ItemService itemService;
    private final OfferService offerService;
    private final MustacheTemplateEngine templateEngine;
    private final JsonProvider jsonProvider;

    public WebController(ItemService itemService, OfferService offerService, JsonProvider jsonProvider) {
        this.itemService = itemService;
        this.offerService = offerService;
        this.templateEngine = new MustacheTemplateEngine();
        this.jsonProvider = jsonProvider;
    }

    public String showItems(Request req, Response res) {
        List<ItemResponseDTO> items = itemService.findAll();

        Map<String, Object> model = new HashMap<>();
        model.put("items", items);
        model.put("hasItems", !items.isEmpty());

        return templateEngine.render(new ModelAndView(model, "items.mustache"));
    }

    public String showItemOffers(Request req, Response res){
        long itemId = RequestValidator.parseAndValidateId(req.params(":id"));

        ItemResponseDTO item = itemService.findById(itemId);
        List<OfferResponseDTO> offers = offerService.findAllOffersByItemId(itemId);

        double currentPrice = item.getPrice();
        if (!offers.isEmpty()) {
            double maxOffer = offers.stream()
                    .mapToDouble(OfferResponseDTO::getAmountOffer)
                    .max()
                    .orElse(item.getPrice());
            if (maxOffer > currentPrice) {
                currentPrice = maxOffer;
            }
        }

        Map<String, Object> model = new HashMap<>();
        model.put("item", item);
        model.put("currentPrice", currentPrice);
        model.put("offers", offers);
        model.put("hasOffers", !offers.isEmpty());

        return templateEngine.render(new ModelAndView(model, "item-offers.mustache"));
    }

    public Object getItemOffersJson(Request req, Response res) {
        long itemId = RequestValidator.parseAndValidateId(req.params(":id"));
        List<OfferResponseDTO> offers = offerService.findAllOffersByItemId(itemId);
        res.type("application/json");
        return jsonProvider.toJson(offers);
    }

    public Object getCurrentPrice(Request req, Response res) {
        long itemId = RequestValidator.parseAndValidateId(req.params(":id"));
        ItemResponseDTO item = itemService.findById(itemId);
        List<OfferResponseDTO> offers = offerService.findAllOffersByItemId(itemId);

        double currentPrice = item.getPrice();
        if (!offers.isEmpty()) {
            double maxOffer = offers.stream()
                    .mapToDouble(OfferResponseDTO::getAmountOffer)
                    .max()
                    .orElse(item.getPrice());
            if (maxOffer > currentPrice) {
                currentPrice = maxOffer;
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("itemId", item);
        response.put("initialPrice", item.getPrice());
        response.put("currentPrice", currentPrice);

        res.type("application/json");
        return jsonProvider.toJson(response);
    }
}
