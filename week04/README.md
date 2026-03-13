# Week 04 - Architecture Styles (Layered, Microkernel)

## Assignment Scope

Topic:
- Layered Architecture
- Microkernel Architecture

Deliverables:
- 2 architecture diagrams (C4 container level)
- Style trade-off analysis
- Recommended style for Plugin-based CMS
- Full implementation (frontend, backend, database)

## Folder Structure

```
week04/
├── architecture/
│   ├── layered-container.dsl
│   ├── microkernel-container.dsl
│   └── style-tradeoff.md
├── cms-specification.md
├── plugin-cms/
│   ├── Dockerfile
│   ├── docker-compose.yml
│   ├── package.json
│   ├── public/
│   │   ├── app.js
│   │   ├── index.html
│   │   └── styles.css
│   └── src/
│       ├── app.js
│       ├── server.js
│       ├── config/database.js
│       ├── kernel/pluginManager.js
│       ├── controllers/contentController.js
│       ├── middleware/errorHandler.js
│       ├── plugins/
│       │   ├── auditPlugin.js
│       │   └── seoPlugin.js
│       ├── repositories/contentRepository.js
│       ├── routes/contentRoutes.js
│       └── services/contentService.js
```

## How to run

### Option 1: Docker Compose (recommended)

```bash
cd week04/plugin-cms
docker-compose up --build
```

Open:
- App UI: http://localhost:4000
- Health check: http://localhost:4000/api/health

### Option 2: Run backend locally

1. Start PostgreSQL manually (or keep only db from compose).
2. Copy `.env.example` to `.env` and adjust `DATABASE_URL`.
3. Install dependencies and run:

```bash
cd week04/plugin-cms
npm install
npm start
```

## Main Features Implemented

1. Content lifecycle management
- Create, list, update, publish, delete content.

2. Publishing workflow
- Draft by default, publish endpoint updates status.

3. Plugin management
- Plugin manager registers plugins and executes hooks.
- API to list and toggle plugin states at runtime.

## Architecture Artifacts

- Layered diagram: `architecture/layered-container.dsl`
- Microkernel diagram: `architecture/microkernel-container.dsl`
- Trade-off analysis: `architecture/style-tradeoff.md`
- Detailed specification: `cms-specification.md`

These DSL files are created for C4 container visualization and can be opened in VS Code C4/architecture text editors.
The container diagrams model only deployable/runtime containers; layered and plugin internals are documented in container descriptions.
