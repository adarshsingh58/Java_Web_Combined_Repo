package communicationModel.ReqResponse.C_SSE;

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

