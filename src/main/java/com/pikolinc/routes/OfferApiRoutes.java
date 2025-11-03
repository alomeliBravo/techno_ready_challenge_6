package com.pikolinc.routes;

import com.pikolinc.controller.OfferController;
import spark.Spark;

public class OfferApiRoutes implements Router{
    private final OfferController offerController;

    public OfferApiRoutes(OfferController offerController) {
        this.offerController = offerController;
    }

    @Override
    public void initRoute(){
        Spark.path("/api/v1", () -> {
            Spark.before("/*", (req, res) -> {
                res.type("application/json");
            });
            Spark.post("/offers", offerController::saveOffer);
            Spark.get("/offers", offerController::findAll);
            Spark.get("/offers/:id", offerController::findById);
            Spark.get("/offers/user/:userId", offerController::findAllOfferByUserId);
            Spark.get("/offers/item/:itemId", offerController::findAllOffersByItemId);
            Spark.put("/offers/:id", offerController::updateOfferById);
            Spark.put("/offers/:id/:status", offerController::updateOfferStatus);
            Spark.options("/offers/:id", offerController::offerExist);
            Spark.delete("/offers/:id", offerController::deleteOfferById);
        });
    }

}
