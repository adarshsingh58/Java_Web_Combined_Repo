package communicationModel.ReqResponse.D_WebSockets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class OrderWebSocketHandler extends TextWebSocketHandler {

    private static final Set<WebSocketSession> sessions =
            ConcurrentHashMap.newKeySet();

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("Connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(
            WebSocketSession session,
            TextMessage message
    ) throws Exception {

        // CLIENT -> SERVER (this is impossible in SSE)
        OrderMessage incoming =
                mapper.readValue(message.getPayload(), OrderMessage.class);

        System.out.println(
                "Client says: order=" + incoming.orderId +
                        " status=" + incoming.status
        );

        // echo / broadcast to all clients
        broadcast(incoming);
    }

    @Override
    public void afterConnectionClosed(
            WebSocketSession session,
            CloseStatus status
    ) {
        sessions.remove(session);
        System.out.println("Disconnected: " + session.getId());
    }

    private void broadcast(OrderMessage msg) throws Exception {
        String json = mapper.writeValueAsString(msg);

        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage(json));
            }
        }
    }
}
