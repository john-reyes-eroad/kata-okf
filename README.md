# Modern Java 25 Workspace and OKF Knowledge Layer

This repository is a Maven multi-module workspace for a Spring Boot app, with a separate OKF knowledge layer under `ai/`.

## Quick Links

- 📋 **[Validation Report](./VALIDATION_REPORT.md)** — Proof that both REST endpoints and WebSocket broadcasts work
- 🔍 **[Codebase Review](./CODEBASE_REVIEW.md)** — Detailed architectural analysis
- 📚 **[OKF Index](./ai/index.md)** — Skill documentation and guidance

## Workspace Layout

```text
kata-okf/
├── README.md                  # Workspace overview + local run guide
├── ai/                        # OKF knowledge layer
│   ├── index.md               # Workspace entrypoint
│   ├── agents/
│   │   └── java-spring-agent-contract.md
│   │                          # Repository-wide agent contract
│   └── skills/
│       ├── bootstrap-spring-boot-application.md
│       ├── add-spring-boot-endpoint.md
│       ├── bootstrap-websocket-foundation.md
│       ├── add-websocket-broadcast.md
│       └── add-websocket-showcase-demo.md
│                              # Skill templates and generation guidance
│
├── pom.xml                    # Parent/aggregator Maven project
└── app/                       # Core production application (agnostic to AI)
    ├── pom.xml                # Spring Boot 4 and Java 25 module config
    └── src/main/java/...      # Application, controllers, and other Java source
```

## Run Locally

### Prerequisites

- Java 25 (configured in `app/pom.xml`)
- Maven 3.9+

### Build and test

Run from the repository root:

```bash
mvn clean compile
mvn test
```

### Start the app

Run from the repository root:

```bash
mvn -pl app spring-boot:run
```

Or package and run the JAR:

```bash
mvn clean package
java -jar app/target/kata-okf-0.0.1-SNAPSHOT.jar
```

## Configuration

Current app config in `app/src/main/resources/application.properties` includes:

- `spring.threads.virtual.enabled=true`

## Typical Workflow

1. Add Java source under `app/src/main/java/...`
2. Add tests under `app/src/test/java/...`
3. Use Maven from the repository root to compile, test, and package
4. Keep OKF docs in `ai/` for guidance and templates, separate from production code

## OKF Conventions

- `ai/index.md` is the root entrypoint for the knowledge layer.
- `ai/agents/java-spring-agent-contract.md` defines repository-wide operating rules.
- Each concrete skill is stored as a descriptive markdown file under `ai/skills/`.
- Spring Boot bootstrap skill: `ai/skills/bootstrap-spring-boot-application.md`
- Endpoint generation skill: `ai/skills/add-spring-boot-endpoint.md`
- WebSocket foundation skill: `ai/skills/bootstrap-websocket-foundation.md`
- WebSocket broadcast skill: `ai/skills/add-websocket-broadcast.md`
- WebSocket showcase demo skill: `ai/skills/add-websocket-showcase-demo.md`
- New developer AI endpoint guide: `AI_ENDPOINT_GUIDE.md`

## AI Prompting Guide (Using Skills)

Use these prompts when working with an AI coding agent so it explicitly follows the OKF skills in `ai/skills`.

### 1) Bootstrap a new Spring Boot app (required first step)

Prompt example:

```text
Use the OKF skill `ai/skills/bootstrap-spring-boot-application.md`.
Bootstrap a Spring Boot 4 + Java 25 app in `/app` with:
- `Application.java`
- `spring-boot-starter-web` dependency
- basic `application.properties`
Then run compile validation from `/app`.
```

### 2) Generate a Hello World REST controller

Prompt example:

```text
Use the OKF skill `ai/skills/add-spring-boot-endpoint.md`.
Create a controller in `com.example.app.controller` with route `GET /api/v1/hello`.
Return a small JSON payload using a Java record and `ResponseEntity`.
Add or update tests for the endpoint and run compile/test validation from `/app`.
```

### 3) Bootstrap WebSocket foundation

Prompt example:

```text
Use the OKF skill `ai/skills/bootstrap-websocket-foundation.md`.
Create the websocket foundation classes and register endpoint `/ws/kata-okf`.
Important: `WebSocketHandler` must only depend on `BroadcastRegistry` and must not import or inject any class from `com.example.app.websocket.service`.
Run compile validation from `/app`.
```

### 4) Add a concrete WebSocket broadcast stream

Prompt example:

```text
Use the OKF skill `ai/skills/add-websocket-broadcast.md`.
Add one broadcaster service under `com.example.app.websocket.service` that extends `AbstractWebSocketMessageService`.
Include a JSON payload with a `type` field and configure `websocket.<name>.fixed-rate-ms` in `application.properties`.
Do not change `WebSocketHandler` dependencies.
Run compile validation from `/app`.
```

### 5) Optional demo browser client

Prompt example:

```text
Use the OKF skill `ai/skills/add-websocket-showcase-demo.md`.
Add a simple browser demo at `/app/src/main/resources/static/websocket-showcase.html` for websocket testing.
```

### Recommended prompt flow (copy/paste sequence)

```text
1) Apply `bootstrap-spring-boot-application`.
2) Apply `add-spring-boot-endpoint` for `GET /api/v1/hello`.
3) Apply `bootstrap-websocket-foundation`.
4) Apply `add-websocket-broadcast` for one stream.
5) (Optional) Apply `add-websocket-showcase-demo`.
After each step, compile from `/app`.
```

## Testing REST and WebSocket

### Start the app

```bash
mvn -pl app spring-boot:run
```

### Test REST endpoint

```bash
curl -s http://localhost:8080/api/v1/hello
```

Expected shape (example):

```json
{"module":"hello","message":"Hello from hello","status":"ACTIVE"}
```

### Test WebSocket broadcast with browser

Open:

```text
http://localhost:8080/websocket-showcase.html
```

### Test WebSocket broadcast with `wscat`

```bash
npm install -g wscat
wscat -c ws://localhost:8080/ws/kata-okf
```

Expected shape (example):

```json
{"type":"name","name":"Ava"}
```

## Current App Surface

- Spring Boot application module: `app/`
- Sample endpoint: `GET /api/v1/hello`
- WebSocket endpoint: `ws://localhost:8080/ws/kata-okf`

## Testing the WebSocket

### Browser-Based Testing (Recommended)

Once the app is running, open your browser to the showcase client:

```
http://localhost:8080/websocket-showcase.html
```

This interactive page connects to the WebSocket endpoint and displays incoming messages in real time (e.g., random names broadcasted every ~1 second).

### Command-Line Testing with wscat

If you have `wscat` installed globally:

```bash
npm install -g wscat
wscat -c ws://localhost:8080/ws/kata-okf
```

Expected message format:
```json
{"type":"name","name":"Alice"}
{"type":"name","name":"Bob"}
```

### Direct Testing with curl (read-only)

For one-directional inspection:

```bash
curl -i -N -H "Connection: Upgrade" -H "Upgrade: websocket" \
  -H "Sec-WebSocket-Version: 13" \
  -H "Sec-WebSocket-Key: $(openssl rand -base64 16)" \
  http://localhost:8080/ws/kata-okf
```

### Browser DevTools Console

In any browser, connect via the JavaScript console:

```javascript
const socket = new WebSocket("ws://localhost:8080/ws/kata-okf");
socket.onmessage = (event) => console.log(JSON.parse(event.data));
socket.onopen = () => console.log("Connected!");
socket.onclose = () => console.log("Closed!");
```
