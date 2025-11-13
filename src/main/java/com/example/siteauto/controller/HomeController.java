package com.example.siteauto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // fie schimbi ruta:
    @GetMapping("/home")
    public String home() {
        return "index"; // dacă ai index.html
    }
}
