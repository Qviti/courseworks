public class Notification {
    private int notificationId;
    private String message;
    private int recipientId;

    public Notification(int notificationId, String message, int recipientId) {
        this.notificationId = notificationId;
        this.message = message;
        this.recipientId = recipientId;
    }

    public void sendNotification() {
        System.out.println("Notification sent to ID: " + recipientId + " - Message: " + message);
    }

    // Getters and Setters
}

