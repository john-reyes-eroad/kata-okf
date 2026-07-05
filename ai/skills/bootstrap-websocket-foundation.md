---
type: skill
format: okf/v1
id: kata-okf.websocket-bootstrap-foundation
version: 0.0.1
title: Bootstrap WebSocket Foundation
description: Approved pattern for setting up the shared websocket infrastructure in app module, without adding any concrete broadcast stream.
last_updated: 2026-07-06
depends_on:
  - kata-okf.bootstrap-spring-boot-application
tags:
  - spring-boot
  - websocket
  - java-25
  - backend/realtime
  - backend/infrastructure
---
# Skill: Bootstrap WebSocket Foundation

Use this skill when the user asks to set up WebSocket support and scheduler plumbing, but does not ask for specific broadcast payloads like timestamp, letters, or domain events.

## Prerequisites
1. `app/pom.xml` must include the Maven dependency `org.springframework.boot:spring-boot-starter-websocket`.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
```

## Target Area
- WebSocket config: `/app/src/main/java/com/example/app/config`
- Core websocket package: `/app/src/main/java/com/example/app/websocket`
- Base message service package: `/app/src/main/java/com/example/app/websocket/service`
- Validation command: `cd /app && mvn clean compile`

## Rules
1. Keep the endpoint registration and session lifecycle in `WebSocketHandler`.
2. `WebSocketHandler` must depend on `BroadcastRegistry` only; never inject or import concrete broadcaster classes from `com.example.app.websocket.service`.
3. Keep broadcaster scheduling concerns in `BroadcastRegistry` and `BroadcasterEntry` only.
4. Keep broadcaster contract in `Broadcaster` only.
5. Future broadcast implementations must use `AbstractWebSocketMessageService` as the parent class.
6. Keep shared send/broadcast mechanics in `AbstractWebSocketMessageService`.
7. Do not duplicate `broadcast(...)` or `send(...)` logic in concrete broadcaster classes.
8. Do not add concrete broadcaster services in this skill.
9. Do not add browser clients in this skill; use the separate demo client skill instead.

## Required Foundation Pattern
The bootstrap output must include and use this class set as the canonical foundation pattern:
1. `Broadcaster`
2. `BroadcasterEntry`
3. `BroadcastRegistry`
4. `WebSocketHandler`
5. `WebSocketConfig`

Do not replace this set with alternative abstractions in bootstrap generation unless the user explicitly requests an architecture change.

## Foundation Components to Add
1. `WebSocketConfig`
   - Annotate with `@Configuration`, `@EnableWebSocket`, `@EnableScheduling`.
   - Register handler at `/ws/kata-okf`.
2. `WebSocketHandler`
   - Extend `TextWebSocketHandler`.
   - Add/remove sessions through `BroadcastRegistry` only.
   - Must not import or reference classes in `com.example.app.websocket.service`.
3. `Broadcaster`
   - Define `broadcast(Set<WebSocketSession> sessions)`.
4. `BroadcasterEntry`
   - Hold broadcaster + rate and execute with short-circuit timing guard.
5. `BroadcastRegistry`
   - Store active sessions.
   - Register broadcasters with rates.
   - Run `broadcastAll()` on a short fixed scheduler.
6. `AbstractWebSocketMessageService`
   - Implement `Broadcaster`.
   - Register itself with `BroadcastRegistry` via constructor.
   - Provide template methods: `send(session)` and `getPayload()`.

## Implementation Checklist
1. Add `spring-boot-starter-websocket` dependency if missing.
2. Create the required class set (`Broadcaster`, `BroadcasterEntry`, `BroadcastRegistry`, `WebSocketHandler`, `WebSocketConfig`).
3. Ensure `WebSocketHandler` has no imports/constructor dependencies from `com.example.app.websocket.service`.
4. Ensure no concrete broadcaster classes are generated.
5. Keep imports minimal and remove dead fields.
6. Compile from `/app` after Java edits.

## Rollback and Cleanup
1. If compile fails, revert only the foundation files changed in scope.
2. Remove unused constructor params/imports before re-running compile.
3. Re-apply with minimal classes first (`Broadcaster`, `BroadcastRegistry`, `WebSocketHandler`, `WebSocketConfig`).

## Code Generation Template
```java
package com.example.app.websocket;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Set;

public interface Broadcaster {
    void broadcast(Set<WebSocketSession> sessions) throws IOException;
}
```

```java
package com.example.app.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final BroadcastRegistry registry;

    public WebSocketHandler(BroadcastRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        registry.getSessions().add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        registry.getSessions().remove(session);
    }
}
```

```java
package com.example.app.websocket.service;

import com.example.app.websocket.BroadcastRegistry;
import com.example.app.websocket.Broadcaster;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Set;

public abstract class AbstractWebSocketMessageService implements Broadcaster {

    protected AbstractWebSocketMessageService(BroadcastRegistry registry, long fixedRateMs) {
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
```

## Minimal Broadcaster Example
Use this as the required concrete implementation pattern when a follow-up skill asks for a specific stream.

```java
package com.example.app.websocket.service;

import com.example.app.websocket.BroadcastRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SampleMessageService extends AbstractWebSocketMessageService {

    private static final String PAYLOAD_TEMPLATE = """
            {"type":"sample","value":"%s"}
            """;

    public SampleMessageService(BroadcastRegistry registry,
            @Value("${websocket.sample.fixed-rate-ms:10000}") long fixedRateMs) {
        super(registry, fixedRateMs);
    }

    @Override
    public String getPayload() {
        return PAYLOAD_TEMPLATE.formatted("sample-value").trim();
    }
}
```

## Browser Client (Optional)

A separate reference skill provides an optional browser-based demo client for testing WebSocket connections:

- **Skill:** [Add WebSocket Showcase Demo Client](./add-websocket-showcase-demo.md)
- **Purpose:** Simple HTML/JavaScript reference implementation for development and testing
- **When to use:** After bootstrap, if you want an immediate browser-based way to test messages

The demo client is **not required** for WebSocket foundation setup but is useful for rapid prototyping and visualization during development.

