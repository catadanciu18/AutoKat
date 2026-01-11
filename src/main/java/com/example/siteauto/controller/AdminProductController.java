package com.example.siteauto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.siteauto.model.Product;
import com.example.siteauto.repository.ProductRepository;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductRepository productRepo;

    public AdminProductController(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productRepo.findAll());
        return "admin/products"; 
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("title", "Adăugare produs");
        return "admin/product-form"; 
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produs inexistent"));
        model.addAttribute("product", product);
        model.addAttribute("title", "Editare produs");
        return "admin/product-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Product product) {

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
