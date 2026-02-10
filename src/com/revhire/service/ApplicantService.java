package com.revhire.service;

import com.revhire.dao.ApplicantDAO;
import com.revhire.model.Applicant;

import java.util.List;

public class ApplicantService {

    private ApplicantDAO dao = new ApplicantDAO();

    public List<Applicant> viewApplicants(int jobId) {
        return dao.getApplicantsByJob(jobId);
    }

    public boolean shortlistApplicant(int applicationId, String comment) {
        return dao.updateStatus(applicationId, "SHORTLISTED", comment);
    }

    public boolean rejectApplicant(int applicationId, String comment) {
        return dao.updateStatus(applicationId, "REJECTED", comment);
    }

    public List<Applicant> filterApplicants(int jobId, String skill, double exp) {
        return dao.filterApplicants(jobId, skill, exp);
    }
}
