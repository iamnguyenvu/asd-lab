<div align="center">

# Week 05 - Docker Compose and Architecture Lab

### Ex02 (Compose Practice) | Ex03 (Spring + DB) | Ex04 (Monolith) | Ex05 (Microservices)

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3+-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-24+-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)

[Overview](#overview) | [Quick Start](#quick-start) | [Project Structure](#project-structure)

---

</div>

## Overview

This week includes four exercises:

1. `ex02`: Docker Compose practice (Part 1 commands, Part 2 guided files, Part 3 advanced exercises).
2. `ex03`: Distributed architecture design for monolith to microservices migration.
3. `ex04`: Mini online food delivery in monolith architecture (React + Spring Boot + PostgreSQL).
4. `ex05`: Same domain as ex04 but redesigned as microservices (Gateway + Catalog + Order + React + PostgreSQL).

## Assignment Coverage

- Docker Compose command familiarity and multi-service orchestration.
- Distributed architecture artifacts: service map, context map, communication diagram, event design.
- Monolith and microservice fullstack implementations with 3 core features.

## Quick Start

### Exercise 02 (Docker Compose Practice)

```bash
cd week05/ex02
```

Check details in:
- `week05/ex02/part1/commands.md`
- `week05/ex02/part2`
- `week05/ex02/part3`

### Exercise 03 (Distributed Architecture Design)

```bash
cd week05/ex03
```

Deliverables:
- `docs/service-map.md`
- `docs/context-map.md`
- `docs/communication-diagram.md`
- `docs/event-messaging-design.md`

### Exercise 04 (Monolith)

```bash
cd week05/ex04
docker compose up --build
```

- Frontend: http://localhost:4173
- Backend: http://localhost:8083/api

### Exercise 05 (Microservices)

```bash
cd week05/ex05
docker compose up --build
```

- Frontend: http://localhost:4174
- Gateway API: http://localhost:8084

## Project Structure

```text
week05/
├── README.md
├── ex01/
├── ex02/
│   ├── part1/ (compose commands)
│   ├── part2/ (bai01..bai15)
│   └── part3/ (baitap01..baitap10)
├── ex03/
│   ├── README.md
│   └── docs/ (service map, context map, communication, event design)
├── ex04/
│   ├── docker-compose.yml
│   ├── README.md
│   ├── backend/  (Spring Boot monolith)
│   └── frontend/ (React)
└── ex05/
    ├── docker-compose.yml
    ├── README.md
    ├── api-gateway/
    ├── catalog-service/
    ├── order-service/
    └── frontend/
```
