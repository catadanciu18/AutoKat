package com.example.siteauto.controller;

import com.example.siteauto.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class CatalogController {

    private final ProductRepository productRepo;

    // Home -> redirecționează la catalog
    @GetMapping("/")
    public String home() {
        return "redirect:/catalog";
    }

    // Catalog public
    @GetMapping("/catalog")
    public String catalog(Model model) {
        model.addAttribute("products", productRepo.findByActiveTrue());
        return "catalog"; // templates/catalog.html
    }

    // Detalii produs
    @GetMapping("/product/{id}")
    public String productDetails(@PathVariable Long id, Model model) {
        var product = productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produs inexistent"));
        model.addAttribute("product", product);
        return "product"; // templates/product.html
    }
}
