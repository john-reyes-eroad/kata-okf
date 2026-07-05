---
type: skill
format: okf/v1
id: kata-okf.websocket-add-broadcast
version: 0.0.1
title: Add a WebSocket Broadcast
description: Approved pattern for adding a new scheduled WebSocket broadcast type to the existing registry-based websocket pipeline.
last_updated: 2026-07-06
depends_on:
  - ./bootstrap-websocket-foundation.md
tags:
  - spring-boot
  - websocket
  - java-25
  - backend/realtime
  - backend/scheduler
---
# Skill: Add a WebSocket Broadcast

Use this skill when the user asks for another data stream on the existing WebSocket endpoint (`/ws/kata-okf`) and the new stream should be broadcast on its own schedule.

Prerequisite: complete the WebSocket foundation setup from `ai/skills/bootstrap-websocket-foundation.md` first.

Mandatory pattern: every new websocket broadcast implementation must extend `AbstractWebSocketMessageService`.

## Target Area
- Message services: `/app/src/main/java/com/example/app/websocket/service`
- Runtime config: `/app/src/main/resources/application.properties`
- Validation command: `cd /app && mvn clean compile`

## Foundation Dependency
1. Reuse the existing foundation classes from `bootstrap-websocket-foundation.md`.
2. Do not regenerate foundation wiring in this skill.
3. Only add concrete broadcaster services and related config.

## Rules
1. Keep the endpoint path unchanged unless the user explicitly asks to modify it.
2. Add one focused broadcaster service per new broadcast type.
3. Future broadcaster classes must extend `AbstractWebSocketMessageService`.
4. Keep payload generation inside the concrete service (`getPayload()`), and keep send/broadcast mechanics in `AbstractWebSocketMessageService`.
5. Do not duplicate base `send(...)` or `broadcast(...)` logic in concrete classes.
6. New broadcaster fixed-rate values must be stored in `app/src/main/resources/application.properties`.
7. Follow the registration approach defined by the current foundation implementation.
8. Update the browser showcase only if the new data should be visible there.

## Implementation Checklist
1. Create a new service class in `com.example.app.websocket.service` extending `AbstractWebSocketMessageService`.
2. Define a payload text block constant with a `type` field unique to the new stream.
3. Add `websocket.<name>.fixed-rate-ms=<value>` to `app/src/main/resources/application.properties`.
4. Inject the fixed-rate config value with `@Value("${websocket.<name>.fixed-rate-ms:<default>}")`.
5. Register the service using the foundation pattern already in place.
6. Implement `getPayload()` with the new data generation logic only.
7. Do not override base `send(...)` or `broadcast(...)` from `AbstractWebSocketMessageService`.
8. If needed, update `websocket-showcase.html` to handle the new `type` and render it.
9. Compile from `/app` after Java changes.

## Rollback and Cleanup
1. If compile fails, revert only the new broadcaster files/edits.
2. Remove any unused imports or unused properties.
3. Re-apply with the smallest possible payload-only implementation that matches the foundation pattern.

## Example Reference
Reuse the concrete class pattern from `bootstrap-websocket-foundation.md` under **Minimal Broadcaster Example**.

For this skill, change only:
1. Class name (`{{ClassName}}MessageService`)
2. `type` value in payload JSON
3. Value expression in `getPayload()`
4. Property key `websocket.{{type}}.fixed-rate-ms` in `app/src/main/resources/application.properties`




