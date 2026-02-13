With REST / polling:

Client controls the conversation

With WebSocket:

Both sides are peers

Connection stays open

Messages flow anytime, either direction

We’ll do this properly and comparatively, using the same Order Status use-case, so you feel the difference.

Why WebSocket exists (the real reason)

SSE fails the moment the client needs to talk back in real time.

That’s it.
Everything else (complexity, scaling pain) is the cost of this power.

### SSE vs WebSocket — conceptual shift
- SSE
Client ───────► Server
(listen only)

- WebSocket
Client ◄──────► Server
(full duplex)


This single difference causes everything else.

### When WebSocket is the RIGHT choice

Use WebSocket when:
- Client must send messages anytime
- Server must push updates
- Latency must be very low
- Interaction is continuous

Examples:

- Chat
- Multiplayer games
- Collaborative editing
- Trading terminals

If client does not send messages → SSE is better.

