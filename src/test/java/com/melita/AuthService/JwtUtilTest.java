package com.melita.AuthService;

import com.melita.AuthService.security.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    private JwtUtil jwtUtil;

    private static final String SECRET = "mysecretkeymysecretkeymysecretkey";
    private static final long EXPIRATION = 3600000; // 1 hour

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secretKey", SECRET);
        ReflectionTestUtils.setField(jwtUtil, "jwtExpiration", EXPIRATION);
    }

    @Test
    void testGenerateToken() {
        String token = jwtUtil.generateToken("testuser");
        assertNotNull(token);
    }

    @Test
    void testValidateToken() {
        String token = jwtUtil.generateToken("testuser");
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void testValidateToken_Invalid() {
        assertFalse(jwtUtil.validateToken("invalidtoken"));
    }

    @Test
    void testExtractUsername() {
        String token = jwtUtil.generateToken("testuser");
        assertEquals("testuser", jwtUtil.extractUsername(token));
    }

    @Test
    void testExtractAllClaims() {
        String token = jwtUtil.generateToken("testuser");
        Claims claims = jwtUtil.extractAllClaims(token);
        assertEquals("testuser", claims.getSubject());
    }

    @Test
    void testGetTokenFromRequest_ValidHeader() {
        when(request.getHeader("Authorization")).thenReturn("Bearer validToken");
        assertEquals("validToken", jwtUtil.getTokenFromRequest(request));
    }

    @Test
    void testGetTokenFromRequest_InvalidHeader() {
        when(request.getHeader("Authorization")).thenReturn(null);
        assertNull(jwtUtil.getTokenFromRequest(request));
    }

    @Test
    void testGetTokenFromRequest_NoBearer() {
        when(request.getHeader("Authorization")).thenReturn("InvalidToken");
        assertNull(jwtUtil.getTokenFromRequest(request));
    }
}
