package com.revhire.dao;

import com.revhire.config.DBConnection;
import com.revhire.model.User;

import java.sql.*;

public class UserDAO {

    public boolean isEmailExists(String email) {
        String sql = "SELECT user_id FROM users WHERE email=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            return false;
        }
    }

//    public boolean register(User user) {
//        String sql = "INSERT INTO users(username, email, password, role, security_question, security_answer) VALUES(?,?,?,?,?,?)";
//        
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, user.getUsername());
//            ps.setString(2, user.getEmail());
//            ps.setString(3, user.getPassword());
//            ps.setString(4, user.getRole());
//            ps.setString(5, user.getSecurityQuestion());
//            ps.setString(6, user.getSecurityAnswer());
//
//            return ps.executeUpdate() > 0;
//
//        } catch (Exception e) {
//            return false;
//        }
//    }
    public boolean register(User user) {
        String sql = "INSERT INTO users(username, email, password, role, security_question, security_answer) VALUES(?,?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getSecurityQuestion());
            ps.setString(6, user.getSecurityAnswer());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace(); // only for developer debugging
            return false;
        }
    }


//    public User login(String email, String password) {
//        String sql = "SELECT * FROM users WHERE email=? AND password=?";
//
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, email);
//            ps.setString(2, password);
//
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                User user = new User();
//                user.setUserId(rs.getInt("user_id"));
//                return new User(
//                        rs.getString("username"),
//                        rs.getString("email"),
//                        rs.getString("password"),
//                        rs.getString("role"),
//                        rs.getString("security_question"),
//                        rs.getString("security_answer")
//                );
//            }
//
//        } catch (Exception e) {
//            return null;
//        }
//        return null;
//    }
    public User login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email=? AND password=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));   // THIS WAS MISSING
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setSecurityQuestion(rs.getString("security_question"));
                user.setSecurityAnswer(rs.getString("security_answer"));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("security_question"),
                        rs.getString("security_answer")
                );
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public boolean updatePassword(String email, String newPassword) {
        String sql = "UPDATE users SET password=? WHERE email=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setString(2, email);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            return false;
        }
    }
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        String sql = "UPDATE users SET password=? WHERE user_id=? AND password=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setInt(2, userId);
            ps.setString(3, oldPassword);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
