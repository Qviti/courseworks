public abstract class FoodItem {
    protected int itemId;
    protected String name;
    protected String category;
    protected double price;
    protected String description;

    public FoodItem(int itemId, String name, String category, double price, String description) {
        this.itemId = itemId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public void displayDetails() {
        // ToDo
    }

    // Getters and Setters
}