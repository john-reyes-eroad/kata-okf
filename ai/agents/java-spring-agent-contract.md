---
type: agent_guide
format: okf/v1
id: kata-okf.agent-guide
version: 0.0.1
title: OKF Agent Contract for Java 25 and Spring Boot 4
description: Canonical operating rules, workspace layout, and delivery checks for agents working in this repository.
last_updated: 2026-07-05
stack:
  - java-25
  - spring-boot-4
  - maven-multi-module
---
# OKF Agent Contract

This repository uses an **OKF** (Open Knowledge Framework) layout to keep AI guidance isolated from production code while still making the project easy to navigate.

## Repository Boundaries
- **Production application:** `/app`
- **OKF knowledge layer:** `/ai`
- **Repository root:** Maven aggregator, shared docs, and workspace metadata

## OKF Layout Rules
1. The workspace entrypoint is `/ai/index.md`.
2. Repository-wide agent policy lives in `/ai/agents/java-spring-agent-contract.md`.
3. Skill guidance lives in descriptive markdown files under `/ai/skills`.
4. AI guidance files must stay inside `/ai`; production code in `/app` must remain AI-agnostic.

## Agent Personas
- **copilot-code-generator:** Produces focused implementation changes by following applicable skills and stack rules.
- **copilot-reviewer:** Prioritizes defects, regressions, and missing tests before style or naming feedback.
- **architect-agent:** Proposes structure and boundaries, then defers implementation details to skills.

## Persona Routing
1. Use **copilot-code-generator** for implementation requests that change code or tests.
2. Use **copilot-reviewer** for review-only requests, risk analysis, or regression-focused audits.
3. Use **architect-agent** for system design, module boundary, or migration planning requests.
4. For mixed requests, perform architecture scoping first, then implementation, then review.

## Engineering Rules
1. **Java baseline:** Target **Java 25**.
2. **Framework baseline:** Use **Spring Boot 4** conventions and built-in platform features first.
3. **Virtual threads first:** Prefer Java 25 virtual-thread-friendly designs for blocking I/O and async work.
4. **Standard library over dependencies:** Reach for JDK and Spring facilities before adding third-party libraries.
5. **Minimal surface area:** Keep changes focused; avoid reshaping unrelated code or docs.

## Delivery Checklist
1. Read `/ai/index.md` before broader changes.
2. Use the relevant descriptive file under `/ai/skills` for generation patterns.
3. If `.java` files change, validate from `/app` with `mvn clean compile` before closing the task.
4. If behavior changes, run tests from `/app` with `mvn test` (or targeted module tests) before closing the task.
5. Update OKF links when files move so the navigation graph stays accurate.

## Error Handling and Recovery
1. If compile fails, confirm toolchain first (`java -version`, `mvn -version`) before changing code.
2. Re-run with debug details from `/app` using `mvn clean compile -e`.
3. Apply the smallest possible fix and avoid broad refactors while recovering.
4. If a generated change is invalid, revert only the scoped change-set and regenerate from the relevant skill template.

## Navigation
- [Workspace Index](../index.md)
- [Endpoint Skill](../skills/add-spring-boot-endpoint.md)




