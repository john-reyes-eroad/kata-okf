# Modern Java 25 Workspace & OKF Agent Automation Hub

Welcome to the development workspace. This project architecture represents a fully decoupled, agent-ready ecosystem. It cleanly isolates our production-grade Java runtime from our AI orchestration layer, ensuring zero AI dependency pollution inside our enterprise source code.

---

## 🏗️ Workspace Layout

The workspace is split into two independent domains:

```text
kata-okf/
├── README.md                  # This master file
├── ai/                        # External Tooling & AI Knowledge Layer (OKF-compliant)
│   ├── index.md               # The master catalog entrypoint for LLMs
│   ├── AGENTS.md              # AI Onboarding Manual, environment rules, and constraints
│   └── skills/
│       └── SKILLS.md          # Reusable engineering blueprints (Java 25 templates)
│
└── app/                       # Core Production Application (Agnostic to AI)
    ├── pom.xml                # Spring Boot 4 & Java 25 dependencies
    └── src/main/java/...      # Clean web architecture
