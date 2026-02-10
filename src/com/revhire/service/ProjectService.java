package com.revhire.service;

import com.revhire.dao.ProjectDAO;
import com.revhire.dao.ResumeDAO;
import com.revhire.model.Project;
import com.revhire.model.Resume;

import java.util.List;

public class ProjectService {
    private final ProjectDAO projectDAO = new ProjectDAO();
    private final ResumeDAO resumeDAO = new ResumeDAO();

    public void addProject(int seekerId, String title, String description, String technologies, String start, String end) {
        try {
            Resume resume = resumeDAO.getResumeBySeekerId(seekerId);
            JobSeekerService jobSeekerService = new JobSeekerService();
            if (resume == null) return;

            Project proj = new Project();
            proj.setResumeId(resume.getResumeId());
            proj.setTitle(title);
            proj.setDescription(description);
            proj.setTechnologies(technologies);
            proj.setStartDate(start);
            proj.setEndDate(end);

            projectDAO.addProject(proj);
            jobSeekerService.refreshProfileCompletionStatus(seekerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Project> getProjects(int seekerId) {
        try {
            Resume resume = resumeDAO.getResumeBySeekerId(seekerId);
            if (resume == null) return null;
            return projectDAO.getProjectsByResumeId(resume.getResumeId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
