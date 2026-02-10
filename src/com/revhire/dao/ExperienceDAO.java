package com.revhire.dao;

import com.revhire.config.DBConnection;
import com.revhire.model.Experience;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExperienceDAO {

    // Add experience
    public void addExperience(Experience exp) throws Exception {
        String sql = "INSERT INTO experience (resume_id, job_title, company, start_date, end_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, exp.getResumeId());
            ps.setString(2, exp.getJobTitle());
            ps.setString(3, exp.getCompany());
            ps.setString(4, exp.getStartDate());
            ps.setString(5, exp.getEndDate());
            ps.executeUpdate();
        }
    }

    // Update experience
    public void updateExperience(Experience exp) throws Exception {
        String sql = "UPDATE experience SET job_title=?, company=?, start_date=?, end_date=? WHERE experience_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, exp.getJobTitle());
            ps.setString(2, exp.getCompany());
            ps.setString(3, exp.getStartDate());
            ps.setString(4, exp.getEndDate());
            ps.setInt(5, exp.getExperienceId());
            ps.executeUpdate();
        }
    }

    // Delete experience
    public void deleteExperience(int experienceId) throws Exception {
        String sql = "DELETE FROM experience WHERE experience_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, experienceId);
            ps.executeUpdate();
        }
    }

    // Get all experiences by resumeId
    public List<Experience> getExperienceByResumeId(int resumeId) throws Exception {
        List<Experience> list = new ArrayList<>();
        String sql = "SELECT * FROM experience WHERE resume_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, resumeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Experience exp = new Experience();
                exp.setExperienceId(rs.getInt("experience_id"));
                exp.setResumeId(rs.getInt("resume_id"));
                exp.setJobTitle(rs.getString("job_title"));
                exp.setCompany(rs.getString("company"));
                exp.setStartDate(rs.getString("start_date"));
                exp.setEndDate(rs.getString("end_date"));
                list.add(exp);
            }
        }
        return list;
    }
}
