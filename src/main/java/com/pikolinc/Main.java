package com.pikolinc;


import com.pikolinc.config.JsonProvider;
import com.pikolinc.config.database.JdbiProvider;
import com.pikolinc.config.json.GsonProvider;
import com.pikolinc.controller.ItemController;
import com.pikolinc.controller.OfferController;
import com.pikolinc.controller.UserController;
import com.pikolinc.controller.WebController;
import com.pikolinc.exception.GlobalExceptionHandler;
import com.pikolinc.repository.ItemRepository;
import com.pikolinc.repository.OfferRepository;
import com.pikolinc.repository.UserRepository;
import com.pikolinc.repository.impl.JdbiItemRepository;
import com.pikolinc.repository.impl.JdbiOfferRepository;
import com.pikolinc.repository.impl.JdbiUserRepository;
import com.pikolinc.routes.*;
import com.pikolinc.service.ItemService;
import com.pikolinc.service.OfferService;
import com.pikolinc.service.UserService;
import com.pikolinc.service.impl.ItemServiceImpl;
import com.pikolinc.service.impl.OfferServiceImpl;
import com.pikolinc.service.impl.UserServiceImpl;
import com.pikolinc.web.ItemWebSocketHandler;
import com.pikolinc.web.OfferWebSocketHandler;
import spark.Spark;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Spark.port(8080);
        Spark.staticFiles.location("/public");
        JdbiProvider jdbiProvider = new JdbiProvider();
        jdbiProvider.connect();
        JsonProvider  jsonProvider = new GsonProvider();

        UserRepository userRepository = new JdbiUserRepository(jdbiProvider);
        ItemRepository itemRepository = new JdbiItemRepository(jdbiProvider);
        OfferRepository offerRepository = new JdbiOfferRepository(jdbiProvider);

        UserService userService = new UserServiceImpl(userRepository);
        ItemService itemService = new ItemServiceImpl(itemRepository);
        OfferService offerService = new OfferServiceImpl(offerRepository, itemService, userService);

        UserController userController = new UserController(userService, jsonProvider);
        ItemController itemController = new ItemController(itemService, jsonProvider);
        OfferController offerController = new OfferController(offerService, jsonProvider);
        WebController webController = new WebController(itemService, offerService, jsonProvider);

        Spark.webSocket("/ws/items", ItemWebSocketHandler.class);
        Spark.webSocket("/ws/offers", OfferWebSocketHandler.class);

        List<Router> routes = List.of(
                new UserApiRoutes(userController),
                new ItemApiRoutes(itemController),
                new OfferApiRoutes(offerController),
                new WebApiRoutes(webController)
        );
        routes.forEach(Router::initRoute);
        GlobalExceptionHandler.init();
    }
}