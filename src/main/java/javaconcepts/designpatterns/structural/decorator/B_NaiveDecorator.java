package javaconcepts.designpatterns.structural.decorator;

/*
 * Real-life scenario: Adding features to a coffee order
 * Requirements:
 *  Base coffee
 *  Optional features: milk, sugar, whipped cream
 *  Any combination
 *  No class explosion
 * */
public class B_NaiveDecorator {
    /*
     * Why this is bad
     *  Logic grows combinatorially
     *  Violates Single Responsibility
     *  Hard to add new features
     *  Impossible to reuse add-ons elsewhere
     * */
    class Coffee {
        boolean milk;
        boolean sugar;

        double cost() {
            double price = 50;
            if (milk) price += 10;
            if (sugar) price += 5;
            return price;
        }
    }

}
