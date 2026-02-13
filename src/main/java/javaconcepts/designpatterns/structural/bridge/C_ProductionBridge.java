package javaconcepts.designpatterns.structural.bridge;

/*
 * Real-life scenario: Notifications across multiple channels
 * You need to support:
 *  Message types: Alert, Reminder
 *  Delivery channels: Email, SMS, Push
 * These two dimensions change independently.
 */
public class C_ProductionBridge {
    
    /**
     * WHAT CHANGED (this is the key)
     * ❌ Before
     *  Each variation multiplied classes
     *  Rigid inheritance
     *  Hard to extend safely
     *
     * ✅ After
     *  Two dimensions evolve independently
     *  Composition over inheritance
     *  No class explosion
     *  Clean separation of concerns
     * */
    public static void main(String[] args) {
        new C_ProductionBridge().testNotifyUsers();
    }

    public void testNotifyUsers() {
        //Step 3: Usage (power of bridge)
        Notification n1 = new Alert(new EmailSender());

        Notification n2 = new Reminder(new SmsSender());

        n1.notifyUser("Server down");
        n2.notifyUser("Pay bill");
    }

    //Step 1: Implementation hierarchy (what varies independently)
    interface MessageSender {
        void sendMessage(String message);
    }

    class EmailSender implements MessageSender {
        public void sendMessage(String message) {
            System.out.println("Email: " + message);
        }
    }

    class SmsSender implements MessageSender {
        public void sendMessage(String message) {
            System.out.println("SMS: " + message);
        }
    }

    //Step 2: Abstraction hierarchy
    abstract class Notification {
        protected MessageSender sender;

        protected Notification(MessageSender sender) {
            this.sender = sender;
        }

        abstract void notifyUser(String msg);
    }

    class Alert extends Notification {
        Alert(MessageSender sender) {
            super(sender);
        }

        void notifyUser(String msg) {
            sender.sendMessage("ALERT: " + msg);
        }
    }

    class Reminder extends Notification {
        Reminder(MessageSender sender) {
            super(sender);
        }

        void notifyUser(String msg) {
            sender.sendMessage("REMINDER: " + msg);
        }
    }


}
