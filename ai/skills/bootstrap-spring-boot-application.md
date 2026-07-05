---
type: skill
format: okf/v1
id: kata-okf.bootstrap-spring-boot-application
version: 0.0.1
title: Bootstrap Spring Boot 4 Application with Java 25
description: Approved pattern for bootstrapping a Spring Boot 4 application with Java 25, including the main Application class with @SpringBootApplication, maven dependency spring-boot-starter-web, and application.properties configured with virtual threads enabled.
last_updated: 2026-07-06
depends_on: []
tags:
  - spring-boot
  - java-25
  - bootstrap
  - application-startup
  - virtual-threads
  - maven
---
# Skill: Bootstrap Spring Boot 4 Application with Java 25

Use this skill when the user asks to set up a new Spring Boot 4 application from scratch with Java 25, including the main entry point, web starter dependency, and virtual threads configuration.

## Prerequisites
1. `app/pom.xml` must exist and be configured as a Maven module.
2. Java 25 must be available in the build environment.
3. Spring Boot 4.x must be available via Maven Central.

## Target Area
- Maven configuration: `/app/pom.xml`
- Main application class: `/app/src/main/java/com/example/app/Application.java`
- Configuration: `/app/src/main/resources/application.properties`
- Validation command: `cd /app && mvn clean compile`

## Rules
1. Use Java 25 as the project baseline.
2. Always add `spring-boot-starter-web` as a primary dependency for REST capabilities.
3. Enable virtual threads by setting `spring.threads.virtual.enabled=true` in `application.properties`.
4. The main Application class must be annotated with `@SpringBootApplication`.
5. Keep the Application class minimal with only the `main(String[])` method.
6. Use `SpringApplication.run(Application.class, args)` as the standard startup pattern.
7. Do not add business logic or controllers to the Application class itself.
8. Ensure the package structure follows the standard `com.example.app` convention.
9. Assume the resources directory exists at `/app/src/main/resources`.

## When Not to Use This Skill
1. Avoid this skill if only modifying an existing application.
2. Avoid this skill for adding additional features beyond the core bootstrap.
3. Avoid this skill if the user specifies a different Java version or Spring Boot version.

## Implementation Checklist
1. Ensure `app/pom.xml` includes Spring Boot 4 parent pom reference.
2. Add `spring-boot-starter-web` dependency to `/app/pom.xml`.
3. Verify or create the directory structure: `/app/src/main/java/com/example/app/`.
4. Create the `Application.java` main class with `@SpringBootApplication` and `main()` method.
5. Ensure `/app/src/main/resources/` directory exists.
6. Create or update `application.properties` with `spring.threads.virtual.enabled=true`.
7. Compile from `/app` using `mvn clean compile` to validate.
8. Verify no import or compilation errors.

## Rollback and Cleanup
1. If compile fails, check the `pom.xml` parent reference first.
2. Verify Java version with `java -version` (must be Java 25+).
3. Remove any unused imports from the Application class.
4. Re-apply with minimal setup and validate again.

## Maven Dependency Template
Add to `/app/pom.xml` in the `<dependencies>` section:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

## Application Class Template
Create at `/app/src/main/java/com/example/app/Application.java`:
```java
package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

## Application Properties Template
Create or update `/app/src/main/resources/application.properties`:
```properties
spring.threads.virtual.enabled=true
```

## Integration Notes
- After bootstrap, follow the **Add a Spring Boot Endpoint** skill to create REST controllers.
- Follow the **Bootstrap WebSocket Foundation** skill if real-time communication is needed.
- The virtual threads setting enables Project Loom's preview feature for non-blocking I/O patterns.

