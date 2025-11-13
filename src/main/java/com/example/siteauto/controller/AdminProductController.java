package com.example.siteauto.controller;

import com.example.siteauto.model.Product;
import com.example.siteauto.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductRepository productRepo;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productRepo.findAll());
        return "admin/products"; // templates/admin/products.html
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("title", "Adăugare produs");
        return "admin/product-form"; // templates/admin/product-form.html
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        var product = productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produs inexistent"));
        model.addAttribute("product", product);
        model.addAttribute("title", "Editare produs");
        return "admin/product-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Product product) {
        // dacă stock e null, punem 0 ca să nu se supere DB
        if (product.getStock() == null) {
            product.setStock(0);
        }
        productRepo.save(product);
        return "redirect:/admin/products";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        productRepo.deleteById(id);
        return "redirect:/admin/products";
    }
}
