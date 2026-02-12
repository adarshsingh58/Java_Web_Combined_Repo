package javaconcepts.designpatterns.creational.singleton;

import org.apache.bcel.generic.INSTANCEOF;

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
public class B_NaiveSingleton {

    public static void main(String[] args) {
        OrderService orderService = new B_NaiveSingleton().new OrderService();
        PaymentService paymentService = new B_NaiveSingleton().new PaymentService();
    }

    public class OrderService {
        private AppConfig config = AppConfig.getInstance();

    }

    public class PaymentService {
        private AppConfig config =  AppConfig.getInstance();
    }

    /*
     * Now constructor is private so no other class can create instance of AppConfig
     * Instance is only created when its null, once initialized it will be re-used by every 
     * caller. 
     * This is singleton pattern. 
     * But In multi-threaded code, this would still fail.
     * */
    static class AppConfig {
        private static AppConfig INSTANCE;

        private AppConfig() {
            // expensive operation
            System.out.println("Loading config...Object Id"+this.hashCode());
        }

        public static AppConfig getInstance() {
            if (INSTANCE == null) {
                INSTANCE = new AppConfig();
            }
            return INSTANCE;
        }

    }
}
