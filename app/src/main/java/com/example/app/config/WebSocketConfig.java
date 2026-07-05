package com.example.app.config;

import com.example.app.websocket.WebSocketHandler;
import com.example.app.websocket.BroadcastRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@EnableScheduling
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;
    private final BroadcastRegistry registry;

    public WebSocketConfig(WebSocketHandler webSocketHandler, BroadcastRegistry registry) {
        this.webSocketHandler = webSocketHandler;
        this.registry = registry;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/ws/kata-okf").setAllowedOriginPatterns("*");
    }
}

