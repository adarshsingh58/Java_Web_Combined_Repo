package javaconcepts.designpatterns.behavioural.strategy;

/*
 * Real-life scenario: Discount calculation
 * Rules:
 *   Different discount rules
 *   Rules change often
 *   Decision made at runtime
 * */
public class B_ProductionStrategy {

    /*
     * WHAT CHANGED (this is the key)
     * Before
     *   One class knew all algorithms
     *   Conditionals everywhere
     *   Hard to extend
     * After
     *   Algorithms isolated
     *   Behavior interchangeable
     *   Open for extension
     *   Runtime flexibility
     *
     * Mental model (remember this)
     * If you’re switching algorithms with if-else → Strategy
     * or
     * If behavior must vary at runtime → Strategy
     * */
    //    Step 1: Strategy interface
    interface DiscountStrategy {
        double apply(double amount);
    }

    //    Step 2: Concrete strategies
    class NoDiscount implements DiscountStrategy {
        public double apply(double amount) {
            return amount;
        }
    }

    class NewUserDiscount implements DiscountStrategy {
        public double apply(double amount) {
            return amount * 0.9;
        }
    }

    class PremiumDiscount implements DiscountStrategy {
        public double apply(double amount) {
            return amount * 0.8;
        }
    }

    //    Step 3: Context
    class PricingService {
        private DiscountStrategy strategy;

        PricingService(DiscountStrategy strategy) {
            this.strategy = strategy;
        }

        void setStrategy(DiscountStrategy strategy) {
            this.strategy = strategy;
        }

        double finalPrice(double amount) {
            return strategy.apply(amount);
        }
    }

    //    Step 4: Usage (runtime flexibility)
    public void test() {
        PricingService service = new PricingService(new NewUserDiscount());
        System.out.println(service.finalPrice(100));
        service.setStrategy(new PremiumDiscount());
        System.out.println(service.finalPrice(100));
    }

    public static void main(String[] args) {
        new B_ProductionStrategy().test();
    }
}
