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
        if (validateCard()) {
            this.paymentStatus = "Paid";
            sendNotification();
            System.out.println("Payment processed successfully for order ID: " + orderId);
        } else {
            this.paymentStatus = "Failed";
            System.out.println("Payment failed for order ID: " + orderId);
        }
    }

    private boolean validateCard() {
        return true; // Assume validation is always successful for simplicity
    }

    private void sendNotification() {
        System.out.println("Notification sent for payment ID: " + paymentId);
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public double getAmount() {
        return amount;
    }

    public int getPaymentId() {
        return paymentId;
    }
}

