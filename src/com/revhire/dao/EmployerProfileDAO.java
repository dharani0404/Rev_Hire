package com.revhire.dao;

import com.revhire.model.EmployerProfile;
import com.revhire.config.DBConnection;

import java.sql.*;

public class EmployerProfileDAO {

    public boolean addCompanyProfile(EmployerProfile p) {
        String sql = "INSERT INTO employer_profiles (user_id, company_name, industry, company_size, website, location, description) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getUserId());
            ps.setString(2, p.getCompanyName());
            ps.setString(3, p.getIndustry());
            ps.setInt(4, p.getCompanySize());
            ps.setString(5, p.getWebsite());
            ps.setString(6, p.getLocation());
            ps.setString(7, p.getDescription());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public EmployerProfile getCompanyProfileByUserId(int userId) {
        String sql = "SELECT * FROM employer_profiles WHERE user_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                EmployerProfile p = new EmployerProfile();
                p.setEmployerId(rs.getInt("employer_id"));
                p.setUserId(rs.getInt("user_id"));
                p.setCompanyName(rs.getString("company_name"));
                p.setIndustry(rs.getString("industry"));
                p.setCompanySize(rs.getInt("company_size"));
                p.setWebsite(rs.getString("website"));
                p.setLocation(rs.getString("location"));
                p.setDescription(rs.getString("description"));
                return p;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateCompanyProfile(EmployerProfile p) {
        String sql = "UPDATE employer_profiles SET company_name=?, industry=?, company_size=?, website=?, location=?, description=? WHERE user_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getCompanyName());
            ps.setString(2, p.getIndustry());
            ps.setInt(3, p.getCompanySize());
            ps.setString(4, p.getWebsite());
            ps.setString(5, p.getLocation());
            ps.setString(6, p.getDescription());
            ps.setInt(7, p.getUserId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public String getCompanyNameById(int employerId) {
        String companyName = null;
        String sql = "SELECT company_name FROM employer_profile WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                companyName = rs.getString("company_name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return companyName;
    }
}
