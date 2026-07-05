package com.example.app.websocket.service;

import com.example.app.websocket.BroadcastRegistry;
import com.example.app.websocket.Broadcaster;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Set;

public abstract class AbstractWebSocketMessageService implements Broadcaster {

    public AbstractWebSocketMessageService(BroadcastRegistry registry, long fixedRateMs) {
        registry.register(this, fixedRateMs);
    }

    @Override
    public final void broadcast(Set<WebSocketSession> sessions) throws IOException {
        for (WebSocketSession session : sessions) {
            try {
                send(session);
            } catch (IOException e) {
                sessions.remove(session);
            }
        }
    }

    public void send(WebSocketSession session) throws IOException {
        session.sendMessage(new TextMessage(getPayload()));
    }

    public abstract String getPayload();
}