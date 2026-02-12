package javaconcepts.designpatterns.creational.factory;

/*
 * If adding a new object type forces changes all over your code, think “creational pattern.”
 * Factory Method:
 * You want to decide which subclass to instantiate at runtime
 * eg: A UI library that creates OS-specific widgets (Windows vs Mac)
 *
 * Real-life scenario: Payment processing
 * You support:
 *  Credit Card
 *  UPI
 *  Wallet
 * Rules change, providers grow, logic must stay clean.
 *
 */
public class A_WithoutFactory {
    public static void main(String[] args) {
        A_WithoutFactory factory = new A_WithoutFactory();
        factory.pay("CARD", 100);
        factory.pay("UPI", 12);
        factory.pay("WALLET", 320);
    }

    /*
     * What goes wrong(type-check hell):
     *  Every new payment type → edit this class
     *  Business logic mixed with creation logic
     *  Violates Open–Closed Principle
     *  Hard to test
     * 👉 Creation logic is leaking everywhere
     * */
    public void pay(String type, double amount) {

        if ("CARD".equals(type)) {
            CardPayment p = new CardPayment();
            p.pay(amount);

        } else if ("UPI".equals(type)) {
            UpiPayment p = new UpiPayment();
            p.pay(amount);

        } else if ("WALLET".equals(type)) {
            WalletPayment p = new WalletPayment();
            p.pay(amount);

        } else {
            throw new IllegalArgumentException("Unknown type");
        }
    }


    private class CardPayment implements Payment {
        public void pay(double amount) {

        }
    }

    private class UpiPayment implements Payment {
        public void pay(double amount) {

        }
    }

    private class WalletPayment implements Payment {
        public void pay(double amount) {

        }
    }

    private interface Payment {
        public void pay(double amount);
    }
}
