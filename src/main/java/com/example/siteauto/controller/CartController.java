package com.example.siteauto.controller;

import com.example.siteauto.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;


@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/cart")
    public String cartPage(Model model) {
        model.addAttribute("cartItems", cartService.getAllItems());
        model.addAttribute("totalAmount", cartService.getTotalAmount());
        return "cart";
    }

    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable Long id) {
        cartService.addProduct(id);
        return "redirect:/cart";
    }

    @PostMapping("/cart/update/{id}")
    public String updateQuantity(@PathVariable Long id,
                                 @RequestParam String action) {

        boolean increase = action.equals("plus");
        cartService.updateQuantity(id, increase);

        return "redirect:/cart";
    }

    @PostMapping("/cart/remove/{id}")
    public String removeItem(@PathVariable Long id) {
        cartService.removeProduct(id);
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(Model model) {

        BigDecimal total = cartService.getTotalAmount();

        cartService.clearCart();

        model.addAttribute("totalPrice", total);
        return "checkout-success";
    }
}
