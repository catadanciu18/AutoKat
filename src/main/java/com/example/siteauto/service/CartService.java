package com.example.siteauto.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.example.siteauto.model.CartItem;
import com.example.siteauto.model.Product;
import com.example.siteauto.repository.ProductRepository;

@Service
@SessionScope
public class CartService {

    private final ProductRepository productRepository;
    private final Map<Long, CartItem> items = new HashMap<>();

    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<CartItem> getAllItems() {
        return new ArrayList<>(items.values());
    }

    public void addProduct(Long productId) {
        Product p = productRepository.findById(productId).orElse(null);
        if (p == null) return;


        int stock = (p.getStock() == null) ? 0 : p.getStock();
        if (stock <= 0) return; 

        CartItem item = items.get(productId);

        if (item == null) {

            items.put(productId, new CartItem(p, 1));
        } else {
            if (item.getQuantity() < stock) {
                item.setQuantity(item.getQuantity() + 1);
            }
        }
    }

    public void updateQuantity(Long productId, boolean increase) {
        CartItem item = items.get(productId);
        if (item == null) return;

        if (increase) {
            Product p = productRepository.findById(productId).orElse(null);
            if (p == null) return;

            int stock = (p.getStock() == null) ? 0 : p.getStock();


            if (item.getQuantity() < stock) {
                item.setQuantity(item.getQuantity() + 1);
            }
        } else {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
            } else {
                items.remove(productId);
            }
        }
    }

    public void removeProduct(Long productId) {
        items.remove(productId);
    }

    public BigDecimal getTotalAmount() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : items.values()) {
            total = total.add(item.getTotal());
        }
        return total;
    }

    public void clearCart() {
        items.clear();
    }
}
