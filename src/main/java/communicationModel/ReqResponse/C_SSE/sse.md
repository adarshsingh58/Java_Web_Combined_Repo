Why SSE exists (one sentence)

SSE = “Stop polling. Let the server push — but keep HTTP.”

- No new protocol.
- No bidirectional complexity.
- Just server → client push.

1️⃣ What changes compared to Polling
- Polling
- Client → Server: status?
- Server → Client: same
- (repeat)

SSE
- Client → Server: subscribe
- Server → Client: update
- Server → Client: update
- (connection stays open)

Key difference:
- One request
- Many responses 
- Server decides when

2️⃣ When SSE is the RIGHT choice

Use SSE when:
- Client only needs to receive updates
- Updates are event-driven
- You want low latency
- You want simple infra

Do NOT use SSE if:
- Client must send messages
- You need binary data
- You need complex routing


### SSE Endpoint (core server logic)

This is the heart of SSE.

```java
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/orders")
public class OrderSseController {

    private final Set<SseEmitter> emitters =
            ConcurrentHashMap.newKeySet();

    @GetMapping("/{id}/events")
    public SseEmitter streamOrderEvents(@PathVariable String id) {

        SseEmitter emitter = new SseEmitter(0L); // no timeout
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(e -> emitters.remove(emitter));

        return emitter;
    }

    // simulate server-side event
    public void publish(OrderStatusEvent event) {

        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name("order-status")
                        .data(event));
            } catch (IOException e) {
                emitter.complete();
            }
        }
    }
}
```

Important details
- Connection stays open
- Server pushes events
- Client does not poll
- HTTP connection is reused

### Simulating order updates

```java
@Component
public class OrderStatusSimulator {

    private final OrderSseController controller;

    public OrderStatusSimulator(OrderSseController controller) {
        this.controller = controller;
    }

    @PostConstruct
    public void simulate() throws Exception {

        Thread.sleep(3000);
        controller.publish(
            new OrderStatusEvent("123", OrderStatus.PAID)
        );

        Thread.sleep(3000);
        controller.publish(
            new OrderStatusEvent("123", OrderStatus.SHIPPED)
        );
    }
}
```

### Java SSE Client

Java doesn’t have first-class SSE support, so we simulate it via streaming HTTP.
```java
Simple client using HttpClient
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SseClient {

    public static void main(String[] args) throws Exception {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                    "http://localhost:8080/orders/123/events"
                ))
                .GET()
                .build();

        client.sendAsync(
                request,
                HttpResponse.BodyHandlers.ofLines()
        ).thenAccept(response ->
                response.body().forEach(System.out::println)
        );

        Thread.sleep(60_000);
    }
}

Output
event:order-status
data:{"orderId":"123","status":"PAID"}

event:order-status
data:{"orderId":"123","status":"SHIPPED"}
```

### Runtime behavior (compare to polling)
Timeline
- Client connects once
- (waiting)
- Server pushes PAID
- Server pushes SHIPPED
- (no polling)

What disappears

❌ Repeated requests
❌ Latency bound to interval
❌ Wasted traffic

### SSE limitations (VERY important)
- ❌ One-direction only
Client → Server = HTTP only
No real messaging back

- ❌ Connection limits

Browsers limit open HTTP connections

- ❌ Not for high-frequency bidirectional data

This is where WebSocket becomes necessary.