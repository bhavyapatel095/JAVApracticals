@FunctionalInterface 
interface Notifier {
    void send(String message);
}

// Marker interface
interface Urgent {
}

// Urgent notifier class
class UrgentNotifier implements Notifier, Urgent {

    private Notifier notifier;

    UrgentNotifier(Notifier notifier) {
        this.notifier = notifier;
    }

    public void send(String message) {
        notifier.send(message);
    }
}

public class NotificationSender {

    public static void main(String[] args) {

        // Email sender using lambda
        Notifier email = message ->
                System.out.println("Email: " + message);

        // SMS sender using lambda
        Notifier sms = message ->
                System.out.println("SMS: " + message);

        // Mark Email as urgent
        Notifier urgentEmail = new UrgentNotifier(email);

        // Array of notification senders
        Notifier[] senders = {
            urgentEmail,
            sms
        };

        String message = "Exam tomorrow at 10 AM";

        // Broadcast message
        for (Notifier sender : senders) {

            sender.send(message);

            // Send twice if sender is urgent
            if (sender instanceof Urgent) {
                sender.send(message);
            }
        }
    }
}