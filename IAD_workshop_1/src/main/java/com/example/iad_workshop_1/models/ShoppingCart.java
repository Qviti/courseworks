package com.example.iad_workshop_1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {

    private Long cartId;
    private List<ProductEntry> products = new ArrayList<>();
    private double totalPrice;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductEntry {
        private Long productId;
        private int quantity;
    }

    public void calculateTotalPrice(List<Product> availableProducts) {
        this.totalPrice = products.stream()
                .mapToDouble(entry -> {
                    Product product = availableProducts.stream()
                            .filter(p -> p.getId().equals(entry.getProductId()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Product not found"));
                    return product.getPrice() * entry.getQuantity();
                })
                .sum();
    }
}


