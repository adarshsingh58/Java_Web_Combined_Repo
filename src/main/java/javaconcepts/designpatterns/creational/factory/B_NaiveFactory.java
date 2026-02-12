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
public class B_NaiveFactory {
    public static void main(String[] args) {
        PaymentFactory factory = new B_NaiveFactory().new PaymentFactory();
        Payment p = factory.create("UPI");
        p.pay(123.1);
    }

    /**
     * Better, but still limited
     * Centralized switch (good)
     * But factory must still be modified for every new type
     * Hard-coded strings
     * */
    class PaymentFactory {
        public Payment create(String type) {
            switch (type) {
                case "CARD":
                    return new B_NaiveFactory().new CardPayment();
                case "UPI":
                    return new B_NaiveFactory().new UpiPayment();
                case "WALLET":
                    return new B_NaiveFactory().new WalletPayment();
                default:
                    throw new IllegalArgumentException();
            }
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
