import java.util.ArrayList;
import java.util.List;

public class Customer implements OrderObserver {
    private static List<Customer> registeredCustomers = new ArrayList<>();

    private int customerId;
    private String name;
    private String email;
    private String mobileNumber;
    private String password;
    private String address;
    private Cart cart;
    private List<Order> orders;
    private UI ui;

    public Customer(int customerId, String name, String email, String mobileNumber, String password, String address, UI ui) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.address = address;
        this.cart = new Cart();
        this.ui = ui;
        this.orders = new ArrayList<>();
    }

    public void register() {
        registeredCustomers.add(this);
        System.out.println("Registration successful for: " + this.name);
    }

    public static Customer login(String email, String password) {
        for (Customer customer : registeredCustomers) {
            if (customer.email.equals(email) && customer.password.equals(password)) {
                System.out.println("Login successful for: " + customer.name);
                return customer;
            }
        }
        System.out.println("Login failed");
        return null;
    }

    public void browseItems(List<FoodItem> items) {
        ui.displayFoodItems(items);
    }

    public void addToCart(FoodItem item) {
        cart.addItem(item);
        ui.displayCart(cart);
    }

    public void removeFromCart(FoodItem item) {
        cart.removeItem(item);
        ui.displayCart(cart);
    }

    public void placeOrder(Order order) {
        orders.add(order);
        order.attach(this);
        System.out.println("Order placed successfully for customer: " + this.name);
    }

    public void makePayment(Payment payment) {
        payment.processPayment();
        if ("Paid".equals(payment.getPaymentStatus())) {
            System.out.println("Payment successful for customer: " + this.name);
        } else {
            System.out.println("Payment failed for customer: " + this.name);
        }
    }

    public void trackOrder(Order order) {
        ui.displayOrderStatus(order);
    }

    public void confirmDelivery(Order order) {
        order.updateStatus("Delivered");
        System.out.println("Delivery confirmed for customer: " + this.name);
    }

    public void rateExperience(int rating) {
        System.out.println("Customer " + this.name + " rated their experience with: " + rating + " stars");
    }

    public void contactSupport(Support support) {
        support.provideSupport();
    }

    @Override
    public void update(Order order) {
        ui.displayOrderStatus(order);
        Notification notification = new Notification(0, "Order status updated to: " + order.getOrderStatus(), customerId);
        ui.displayNotification(notification);
    }

    public int getCustomerId() {
        return customerId;
    }

    public Cart getCart() {
        return cart;
    }
}
