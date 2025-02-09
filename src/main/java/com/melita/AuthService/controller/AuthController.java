package com.melita.AuthService.controller;

import com.melita.AuthService.model.User;
import com.melita.AuthService.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        logger.info("Registering user with username: {}", user.getUsername());
        User registeredUser = authService.register(user);
        logger.info("User registered successfully: {}", registeredUser.getUsername());
        return registeredUser;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        logger.info("User attempting to log in: {}", username);
        String token = authService.login(username, credentials.get("password"));
        logger.info("Login successful for user: {}", username);
        return Map.of("token", token);
    }
}
