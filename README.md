# OKF Showcase: Java 25 + Spring Boot 4 REST + WebSocket

This repository showcases an **OKF (Open Knowledge Framework)** setup where an AI agent uses an agent contract plus skills to generate and evolve a Spring Boot application.

Primary goal for new developers: clone this repo, use AI prompts that reference `ai/skills`, and generate implementations for:
- REST endpoint(s)
- WebSocket foundation and broadcast stream(s)

## What This Repo Demonstrates

- `ai/agents/java-spring-agent-contract.md`: repository-level agent rules
- `ai/skills/*.md`: reusable implementation skills
- `app/`: generated/maintained production Spring Boot code

## New Developer Quickstart (AI First)

1. Clone and open the repo.
2. Ask the AI agent to follow the contract and skills under `ai/`.
3. Run the prompt sequence below to generate/update implementations.
4. Compile and test after each generated step.

## 1) Clone and Open

```bash
git clone <your-repo-url>
cd kata-okf
```

Before prompting the AI agent, open this folder in your IDE and ensure the agent can read files under `ai/` (especially `ai/index.md`, `ai/agents/java-spring-agent-contract.md`, and `ai/skills/*.md`).

## 2) Prompt the AI to Use OKF Skills

Use prompts that explicitly mention the skill file path.

### Prompt A: Bootstrap Application

```text
Read and follow `ai/agents/java-spring-agent-contract.md`.
Then apply skill `ai/skills/bootstrap-spring-boot-application.md`.
Bootstrap/verify a Spring Boot 4 + Java 25 app in `/app`.
Run compile validation from `/app`.
```

### Prompt B: Generate Hello REST Endpoint

```text
Apply skill `ai/skills/add-spring-boot-endpoint.md`.
Create `GET /api/v1/hello` in `com.example.app.controller`.
Return `ResponseEntity` with a small response record.
Add/update tests and run `mvn clean compile` and `mvn test` from `/app`.
```

### Prompt C: Bootstrap WebSocket Foundation

```text
Apply skill `ai/skills/bootstrap-websocket-foundation.md`.
Set up endpoint `/ws/kata-okf` and foundation classes.
Enforce this rule: `WebSocketHandler` must depend only on `BroadcastRegistry` and must not import/inject concrete services from `com.example.app.websocket.service`.
Run compile validation from `/app`.
```

### Prompt D: Add WebSocket Broadcast Stream

```text
Apply skill `ai/skills/add-websocket-broadcast.md`.
Add one concrete broadcaster service extending `AbstractWebSocketMessageService`.
Emit JSON with a unique `type` field.
Add `websocket.<name>.fixed-rate-ms` in `app/src/main/resources/application.properties`.
Run compile validation and tests from `/app`.
```

### Prompt E (Optional): Add Browser Demo Client

```text
Apply skill `ai/skills/add-websocket-showcase-demo.md`.
Create/update `app/src/main/resources/static/websocket-showcase.html` to visualize websocket messages.
```

## Recommended Prompt Sequence

```text
1) bootstrap-spring-boot-application
2) add-spring-boot-endpoint
3) bootstrap-websocket-foundation
4) add-websocket-broadcast
5) add-websocket-showcase-demo (optional)
```

## 2.1) Validate After Each Prompt

Run these from `app/` after each generated step:

```bash
cd app
mvn clean compile
mvn test
```

If a step is infrastructure-only and no tests were added yet, `mvn clean compile` is the minimum validation.

## 3) Build, Run, and Test

### Prerequisites

- Java 25
- Maven 3.9+

### Compile and test

```bash
cd app
mvn clean compile
mvn test
```

### Start application

```bash
cd ..
mvn -pl app spring-boot:run
```

### Test REST endpoint

```bash
curl -s http://localhost:8080/api/v1/hello
```

Expected example response:

```json
{"module":"hello","message":"Hello from hello","status":"ACTIVE"}
```

### Test WebSocket broadcast (browser)

Use this only if you ran Prompt E (`add-websocket-showcase-demo`) or already have `app/src/main/resources/static/websocket-showcase.html` in your project.

Open:

```text
http://localhost:8080/websocket-showcase.html
```

### Test WebSocket broadcast (`wscat`)

This works even without the optional browser demo client.

```bash
npm install -g wscat
wscat -c ws://localhost:8080/ws/kata-okf
```

Expected example message:

```json
{"type":"name","name":"Ava"}
```

## Skill and Contract Index

- `ai/index.md`
- `ai/agents/java-spring-agent-contract.md`
- `ai/skills/bootstrap-spring-boot-application.md`
- `ai/skills/add-spring-boot-endpoint.md`
- `ai/skills/bootstrap-websocket-foundation.md`
- `ai/skills/add-websocket-broadcast.md`
- `ai/skills/add-websocket-showcase-demo.md`
