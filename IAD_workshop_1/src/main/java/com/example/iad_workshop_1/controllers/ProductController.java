package com.example.iad_workshop_1.controllers;

import com.example.iad_workshop_1.models.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Map<Integer, Product> products = new HashMap<>();

    static {
        products.put(1, new Product(1, "Green Tea", 12.50, 100, "green-tea.jpg"));
        products.put(2, new Product(2, "Black Tea", 10.00, 50, "black-tea.jpg"));
    }

    @GetMapping
    public Set<Integer> getProductList() {
        return products.keySet();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        Product product = products.get(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<String> setProductImage(@PathVariable int id, @RequestBody String imageFile) {
        Product product = products.get(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        product.setImageFile(imageFile);
        return ResponseEntity.ok("Image updated for product ID: " + id);
    }

    @DeleteMapping("/{id}/image")
    public ResponseEntity<String> deleteProductImage(@PathVariable int id) {
        Product product = products.get(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        product.setImageFile(null);
        return ResponseEntity.ok("Image deleted for product ID: " + id);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<String> downloadProductImage(@PathVariable int id) {
        Product product = products.get(id);
        if (product == null || product.getImageFile() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Downloading image for product: " + product.getImageFile());
    }
}


