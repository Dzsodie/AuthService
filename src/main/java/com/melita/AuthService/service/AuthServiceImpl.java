package com.melita.AuthService.service;

import com.melita.AuthService.model.User;
import com.melita.AuthService.repository.UserRepository;
import com.melita.AuthService.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String login(String username, String password) {
        logger.info("Attempting to log in user: {}", username);

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                String token = jwtUtil.generateToken(username);
                logger.info("Login successful for user: {}", username);
                return token;
            }
        }

        logger.warn("Invalid login attempt for user: {}", username);
        throw new RuntimeException("Invalid credentials");
    }

    @Override
    public User register(User user) {
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            logger.error("Invalid user data provided for registration");
            throw new IllegalArgumentException("User details cannot be null");
        }

        logger.info("Registering new user: {}", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        logger.info("User registered successfully: {}", savedUser.getUsername());
        return savedUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Loading user by username: {}", username);
        if ("admin".equals(username)) {
            return org.springframework.security.core.userdetails.User.withUsername("admin")
                    .password("{noop}password")
                    .roles("ADMIN")
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
