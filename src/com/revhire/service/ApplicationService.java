package com.revhire.service;

import com.revhire.dao.ApplicationDAO;
import com.revhire.model.Application;

import java.util.List;

public class ApplicationService {

    private ApplicationDAO dao = new ApplicationDAO();

    public List<Application> viewApplicants(int jobId) {
        return dao.getApplicantsByJob(jobId);
    }

    public boolean shortlist(int appId, String comment) {
        return dao.updateStatus(appId, "SHORTLISTED", comment);
    }

    public boolean reject(int appId, String comment) {
        return dao.updateStatus(appId, "REJECTED", comment);
    }

    public List<Application> filterApplicants(int jobId, String skill, double exp) {
        return dao.filterApplicants(jobId, skill, exp);
    }
    
//    public boolean applyForJob(int userId, int jobId, String coverLetter) {
//        return dao.applyForJob(userId, jobId, coverLetter);
//    }
    
    // job seeker
    public boolean applyForJob(int jobId, int seekerId, int resumeId, String coverLetter) {
        return dao.applyForJob(jobId, seekerId, resumeId, coverLetter);
    }
    
    public boolean hasAlreadyApplied(int jobId, int seekerId) {
        return dao.hasAlreadyApplied(jobId, seekerId);
    }
    
    public List<Application> getMyApplications(int seekerId) {
        return dao.getApplicationsBySeeker(seekerId);
    }
    
    public boolean withdrawMyApplication(int applicationId, String reason) {
        return dao.withdrawApplication(applicationId, reason);
    }



}
