import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private int customerId;
    private int cartId;
    private String orderStatus;
    private String deliveryDetails;
    private String deliveryTime;
    private String paymentStatus;
    private List<OrderObserver> observers;

    public Order(int orderId, int customerId, int cartId, String deliveryDetails, String deliveryTime) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.cartId = cartId;
        this.orderStatus = "Pending";
        this.deliveryDetails = deliveryDetails;
        this.deliveryTime = deliveryTime;
        this.paymentStatus = "Unpaid";
        this.observers = new ArrayList<>();
    }

    public void attach(OrderObserver observer) {
        observers.add(observer);
    }

    public void detach(OrderObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (OrderObserver observer : observers) {
            observer.update(this);
        }
    }

    public void updateStatus(String status) {
        this.orderStatus = status;
        notifyObservers();
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getCartId() {
        return cartId;
    }

    public String getDeliveryDetails() {
        return deliveryDetails;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
