package javaconcepts.designpatterns.structural.proxy;

/*
 * Real-life scenario: Expensive remote service
 * Assume:
 *   Fetching user data is slow (network / DB)
 *   You don’t always need it
 *   You want to control access
 * */
public class C_ProductionProxy {

    //    Step 1: Common interface
    interface UserService {
        String getUserName();
    }

    //    Step 2: Real object
    class RealUserService implements UserService {

        public RealUserService() {
            System.out.println("Connecting to remote service...");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
        }

        @Override
        public String getUserName() {
            return "Adarsh";
        }
    }

    //    Step 3: Proxy (controls access)
    class UserServiceProxy implements UserService {

        private RealUserService realService;

        @Override
        public String getUserName() {
            if (realService == null) {
                realService = new RealUserService(); // lazy creation
            }
            return realService.getUserName();
        }
    }


    //    Step 4: Usage (key difference)
    public void test() {
        UserService service = new UserServiceProxy();

        System.out.println("App started");  // fast
        System.out.println(service.getUserName()); // heavy work happens here

    }

    /*
     * WHAT CHANGED (this is the key)
     * Before
     *   No control over object creation
     *   Heavy cost always paid
     *   No interception possible
     * After
     *   Object created on demand
     *   Access fully controlled
     *   Real object hidden
     *   Client code unchanged
     * */
    public static void main(String[] args) {
        new C_ProductionProxy().test();
    }
}
