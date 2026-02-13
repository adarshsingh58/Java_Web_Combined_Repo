package communicationModel.ReqResponse.C_SSE;

import org.springframework.stereotype.Component;

@Component
public class OrderStatusSimulator {

    private final OrderSseController controller;

    public OrderStatusSimulator(OrderSseController controller) {
        this.controller = controller;
    }

//    @PostConstruct
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
