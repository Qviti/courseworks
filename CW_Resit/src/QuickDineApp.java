import java.util.Arrays;

public class QuickDineApp {
    public static void main(String[] args) {
        // Create UI implementations
        UI webUI = new WebUI();
        UI mobileUI = new MobileUI();

        // Create customers with different UI implementations
        Customer webCustomer = new Customer(1, "John Doe", "john@example.com", "1234567890", "password", "123 Street", webUI);
        Customer mobileCustomer = new Customer(2, "Jane Doe", "jane@example.com", "0987654321", "password", "456 Avenue", mobileUI);

        // Create food items
        FoodItem soup = new Appetizer(1, "Tomato Soup", 5.99, "Soup made from tomatoes");
        FoodItem steak = new MainCourse(2, "Steak", 19.99, "Medium rare steak");
        FoodItem iceCream = new Dessert(3, "Ice Cream", 3.99, "Vanilla ice cream");

        // Customers browse and add items to cart
        webCustomer.browseItems(Arrays.asList(soup, steak, iceCream));
        webCustomer.addToCart(soup);
        webCustomer.addToCart(steak);

        mobileCustomer.browseItems(Arrays.asList(soup, steak, iceCream));
        mobileCustomer.addToCart(iceCream);

        // Create orders
        Order webOrder = new Order(1, webCustomer.getCustomerId(), webCustomer.getCart().hashCode(), "123 Street", "12:00 PM");
        Order mobileOrder = new Order(2, mobileCustomer.getCustomerId(), mobileCustomer.getCart().hashCode(), "456 Avenue", "1:00 PM");

        // Place orders
        webCustomer.placeOrder(webOrder);
        mobileCustomer.placeOrder(mobileOrder);

        // Update order status to trigger notifications
        webOrder.updateStatus("Delivered");
        mobileOrder.updateStatus("Out for Delivery");
    }
}


