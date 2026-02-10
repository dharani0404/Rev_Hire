package com.revhire.dao;

import com.revhire.config.DBConnection;
import com.revhire.model.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillsDAO {

    // Add skill
    public void addSkill(Skill skill) throws Exception {
        String sql = "INSERT INTO skills (resume_id, skill_name, proficiency) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, skill.getResumeId());
            ps.setString(2, skill.getSkillName());
            ps.setString(3, skill.getProficiency());
            ps.executeUpdate();
        }
    }

    // Update skill
    public void updateSkill(Skill skill) throws Exception {
        String sql = "UPDATE skills SET skill_name=?, proficiency=? WHERE skill_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, skill.getSkillName());
            ps.setString(2, skill.getProficiency());
            ps.setInt(3, skill.getSkillId());
            ps.executeUpdate();
        }
    }

    // Delete skill
    public void deleteSkill(int skillId) throws Exception {
        String sql = "DELETE FROM skills WHERE skill_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, skillId);
            ps.executeUpdate();
        }
    }

    // Get all skills by resumeId
    public List<Skill> getSkillsByResumeId(int resumeId) throws Exception {
        List<Skill> list = new ArrayList<>();
        String sql = "SELECT * FROM skills WHERE resume_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, resumeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Skill skill = new Skill();
                skill.setSkillId(rs.getInt("skill_id"));
                skill.setResumeId(rs.getInt("resume_id"));
                skill.setSkillName(rs.getString("skill_name"));
                skill.setProficiency(rs.getString("proficiency"));
                list.add(skill);
            }
        }
        return list;
    }
}
