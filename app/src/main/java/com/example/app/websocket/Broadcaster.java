package com.example.app.websocket;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Set;

public interface Broadcaster {

    void broadcast(Set<WebSocketSession> sessions) throws IOException;
}

