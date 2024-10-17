package com.example.iad_workshop_1.controllers;

import com.example.iad_workshop_1.models.ShoppingCart;
import com.example.iad_workshop_1.services.ShoppingCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping
    public ResponseEntity<Long> createShoppingCart() {
        Long cartId = shoppingCartService.createCart();
        return ResponseEntity.ok(cartId);
    }

    @PostMapping("/{cartId}/products")
    public ResponseEntity<String> addProductToCart(@PathVariable Long cartId, @RequestBody ShoppingCart.ProductEntry productEntry) {
        shoppingCartService.addProductToCart(cartId, productEntry);
        return ResponseEntity.ok("Product added to cart");
    }

    @GetMapping("/{cartId}")
    public ShoppingCart getShoppingCart(@PathVariable Long cartId) {
        return shoppingCartService.getCart(cartId);
    }
}


