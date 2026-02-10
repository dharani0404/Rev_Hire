package com.revhire.service;

import com.revhire.dao.JobSeekerDAO;
import com.revhire.model.JobSeekerProfile;

public class JobSeekerService {

    private final JobSeekerDAO dao = new JobSeekerDAO();

    public void createOrUpdateProfile(JobSeekerProfile profile) throws Exception {
        validate(profile);

        if (dao.profileExists(profile.getUserId())) {
            dao.updateProfile(profile);
        } else {
            dao.createProfile(profile);
        }
    }

    public JobSeekerProfile viewProfile(int userId) throws Exception {
        return dao.getProfileByUserId(userId);
    }

    private void validate(JobSeekerProfile p) {
        if (p.getFullName() == null || p.getFullName().trim().length() < 3) {
            throw new IllegalArgumentException("Full name must have at least 3 characters");
        }

        if (p.getPhone() == null || !p.getPhone().matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone number must contain exactly 10 digits");
        }

        if (p.getLocation() == null || p.getLocation().trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be empty");
        }

        if (p.getExperienceYears() < 0 || p.getExperienceYears() > 60) {
            throw new IllegalArgumentException("Experience years must be between 0 and 60");
        }
    }
    
    public boolean refreshProfileCompletionStatus(int seekerId) {
        boolean completed = dao.isProfileComplete(seekerId);
        dao.updateProfileCompletion(seekerId, completed);
        return completed;
    }

}
