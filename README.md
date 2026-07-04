# Modern Java 25 Workspace and OKF Knowledge Layer

This repository is a Maven multi-module workspace for a Spring Boot app, with a separate OKF knowledge layer under `ai/`.

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
│       └── add-spring-boot-endpoint.md
│                              # Endpoint-generation skill
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
- New developer AI endpoint guide: `AI_ENDPOINT_GUIDE.md`

## Current App Surface

- Spring Boot application module: `app/`
- Sample endpoint: `GET /api/v1/hello`

