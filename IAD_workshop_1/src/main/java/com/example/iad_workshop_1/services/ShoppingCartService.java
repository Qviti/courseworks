package com.example.iad_workshop_1.services;


import com.example.iad_workshop_1.models.Product;
import com.example.iad_workshop_1.models.ShoppingCart;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartService {

    private final List<ShoppingCart> carts = new ArrayList<>();
    private final ProductService productService;

    public ShoppingCartService(ProductService productService) {
        this.productService = productService;
    }

    public Long createCart() {
        ShoppingCart newCart = new ShoppingCart();
        newCart.setCartId((long) (carts.size() + 1));
        carts.add(newCart);
        return newCart.getCartId();
    }

    public void addProductToCart(Long cartId, ShoppingCart.ProductEntry productEntry) {
        ShoppingCart cart = carts.stream()
                .filter(c -> c.getCartId().equals(cartId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getProducts().add(productEntry);
        List<Product> allProducts = productService.getAllProducts();
        cart.calculateTotalPrice(allProducts);
    }

    public ShoppingCart getCart(Long cartId) {
        return carts.stream()
                .filter(c -> c.getCartId().equals(cartId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }
}


