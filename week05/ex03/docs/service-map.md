# Service Map - Online Food Delivery

## Target microservices after migration

1. api-gateway
2. customer-service
3. catalog-service
4. order-service
5. payment-service
6. delivery-service
7. notification-service

```mermaid
graph LR
  C[Web or Mobile Client] --> G[API Gateway]
  G --> CS[customer-service]
  G --> CAT[catalog-service]
  G --> ORD[order-service]

  ORD --> PAY[payment-service]
  ORD --> DEL[delivery-service]
  ORD --> NOTI[notification-service]

  PAY --> NOTI
  DEL --> NOTI

  CAT --> CATDB[(Catalog DB)]
  ORD --> ORDDB[(Order DB)]
  PAY --> PAYDB[(Payment DB)]
  DEL --> DELDB[(Delivery DB)]
  CS --> CUSDB[(Customer DB)]
```

## Monolith baseline

Before migration, all capabilities are in one monolithic application with a shared database.

```mermaid
graph LR
  C[Web or Mobile Client] --> M[Food Delivery Monolith]
  M --> DB[(Shared Database)]
```
