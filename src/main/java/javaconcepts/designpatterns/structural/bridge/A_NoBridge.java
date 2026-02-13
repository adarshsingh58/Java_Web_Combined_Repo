package javaconcepts.designpatterns.structural.bridge;

/*
 * Real-life scenario: Notifications across multiple channels
 * You need to support:
 *  Message types: Alert, Reminder
 *  Delivery channels: Email, SMS, Push
 * These two dimensions change independently.
 */
public class A_NoBridge {
    public static void main(String[] args) {
        
    }

    /*
     * WITHOUT Bridge (inheritance explosion)
     * What goes wrong:
     *  2 dimensions → 6 classes
     *  Add WhatsApp → 3 more
     *  Add “Promotion” → explosion
     * 👉 This is Cartesian product problem.
     * */
    class AlertEmail {}
    class AlertSMS {}
    class AlertPush {}

    class ReminderEmail {}
    class ReminderSMS {}
    class ReminderPush {}

}
