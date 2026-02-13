package communicationModel.ReqResponse.C_SSE;

public class OrderStatusEvent {
    public String orderId;
    public OrderStatus status;

    public OrderStatusEvent(String orderId, OrderStatus status) {
        this.orderId = orderId;
        this.status = status;
    }
}
