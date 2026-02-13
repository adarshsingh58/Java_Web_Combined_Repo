package javaconcepts.designpatterns.behavioural.strategy;

/*
 * Real-life scenario: Discount calculation
 * Rules:
 *   Different discount rules
 *   Rules change often
 *   Decision made at runtime
 * */
public class A_NoStrategy {
    /*
     * What goes wrong
     *  Every new rule → modify this class
     *  Conditionals grow uncontrollably
     *  Business logic tangled
     *  Violates Open–Closed Principle
     * Algorithm choice mixed with execution
     * */
    class DiscountService {
        double calculate(String userType, double amount) {
            if ("NEW".equals(userType)) {
                return amount * 0.9;
            } else if ("PREMIUM".equals(userType)) {
                return amount * 0.8;
            } else if ("FESTIVAL".equals(userType)) {
                return amount * 0.7;
            }
            return amount;
        }
    }

}
