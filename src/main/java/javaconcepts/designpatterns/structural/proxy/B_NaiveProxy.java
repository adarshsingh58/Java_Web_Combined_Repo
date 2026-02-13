package javaconcepts.designpatterns.structural.proxy;

/*
 * Real-life scenario: Expensive remote service
 * Assume:
 *   Fetching user data is slow (network / DB)
 *   You don’t always need it
 *   You want to control access
 * */
public class B_NaiveProxy {

    class UserService {

        private boolean initialized = false;

        private void init() {
            if (!initialized) {
                System.out.println("Connecting...");
                initialized = true;
            }
        }

        public String getUserName() {
            init();
            return "Adarsh";
        }
    }

    public void test(){
        B_NaiveProxy.UserService service = new B_NaiveProxy.UserService(); // always expensive
        System.out.println("App started");

    }
    /*
     * NAIVE FIX (lazy flag inside class)
     * Why this is bad
     *   Business logic polluted with lifecycle logic
     *   Harder to test
     *   Violates Single Responsibility Principle
     * */
    public static void main(String[] args) {
        new B_NaiveProxy().test();
    }
}
