package com.revhire.service;

import com.revhire.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {

    private final AuthService authService = new AuthService();

    @Test
    void testRegisterWithInvalidEmail() {
        User user = new User("validUser", "invalid_email", "Test123", "JOB_SEEKER", "q", "a");
        String result = authService.register(user);
        assertEquals("Invalid email format", result);
    }

    @Test
    void testRegisterWithWeakPassword() {
        String email = "weak" + System.nanoTime() + "@test.com";
        User user = new User("validUser", email, "test123", "JOB_SEEKER", "q", "a");
        String result = authService.register(user);
        assertEquals("Password must contain at least one uppercase letter", result);
    }
    @Test
    void testRegisterWithInvalidRole() {
        String email = "role" + System.nanoTime() + "@test.com";
        User user = new User("validUser", email, "Test123", "ADMIN", "q", "a");
        String result = authService.register(user);
        assertEquals("Invalid role selected", result);
    }

    @Test
    void testLoginWithInvalidEmail() {
        User user = authService.login("wrongemail", "1234");
        assertNull(user);
    }

    @Test
    void testForgotPasswordUserNotFound() {
        String email = "nouser_" + System.currentTimeMillis() + "@test.com";
        String result = authService.forgotPassword(email, "wrong", "New1234");
        assertEquals("User not found", result);
    }

}
