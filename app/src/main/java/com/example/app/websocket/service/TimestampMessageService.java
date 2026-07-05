package com.example.app.websocket.service;

import com.example.app.websocket.BroadcastRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TimestampMessageService extends AbstractWebSocketMessageService {

    private static final String PAYLOAD_TEMPLATE = """
            {"type":"timestamp","timestamp":"%s"}
            """;

    public TimestampMessageService(BroadcastRegistry registry, @Value("${websocket.timestamp.fixed-rate-ms:5000}") long fixedRateMs) {
        super(registry, fixedRateMs);
    }

    @Override
    public String getPayload() {
        return PAYLOAD_TEMPLATE.formatted(Instant.now()).trim();
    }
}