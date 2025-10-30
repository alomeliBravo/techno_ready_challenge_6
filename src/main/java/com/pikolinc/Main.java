package com.pikolinc;


import com.pikolinc.config.database.JdbiProvider;
import com.pikolinc.config.json.GsonProvider;
import com.pikolinc.controller.ItemController;
import com.pikolinc.controller.UserController;
import com.pikolinc.exception.GlobalExceptionHandler;
import com.pikolinc.repository.impl.JdbiItemRepository;
import com.pikolinc.repository.impl.JdbiUserRepository;
import com.pikolinc.routes.ItemApiRoutes;
import com.pikolinc.routes.Router;
import com.pikolinc.routes.UserApiRoutes;
import com.pikolinc.service.impl.ItemServiceImpl;
import com.pikolinc.service.impl.UserServiceImpl;
import spark.Spark;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Spark.port(8080);
        JdbiProvider jdbiProvider = new JdbiProvider();
        jdbiProvider.connect();
        List<Router> routes = List.of(
                new UserApiRoutes(new UserController(new UserServiceImpl(new JdbiUserRepository(jdbiProvider)), new GsonProvider())),
                new ItemApiRoutes(new ItemController(new ItemServiceImpl(new JdbiItemRepository(jdbiProvider)), new GsonProvider()))
        );
        routes.forEach(Router::initRoute);
        GlobalExceptionHandler.init();
    }
}