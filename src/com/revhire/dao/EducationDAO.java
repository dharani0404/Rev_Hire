package com.revhire.dao;

import com.revhire.config.DBConnection;
import com.revhire.model.Education;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EducationDAO {

    public void addEducation(Education edu) throws Exception {
        String sql = "INSERT INTO education (resume_id, degree, institution, start_date, end_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, edu.getResumeId());
            ps.setString(2, edu.getDegree());
            ps.setString(3, edu.getInstitution());
            ps.setString(4, edu.getStartDate());
            ps.setString(5, edu.getEndDate());
            ps.executeUpdate();
        }
    }
 // Update
    public void updateEducation(Education edu) throws Exception {
        String sql = "UPDATE education SET degree=?, institution=?, start_date=?, end_date=? WHERE education_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, edu.getDegree());
            ps.setString(2, edu.getInstitution());
            ps.setString(3, edu.getStartDate());
            ps.setString(4, edu.getEndDate());
            ps.setInt(5, edu.getEducationId());
            ps.executeUpdate();
        }
    }

    // Delete
    public void deleteEducation(int educationId) throws Exception {
        String sql = "DELETE FROM education WHERE education_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, educationId);
            ps.executeUpdate();
        }
    }


    public List<Education> getEducationByResumeId(int resumeId) throws Exception {
        List<Education> list = new ArrayList<>();
        String sql = "SELECT * FROM education WHERE resume_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, resumeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Education edu = new Education();
                edu.setEducationId(rs.getInt("education_id"));
                edu.setResumeId(rs.getInt("resume_id"));
                edu.setDegree(rs.getString("degree"));
                edu.setInstitution(rs.getString("institution"));
                edu.setStartDate(rs.getString("start_date"));
                edu.setEndDate(rs.getString("end_date"));
                list.add(edu);
            }
        }
        return list;
    }
	

}
