package com.revhire.ui;

import com.revhire.model.JobListing;
import com.revhire.model.JobSeekerProfile;
import com.revhire.service.ApplicationService;
import com.revhire.service.JobListingService;
import com.revhire.service.JobSeekerService;

import java.util.List;
import java.util.Scanner;

public class JobSearchUI {

    private JobListingService jobService = new JobListingService();
    private ApplicationService applicationService = new ApplicationService();
    private JobSeekerService jobSeekerService = new JobSeekerService();

    private Scanner sc = new Scanner(System.in);
    
    private int seekerId;
    private int resumeId;
    
    public JobSearchUI(int seekerId, int resumeId) {
        this.seekerId = seekerId;
        this.resumeId = resumeId;
    }

    public void showJobSearchMenu() {
        while (true) {
            System.out.println("\n====== Job Search ======");
            System.out.println("1. View All Jobs");
            System.out.println("2. Search Job");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    viewAllJobs();
                    break;
                case 2:
                    advancedSearch();
                    break;

                case 0:
                    return;
                default:
                    System.out.println("Feature coming soon...");
            }
        }
    }

    private void viewAllJobs() {
        List<JobListing> jobs = jobService.fetchAllOpenJobs();

        System.out.println("\n--- Available Jobs ---");

        if (jobs.isEmpty()) {
            System.out.println("No jobs available right now.");
            return;
        }

        int index = 1;
        for (JobListing job : jobs) {
            System.out.println(index++ + ". " +
                    job.getTitle() + " | " +
                    job.getCompanyName() + " | " +
                    job.getLocation() + " | ₹" +
                    job.getSalaryMin() + " - ₹" + job.getSalaryMax());
        }

        System.out.print("\nEnter job number to view details or 0 to go back: ");
        int opt = Integer.parseInt(sc.nextLine());

        if (opt > 0 && opt <= jobs.size()) {
            JobListing selected = jobs.get(opt - 1);
            viewJobDetails(selected.getJobId());
        }
    }

    private void viewJobDetails(int jobId) {

        //JobListing job = jobService.getJobDetails(jobId); this is giving null value for the company
        JobListing job = jobService.getJobDetailsForJobSeeker(jobId);


        System.out.println("\n--- Job Details ---");
        System.out.println("Role       : " + job.getTitle());
        System.out.println("Company    : " + job.getCompanyName());
        System.out.println("Location   : " + job.getLocation());
        System.out.println("Experience : " + job.getExperienceRequired() + " years");
        System.out.println("Salary     : ₹" + job.getSalaryMin() + " - ₹" + job.getSalaryMax());
        System.out.println("Job Type   : " + job.getJobType());
        System.out.println("Description: " + job.getDescription());

        System.out.println("\n1. Apply for this Job");
        System.out.println("0. Back");
        System.out.print("Choose: ");

        int ch = Integer.parseInt(sc.nextLine());

//        if (ch == 1) {
//            applyForJob(jobId);
//        }
        switch (ch) {
        case 1:
            applyForJob(jobId);
            System.out.println("\nPress Enter to continue...");
            sc.nextLine();

            // Go back to Available Jobs (NOT Job Search menu)
            viewAllJobs();
            break;

        case 0:
            // Go back to Available Jobs
            viewAllJobs();
            break;

        default:
            System.out.println("Invalid choice.");
            viewAllJobs();
    }
    }
    
    private void applyForJob(int jobId) {
    	
    	JobSeekerProfile profile = null;
        try {
            profile = jobSeekerService.viewProfile(seekerId);
        } catch (Exception e) {
            System.out.println("Error fetching profile: " + e.getMessage());
            return; // exit if profile can't be fetched
        }

        if (profile == null || !profile.isProfileComplete()) {
            System.out.println(" Complete your profile before applying for jobs.");
            return;
        }
        
    	boolean alreadyApplied = applicationService.hasAlreadyApplied(jobId, seekerId);
    	
    	if (alreadyApplied) {
            System.out.println("You have already applied for this job.");
            return;
        }
        System.out.println("\n====== Apply for Job ======");
        System.out.println("Enter your cover letter:");
        String coverLetter = sc.nextLine();

        System.out.print("\nConfirm application? (Y/N): ");
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("Y")) {
            boolean applied = applicationService.applyForJob(jobId, seekerId, resumeId, coverLetter);

            if (applied) {
                System.out.println("\nApplication Submitted Successfully!");
            } else {
                System.out.println("\nFailed to submit application.");
            }
        }

    }
    
    private void advancedSearch() {

        System.out.println("\n====== Job Search ======");

        System.out.print("Role (leave blank to skip): ");
        String role = sc.nextLine();

        System.out.print("Location (leave blank to skip): ");
        String location = sc.nextLine();

        System.out.print("Minimum Experience (years, blank to skip): ");
        String expInput = sc.nextLine();
        Double minExp = expInput.isBlank() ? null : Double.parseDouble(expInput);

        System.out.print("Minimum Salary (blank to skip): ");
        String minSalInput = sc.nextLine();
        Double minSalary = minSalInput.isBlank() ? null : Double.parseDouble(minSalInput);

        System.out.print("Maximum Salary (blank to skip): ");
        String maxSalInput = sc.nextLine();
        Double maxSalary = maxSalInput.isBlank() ? null : Double.parseDouble(maxSalInput);

        System.out.print("Company Name (leave blank to skip): ");
        String company = sc.nextLine();

        System.out.print("Job Type (FULL_TIME / PART_TIME / INTERNSHIP / CONTRACT, blank to skip): ");
        String jobType = sc.nextLine();

        List<JobListing> jobs = jobService.searchJobs(role, location, minExp, minSalary, maxSalary, company, jobType);

        System.out.println("\n--- Filtered Jobs ---");

        if (jobs.isEmpty()) {
            System.out.println("No jobs found for given filters.");
            return;
        }

        int i = 1;
        for (JobListing job : jobs) {
            System.out.println(i++ + ". " +
                    job.getTitle() + " | " +
                    job.getCompanyName() + " | " +
                    job.getLocation() + " | ₹" +
                    job.getSalaryMin() + " - ₹" + job.getSalaryMax() +
                    " | " + job.getJobType());
        }
    }

}
