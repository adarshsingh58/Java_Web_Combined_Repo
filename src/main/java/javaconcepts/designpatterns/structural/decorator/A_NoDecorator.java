package javaconcepts.designpatterns.structural.decorator;

/*
 * Real-life scenario: Adding features to a coffee order
 * Requirements:
 *  Base coffee
 *  Optional features: milk, sugar, whipped cream
 *  Any combination
 *  No class explosion
 * */
public class A_NoDecorator {

    /*
     * What goes wrong
     *   Every combination → new class
     *   3 add-ons → 7 classes
     *   Impossible to scale
     * Same problem Bridge solved — but different intent
     * */
    class Coffee {
        double cost() {
            return 50;
        }
    }

    class MilkCoffee extends Coffee {
        double cost() {
            return 60;
        }
    }

    class SugarCoffee extends Coffee {
        double cost() {
            return 55;
        }
    }

    class MilkSugarCoffee extends Coffee {
        double cost() {
            return 65;
        }
    }

}
