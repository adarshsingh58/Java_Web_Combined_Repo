package javaconcepts.designpatterns.structural.bridge;

/*
 * Real-life scenario: Notifications across multiple channels
 * You need to support:
 *  Message types: Alert, Reminder
 *  Delivery channels: Email, SMS, Push
 * These two dimensions change independently.
 */
public class B_NaiveBridge {
    public static void main(String[] args) {
        
    }

    /**
     * Still broken
     *  Channel + message tightly coupled
     *  Cannot vary independently
     *  Violates Single Responsibility Principle
     * */
    abstract class Notification {
        abstract void send(String msg);
    }

    class EmailAlert extends Notification {
        @Override
        void send(String msg) {        }
    }
    class SmsAlert extends Notification {
        @Override
        void send(String msg) {
        }
    }
    class EmailReminder extends Notification {
        @Override
        void send(String msg) {}
    }


}
