package com.example.siteauto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.siteauto.repository.ProductRepository;

@Controller
public class CatalogController {

    private final ProductRepository productRepo;

    public CatalogController(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/catalog";
    }

    @GetMapping("/catalog")
    public String catalog(Model model) {
        model.addAttribute("products", productRepo.findByActiveTrue());
        return "catalog"; 
    }
}
