---
type: index
format: okf/v1
id: kata-okf.index
title: OKF Workspace Index
description: Root navigation graph for the OKF knowledge layer that supports the Java 25 and Spring Boot 4 workspace.
---
# OKF Workspace Index

Use this file as the first stop when working in the repository. It defines the canonical OKF navigation path for agents supporting the **Java 25** and **Spring Boot 4** application.

## Layout Summary
- `/app` contains production Java code and runtime resources.
- `/ai` contains the OKF knowledge layer only.
- `/ai/agents` stores descriptive agent contract files.
- `/ai/skills` stores descriptive skill markdown files.

## Core Navigation Graph
- **Operational policy:** [agents/java-spring-agent-contract.md](./agents/java-spring-agent-contract.md)
- **Endpoint template skill:** [skills/add-spring-boot-endpoint.md](./skills/add-spring-boot-endpoint.md)

## Execution Constraints
1. Keep AI documentation and prompts inside `/ai` only.
2. Keep navigation links synchronized with any structural changes.
3. Compile from `/app` after Java changes to protect the production module.
