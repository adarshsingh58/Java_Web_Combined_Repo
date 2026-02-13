package javaconcepts.designpatterns.structural.adapter;

/**
 * Real-life scenario: Integrating a legacy payment gateway
 * <p>
 * You already have clean business code that expects this:
 *
 */
public class A_WithoutAdapter {

    /*
     * What goes wrong
     *  Business logic knows legacy API details
     *  Currency conversion scattered everywhere
     *  If API changes → many files break
     *  Cannot swap implementation
     *  Integration detail leaked into core logic
     * */
    public static void main(String[] args) {
        A_WithoutAdapter checkoutService = new A_WithoutAdapter();
        checkoutService.checkout(100);
    }

    void checkout(double amount) {
        OldPaymentProcessor p = new OldPaymentProcessor();
        p.makePayment((int) (amount * 100)); // conversion leaked
    }

    interface PaymentGateway {
        void pay(double amount);
    }

    // legacy library (cannot change)
    class OldPaymentProcessor {
        public void makePayment(int amountInPaise) {
            System.out.println("Paid: " + amountInPaise + " paise");
        }
    }

}
