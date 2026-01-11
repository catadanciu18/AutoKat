package com.example.siteauto.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.siteauto.model.UserAccount;
import com.example.siteauto.repository.UserAccountRepository;

@Component
public class DataInit implements CommandLineRunner {

    private final UserAccountRepository userRepo;
    private final PasswordEncoder encoder;


    public DataInit(UserAccountRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        if (userRepo.findByUsername("admin").isEmpty()) {
            UserAccount admin = new UserAccount();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("admin123"));
            admin.setRole("ROLE_ADMIN");
            admin.setEnabled(true);
            userRepo.save(admin);
        }
    }
}
