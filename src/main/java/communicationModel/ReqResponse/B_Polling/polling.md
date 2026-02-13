What polling actually is 

**Polling = REST + time**

Nothing new on the server.
All the pain is on the client side.

1️⃣ Our existing REST API (unchanged)

We already have:

GET /orders/{id}

Returns:

{
"id": "123",
"status": "PAID"
}


⚠️ Important:
- Server does NOT notify clients
- Server does NOT track subscribers
- Server is stateless

This is pure REST.

2️⃣ Polling client (Java)

# Now let’s write a Java client that polls every 2 seconds.

**Order DTO (client-side)**

```java
public class OrderDto {
public String id;
public String status;
}

Polling client implementation
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PollingClient {

    private static final HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) throws Exception {

        String orderId = "123";
        String lastStatus = null;

        while (true) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/orders/" + orderId))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            OrderDto order =
                    new com.fasterxml.jackson.databind.ObjectMapper()
                            .readValue(response.body(), OrderDto.class);

            if (!order.status.equals(lastStatus)) {
                System.out.println("Order status changed: " + order.status);
                lastStatus = order.status;
            } else {
                System.out.println("No change...");
            }

            Thread.sleep(2000); // polling interval
        }
    }
}
```

What happens at runtime (timeline)

Assume order changes like this:
- t=0s    CREATED
- t=7s    PAID
- t=16s   SHIPPED


Polling every 2 seconds:

- t=0s    GET -> CREATED
- t=2s    GET -> CREATED
- t=4s    GET -> CREATED
- t=6s    GET -> CREATED
- t=8s    GET -> PAID   <-- detected late

🔴 Observation
- Latency = polling interval
- You never get instant updates
- You spam the server even when nothing changes

Want to Increase frequency? New problems.
**Poll every 500ms
Thread.sleep(500);**

Now you get:
- Better latency
- 4× traffic
- More CPU
- More load
- More cost

This is the polling trap.

## Scaling problem (this is critical)

Assume:
10,000 users

Poll every 2 seconds

That is: 
5,000 requests / second   Even if: Nothing changes and 
Responses are identical

👉 This is wasted work.

Why polling still exists

Polling is not evil — it’s simple.

Polling is acceptable when:
- ✔ Very low traffic
- ✔ Updates are rare
- ✔ Simplicity > efficiency
- ✔ Legacy systems

Example:

- Cron job checking job status 
- Admin dashboards with refresh button

Why polling feels wrong (and should)
- Polling violates a natural expectation:
“Tell me when it changes — not let me keep asking.”

This feeling is the design smell that leads to:

➡️ SSE

REST vs Polling (important distinction)

Polling is not a server feature.
It’s a client workaround.

9️⃣ Mental model (lock this in)

Polling = “Are we there yet?”

Repeatedly.
Even when nothing changes.

##  The transition moment (key insight)

When you ask:

“Why can’t the server just tell me when it changes?”

You are ready for SSE.


# How short polling is done properly in Java
Rule of thumb:

Never block threads for polling in production

### Option 1: Scheduled polling (most common)
Using ScheduledExecutorService
```java
ScheduledExecutorService scheduler =
Executors.newSingleThreadScheduledExecutor();

Runnable pollTask = () -> {
try {
// call REST API
System.out.println("Polling server...");
} catch (Exception e) {
e.printStackTrace();
}
};

scheduler.scheduleAtFixedRate(
pollTask,
0,
2,
TimeUnit.SECONDS
);
```
Why this is better

✔ No manual sleep
✔ Controlled lifecycle
✔ Clean shutdown
✔ Scales better

Option 2: Spring @Scheduled (server or client)
```java
@Scheduled(fixedDelay = 2000)
public void pollOrderStatus() {
// REST call
}
```


Used heavily for:
- Background jobs
- Internal polling
- Batch workflows

Option 3: Reactive polling (modern approach)

Spring WebFlux
```java
Flux.interval(Duration.ofSeconds(2))
.flatMap(tick -> webClient.get()
.uri("/orders/{id}", id)
.retrieve()
.bodyToMono(OrderDto.class)
)
.subscribe(order ->
System.out.println(order.status)
);
```
Why this is best at scale

✔ No blocking threads
✔ Efficient resource usage
✔ Backpressure support