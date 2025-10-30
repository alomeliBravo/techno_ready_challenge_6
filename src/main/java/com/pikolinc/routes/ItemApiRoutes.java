package com.pikolinc.routes;

import com.pikolinc.controller.ItemController;
import spark.Spark;

public class ItemApiRoutes implements Router {

    private final ItemController itemController;

    public ItemApiRoutes(ItemController itemController) {
        this.itemController = itemController;
    }

    @Override
    public void initRoute() {
        Spark.path("/api/v1/", () -> {
            Spark.before("/*", (req, res) -> {
                res.type("application/json");
            });
            Spark.post("/items", itemController::saveItem);
            Spark.get("/items", itemController::findAll);
            Spark.get("/items/:id", itemController::findById);
            Spark.put("/items/:id", itemController::updateItem);
            Spark.patch("/items/:id", itemController::patchItem);
            Spark.options("/items/:id", itemController::itemExist);
            Spark.delete("/items/:id", itemController::deleteItem);
        });
    }
}
