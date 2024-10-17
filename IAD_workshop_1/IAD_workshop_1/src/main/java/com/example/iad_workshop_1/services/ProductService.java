package com.example.iad_workshop_1.services;

import com.example.iad_workshop_1.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>();

    public List<Product> getAllProducts() {
        return products;
    }

    public void saveProductImage(Long productId, MultipartFile file) {
        Product product = products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found"));
        try {
            product.setImageFile(file);
        } catch (IOException e) {
            throw new RuntimeException("Error saving image", e);
        }
    }

    public byte[] getProductImage(Long productId) {
        Product product = products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return product.getImageFile();
    }
}


