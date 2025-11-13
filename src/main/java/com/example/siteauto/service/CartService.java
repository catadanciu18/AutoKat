package com.example.siteauto.service;

import com.example.siteauto.model.CartItem;
import com.example.siteauto.model.Product;
import com.example.siteauto.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import java.math.BigDecimal;

import java.util.*;

@Service
@SessionScope     // fiecare user are propriul coș
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepository;
    private final Map<Long, CartItem> items = new HashMap<>();

    public List<CartItem> getAllItems() {
        return new ArrayList<>(items.values());
    }

    public void addProduct(Long productId) {
        Product p = productRepository.findById(productId).orElse(null);
        if (p == null) return;

        CartItem item = items.get(productId);

        if (item == null) {
            items.put(productId, new CartItem(p, 1));
        } else {
            item.setQuantity(item.getQuantity() + 1);
        }
    }

    public void updateQuantity(Long productId, boolean increase) {
        CartItem item = items.get(productId);
        if (item == null) return;

        if (increase) {
            item.setQuantity(item.getQuantity() + 1);
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
        return items.values().stream()
                .map(CartItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public void clearCart() {
        items.clear();
    }
}
