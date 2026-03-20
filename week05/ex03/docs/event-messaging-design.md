# Event Messaging Design

## Broker choice

The architecture can use Kafka or RabbitMQ.

1. Kafka is suitable for high-throughput event streams and replay.
2. RabbitMQ is suitable for flexible routing and task queue patterns.

## Proposed topics or exchanges

1. order-events
2. payment-events
3. delivery-events
4. notification-events

## Core events

1. OrderCreated
2. OrderConfirmed
3. PaymentCompleted
4. PaymentFailed
5. DeliveryAssigned
6. DeliveryCompleted
7. NotificationSent

## Event payload model

```json
{
  "eventId": "uuid",
  "eventType": "OrderCreated",
  "occurredAt": "2026-03-20T12:00:00Z",
  "aggregateId": "order-1001",
  "traceId": "trace-xyz",
  "payload": {
    "customerId": "c-001",
    "items": [
      { "itemId": "f-001", "quantity": 2 }
    ],
    "totalAmount": 120000
  }
}
```

## Reliability considerations

1. Use outbox pattern at producer side.
2. Use idempotency key at consumer side.
3. Use dead letter queue for failed processing.
4. Use correlation ID for end-to-end traceability.
