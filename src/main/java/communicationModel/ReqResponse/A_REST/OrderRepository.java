package communicationModel.ReqResponse.A_REST;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrderRepository {

    private final Map<String, Order> store = new ConcurrentHashMap<>();

    public Order create(String id) {
        Order order = new Order(id);
        store.put(id, order);
        return order;
    }

    public Order get(String id) {
        return store.get(id);
    }

    public void updateStatus(String id, OrderStatus status) {
        store.get(id).setStatus(status);
    }
}