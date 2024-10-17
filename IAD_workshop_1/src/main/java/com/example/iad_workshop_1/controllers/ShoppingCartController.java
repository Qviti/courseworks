package com.example.iad_workshop_1.controllers;

import com.example.iad_workshop_1.models.Product;
import com.example.iad_workshop_1.models.ShoppingCart;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    private static final Map<Integer, ShoppingCart> shoppingCarts = new HashMap<>();
    private static int nextCartId = 1;

    @PostMapping
    public ResponseEntity<String> createShoppingCart() {
        ShoppingCart cart = new ShoppingCart(nextCartId++);
        shoppingCarts.put(cart.getId(), cart);
        return ResponseEntity.ok("Shopping cart created with ID: " + cart.getId());
    }

    @PutMapping("/{id}/products")
    public ResponseEntity<String> addProductToCart(@PathVariable int id, @RequestBody Product product) {
        ShoppingCart cart = shoppingCarts.get(id);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        cart.addProduct(product);
        return ResponseEntity.ok("Product added to cart ID: " + id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart> getCartDetails(@PathVariable int id) {
        ShoppingCart cart = shoppingCarts.get(id);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cart);
    }
}

