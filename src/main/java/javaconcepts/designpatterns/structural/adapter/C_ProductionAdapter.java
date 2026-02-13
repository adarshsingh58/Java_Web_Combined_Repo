package javaconcepts.designpatterns.structural.adapter;

/*
 * Real-life scenario: Integrating a legacy payment gateway
 * 
 * Real-world Adapter examples
 * Java standard library
 * List<String> list = Arrays.asList("a", "b");
 * Arrays.asList adapts an array → List interface.
 *
 * InputStreamReader
 * Reader r = new InputStreamReader(InputStream);
 * Adapts byte stream → character stream.
 *
 * JDBC Drivers
 * Connection conn = DriverManager.getConnection(url);
 * JDBC driver adapts vendor protocol → JDBC API.
 * 
 * Mental model (remember this)
If two things should work together but don’t → Adapter
or
If you’re changing someone else’s API to fit yours → Adapter
 *
 */
public class C_ProductionAdapter {

    /*
     * WHAT CHANGED (this is the key)
     * ❌ Before
     * Business logic polluted by integration details
     * Currency conversion duplicated
     * Hard to swap dependencies
     *
     * ✅ After
     * Legacy complexity isolated
     * Single place for translation
     * Business logic unchanged
     * Clean abstraction boundary
     * */
    private PaymentGateway gateway = new OldPaymentAdapter(new OldPaymentProcessor());

    public static void main(String[] args) {
        C_ProductionAdapter checkoutService = new C_ProductionAdapter();
        checkoutService.checkout(500.0);
    }

    void checkout(double amount) {
        gateway.pay(amount);
    }


    interface PaymentGateway {
        void pay(double amount);
    }

    class OldPaymentAdapter implements PaymentGateway {

        private final OldPaymentProcessor legacy;

        OldPaymentAdapter(OldPaymentProcessor legacy) {
            this.legacy = legacy;
        }

        @Override
        public void pay(double amount) {
            int paise = (int) (amount * 100);
            legacy.makePayment(paise);
        }
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
