package com.revhire.model;

import java.util.List;

public class Resume {
    private int resumeId;
    private int seekerId;
    private String objective;

    private List<Education> educationList;
    private List<Experience> experienceList;
    private List<Skill> skills;
    private List<Project> projects;

    // Getters and setters
    public int getResumeId() { return resumeId; }
    public void setResumeId(int resumeId) { this.resumeId = resumeId; }

    public int getSeekerId() { return seekerId; }
    public void setSeekerId(int seekerId) { this.seekerId = seekerId; }

    public String getObjective() { return objective; }
    public void setObjective(String objective) { this.objective = objective; }

    public List<Education> getEducationList() { return educationList; }
    public void setEducationList(List<Education> educationList) { this.educationList = educationList; }

    public List<Experience> getExperienceList() { return experienceList; }
    public void setExperienceList(List<Experience> experienceList) { this.experienceList = experienceList; }

    public List<Skill> getSkills() { return skills; }
    public void setSkills(List<Skill> skills) { this.skills = skills; }

    public List<Project> getProjects() { return projects; }
    public void setProjects(List<Project> projects) { this.projects = projects; }
}
