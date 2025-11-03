package com.pikolinc.routes;

import com.pikolinc.controller.UserController;
import spark.Spark;

public class UserApiRoutes implements Router {

    private final UserController userController;

    public UserApiRoutes(UserController userController) {
        this.userController = userController;
    }

    @Override
    public void initRoute() {
        Spark.path("/api/v1", () -> {
           Spark.before("/*",(req, res) -> {
               res.type("application/json");
           });
           Spark.post("/users", userController::saveUser);
           Spark.get("/users", userController::findAll);
           Spark.get("/users/:id", userController::findById);
           Spark.get("/users/by-email/:email", userController::findByEmail);
           Spark.put("/users/:id", userController::updateUserById);
           Spark.patch("/users/:id", userController::patchUserById);
           Spark.options("/users/:id", userController::options);
           Spark.delete("/users/:id", userController::deleteUserById);
        });
    }
}
