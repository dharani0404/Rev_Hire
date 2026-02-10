package com.revhire.dao;

import com.revhire.config.DBConnection;
import com.revhire.model.Application;

import java.sql.*;
import java.util.*;

public class ApplicationDAO {

    public List<Application> getApplicantsByJob(int jobId) {
        List<Application> list = new ArrayList<>();
        String sql = "SELECT * FROM applications WHERE job_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Application a = new Application();
                a.setApplicationId(rs.getInt("application_id"));
                a.setJobId(rs.getInt("job_id"));
                a.setSeekerId(rs.getInt("seeker_id"));
                a.setResumeId(rs.getInt("resume_id"));
                a.setStatus(rs.getString("status"));
                a.setAppliedDate(rs.getTimestamp("applied_date"));
                a.setEmployerComment(rs.getString("employer_comment"));
                list.add(a);
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

    public List<Application> filterApplicants(int jobId, String skill, double minExp) {
        List<Application> list = new ArrayList<>();

        String sql = 
            "SELECT a.* FROM applications a" +
            "JOIN resumes r ON a.resume_id = r.resume_id" +
            "JOIN skills s ON r.resume_id = s.resume_id" +
            "JOIN job_seeker_profiles p ON a.seeker_id = p.seeker_id" +
            "WHERE a.job_id = ?"+
            "AND s.skill_name LIKE ?" +
            "AND p.experience_years >= ?" ;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobId);
            ps.setString(2, "%" + skill + "%");
            ps.setDouble(3, minExp);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Application a = new Application();
                a.setApplicationId(rs.getInt("application_id"));
                a.setJobId(rs.getInt("job_id"));
                a.setSeekerId(rs.getInt("seeker_id"));
                a.setStatus(rs.getString("status"));
                list.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
//    public boolean applyForJob(int userId, int jobId, String coverLetter) {
//        String sql = "INSERT INTO applications(job_id, user_id, cover_letter, status, applied_at) VALUES (?, ?, ?, 'APPLIED', NOW())";
//
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setInt(1, jobId);
//            ps.setInt(2, userId);
//            ps.setString(3, coverLetter);
//
//            return ps.executeUpdate() > 0;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
    public boolean applyForJob(int jobId, int seekerId, int resumeId, String coverLetter) {

        String sql = "INSERT INTO applications (job_id, seeker_id, resume_id, cover_letter) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobId);
            ps.setInt(2, seekerId);
            ps.setInt(3, resumeId);
            ps.setString(4, coverLetter);

            return ps.executeUpdate() > 0;

        }catch (SQLIntegrityConstraintViolationException e) {
            // Duplicate apply
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean hasAlreadyApplied(int jobId, int seekerId) {
        String sql = "SELECT application_id FROM applications WHERE job_id = ? AND seeker_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobId);
            ps.setInt(2, seekerId);

            ResultSet rs = ps.executeQuery();
            return rs.next(); // true if record exists

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Application> getApplicationsBySeeker(int seekerId) {

        List<Application> list = new ArrayList<>();

        String sql =
                "SELECT a.application_id, a.status, a.applied_date, " +
                "j.job_id, j.title, j.location, e.company_name " +
                "FROM applications a " +
                "JOIN job_listings j ON a.job_id = j.job_id " +
                "JOIN employer_profiles e ON j.employer_id = e.employer_id " +
                "WHERE a.seeker_id = ? " +
                "ORDER BY a.applied_date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, seekerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Application app = new Application();
                app.setApplicationId(rs.getInt("application_id"));
                app.setJobId(rs.getInt("job_id"));
                app.setJobTitle(rs.getString("title"));
                app.setCompanyName(rs.getString("company_name"));
                app.setLocation(rs.getString("location"));
                app.setStatus(rs.getString("status"));
                app.setAppliedDate(rs.getTimestamp("applied_date"));

                list.add(app);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public boolean withdrawApplication(int applicationId, String reason) {

        String sql = "UPDATE applications SET status = ?, withdraw_reason = ? WHERE application_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "WITHDRAWN");
            ps.setString(2, reason);
            ps.setInt(3, applicationId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }



}
