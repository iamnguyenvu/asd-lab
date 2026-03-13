# Week 04 - Plugin-based CMS Detailed Specification

## 1. System Goal

Build a plugin-based Content Management System (CMS) with:
- Layered architecture for clear separation of concerns.
- Microkernel architecture for extensibility using plugins.
- Full stack implementation: frontend, backend, database.

## 2. Main Functionalities (3 core functions)

1. Content Lifecycle Management
- Create, read, update, delete content.
- Persist content fields: title, slug, body, metadata.
- Maintain status: draft or published.

2. Publishing Workflow
- Editorial flow starts in draft state.
- Publish action turns draft into published content.
- Published state can be queried for display use-cases.

3. Plugin Management
- Core system loads plugins dynamically at startup.
- Admin can enable/disable each plugin.
- Plugins can execute hooks before/after content lifecycle events.

## 3. Non-functional Requirements

- Modularity: each layer should be independently testable.
- Extensibility: new plugin can be added without rewriting core domain logic.
- Simplicity: easy local setup via Docker Compose.
- Observability: audit trail for key lifecycle actions.

## 4. Actors

- Content Editor: creates and publishes content.
- System Admin: enables/disables plugins.
- End User (future): consumes published content.

## 5. Data Model

### Table: contents
- id (PK)
- title
- slug (unique)
- body
- status (draft, published)
- metadata (JSONB)
- created_at
- updated_at

### Table: plugin_states
- plugin_name (PK)
- enabled
- updated_at

### Table: audit_logs
- id (PK)
- event_type
- content_id
- payload (JSONB)
- created_at

## 6. API Summary

- GET /api/health
- GET /api/contents
- GET /api/contents/:id
- POST /api/contents
- PUT /api/contents/:id
- POST /api/contents/:id/publish
- DELETE /api/contents/:id
- GET /api/plugins
- PATCH /api/plugins/:name/toggle

## 7. Implemented Plugins

- seo-plugin
  - beforeCreate, beforeUpdate
  - adds SEO metadata and readability score.

- audit-plugin
  - afterCreate, afterPublish, afterDelete
  - writes lifecycle events to audit_logs.
