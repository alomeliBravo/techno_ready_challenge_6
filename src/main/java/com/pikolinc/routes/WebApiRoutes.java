package com.pikolinc.routes;

import com.pikolinc.controller.WebController;
import spark.Spark;

public class WebApiRoutes implements Router{
    private final WebController webController;
    public WebApiRoutes(WebController webController){
        this.webController = webController;
    }

    @Override
    public void initRoute(){
        Spark.get("/", webController::showItems);
        Spark.get("/items/:id/offers", webController::showItemOffers);

        Spark.path("/api", () -> {
            Spark.before("/*", (req, res) -> {
                res.type("application/json");
            });
            Spark.get("/items/:id/offers", webController::getItemOffersJson);
            Spark.get("/items/:id/current-price", webController::getCurrentPrice);
        });
    }
}
