package com.revhire.dao;

import com.revhire.config.DBConnection;
import com.revhire.model.JobSeekerProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JobSeekerDAO {

    public boolean profileExists(int userId) throws Exception {
        String sql = "SELECT seeker_id FROM job_seeker_profiles WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    public void createProfile(JobSeekerProfile profile) throws Exception {
        String sql = "INSERT INTO job_seeker_profiles (user_id, full_name, phone, location, experience_years, profile_complete) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, profile.getUserId());
            ps.setString(2, profile.getFullName());
            ps.setString(3, profile.getPhone());
            ps.setString(4, profile.getLocation());
            ps.setDouble(5, profile.getExperienceYears());
            ps.setBoolean(6, true);
            ps.executeUpdate();
        }
    }

    public JobSeekerProfile getProfileByUserId(int userId) throws Exception {
        String sql = "SELECT * FROM job_seeker_profiles WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JobSeekerProfile p = new JobSeekerProfile();
                p.setSeekerId(rs.getInt("seeker_id"));
                p.setUserId(rs.getInt("user_id"));
                p.setFullName(rs.getString("full_name"));
                p.setPhone(rs.getString("phone"));
                p.setLocation(rs.getString("location"));
                p.setExperienceYears(rs.getDouble("experience_years"));
                p.setProfileComplete(rs.getBoolean("profile_complete"));
                return p;
            }
        }
        return null;
    }

    public void updateProfile(JobSeekerProfile profile) throws Exception {
        String sql = "UPDATE job_seeker_profiles SET full_name=?, phone=?, location=?, experience_years=?, profile_complete=? WHERE user_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, profile.getFullName());
            ps.setString(2, profile.getPhone());
            ps.setString(3, profile.getLocation());
            ps.setDouble(4, profile.getExperienceYears());
            ps.setBoolean(5, true);
            ps.setInt(6, profile.getUserId());
            ps.executeUpdate();
        }
    }
    
    public boolean updateProfileCompletion(int seekerId, boolean status) {
        String sql = "UPDATE job_seeker_profiles SET profile_complete=? WHERE seeker_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, status);
            ps.setInt(2, seekerId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isProfileComplete(int seekerId) {
        String sql = 
            "SELECT " + 
            "(SELECT COUNT(*) FROM resumes WHERE seeker_id = ?) > 0 " +
            "AND (SELECT COUNT(*) FROM education e JOIN resumes r ON e.resume_id = r.resume_id WHERE r.seeker_id = ?) > 0 " +
            "AND (SELECT COUNT(*) FROM experience e JOIN resumes r ON e.resume_id = r.resume_id WHERE r.seeker_id = ?) > 0 " +
            "AND (SELECT COUNT(*) FROM skills s JOIN resumes r ON s.resume_id = r.resume_id WHERE r.seeker_id = ?) > 0 " +
            "AND (SELECT COUNT(*) FROM projects p JOIN resumes r ON p.resume_id = r.resume_id WHERE r.seeker_id = ?) > 0 " +
            "AS completed " ;
       

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 1; i <= 5; i++) {
                ps.setInt(i, seekerId);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
