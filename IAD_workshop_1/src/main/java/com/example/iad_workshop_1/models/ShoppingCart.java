package com.example.iad_workshop_1.models;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCart {
    private int id;
    private List<Product> products;

    public ShoppingCart(int id) {
        this.id = id;
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }
}


