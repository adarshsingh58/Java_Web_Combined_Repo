package javaconcepts.designpatterns.structural.proxy;

/*
 * Real-life scenario: Expensive remote service
 * Assume:
 *   Fetching user data is slow (network / DB)
 *   You don’t always need it
 *   You want to control access
 * */
public class A_NoProxy {

    class UserService {

        public UserService() {
            // simulate heavy setup
            System.out.println("Connecting to remote service...");
            try { Thread.sleep(2000); } catch (Exception e) {}
        }

        public String getUserName() {
            return "Adarsh";
        }
    }

    public void test(){
        UserService service = new UserService(); // always expensive
        System.out.println("App started");

    }

    /*
     * What goes wrong
     *   Heavy object created eagerly
     *   Cost paid even if not used
     *   App startup slows
     *   No access control possible
     *  You have no control over object usage
     * */
    public static void main(String[] args) {
        new A_NoProxy().test();
    }
}
