package com.revhire.service;

import com.revhire.dao.*;
import com.revhire.model.*;

import java.util.List;

public class ResumeService {

    private final ResumeDAO resumeDAO = new ResumeDAO();
    private final EducationDAO educationDAO = new EducationDAO();
    private final ExperienceDAO experienceDAO = new ExperienceDAO();
    private final SkillsDAO skillsDAO = new SkillsDAO();
    private final ProjectDAO projectDAO = new ProjectDAO();

    // ====================== OBJECTIVE ======================
    public void saveOrUpdateObjective(int seekerId, String objective) {
        try {
            Resume resume = resumeDAO.getResumeBySeekerId(seekerId);
            if (resume == null) {
                resume = new Resume();
                resume.setSeekerId(seekerId);
                resume.setObjective(objective);
                resumeDAO.createResume(resume);
            } else {
                resume.setObjective(objective);
                resumeDAO.updateResumeObjective(resume);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ====================== EDUCATION ======================
    public void addEducation(int seekerId, String degree, String institution, String start, String end) {
        try {
            Resume resume = resumeDAO.getResumeBySeekerId(seekerId);
            if (resume == null) return;

            Education edu = new Education();
            edu.setResumeId(resume.getResumeId());
            edu.setDegree(degree);
            edu.setInstitution(institution);
            edu.setStartDate(start);
            edu.setEndDate(end);

            educationDAO.addEducation(edu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateEducation(int educationId, String degree, String institution, String start, String end) {
        try {
            Education edu = new Education();
            edu.setEducationId(educationId);
            edu.setDegree(degree);
            edu.setInstitution(institution);
            edu.setStartDate(start);
            edu.setEndDate(end);
            educationDAO.updateEducation(edu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteEducation(int educationId) {
        try {
            educationDAO.deleteEducation(educationId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Education> getEducationList(int seekerId) {
        try {
            Resume resume = resumeDAO.getResumeBySeekerId(seekerId);
            if (resume == null) return List.of();
            return educationDAO.getEducationByResumeId(resume.getResumeId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    // ====================== EXPERIENCE ======================
    public void addExperience(int seekerId, String title, String company, String start, String end) {
        try {
            Resume resume = resumeDAO.getResumeBySeekerId(seekerId);
            if (resume == null) return;

            Experience exp = new Experience();
            exp.setResumeId(resume.getResumeId());
            exp.setJobTitle(title);
            exp.setCompany(company);
            exp.setStartDate(start);
            exp.setEndDate(end);
            experienceDAO.addExperience(exp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateExperience(int experienceId, String title, String company, String start, String end) {
        try {
            Experience exp = new Experience();
            exp.setExperienceId(experienceId);
            exp.setJobTitle(title);
            exp.setCompany(company);
            exp.setStartDate(start);
            exp.setEndDate(end);
            experienceDAO.updateExperience(exp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteExperience(int experienceId) {
        try {
            experienceDAO.deleteExperience(experienceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Experience> getExperienceList(int seekerId) {
        try {
            Resume resume = resumeDAO.getResumeBySeekerId(seekerId);
            if (resume == null) return List.of();
            return experienceDAO.getExperienceByResumeId(resume.getResumeId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    // ====================== SKILLS ======================
    public void addSkill(int seekerId, String skillName, String proficiency) {
        try {
            Resume resume = resumeDAO.getResumeBySeekerId(seekerId);
            if (resume == null) return;

            Skill skill = new Skill();
            skill.setResumeId(resume.getResumeId());
            skill.setSkillName(skillName);
            skill.setProficiency(proficiency);
            skillsDAO.addSkill(skill);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSkill(int skillId, String skillName, String proficiency) {
        try {
            Skill skill = new Skill();
            skill.setSkillId(skillId);
            skill.setSkillName(skillName);
            skill.setProficiency(proficiency);
            skillsDAO.updateSkill(skill);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSkill(int skillId) {
        try {
            skillsDAO.deleteSkill(skillId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Skill> getSkillsList(int seekerId) {
        try {
            Resume resume = resumeDAO.getResumeBySeekerId(seekerId);
            if (resume == null) return List.of();
            return skillsDAO.getSkillsByResumeId(resume.getResumeId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    // ====================== PROJECTS ======================
    public void addProject(int seekerId, String title, String desc, String tech, String start, String end) {
        try {
            Resume resume = resumeDAO.getResumeBySeekerId(seekerId);
            if (resume == null) return;

            Project proj = new Project();
            proj.setResumeId(resume.getResumeId());
            proj.setTitle(title);
            proj.setDescription(desc);
            proj.setTechnologies(tech);
            proj.setStartDate(start);
            proj.setEndDate(end);
            projectDAO.addProject(proj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProject(int projectId, String title, String desc, String tech, String start, String end) {
        try {
            Project proj = new Project();
            proj.setProjectId(projectId);
            proj.setTitle(title);
            proj.setDescription(desc);
            proj.setTechnologies(tech);
            proj.setStartDate(start);
            proj.setEndDate(end);
            projectDAO.updateProject(proj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProject(int projectId) {
        try {
            projectDAO.deleteProject(projectId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Project> getProjectsList(int seekerId) {
        try {
            Resume resume = resumeDAO.getResumeBySeekerId(seekerId);
            if (resume == null) return List.of();
            return projectDAO.getProjectsByResumeId(resume.getResumeId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    // ====================== GET FULL RESUME ======================
    public Resume getFullResume(int seekerId) {
        try {
            Resume resume = resumeDAO.getResumeBySeekerId(seekerId);
            if (resume != null) {
                resume.setEducationList(getEducationList(seekerId));
                resume.setExperienceList(getExperienceList(seekerId));
                resume.setSkills(getSkillsList(seekerId));
                resume.setProjects(getProjectsList(seekerId));
            }
            return resume;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Resume getResumeBySeekerId(int seekerId) {
        try {
            return resumeDAO.getResumeBySeekerId(seekerId);
        } catch (Exception e) {
            System.out.println("Error fetching resume: " + e.getMessage());
            return null;
        }
    }
}
