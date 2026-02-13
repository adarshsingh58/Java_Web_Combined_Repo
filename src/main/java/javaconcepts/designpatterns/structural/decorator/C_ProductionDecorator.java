package javaconcepts.designpatterns.structural.decorator;

/*
 * Real-life scenario: Adding features to a coffee order
 * Requirements:
 *  Base coffee
 *  Optional features: milk, sugar, whipped cream
 *  Any combination
 *  No class explosion
 * 
 * Real-world Decorator examples (Java)
 * Java I/O (classic)
 * InputStream in =
 *     new BufferedInputStream(
 *         new DataInputStream(
 *             new FileInputStream("data.txt")
 *         )
 *     );
 * Each layer adds behavior:
 * buffering
 * data conversion
 * file access
 * Spring Security filters
 * Authentication
 * Authorization
 * Logging
 * Each decorates request processing.
 * */
public class C_ProductionDecorator {

    //    Step 1: Component interface
    interface Coffee {
        double cost();

        String description();
    }

    //    Step 2: Concrete component
    class BasicCoffee implements Coffee {
        public double cost() {
            return 50;
        }

        public String description() {
            return "Basic coffee";
        }
    }

    //    Step 3: Base decorator
    abstract class CoffeeDecorator implements Coffee {
        protected final Coffee coffee;

        protected CoffeeDecorator(Coffee coffee) {
            this.coffee = coffee;
        }
    }

    //    Step 4: Concrete decorators
    class MilkDecorator extends CoffeeDecorator {
        MilkDecorator(Coffee coffee) {
            super(coffee);
        }

        public double cost() {
            return coffee.cost() + 10;
        }

        public String description() {
            return coffee.description() + ", milk";
        }
    }

    class SugarDecorator extends CoffeeDecorator {
        SugarDecorator(Coffee coffee) {
            super(coffee);
        }

        public double cost() {
            return coffee.cost() + 5;
        }

        public String description() {
            return coffee.description() + ", sugar";
        }
    }


    public void test() {
//    Step 5: Usage (this is the magic)
        Coffee coffee =
                new SugarDecorator(
                        new MilkDecorator(
                                new BasicCoffee()
                        )
                );

        System.out.println(coffee.description());
        System.out.println(coffee.cost());
    }

    
    /**
     * WHAT CHANGED (this is the key)
     * Before
     *   Behavior baked into inheritance
     *   Combinations impossible to manage
     *   Logic tightly coupled
     * After
     *   Behavior added dynamically
     *   Any combination allowed
     *   Open for extension, closed for modification
     *   No class explosion
     * */
    public static void main(String[] args) {
        new C_ProductionDecorator().test();
    }

}
