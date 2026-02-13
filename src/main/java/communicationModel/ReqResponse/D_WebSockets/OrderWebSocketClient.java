package communicationModel.ReqResponse.D_WebSockets;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

public class OrderWebSocketClient {

    public static void main(String[] args) throws Exception {

        HttpClient client = HttpClient.newHttpClient();

        WebSocket ws = client.newWebSocketBuilder()
                .buildAsync(
                        URI.create("ws://localhost:8080/ws/orders"),
                        new WebSocket.Listener() {

                            @Override
                            public CompletionStage<?> onText(
                                    WebSocket webSocket,
                                    CharSequence data,
                                    boolean last
                            ) {
                                System.out.println("Received: " + data);
                                webSocket.request(1);
                                return null;
                            }
                        }
                ).join();

        // CLIENT -> SERVER
        ws.sendText(
                "{\"orderId\":\"123\",\"status\":\"PAID\"}",
                true
        );

        Thread.sleep(30_000);
    }
}

