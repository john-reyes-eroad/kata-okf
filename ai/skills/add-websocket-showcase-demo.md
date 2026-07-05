---
type: skill
format: okf/v1
id: kata-okf.add-websocket-showcase-demo
version: 0.0.1
title: Add WebSocket Showcase Demo Client
description: Optional reference implementation of a browser-based WebSocket client for testing and demonstrating real-time message streams.
last_updated: 2026-07-06
depends_on:
  - kata-okf.websocket-bootstrap-foundation
tags:
  - spring-boot
  - websocket
  - frontend
  - demo
  - reference-implementation
---

# Skill: Add WebSocket Showcase Demo Client

Use this skill when you want to add a working HTML reference implementation for testing WebSocket connections and visualizing real-time message streams. This is **optional** and primarily useful for development and demonstration purposes.

## When to Use

- After successfully bootstrapping WebSocket foundation
- When you want an immediate way to test WebSocket connectivity in the browser
- When you need a reference template for building custom WebSocket clients
- For rapid prototyping and visualization of message payloads

## When Not to Use

- For production deployments (use a proper frontend framework instead)
- If you only need server-side WebSocket infrastructure without testing UI
- If you're integrating an existing frontend framework

## Prerequisites

1. WebSocket foundation must be bootstrapped (see `bootstrap-websocket-foundation.md`).
2. The application must be running on a known host and port (default: `localhost:8080`).

## Target Area

- Browser client: `/app/src/main/resources/static/websocket-showcase.html`
- Validation: Open `http://localhost:8080/websocket-showcase.html` in a browser after starting the app

## Rules

1. Keep the showcase client simple and dependency-free (vanilla JavaScript only).
2. Support both `ws://` and `wss://` protocols for development and production.
3. Display connection status with visual feedback (color-coded).
4. Parse and display JSON payloads as-is without hardcoded type handling.
5. Preserve the WebSocket lifecycle event handlers (onopen, onmessage, onclose, onerror).
6. Do not add DOM manipulation libraries or complex styling frameworks.
7. This is a reference implementation; do not expand it into a full-featured UI.

## Implementation Checklist

1. Create `/app/src/main/resources/static/` directory if it does not exist.
2. Create the HTML file at `/app/src/main/resources/static/websocket-showcase.html`.
3. Verify the endpoint path matches your WebSocket handler registration (default: `/ws/kata-okf`).
4. Start the app and navigate to the URL in a browser.
5. Verify real-time messages appear if broadcasters are registered.

## Code Generation Template

Create at `/app/src/main/resources/static/websocket-showcase.html`:

```html
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WebSocket Showcase</title>
    <style>
        body {
            font-family: sans-serif;
            margin: 20px;
        }
        #status {
            font-weight: bold;
        }
        .connected {
            color: green;
        }
        .disconnected {
            color: red;
        }
        .error {
            color: orange;
        }
        #message {
            background-color: #f0f0f0;
            padding: 10px;
            border-radius: 4px;
            font-family: monospace;
            word-break: break-all;
        }
    </style>
</head>
<body>
<h1>WebSocket Message Stream</h1>
<p>Connection status: <span id="status" class="disconnected">connecting...</span></p>
<p>Latest message:</p>
<div id="message">waiting for broadcast...</div>

<script>
    const protocol = window.location.protocol === "https:" ? "wss" : "ws";
    const socket = new WebSocket(`${protocol}://${window.location.host}/ws/kata-okf`);
    const statusEl = document.getElementById("status");
    const messageEl = document.getElementById("message");

    socket.onopen = () => {
        statusEl.textContent = "connected";
        statusEl.className = "connected";
    };

    socket.onmessage = (event) => {
        try {
            const payload = JSON.parse(event.data);
            messageEl.textContent = JSON.stringify(payload, null, 2);
        } catch (e) {
            messageEl.textContent = event.data;
        }
    };

    socket.onclose = () => {
        statusEl.textContent = "connection closed";
        statusEl.className = "disconnected";
    };

    socket.onerror = (error) => {
        statusEl.textContent = "error";
        statusEl.className = "error";
        console.error("WebSocket error:", error);
    };
</script>
</body>
</html>
```

## Browser Testing Workflow

1. **Start the application:**
   ```bash
   mvn -pl app spring-boot:run
   ```

2. **Open the showcase in a browser:**
   ```
   http://localhost:8080/websocket-showcase.html
   ```

3. **Verify connection:**
   - Status should show "connected" in green
   - If not connected, check browser console for errors

4. **Observe messages (if broadcasters are registered):**
   - Real-time JSON payloads appear in the "Latest message" area
   - Each new broadcast updates the display

5. **Use DevTools for debugging:**
   - Press F12 to open browser DevTools
   - Check the Network tab for WebSocket frames
   - Check the Console tab for JavaScript errors

## Customization

When adding new broadcast types (via follow-up skills), you can extend this demo to:

1. Parse the `type` field and apply custom rendering:
   ```javascript
   socket.onmessage = (event) => {
       const payload = JSON.parse(event.data);
       if (payload.type === "name") {
           messageEl.textContent = `Name: ${payload.name}`;
       } else if (payload.type === "timestamp") {
           messageEl.textContent = `Time: ${payload.timestamp}`;
       }
   };
   ```

2. Add more visual elements:
   - Charts for numeric data
   - Lists for repeated messages
   - Styling per message type

3. Add client-side filtering or aggregation if needed

**Note:** Keep customizations minimal and focused. For complex UIs, use a proper frontend framework (React, Vue, Angular, etc.) instead.

## Integration Notes

- This demo client coexists with the WebSocket foundation infrastructure.
- It can be safely served alongside production APIs without interfering.
- Remove or replace this file when deploying to production with a real frontend.

---

**Navigation:**
- [OKF Index](../index.md)
- [Bootstrap WebSocket Foundation](./bootstrap-websocket-foundation.md)
- [Add WebSocket Broadcast](./add-websocket-broadcast.md)

