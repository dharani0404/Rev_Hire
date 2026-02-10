package com.revhire.service;

import com.revhire.dao.EducationDAO;
import com.revhire.dao.ResumeDAO;
import com.revhire.model.Education;
import com.revhire.model.Resume;

import java.util.List;

public class EducationService {
    private final EducationDAO educationDAO = new EducationDAO();
    private final ResumeDAO resumeDAO = new ResumeDAO();

    // Add education
    public void addEducation(int seekerId, String degree, String institution, String start, String end) {
        try {
            Resume resume = resumeDAO.getResumeBySeekerId(seekerId);
            JobSeekerService jobSeekerService = new JobSeekerService();
            if (resume == null) {
                System.out.println("No resume found for this seeker. Create a resume first.");
                return;
            }

            Education edu = new Education();
            edu.setResumeId(resume.getResumeId());
            edu.setDegree(degree);
            edu.setInstitution(institution);
            edu.setStartDate(start);
            edu.setEndDate(end);

            educationDAO.addEducation(edu);
            
            jobSeekerService.refreshProfileCompletionStatus(seekerId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all education for a resume
    public List<Education> getEducation(int seekerId) {
        try {
            Resume resume = resumeDAO.getResumeBySeekerId(seekerId);
            if (resume == null) return null;
            return educationDAO.getEducationByResumeId(resume.getResumeId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
