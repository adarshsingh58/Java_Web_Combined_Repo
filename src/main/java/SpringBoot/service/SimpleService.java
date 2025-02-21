package SpringBoot.service;

import SpringBoot.repository.SimpleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleService {

    @Autowired
    SimpleRepository simpleRepository;

    boolean USE_HIBERNATE = true;

    public String homePage() {
        if (USE_HIBERNATE)
            return simpleRepository.getHomeHibernate();
        else
            return simpleRepository.getHome();
    }


}
