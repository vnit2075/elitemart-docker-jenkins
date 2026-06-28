package com.elitemart.config;

import com.elitemart.model.Role;
import com.elitemart.model.User;
import com.elitemart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Create default admin if not present
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@elitemart.com");
            admin.setPassword(passwordEncoder.encode("admin1234"));
            admin.setRole(Role.ROLE_ADMIN);
            userRepository.save(admin);
            log.info("✅ Default admin created  →  username: admin  |  password: admin1234");
        }
    }
}
