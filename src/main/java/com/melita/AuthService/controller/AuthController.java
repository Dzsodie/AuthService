package com.melita.AuthService.controller;

import com.melita.AuthService.model.User;
import com.melita.AuthService.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> credentials) {
        String token = authService.login(credentials.get("username"), credentials.get("password"));
        return Map.of("token", token);
    }
}

