---
type: skill
format: okf/v1
id: kata-okf.api-add-endpoint
version: 0.0.1
title: Add a Spring Boot Endpoint
description: Approved pattern for creating or modifying a lightweight REST endpoint in the app module.
last_updated: 2026-07-05
depends_on: []
tags:
  - spring-boot
  - endpoint
  - java-25
  - backend/rest-api
  - backend/spring-boot/controller
---
# Skill: Add a Spring Boot Endpoint

Use this skill when the user asks for a new HTTP endpoint, a simple response payload, or a lightweight controller update.

## Target Area
- Java source root: `/app/src/main/java`
- Preferred controller package: `/app/src/main/java/com/example/app/controller`
- Validation command: `cd /app && mvn clean compile`

## Rules
1. Keep production code inside `/app` only.
2. Prefer one focused controller change per endpoint request.
3. Use a Java `record` for compact response payloads when the payload is small and request-scoped.
4. Return `ResponseEntity` from controller methods.
5. Prefer domain-shaped response fields over embedding JSON as a string payload.
6. When request input exists, include validation and security annotations consistent with project conventions.

## When Not to Use This Skill
1. Avoid this skill for streaming, SSE, WebSocket, or other long-lived response patterns.
2. Avoid this skill when endpoint creation also requires cross-module architecture changes.
3. Avoid batching unrelated endpoint additions into a single change.

## Implementation Checklist
1. Locate the target package and confirm whether an existing controller should be extended.
2. Add or update a `@RestController` in `com.example.app.controller`.
3. Add a small response `record` when the endpoint returns structured JSON.
4. Use a clear route under `/api/v1/...` unless the user asks for another path.
5. Compile from `/app` after editing Java sources.
6. Add or update controller tests for new behavior (mock MVC, slice test, or project-standard style).

## Rollback and Cleanup
1. If compile validation fails, revert only the endpoint change in scope.
2. Remove newly introduced unused imports or empty packages before re-running compile.
3. Re-apply the endpoint from the template with minimal edits and validate again.

## Code Generation Template
```java
package {{packageName}};

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/{{resourceName}}")
public class {{ClassName}}Controller {

    public record {{ClassName}}Response(String module, String message, String status) {}

    @GetMapping
    public ResponseEntity<{{ClassName}}Response> fetchDetails() {
        return ResponseEntity.ok(new {{ClassName}}Response("{{resourceName}}", "Hello from {{resourceName}}", "ACTIVE"));
    }
}


