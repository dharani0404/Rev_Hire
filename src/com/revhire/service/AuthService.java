package com.revhire.service;

import com.revhire.dao.UserDAO;
import com.revhire.model.User;

import java.util.regex.Pattern;

public class AuthService {

    private final UserDAO userDAO = new UserDAO();

    public String register(User user) {

        if (user.getUsername() == null || user.getUsername().isBlank())
            return "Username cannot be empty";

        if (user.getUsername().length() < 3 || user.getUsername().length() > 20)
            return "Username must be between 3 and 20 characters";

        if (!user.getUsername().matches("^[a-zA-Z0-9_]+$"))
            return "Username can contain only letters, numbers, and underscore";

        if (!isValidEmail(user.getEmail()))
            return "Invalid email format";

        if (user.getPassword() == null || user.getPassword().isBlank())
            return "Password cannot be empty";

        if (user.getPassword().length() < 6)
            return "Password must be at least 6 characters";

        if (!user.getPassword().matches(".*[A-Z].*"))
            return "Password must contain at least one uppercase letter";

        if (!user.getPassword().matches(".*[a-z].*"))
            return "Password must contain at least one lowercase letter";

        if (!user.getPassword().matches(".*\\d.*"))
            return "Password must contain at least one number";

        if (!(user.getRole().equals("JOB_SEEKER") || user.getRole().equals("EMPLOYER")))
            return "Invalid role selected";

        if (userDAO.isEmailExists(user.getEmail()))
            return "Email already registered";

        boolean success = userDAO.register(user);
        return success ? "SUCCESS" : "Registration failed. Try again.";
    }

    public User login(String email, String password) {

        if (!isValidEmail(email))
            return null;

        if (password == null || password.isBlank())
            return null;

        return userDAO.login(email, password);
    }

    public String forgotPassword(String email, String answer, String newPassword) {

        if (!isValidEmail(email))
            return "Invalid email format";

        if (newPassword == null || newPassword.length() < 6)
            return "New password must be at least 6 characters";

        if (!newPassword.matches(".*\\d.*"))
            return "New password must contain at least one number";

        User user = userDAO.findByEmail(email);
        if (user == null)
            return "User not found";

        if (!user.getSecurityAnswer().equalsIgnoreCase(answer))
            return "Security answer is incorrect";

        boolean updated = userDAO.updatePassword(email, newPassword);
        return updated ? "SUCCESS" : "Password update failed";
    }

    public String changePasswordByEmail(String email, String oldPass, String newPass) {

        if (newPass == null || newPass.length() < 6)
            return "New password must be at least 6 characters";

        if (!newPass.matches(".*\\d.*"))
            return "New password must contain at least one number";

        User user = userDAO.login(email, oldPass);
        if (user == null)
            return "Old password is incorrect";

        boolean updated = userDAO.updatePassword(email, newPass);
        return updated ? "SUCCESS" : "Password update failed";
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) return false;
        return Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email);
    }
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        return userDAO.changePassword(userId, oldPassword, newPassword);
    }

}
