package com.example.app.websocket;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class BroadcastRegistry {

    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
    private final List<BroadcasterEntry> broadcasters = new CopyOnWriteArrayList<>();

    public void register(Broadcaster broadcaster, long defaultRate) {
        broadcasters.add(new BroadcasterEntry(broadcaster, defaultRate));
    }

    public Set<WebSocketSession> getSessions() {
        return sessions;
    }

    @Scheduled(fixedRate = 100)
    public void broadcastAll() {
        sessions.removeIf(session -> !session.isOpen());
        for (BroadcasterEntry entry : broadcasters) {
            entry.executeIfNeeded(sessions);
        }
    }
}