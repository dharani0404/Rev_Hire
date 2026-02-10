package com.revhire.service;

import com.revhire.dao.ResumeDAO;
import com.revhire.dao.SkillsDAO;
import com.revhire.model.Resume;
import com.revhire.model.Skill;

import java.util.List;

public class SkillsService {
    private final SkillsDAO skillsDAO = new SkillsDAO();
    private final ResumeDAO resumeDAO = new ResumeDAO();

    public void addSkill(int seekerId, String skillName, String proficiency) {
        try {
            Resume resume = resumeDAO.getResumeBySeekerId(seekerId);
            JobSeekerService jobSeekerService = new JobSeekerService();
            if (resume == null) return;

            Skill skill = new Skill();
            skill.setResumeId(resume.getResumeId());
            skill.setSkillName(skillName);
            skill.setProficiency(proficiency);

            skillsDAO.addSkill(skill);
            
            jobSeekerService.refreshProfileCompletionStatus(seekerId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Skill> getSkills(int seekerId) {
        try {
            Resume resume = resumeDAO.getResumeBySeekerId(seekerId);
            if (resume == null) return null;
            return skillsDAO.getSkillsByResumeId(resume.getResumeId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
