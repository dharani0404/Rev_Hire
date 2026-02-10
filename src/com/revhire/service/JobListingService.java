package com.revhire.service;

import java.util.List;

import com.revhire.dao.JobListingDAO;
import com.revhire.model.JobListing;

public class JobListingService {

    private JobListingDAO dao = new JobListingDAO();

    public boolean postJob(JobListing job) {
        return dao.createJob(job);
    }

    public List<JobListing> getMyJobs(int employerId) {
        return dao.getJobsByEmployer(employerId);
    }
    
    public boolean updateJob(JobListing job) {
        return dao.updateJob(job);
    }

    public JobListing getJobDetails(int jobId) {
        return dao.getJobById(jobId);
    }

    public JobListing getJobById(int jobId, int employerId) {
        return dao.getJobById(jobId, employerId);
    }
    
    public JobListing getJobByIdAndEmployer(int jobId, int employerId) {
        return dao.getJobByIdAndEmployer(jobId, employerId);
    }

    public boolean toggleJobStatus(int jobId, int employerId, String newStatus) {
        return dao.toggleJobStatus(jobId, employerId, newStatus);
    }

    public boolean deleteJob(int jobId, int employerId) {
        return dao.deleteJob(jobId, employerId);
    }
    
    public int getTotalJobs(int employerId) {
        return dao.countJobsByEmployer(employerId);
    }

    public int getOpenJobs(int employerId) {
        return dao.countJobsByStatus(employerId, "OPEN");
    }

    public int getClosedJobs(int employerId) {
        return dao.countJobsByStatus(employerId, "CLOSED");
    }

    public int getTotalApplications(int employerId) {
        return dao.countTotalApplicationsForEmployer(employerId);
    }

    public List<Object[]> getApplicationsPerJob(int employerId) {
        return dao.getApplicationsCountPerJob(employerId);
    }
  //job seeker
    
   public List<JobListing> fetchAllOpenJobs() {
      return dao.getAllActiveJobs();
   }
   
//   public JobListing getJobDetails(int jobId) {
//	    return jobListingDAO.getJobDetailsById(jobId);
//	} // this method already declared in above 24 line
   
   public JobListing getJobDetailsForJobSeeker(int jobId) {
	    return dao.getJobDetailsForJobSeeker(jobId);
	}
   
   public List<JobListing> searchJobs(String role, String location, Double minExp,
           Double minSalary, Double maxSalary,
           String companyName, String jobType) {

	   	return dao.searchJobs(role, location, minExp, minSalary, maxSalary, companyName, jobType);
    }
   
   public void notifyJobSeekersForNewJob(String jobTitle, String location, List<Integer> seekerUserIds) {

	    NotificationService notificationService = new NotificationService();

	    for (int userId : seekerUserIds) {
	        notificationService.sendNotification(
	                userId,
	                "New job posted: " + jobTitle + " in " + location
	        );
	    }

    } 
}


