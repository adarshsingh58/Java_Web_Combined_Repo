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
 * Real-world Java examples you already use
 *  Logger logger = LoggerFactory.getLogger(MyClass.class);
 *  Connection conn = DriverManager.getConnection(url);
 *  Executors.newFixedThreadPool(10);
 * You use factories daily.
 */
public class C_ProductionFactory {

/**
 * Business code knows only interfaces
 * Creation logic isolated
 * New payment type = new class, no edits
 * Easy testing & DI friendly
 * 
 * When to use Factory (decision rule)
 *
 * Use Factory when:
 * ✔ Creation depends on runtime choice
 * ✔ You don’t want callers to know concrete types
 * ✔ Object creation may change
 * ✔ You want to enforce invariants
 *
 * Do NOT use Factory when:
 * ❌ There is only one implementation
 * ❌ Creation is trivial and stable
 * ❌ You control all callers
 * */
    public static void main(String[] args) {
        Payment p = new C_ProductionFactory().new CardPaymentFactory().create();
        p.pay(12);
    }


    interface PaymentFactory {
        Payment create();
    }

    class CardPaymentFactory implements PaymentFactory {
        public Payment create() {
            return new CardPayment();
        }
    }

    class UpiPaymentFactory implements PaymentFactory {
        public Payment create() {
            return new UpiPayment();
        }
    }


    private class CardPayment implements Payment {
        public void pay(double amount) {
            System.out.println("Paid by card: " + amount);
        }
    }

    private class UpiPayment implements Payment {
        public void pay(double amount) {
            System.out.println("Paid by upi: " + amount);
        }
    }

    private class WalletPayment implements Payment {
        public void pay(double amount) {
            System.out.println("Paid by wallet: " + amount);
        }
    }

    private interface Payment {
        public void pay(double amount);
    }
}
