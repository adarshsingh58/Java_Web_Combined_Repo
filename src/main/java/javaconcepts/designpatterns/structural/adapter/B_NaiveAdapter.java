package javaconcepts.designpatterns.structural.adapter;

/**
 * Real-life scenario: Integrating a legacy payment gateway
 * <p>
 * You already have clean business code that expects this:
 *
 */
public class B_NaiveAdapter {

    /*
     * Still bad:
     *  Conditionals everywhere
     *  Business code still aware of differences
     *  Adding another gateway explodes conditionals
     * */
    public static void main(String[] args) {
        B_NaiveAdapter checkoutService = new B_NaiveAdapter();
        checkoutService.checkout(100, true);
    }

    void checkout(double amount, boolean useLegacy) {
        if (useLegacy) {
            new OldPaymentProcessor().makePayment((int) (amount * 100));
        } else {
            new NewPaymentGateway().pay(amount);
        }
    }


    interface PaymentGateway {
        void pay(double amount);
    }

    class NewPaymentGateway implements PaymentGateway {
        public void pay(double amount) {
            System.out.println("Paid: " + amount + " rupees");
        }
    }

    // legacy library (cannot change)
    class OldPaymentProcessor {
        public void makePayment(int amountInPaise) {
            System.out.println("Paid: " + amountInPaise + " paise");
        }
    }

}
