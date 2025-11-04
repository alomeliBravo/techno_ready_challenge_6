package com.pikolinc.web;

import com.pikolinc.config.JsonProvider;
import com.pikolinc.config.json.GsonProvider;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@WebSocket
public class ItemWebSocketHandler {
    private static final CopyOnWriteArraySet<Session> sessions = new CopyOnWriteArraySet<>();
    private static final JsonProvider jsonProvider;

    static {
        jsonProvider = new GsonProvider();
    }

    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException {
        sessions.add(session);
        System.out.println("Item WebSocket connected: " + session.getRemoteAddress());

        Map<String, Object> response = Map.of(
                "type", "connected",
                "message", "Connected to items updates"
        );

        session.getRemote().sendString(jsonProvider.toJson(response));
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reaseon) {
        sessions.remove(session);
        System.out.println("Item WebSocket closed: " + session.getRemoteAddress());
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {}

    @OnWebSocketError
    public void onError(Session session, Throwable error) {
        System.out.println("Item WebSocket error: " + error.getMessage());
    }

    public static void notifyItemsUpdate() {
        if (sessions.isEmpty()) return;

        Map<String, Object> update = Map.of(
                "type", "itemsUpdate",
                "message", "Item list has been updated"
        );

        String jsonUpdate = jsonProvider.toJson(update);

        sessions.forEach(session -> {
            try {
                if (session.isOpen()) {
                    session.getRemote().sendString(jsonUpdate);
                }
            } catch (IOException e) {
                System.err.println("Error sending Item WebsSocket message: " + e.getMessage());
                sessions.remove(session);
            }
        });
        System.out.println("Notified" + sessions.size() + " clients");
    }
}
