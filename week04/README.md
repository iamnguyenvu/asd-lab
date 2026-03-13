<div align="center">

# Week 04 - Architecture Styles Lab

### Plugin-based CMS with Layered and Microkernel Architecture

[![Node.js](https://img.shields.io/badge/Node.js-20+-339933?style=for-the-badge&logo=node.js&logoColor=white)](https://nodejs.org/)
[![Express](https://img.shields.io/badge/Express-4.19-000000?style=for-the-badge&logo=express&logoColor=white)](https://expressjs.com/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-24+-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![C4 Model](https://img.shields.io/badge/C4-Container%20View-1f6feb?style=for-the-badge)](https://c4model.com/)

[Architecture](#-architecture-artifacts) | [Quick Start](#-quick-start) | [API](#-api-overview) | [Project Structure](#-project-structure)

---

</div>

## Overview

This week delivers a complete Plugin-based CMS that demonstrates both:
- Layered Architecture for clean separation of concerns.
- Microkernel Architecture for plugin-driven extensibility.

The system includes:
- Full-stack runnable application (frontend, backend, database).
- C4 container-level architecture diagrams.
- Trade-off analysis and style recommendation.
- Working plugin runtime with enable/disable support.

## Assignment Deliverables

1. Two architecture diagrams at C4 Container level.
2. Style trade-off analysis (Layered vs Microkernel).
3. Architecture decision for CMS.
4. Full implementation with frontend, backend, and PostgreSQL.

## Core Features

### 1) Content Lifecycle Management
- Create content draft.
- List and view stored content.
- Update and delete content.

### 2) Publishing Workflow
- Draft by default.
- Explicit publish action to move content into published state.

### 3) Plugin Management
- Plugin manager loads and orchestrates plugin hooks.
- Runtime API to list and toggle plugins.
- Built-in plugins:
	- `seo-plugin`
	- `audit-plugin`

## Architecture Artifacts

### C4 Container Diagrams

- Layered Architecture DSL: `architecture/layered-container.dsl`
- Microkernel Architecture DSL: `architecture/microkernel-container.dsl`
- Trade-off document: `architecture/style-tradeoff.md`
- Detailed specification: `cms-specification.md`

### Exported Diagram Images

- Layered SVG: `architecture/exports/layer.svg`
- Microkernel SVG: `architecture/exports/microkernel.svg`

### Preview

<p align="center">
	<img src="architecture/exports/layer.svg" alt="Layered C4 Container Diagram" width="850" />
</p>

<p align="center">
	<img src="architecture/exports/microkernel.svg" alt="Microkernel C4 Container Diagram" width="850" />
</p>

Note:
- The DSL files are designed for C4 container visualization in VS Code C4 editors.
- Both diagrams are arranged top-down for readability.

## Tech Stack

| Layer | Technologies |
|------|--------------|
| Frontend | HTML, CSS, Vanilla JavaScript |
| Backend | Node.js, Express |
| Data | PostgreSQL (`pg`) |
| Extensibility | Microkernel plugin manager + hook execution |
| Security/Hardening | `helmet`, `express-rate-limit`, payload validation |
| DevOps | Docker, Docker Compose |

## Quick Start

### Option A - Docker Compose (Recommended)

```bash
cd week04/plugin-cms
docker-compose up --build
```

Access:
- App UI: http://localhost:4000
- Health check: http://localhost:4000/api/health

### Option B - Run Locally

1. Start PostgreSQL manually.
2. Configure environment:

```bash
cd week04/plugin-cms
copy .env.example .env
```

3. Install and run:

```bash
npm install
npm start
```

## API Overview

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/health` | GET | Service health status |
| `/api/contents` | GET | List contents |
| `/api/contents/:id` | GET | Get content detail |
| `/api/contents` | POST | Create draft content |
| `/api/contents/:id` | PUT | Update content |
| `/api/contents/:id/publish` | POST | Publish content |
| `/api/contents/:id` | DELETE | Delete content |
| `/api/plugins` | GET | List plugins and states |
| `/api/plugins/:name/toggle` | PATCH | Enable/disable plugin |

## Project Structure

```text
week04/
├── architecture/
│   ├── layered-container.dsl
│   ├── microkernel-container.dsl
│   ├── style-tradeoff.md
│   └── exports/
│       ├── layer.svg
│       └── microkernel.svg
├── cms-specification.md
├── README.md
└── plugin-cms/
		├── .env.example
		├── .gitignore
		├── Dockerfile
		├── docker-compose.yml
		├── package.json
		├── public/
		│   ├── app.js
		│   ├── index.html
		│   └── styles.css
		└── src/
				├── app.js
				├── server.js
				├── config/database.js
				├── controllers/contentController.js
				├── kernel/pluginManager.js
				├── middleware/errorHandler.js
				├── plugins/
				│   ├── auditPlugin.js
				│   └── seoPlugin.js
				├── repositories/contentRepository.js
				├── routes/contentRoutes.js
				└── services/contentService.js
```

## Learning Outcomes

After this lab, you can:
- Design architecture style alternatives and compare trade-offs.
- Model systems at C4 container level.
- Implement a microkernel plugin runtime on a layered backend.
- Build and run a complete architecture demo end-to-end.
