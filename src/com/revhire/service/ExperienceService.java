package com.revhire.service;

import com.revhire.dao.ExperienceDAO;
import com.revhire.dao.ResumeDAO;
import com.revhire.model.Experience;
import com.revhire.model.Resume;

import java.util.List;

public class ExperienceService {
    private final ExperienceDAO experienceDAO = new ExperienceDAO();
    private final ResumeDAO resumeDAO = new ResumeDAO();

    public void addExperience(int seekerId, String title, String company, String start, String end) {
        try {
            Resume resume = resumeDAO.getResumeBySeekerId(seekerId);
            JobSeekerService jobSeekerService = new JobSeekerService();
            if (resume == null) return;

            Experience exp = new Experience();
            exp.setResumeId(resume.getResumeId());
            exp.setJobTitle(title);
            exp.setCompany(company);
            exp.setStartDate(start);
            exp.setEndDate(end);

            experienceDAO.addExperience(exp);
            jobSeekerService.refreshProfileCompletionStatus(seekerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Experience> getExperience(int seekerId) {
        try {
            Resume resume = resumeDAO.getResumeBySeekerId(seekerId);
            if (resume == null) return null;
            return experienceDAO.getExperienceByResumeId(resume.getResumeId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
