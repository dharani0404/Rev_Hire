package com.revhire.dao;

import com.revhire.config.DBConnection;
import com.revhire.model.Applicant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicantDAO {

    public List<Applicant> getApplicantsByJob(int jobId) {
        List<Applicant> list = new ArrayList<>();

        
        	String sql = "SELECT a.application_id, js.seeker_id, js.full_name, u.email, a.status " +
        	             "FROM applications a " +
        	             "JOIN job_seeker_profiles js ON a.seeker_id = js.seeker_id " +
        	             "JOIN users u ON js.user_id = u.user_id " +
        	             "WHERE a.job_id = ?";

        

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Applicant app = new Applicant();
                app.setApplicationId(rs.getInt("application_id"));
                app.setApplicantId(rs.getInt("seeker_id"));
                app.setName(rs.getString("full_name"));
                app.setEmail(rs.getString("email"));
                app.setStatus(rs.getString("status"));
                list.add(app);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateStatus(int applicationId, String status, String comment) {
        String sql = "UPDATE applications SET status=?, employer_comment=? WHERE application_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setString(2, comment);
            ps.setInt(3, applicationId);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Applicant> filterApplicants(int jobId, String skill, double exp) {
        List<Applicant> list = new ArrayList<>();

	   String sql = "SELECT a.application_id, js.seeker_id, js.full_name, u.email, a.status " +
	                "FROM applications a " +
	                "JOIN resumes r ON a.resume_id = r.resume_id " +
	                "JOIN skills s ON r.resume_id = s.resume_id " +
	                "JOIN job_seeker_profiles js ON a.seeker_id = js.seeker_id " +
	                "JOIN users u ON js.user_id = u.user_id " +
	                "WHERE a.job_id = ? " +
	                "AND s.skill_name LIKE ? " +
	                "AND js.experience_years >= ?";

        

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobId);
            ps.setString(2, "%" + skill + "%");
            ps.setDouble(3, exp);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Applicant app = new Applicant();
                app.setApplicationId(rs.getInt("application_id"));
                app.setApplicantId(rs.getInt("seeker_id"));
                app.setName(rs.getString("full_name"));
                app.setEmail(rs.getString("email"));
                app.setStatus(rs.getString("status"));
                list.add(app);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
