package com.revhire.dao;

import com.revhire.config.DBConnection;
import com.revhire.model.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {

    // Add project
    public void addProject(Project proj) throws Exception {
        String sql = "INSERT INTO projects (resume_id, title, description, technologies, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, proj.getResumeId());
            ps.setString(2, proj.getTitle());
            ps.setString(3, proj.getDescription());
            ps.setString(4, proj.getTechnologies());
            ps.setString(5, proj.getStartDate());
            ps.setString(6, proj.getEndDate());
            ps.executeUpdate();
        }
    }

    // Update project
    public void updateProject(Project proj) throws Exception {
        String sql = "UPDATE projects SET title=?, description=?, technologies=?, start_date=?, end_date=? WHERE project_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, proj.getTitle());
            ps.setString(2, proj.getDescription());
            ps.setString(3, proj.getTechnologies());
            ps.setString(4, proj.getStartDate());
            ps.setString(5, proj.getEndDate());
            ps.setInt(6, proj.getProjectId());
            ps.executeUpdate();
        }
    }

    // Delete project
    public void deleteProject(int projectId) throws Exception {
        String sql = "DELETE FROM projects WHERE project_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, projectId);
            ps.executeUpdate();
        }
    }

    // Get all projects by resumeId
    public List<Project> getProjectsByResumeId(int resumeId) throws Exception {
        List<Project> list = new ArrayList<>();
        String sql = "SELECT * FROM projects WHERE resume_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, resumeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Project proj = new Project();
                proj.setProjectId(rs.getInt("project_id"));
                proj.setResumeId(rs.getInt("resume_id"));
                proj.setTitle(rs.getString("title"));
                proj.setDescription(rs.getString("description"));
                proj.setTechnologies(rs.getString("technologies"));
                proj.setStartDate(rs.getString("start_date"));
                proj.setEndDate(rs.getString("end_date"));
                list.add(proj);
            }
        }
        return list;
    }
}
