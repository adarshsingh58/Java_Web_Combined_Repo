package javaconcepts.designpatterns.creational.singleton;

/*
 * If adding a new object type forces changes all over your code, think “creational pattern.”
 * Singleton: Only one instance of something should exist
 * eg: A logging service or config manager in an application
 *
 * Real-life scenario: Application Configuration
 * Assume:Config is loaded from DB / file
 * Expensive to load
 * Must be consistent across app
 *
 * Mental model (remember this)
 * If creating more than one instance is a bug → Singleton
 * Examples that qualify: Config manager,Cache registry,Thread pool,Metrics registry,Feature flag service
 * */
public class C_ProductionSingleton {

    public static void main(String[] args) {
        OrderService orderService = new C_ProductionSingleton().new OrderService();
        PaymentService paymentService = new C_ProductionSingleton().new PaymentService();
    }

    public class OrderService {
        private AppConfig config = AppConfig.getInstance();

    }

    public class PaymentService {
        private AppConfig config = AppConfig.getInstance();
    }

    /*
     * This code now would work in multi threaded env
     * */
    static class AppConfig {
        private volatile static AppConfig INSTANCE;

        private AppConfig() {
            // expensive operation
            System.out.println("Loading config...Object Id" + this.hashCode());
        }

        public static AppConfig getInstance() {
            if (INSTANCE == null) {// 2 threads come and see instance is null and go inside now
                synchronized (AppConfig.class) {// only 1 thread gets the lock and get to create the object
                    if (INSTANCE == null) {// this is needed coz if 2 threads enter the block, 
                        // and 1 thread creates the object, the other thread should not create it again
                        INSTANCE = new AppConfig();
                    }
                }
            }
            return INSTANCE;
        }

    }
}
