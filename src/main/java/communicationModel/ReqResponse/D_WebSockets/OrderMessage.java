package communicationModel.ReqResponse.D_WebSockets;

public class OrderMessage {
    public String orderId;
    public OrderStatus status;

    public OrderMessage() {}

    public OrderMessage(String orderId, OrderStatus status) {
        this.orderId = orderId;
        this.status = status;
    }
}
