package communicationModel.ReqResponse.C_SSE;

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

