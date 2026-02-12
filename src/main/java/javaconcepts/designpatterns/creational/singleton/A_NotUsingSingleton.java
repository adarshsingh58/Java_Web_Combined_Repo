package javaconcepts.designpatterns.creational.singleton;

import java.util.Properties;

/*
 * If adding a new object type forces changes all over your code, think “creational pattern.”
 * Singleton: Only one instance of something should exist
 * eg: A logging service or config manager in an application
 * 
 * Real-life scenario: Application Configuration
 * Assume:Config is loaded from DB / file
    Expensive to load
    Must be consistent across app
 * */
public class A_NotUsingSingleton {

    public static void main(String[] args) {
        OrderService orderService = new A_NotUsingSingleton().new OrderService();
        PaymentService paymentService = new A_NotUsingSingleton().new PaymentService();
    }

    public class OrderService {
        private AppConfig config = new AppConfig();
    }

    public class PaymentService {
        private AppConfig config = new AppConfig();
    }

    /*
    * Config loaded multiple times: Memory wasted, If config changes → services may disagree, Hard to control lifecycle
    * Root problem: Nothing prevents multiple instances
    * */
    class AppConfig {

        public AppConfig() {
            // expensive operation
            System.out.println("Loading config...Object Id"+this.hashCode());
            
        }

    }
}
