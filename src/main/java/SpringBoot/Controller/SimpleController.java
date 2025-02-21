package SpringBoot.Controller;

import SpringBoot.service.SimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {
    @Autowired
    SimpleService simpleService;

    @GetMapping("/getName")
    public String homePage() {
        return simpleService.homePage();
    }

}
