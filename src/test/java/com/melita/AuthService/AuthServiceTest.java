package com.melita.AuthService;

import com.melita.AuthService.model.User;
import com.melita.AuthService.repository.UserRepository;
import com.melita.AuthService.security.JwtUtil;
import com.melita.AuthService.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("encodedPassword");
    }

    @Test
    void testLogin_Success() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("rawPassword", "encodedPassword")).thenReturn(true);
        when(jwtUtil.generateToken("testUser")).thenReturn("mockedToken");

        String token = authService.login("testUser", "rawPassword");

        assertEquals("mockedToken", token);
        verify(userRepository).findByUsername("testUser");
        verify(passwordEncoder).matches("rawPassword", "encodedPassword");
        verify(jwtUtil).generateToken("testUser");
    }

    @Test
    void testLogin_InvalidCredentials() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> authService.login("testUser", "wrongPassword"));
    }

    @Test
    void testLogin_UserNotFound() {
        when(userRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authService.login("unknownUser", "password"));
    }

    @Test
    void testRegister_Success() {
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User savedUser = authService.register(new User("testUser", "rawPassword"));

        assertNotNull(savedUser);
        assertEquals("testUser", savedUser.getUsername());
        verify(passwordEncoder).encode("rawPassword");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testLoadUserByUsername_Admin() {
        var userDetails = authService.loadUserByUsername("admin");

        assertNotNull(userDetails);
        assertEquals("admin", userDetails.getUsername());
    }

    @Test
    void testLoadUserByUsername_NotFound() {
        assertThrows(UsernameNotFoundException.class, () -> authService.loadUserByUsername("unknownUser"));
    }
}
