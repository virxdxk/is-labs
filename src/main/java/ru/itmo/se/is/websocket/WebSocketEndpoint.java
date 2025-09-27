package ru.itmo.se.is.websocket;

import jakarta.json.Json;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/ws")
public class WebSocketEndpoint {
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

    public static void broadcast(WebSocketMessageType type) {
        String message = Json.createObjectBuilder()
                .add("type", type.name())
                .build()
                .toString();
        synchronized (sessions) {
            for (Session session : sessions) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }
}
