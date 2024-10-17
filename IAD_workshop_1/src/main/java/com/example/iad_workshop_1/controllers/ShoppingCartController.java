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

    // C. Create a shopping cart and get the ID
    @PostMapping
    public ResponseEntity<Long> createShoppingCart() {
        Long cartId = shoppingCartService.createCart();
        return ResponseEntity.ok(cartId);
    }

    // D. Add products to a shopping cart with an ID
    @PostMapping("/{cartId}/products")
    public ResponseEntity<String> addProductToCart(@PathVariable Long cartId, @RequestBody ShoppingCart.ProductEntry productEntry) {
        shoppingCartService.addProductToCart(cartId, productEntry);
        return ResponseEntity.ok("Product added to cart");
    }

    // E. Get information about shopping cart with a given ID
    @GetMapping("/{cartId}")
    public ShoppingCart getShoppingCart(@PathVariable Long cartId) {
        return shoppingCartService.getCart(cartId);
    }
}



