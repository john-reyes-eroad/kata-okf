package com.example.app.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.example.app.websocket.service.LetterMessageService;
import com.example.app.websocket.service.TimestampMessageService;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final BroadcastRegistry registry;
    private final TimestampMessageService timestampMessageService;
    private final LetterMessageService letterMessageService;

    public WebSocketHandler(BroadcastRegistry registry,
                           TimestampMessageService timestampMessageService,
                           LetterMessageService letterMessageService) {
        this.registry = registry;
        this.timestampMessageService = timestampMessageService;
        this.letterMessageService = letterMessageService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        registry.getSessions().add(session);
        timestampMessageService.send(session);
        letterMessageService.send(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        registry.getSessions().remove(session);
    }
}