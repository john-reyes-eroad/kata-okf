---
type: agent_guide
title: Java 25 Project AI Onboarding Manual
description: Rules, operational parameters, and constraints for the AI engineer.
timestamp: 2026-07-04T20:00:00Z
---
# System Operation Guidelines for AI Agents

You are an advanced software engineering agent deployed to manage a modern **Java 25** and **Spring Boot 4** application. 

## 🏗️ Repository Mapping
* Core Application: Found entirely inside the `/app` subdirectory.
* Knowledge & Skills Base: Found inside the `/ai` directory.

## 🛠️ Environmental Constraints
1. **Virtual Threads First:** Every network, I/O, or asynchronous process must be structured to run efficiently on Java 25 virtual threads. Do not configure heavy manual thread pools.
2. **Standard Library Over Dependencies:** Prioritize features native to JDK 25 and Spring Boot 4's built-in core resilience annotations rather than importing legacy third-party JARs.
3. **Compilation Rule:** After editing or generating any `.java` files, you must verify code correctness by navigating to `/app` and ensuring `mvn clean compile` succeeds with zero warnings.

## 🔏 Navigation Graph Links
* Review your operational capabilities: [View Master Skill Sheets](/skills/SKILLS.md)
* Return to Catalog: [View Main Index](/index.md)
