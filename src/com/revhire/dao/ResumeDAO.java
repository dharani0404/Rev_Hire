package com.revhire.dao;

import com.revhire.config.DBConnection;
import com.revhire.model.Resume;

import java.sql.*;

public class ResumeDAO {

    public Resume getResumeBySeekerId(int seekerId) throws Exception {
        String sql = "SELECT * FROM resumes WHERE seeker_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, seekerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Resume r = new Resume();
                r.setResumeId(rs.getInt("resume_id"));
                r.setSeekerId(rs.getInt("seeker_id"));
                r.setObjective(rs.getString("objective"));
                return r;
            }
        }
        return null;
    } 
	// the below method is used for the job seeker

    public int createResume(Resume resume) throws Exception {
        String sql = "INSERT INTO resumes (seeker_id, objective) VALUES (?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, resume.getSeekerId());
            ps.setString(2, resume.getObjective());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public void updateResumeObjective(Resume resume) throws Exception {
        String sql = "UPDATE resumes SET objective=? WHERE seeker_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, resume.getObjective());
            ps.setInt(2, resume.getSeekerId());
            ps.executeUpdate();
        }
    }
    
    
    
}
