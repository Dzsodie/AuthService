package com.melita.AuthService.controller;

import com.melita.AuthService.model.User;
import com.melita.AuthService.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication API for user registration and login")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user with encrypted password")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            logger.error("Invalid user registration request");
            return ResponseEntity.badRequest().body("Invalid user details");
        }

        logger.info("Registering user with username: {}", user.getUsername());
        try {
            User registeredUser = authService.register(user);
            logger.info("User registered successfully: {}", registeredUser.getUsername());
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            logger.error("Error during registration: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Registration failed");
        }
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates user and returns a JWT token")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username == null || password == null) {
            logger.error("Invalid login request");
            return ResponseEntity.badRequest().body("Username and password must be provided");
        }

        logger.info("User attempting to log in: {}", username);
        try {
            String token = authService.login(username, password);
            logger.info("Login successful for user: {}", username);
            return ResponseEntity.ok(Map.of("token", token));
        } catch (RuntimeException e) {
            logger.warn("Login failed for user: {}", username);
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
