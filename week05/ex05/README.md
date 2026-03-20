# Week 05 - Exercise 05 (Microservices)

Mini online food delivery using microservice architecture:
- Frontend: React + Vite
- API Gateway: Spring Cloud Gateway
- Microservices:
  - catalog-service
  - order-service
- Database: PostgreSQL
- DevOps: Docker Compose

## Three Main Features

1. View food catalog
2. Place order
3. Track and update order status

## Run With Docker

```bash
cd week05/ex05
docker compose up --build
```

## URLs

- Frontend: http://localhost:4174
- API Gateway: http://localhost:8084
- Catalog (direct): http://localhost:8091/api/catalog/items
- Order (direct): http://localhost:8092/api/orders

## Main API Through Gateway

- `GET /api/catalog/items`
- `POST /api/orders`
- `GET /api/orders/{id}`
- `PATCH /api/orders/{id}/status?status=DELIVERING`
