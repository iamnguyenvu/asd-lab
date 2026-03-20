# Communication Diagram - Sync and Async

## Sync communication

Used for request-response operations requiring immediate result.

1. Client to API Gateway via HTTP REST.
2. API Gateway to Catalog Service for menu query.
3. API Gateway to Order Service for order creation.
4. Order Service to Catalog Service for item validation.

## Async communication

Used for decoupled workflows and eventual consistency.

1. Order Service publishes OrderCreated.
2. Payment Service consumes OrderCreated and publishes PaymentCompleted or PaymentFailed.
3. Delivery Service consumes PaymentCompleted and publishes DeliveryAssigned, DeliveryCompleted.
4. Notification Service consumes lifecycle events and sends notifications.

```mermaid
sequenceDiagram
  participant U as User Client
  participant G as API Gateway
  participant O as Order Service
  participant C as Catalog Service
  participant MQ as Message Broker
  participant P as Payment Service
  participant D as Delivery Service
  participant N as Notification Service

  U->>G: POST /orders
  G->>O: Create order request
  O->>C: Validate item and price (sync)
  C-->>O: Item valid
  O-->>G: Order created response
  G-->>U: 201 Created

  O->>MQ: Publish OrderCreated (async)
  MQ->>P: Consume OrderCreated
  P->>MQ: Publish PaymentCompleted
  MQ->>D: Consume PaymentCompleted
  D->>MQ: Publish DeliveryAssigned and DeliveryCompleted
  MQ->>N: Consume events and notify user
```
