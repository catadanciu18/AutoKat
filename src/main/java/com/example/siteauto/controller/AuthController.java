package com.example.siteauto.controller;

import com.example.siteauto.model.UserAccount;
import com.example.siteauto.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserAccountRepository userRepo;
    private final PasswordEncoder encoder;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("userForm", new UserAccount());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute("userForm") UserAccount form, Model model) {
        if (userRepo.findByUsername(form.getUsername()).isPresent()) {
            model.addAttribute("error", "Username deja folosit.");
            return "register";
        }

        UserAccount user = new UserAccount();
        user.setUsername(form.getUsername());
        user.setPassword(encoder.encode(form.getPassword()));
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        userRepo.save(user);

        return "redirect:/login?registered";
    }
}
