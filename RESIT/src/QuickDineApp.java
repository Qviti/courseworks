import java.util.Arrays;

public class QuickDineApp {
    public static void main(String[] args) {
        // Create UI implementations
        UI webUI = new WebUI();
        UI mobileUI = new MobileUI();

        // Create customers with different UI implementations
        Customer webCustomer = new Customer(1, "John Doe", "john@example.com", "1234567890", "password", "123 Street", webUI);
        Customer mobileCustomer = new Customer(2, "Jane Doe", "jane@example.com", "0987654321", "password", "456 Avenue", mobileUI);

        // Register customers
        webCustomer.register();
        mobileCustomer.register();

        // Login customers
        Customer loggedInWebCustomer = Customer.login("john@example.com", "password");
        Customer loggedInMobileCustomer = Customer.login("jane@example.com", "password");

        if (loggedInWebCustomer != null && loggedInMobileCustomer != null) {
            // Create food items
            FoodItem soup = new Appetizer(1, "Tomato Soup", 5.99, "Delicious tomato soup");
            FoodItem steak = new MainCourse(2, "Grilled Steak", 19.99, "Juicy grilled steak");
            FoodItem iceCream = new Dessert(3, "Vanilla Ice Cream", 3.99, "Creamy vanilla ice cream");

            // Customers browse and add items to cart
            loggedInWebCustomer.browseItems(Arrays.asList(soup, steak, iceCream));
            loggedInWebCustomer.addToCart(soup);
            loggedInWebCustomer.addToCart(steak);

            loggedInMobileCustomer.browseItems(Arrays.asList(soup, steak, iceCream));
            loggedInMobileCustomer.addToCart(iceCream);

            // Create orders
            Order webOrder = new Order(1, loggedInWebCustomer.getCustomerId(), loggedInWebCustomer.getCart().hashCode(), "123 Street", "12:00 PM");
            Order mobileOrder = new Order(2, loggedInMobileCustomer.getCustomerId(), loggedInMobileCustomer.getCart().hashCode(), "456 Avenue", "1:00 PM");

            // Place orders
            loggedInWebCustomer.placeOrder(webOrder);
            loggedInMobileCustomer.placeOrder(mobileOrder);

            // Process payments
            Payment webPayment = new Payment(1, webOrder.getOrderId(), "Credit Card", 25.98);
            webPayment.processPayment();

            Payment mobilePayment = new Payment(2, mobileOrder.getOrderId(), "Credit Card", 3.99);
            mobilePayment.processPayment();

            // Update order status to trigger notifications
            webOrder.updateStatus("Delivered");
            mobileOrder.updateStatus("Out for Delivery");
        }
    }
}
