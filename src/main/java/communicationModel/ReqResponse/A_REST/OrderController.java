package communicationModel.ReqResponse.A_REST;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository repo = new OrderRepository();

    @PostMapping("/{id}")
    public Order create(@PathVariable String id) {
        return repo.create(id);
    }

    @GetMapping("/{id}")
    public Order get(@PathVariable String id) {
        return repo.get(id);
    }

    @PostMapping("/{id}/status")
    public void updateStatus(
            @PathVariable String id,
            @RequestParam OrderStatus status
    ) {
        repo.updateStatus(id, status);
    }
}
