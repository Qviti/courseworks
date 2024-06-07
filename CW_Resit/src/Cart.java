import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int cartId;
    private List<FoodItem> items;
    private double totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addItem(FoodItem item) {
        items.add(item);
        calculateTotal();
    }

    public void removeItem(FoodItem item) {
        items.remove(item);
        calculateTotal();
    }

    public void updateQuantity(FoodItem item, int quantity) {
        // ToDo
        calculateTotal();
    }

    private void calculateTotal() {
        totalPrice = items.stream().mapToDouble(item -> item.price).sum();
    }

    // Getters and Setters
}