public class Payment {
    private int paymentId;
    private int orderId;
    private String paymentType;
    private double amount;
    private String paymentStatus;

    public Payment(int paymentId, int orderId, String paymentType, double amount) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.paymentType = paymentType;
        this.amount = amount;
        this.paymentStatus = "Pending";
    }

    public void processPayment() {
        // ToDo
        validateCard();
        sendNotification();
    }

    private void validateCard() {
        // ToDo
    }

    private void sendNotification() {
        // ToDo
    }

    // Getters and Setters
}

