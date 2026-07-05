package com.example.app.websocket;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Set;

final class BroadcasterEntry {

    private final Broadcaster broadcaster;
    private final long rateMs;
    private long lastExecution = 0;

    BroadcasterEntry(Broadcaster broadcaster, long defaultRate) {
        this.broadcaster = broadcaster;
        this.rateMs = defaultRate;
    }

    void executeIfNeeded(Set<WebSocketSession> sessions) {
        long now = System.currentTimeMillis();
        if (now - lastExecution < rateMs) {
            return;
        }

        lastExecution = now;
        try {
            broadcaster.broadcast(sessions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

