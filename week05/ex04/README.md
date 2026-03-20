# Week 05 - Exercise 04 (Monolith)

Mini online food delivery using monolith architecture:
- Frontend: React + Vite
- Backend: Spring Boot
- Database: PostgreSQL
- DevOps: Docker Compose

## Three Main Features

1. View food menu
2. Place order
3. Track and update order status

## Run With Docker

```bash
cd week05/ex04
docker compose up --build
```

## URLs

- Frontend: http://localhost:4173
- Backend API: http://localhost:8083/api
- Health: http://localhost:8083/api/health

## Main API

- `GET /api/items`: list menu
- `POST /api/orders`: create order
- `GET /api/orders/{id}`: get order by id
- `PATCH /api/orders/{id}/status?status=DELIVERING`: update status
