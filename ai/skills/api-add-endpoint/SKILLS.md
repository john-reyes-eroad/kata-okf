---
type: skill_catalog
title: Master Agent Skills Ledger
description: A collection of executable recipes for modifying and inspecting the project.
tags:
  - automation
  - boilerplate-generation
---
# Reusable Engineering Skills

Use the blueprints below when requested to expand the Java application.

---

## Skill 1: Injecting a Java 25 Multi-Line Data Endpoint
Use this skill when the user asks to add an informational endpoint or JSON-mock provider route to the application.

### Implementation Checklist
1. Find the target package route: `maven-app/src/main/java/com/example/app/controller/`
2. Create a clean Java record for data transport to maximize memory safety and lean compilation.
3. Write a standard Spring Boot 4 `@RestController` returning a `ResponseEntity`.

### Code Generation Template
```java
package com.example.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/{{resourceName}}")
public class {{ClassName}}Controller {

    // Utilizing modern Java 25 Record definitions
    public record {{ClassName}}Response(String status, String payload) {}

    @GetMapping
    public ResponseEntity<{{ClassName}}Response> fetchDetails() {
        // Enforcing Java 25 text blocks for structural layout
        String rawData = """
            {
                "module": "{{resourceName}}",
                "compiledVia": "JDK 25"
            }
            """;
            
        return ResponseEntity.ok(new {{ClassName}}Response("ACTIVE", rawData.stripIndent()));
    }
}
