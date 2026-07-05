package com.example.app.websocket.service;

import com.example.app.websocket.BroadcastRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class LetterMessageService extends AbstractWebSocketMessageService {

    private static final String PAYLOAD_TEMPLATE = """
            {"type":"letter","letter":"%s"}
            """;

    public LetterMessageService(BroadcastRegistry registry, @Value("${websocket.letter.fixed-rate-ms:10000}") long fixedRateMs) {
        super(registry, fixedRateMs);
    }

    @Override
    public String getPayload() {
        return PAYLOAD_TEMPLATE.formatted(randomLetter()).trim();
    }

    private static char randomLetter() {
        return (char) ('A' + ThreadLocalRandom.current().nextInt(26));
    }
}