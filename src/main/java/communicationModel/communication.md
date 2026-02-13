REST / SSE / WebSocket / Webhooks
- Producer ─────────► Consumer
- Producer knows consumer
- Direct communication 
- If consumer is down → problem
- No built-in history

Kafka
- Producer ──► Kafka Topic ──► Consumers (0..N)
- Producer does NOT know consumers
- Consumers come and go
- Events are stored
- Consumers read at their own pace
- Kafka breaks time coupling.

### Kafka vs Webhooks (very common confusion)
Webhook
- Order Service ──► Notification Service
- Push
- One destination
- Needs retries
- Endpoint must be up

Kafka

- Order Service ──► order-events topic
↓
┌───────────┼───────────┐
↓           ↓           ↓
EmailSvc     AnalyticsSvc   AuditSvc

- No direct knowledge
- Unlimited consumers
- Built-in buffering
- Replayable

 Kafka replaces webhooks when fan-out & reliability matter.