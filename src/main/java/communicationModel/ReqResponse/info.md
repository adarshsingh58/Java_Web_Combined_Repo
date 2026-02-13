Big picture (one sentence each)
- Tech:	Core idea
- Polling:	Client keeps asking: “Any update?”
- REST:	Client asks → server responds (stateless)
- SSE:	Server pushes updates over HTTP (one-way)
- WebSocket:	Full-duplex persistent connection
- gRPC:	High-performance binary RPC (service-to-service)

1️⃣ Polling (baseline – usually the wrong default)

How it works
Client → Server: any update?

Server → Client: no

(repeat every X seconds)

Example
setInterval(() => {
fetch("/notifications");
}, 5000);

Pros

✅ Simple
✅ Works everywhere
✅ Stateless server

Cons

❌ Wasted requests
❌ High latency (up to poll interval)
❌ Terrible at scale

When polling is OK
- Low traffic
- Updates are rare
- Simplicity > efficiency

🚨 Anti-pattern for chat, live feeds, stock prices.

2️⃣ REST (request–response, stateless)
How it works
- Client → Server: GET /orders/123
- Server → Client: JSON response
- (connection closed)

Core characteristics
- Stateless
- Synchronous
- Client-driven
- HTTP semantics (verbs, status codes)

Pros

✅ Simple mental model
✅ Caching, proxies, auth are easy
✅ Internet-scale friendly

Cons

❌ No server push
❌ Over-fetching / under-fetching
❌ Not real-time

Use REST when

- ✔ CRUD APIs
- ✔ Public APIs
- ✔ Mobile & web backends
- ✔ Idempotent operations

👉 REST is not real-time, and that’s OK.

3️⃣ SSE (Server-Sent Events)
How it works
- Client → Server: subscribe 
- Server → Client: event
- Server → Client: event
- (connection stays open)

One-way: server → client

Example (browser)
const es = new EventSource("/events");
es.onmessage = e => console.log(e.data);

Pros

✅ True server push
✅ Built on HTTP
✅ Auto-reconnect
✅ Simple (much simpler than WebSocket)

Cons

❌ One-direction only
❌ Limited browser support on older clients
❌ Not great for massive fan-out without tuning

Use SSE when

- ✔ Notifications
- ✔ Live dashboards
- ✔ Activity feeds
- ✔ Stock price ticks (read-only)

👉 If client doesn’t need to send messages → SSE > WebSocket

4️⃣ WebSockets (full-duplex, real-time)
How it works
- Client ↔ Server
- (bidirectional, persistent connection)

Example
socket.send("hello");
socket.onmessage = e => ...

Pros

✅ Low latency
✅ Bidirectional
✅ Real-time
✅ Efficient after handshake

Cons

❌ Stateful connections
❌ Harder to scale (connection management)
❌ Load balancers need sticky sessions or pub/sub

Use WebSockets when

- ✔ Chat apps
- ✔ Multiplayer games
- ✔ Collaborative editing
- ✔ Real-time trading

🚨 Common mistake: using WebSockets when SSE is enough.

5️⃣ gRPC (high-performance RPC)
How it works
- Service A → Service B (binary, HTTP/2)


Supports:
- Unary
- Server streaming
- Client streaming
- Bidirectional streaming

Pros

✅ Very fast
✅ Strong typing (protobuf)
✅ Streaming built-in
✅ Great for internal services

Cons

❌ Not browser-friendly
❌ Harder debugging
❌ Tight coupling (schema-first)

Use gRPC when

- ✔ Microservice-to-microservice 
- ✔ Low latency required
- ✔ High throughput
- ✔ Internal systems

👉 gRPC is not a replacement for REST at the edge.

6️⃣ Side-by-side comparison (this is the money table)
| Dimension            | Polling           | REST              | SSE                     | WebSocket               | gRPC                    |
|----------------------|-------------------|-------------------|-------------------------|-------------------------|-------------------------|
| Communication Model  | Client → Server   | Client → Server   | Server → Client         | Bidirectional           | Bidirectional           |
| Real-time Support    | ❌ No             | ❌ No             | ✅ Yes                  | ✅ Yes                  | ✅ Yes                  |
| Persistent Connection| ❌ No             | ❌ No             | ✅ Yes

7️⃣ Decision guide (memorize this, not definitions)

Ask these questions in order:
❓ Do you need real-time updates?

❌ No → REST

✅ Yes → continue

❓ Is communication only server → client?

✅ Yes → SSE

❌ No → continue

❓ Is this browser-based?

✅ Yes → WebSocket

❌ No → continue

❓ Is this service-to-service?

✅ Yes → gRPC

8️⃣ Real-world architecture example

Chat application
- REST → login, history
- WebSocket → live messages 
- SSE → online status / notifications

Trading system
- WebSocket → price updates
- gRPC → internal matching engine
- REST → reports, account data

9️⃣ Common wrong choices (important)

- ❌ Using WebSocket for CRUD
- ❌ Using polling for chat
- ❌ Using gRPC for public APIs
- ❌ Using REST for streaming data
- 
❌ Using WebSocket just because “it’s real-time”

🧠 Mental models (short & sticky)

REST → ask & answer

Polling → keep asking

SSE → listen

WebSocket → talk continuously

gRPC → machine-to-machine speed

Final takeaway

Choose the simplest mechanism that satisfies the communication pattern.

Most systems:

Start with REST

Add SSE for push

Use WebSocket only when truly interactive

Use gRPC internally, not at the edge