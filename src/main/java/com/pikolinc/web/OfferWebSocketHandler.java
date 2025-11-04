package com.pikolinc.web;

import com.pikolinc.config.JsonProvider;
import com.pikolinc.config.json.GsonProvider;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@WebSocket
public class OfferWebSocketHandler {
    private static final Map<Long, CopyOnWriteArraySet<Session>> itemSubscriptions = new ConcurrentHashMap<>();
    private static final JsonProvider jsonProvider;
    static {
        jsonProvider = new GsonProvider();
    }

    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException {
        System.out.println("WebSocket connected: " + session.getRemoteAddress());
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) throws IOException {
        itemSubscriptions.values().forEach(sessions -> sessions.remove(session));
        System.out.println("WebSocket closes: " + session.getRemoteAddress());
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException{
        try {
            Map<String, Object> messageData = jsonProvider.fromJson(message, Map.class);
            String action = (String) messageData.get("action");
            if ("subscribe".equals(action)) {
                Number itemIdNum = (Number) messageData.get("itemId");
                Long itemId = itemIdNum.longValue();

                itemSubscriptions.computeIfAbsent(itemId, k -> new CopyOnWriteArraySet<>()).add(session);

                Map<String, Object> response = Map.of(
                        "type", "subscribed",
                        "itemId", itemId
                );
                session.getRemote().sendString(jsonProvider.toJson(response));
                System.out.println("Session subscribed to item: " + itemId);
            }
        } catch (Exception e) {
            System.out.println("Error processing WebSocket message: " + e.getMessage());
            Map<String, Object> errorResponse = Map.of(
                "type", "error",
                "message", "Invalid message format"
            );
            session.getRemote().sendString(jsonProvider.toJson(errorResponse));
        }
    }

    @OnWebSocketError
    public void onError(Session session, Throwable error) {
        System.err.println("WebSocket error: " + error.getMessage());
    }

    public static void notifyPriceUpdate(Long itemId, double newPrice, double initialPrice) {
        CopyOnWriteArraySet<Session> sessions = itemSubscriptions.get(itemId);
        if (sessions != null && !sessions.isEmpty()) {
            Map<String, Object> update = Map.of(
                "type", "priceUpdate",
                "itemId", itemId,
                "currentPrice", newPrice,
                "initialPrice", initialPrice
            );

            String jsonUpdate = jsonProvider.toJson(update);

            sessions.forEach(session -> {
                try {
                    if (session.isOpen()) {
                        session.getRemote().sendString(jsonUpdate);
                    }
                } catch (IOException e) {
                    System.out.println("Error sending WebSocket message: " + e.getMessage());
                    sessions.remove(session);
                }
            });

            System.out.println("Notified " + sessions.size() + " clients about price update for item" + itemId);
        }
    }
}
