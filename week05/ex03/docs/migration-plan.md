# Monolith to Microservices Migration Plan

## Step 1 - Identify seams in monolith

1. Extract module boundaries around Customer, Catalog, Order, Payment, Delivery, Notification.
2. Keep monolith database unchanged in this phase.

## Step 2 - Start with strangler pattern

1. Introduce API Gateway as entry point.
2. Route read-heavy catalog APIs to new catalog-service first.
3. Keep write operations in monolith until order-service is stable.

## Step 3 - Extract ordering workflow

1. Move order creation and tracking to order-service.
2. Add asynchronous events for payment and delivery lifecycle.

## Step 4 - Extract payment and delivery

1. Introduce payment-service and delivery-service with dedicated databases.
2. Keep eventual consistency by events and compensating actions.

## Step 5 - Decommission monolith modules

1. Remove migrated modules from monolith.
2. Keep only fallback APIs temporarily.
3. Finalize observability and runbook.
