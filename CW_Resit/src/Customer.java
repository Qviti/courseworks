import java.util.List;

public class Customer implements OrderObserver {
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
    }

    public void register() {
        // ToDo
    }

    public void login() {
        // ToDo
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
        // ToDo
        orders.add(order);
        order.attach(this);
    }

    public void makePayment(Payment payment) {
        // ToDo
    }

    public void trackOrder(Order order) {
        ui.displayOrderStatus(order);
    }

    public void confirmDelivery() {
        // ToDo
    }

    public void rateExperience(int rating) {
        // ToDo
    }

    public void contactSupport() {
        // ToDo
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
