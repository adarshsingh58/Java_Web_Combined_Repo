The Nuances (THIS is what matters)

🔹 1. Client must ask
- REST cannot push updates.
- If order status changes:
- Server does nothing
- Client must poll again

This is by design, not a limitation.

🔹 2. Statelessness

Each request:
- Contains all info needed
- Server doesn’t remember clients

This is why REST:
- Scales well
- Works with caches
- Survives restarts

🔹 3. Latency trade-off

If client polls every:

- 10 seconds → stale data
- 1 second → wasted traffic
- REST forces this trade-off.

🔹 4. REST is honest

REST makes inefficiency visible.

When you feel pain, it’s telling you:

“You might need a different communication model”

That’s when SSE/WebSocket enter.

7️⃣ What REST is BAD at (by design)

❌ Live updates
❌ Streaming data
❌ Bidirectional communication
❌ Server-initiated events

Trying to force REST into these leads to:
- Polling
- Long-polling hacks
- Over-engineering

8️⃣ Mental model (lock this in)

REST = Client pulls state

If your problem is:
- CRUD
- Fetching resources
- Commands with responses

REST is perfect.

If your problem is:
- “Tell me when it changes”

- REST is the wrong abstraction.