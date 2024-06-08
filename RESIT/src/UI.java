import java.util.List;

public interface UI {
    void displayFoodItems(List<FoodItem> items);
    void displayCart(Cart cart);
    void displayOrderStatus(Order order);
    void displayNotification(Notification notification);
}

