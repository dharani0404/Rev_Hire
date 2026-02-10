package com.revhire.ui;

import com.revhire.model.Applicant;
import com.revhire.model.EmployerProfile;
import com.revhire.model.JobListing;
import com.revhire.model.Notification;
import com.revhire.service.ApplicantService;
import com.revhire.service.AuthService;
import com.revhire.service.EmployerProfileService;
import com.revhire.service.JobListingService;
import com.revhire.service.NotificationService;

//import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class EmployerUI {

    private static EmployerProfileService service = new EmployerProfileService();
    private static AuthService authService = new AuthService();
    private static JobListingService jobService = new JobListingService();
    private static ApplicantService applicantService = new ApplicantService();
    private static NotificationService notificationService = new NotificationService();



    public static void showEmployerDashboard(int userId) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n====== Employer Dashboard ======");
            System.out.println("1. Post a Job");
            System.out.println("2. Manage Job Postings");
            System.out.println("3. Manage Applicants");
            System.out.println("4. Manage Company Profile");
            System.out.println("5. Manage Notifications");
            System.out.println("6. Change Password");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
            	case 1:
            		postJob(userId);
            		break;
            	case 2:
            	    showManageJobsMenu(userId);
            	    break;
            	case 3:
            	    viewApplicants(userId);
            	    break;

                case 4:
                    showCompanyProfileMenu(userId);
                    break;
                case 5:
                    showNotificationsMenu(userId);
                    break;


                case 6:
                    changePassword(userId);
                    break;

                case 0:
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Feature coming soon...");
            }
        }
    }

    private static void showCompanyProfileMenu(int userId) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n============= Company Profile =============");
            System.out.println("1. Add Company Profile");
            System.out.println("2. Update Company Profile");
            System.out.println("3. View Company Profile");
            System.out.println("0. Back to Employer Dashboard");
            System.out.print("Choose: ");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1:
                    addProfile(userId, sc);
                    break;
                case 2:
                    updateProfile(userId, sc);
                    break;
                case 3:
                    viewProfile(userId);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private static void addProfile(int userId, Scanner sc) {
        EmployerProfile existing = service.getProfile(userId);
        if (existing != null) {
            System.out.println("Company profile already exists. Please use Update option.");
            return;
        }

        EmployerProfile p = new EmployerProfile();
        p.setUserId(userId);

        System.out.print("Company Name: ");
        p.setCompanyName(sc.nextLine());

        System.out.print("Industry: ");
        p.setIndustry(sc.nextLine());

        System.out.print("Company Size: ");
        p.setCompanySize(Integer.parseInt(sc.nextLine()));

        System.out.print("Location: ");
        p.setLocation(sc.nextLine());

        System.out.print("Website: ");
        p.setWebsite(sc.nextLine());

        System.out.print("Description: ");
        p.setDescription(sc.nextLine());

        if (service.addProfile(p)) {
            System.out.println("Company Profile Added Successfully!");
        } else {
            System.out.println("Failed to add Company Profile.");
        }
    }


    private static void updateProfile(int userId, Scanner sc) {
        EmployerProfile p = service.getProfile(userId);

        if (p == null) {
            System.out.println("No company profile found. Please add profile first.");
            return;
        }

        //try {
            System.out.print("Company Name: ");
//            p.setCompanyName(sc.nextLine());
            String name = sc.nextLine().trim();
            if (!name.isEmpty()) {
                p.setCompanyName(name);
            }

            System.out.print("Industry: ");
//            p.setIndustry(sc.nextLine());
            String industry = sc.nextLine().trim();
            if (!industry.isEmpty()) {
                p.setIndustry(industry);
            }

            System.out.print("Company Size (" + p.getCompanySize() + "): ");
//            int size = Integer.parseInt(sc.nextLine());   // safer than sc.nextInt()
//            p.setCompanySize(size);
            String sizeInput = sc.nextLine().trim();
            if (!sizeInput.isEmpty()) {
                if (!sizeInput.matches("\\d+")) {
                    System.out.println("Company Size must be a number.");
                    return;
                }
                int size = Integer.parseInt(sizeInput);
                if (size <= 0) {
                    System.out.println("Company Size must be greater than 0.");
                    return;
                }
                p.setCompanySize(size);
            }

            System.out.print("Location (" + p.getLocation() + "): ");
//            p.setLocation(sc.nextLine());
            String location = sc.nextLine().trim();
            if (!location.isEmpty()) {
                // Validation: location should not be only numbers
                if (location.matches("\\d+")) {
                    System.out.println("Location must be a valid city name (not numbers).");
                    return;
                }
                p.setLocation(location);
            }

            System.out.print("Website (" + p.getWebsite() + "): ");
//            p.setWebsite(sc.nextLine());
            String website = sc.nextLine().trim();
            if (!website.isEmpty()) {
                if (!website.startsWith("http://") && !website.startsWith("https://")) {
                    System.out.println("Website must start with http:// or https://");
                    return;
                }
                p.setWebsite(website);
            }

            System.out.print("Description (" + p.getDescription() + "): ");
//            p.setDescription(sc.nextLine());
            String desc = sc.nextLine().trim();
            if (!desc.isEmpty()) {
                p.setDescription(desc);
            }

            boolean updated = service.updateProfile(p);

            if (updated) {
                System.out.println("Company Profile Updated Successfully!");
            } else {
                System.out.println("Failed to update Company Profile. Please try again.");
            }

        //} 
//        catch (NumberFormatException e) {
//            System.out.println("Invalid input for Company Size. Please enter a number (e.g., 50, 100).");
//        } 
//        catch (Exception e) {
//            System.out.println("Something went wrong while updating the profile.");
//            e.printStackTrace();
//        }
    }


    private static void viewProfile(int userId) {
        EmployerProfile p = service.getProfile(userId);

        if (p == null) {
            System.out.println("No company profile found.");
            return;
        }

        System.out.println("\n--- Company Profile ---");
        System.out.println("Company Name : " + p.getCompanyName());
        System.out.println("Industry     : " + p.getIndustry());
        System.out.println("Company Size : " + p.getCompanySize());
        System.out.println("Location     : " + p.getLocation());
        System.out.println("Website      : " + p.getWebsite());
        System.out.println("Description  : " + p.getDescription());
    }
    private static void changePassword(int userId) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n====== Change Password ======");
        System.out.print("Enter Old Password: ");
        String oldPwd = sc.nextLine();

        System.out.print("Enter New Password: ");
        String newPwd = sc.nextLine();

        System.out.print("Confirm New Password: ");
        String confirmPwd = sc.nextLine();

        if (!newPwd.equals(confirmPwd)) {
            System.out.println("New password and Confirm password do not match.");
            return;
        }

        boolean updated = authService.changePassword(userId, oldPwd, newPwd);

        if (updated) {
            System.out.println("Password changed successfully!");
        } else {
            System.out.println("Old password is incorrect. Password not changed.");
        }
    }
    
//    private static void postJob(int userId) {
//        Scanner sc = new Scanner(System.in);
//
//        JobListing job = new JobListing();
//
//        // Get employer_id from employer_profiles using user_id
//        //int employerId = service.getProfile(userId).getEmployerId();
//        EmployerProfile profile = service.getProfile(userId);
//        if (profile == null) {
//            System.out.println("Please create Company Profile before posting a job.");
//            return;
//        }
//        int employerId = profile.getEmployerId();
//
//        job.setEmployerId(employerId);
//
//        System.out.println("\n====== Post a Job ======");
//
//        System.out.print("Job Title: ");
//        job.setTitle(sc.nextLine());
//
//        System.out.print("Job Description: ");
//        job.setDescription(sc.nextLine());
//
//        System.out.print("Skills Required (comma separated): ");
//        job.setSkillsRequired(sc.nextLine());
//
//        System.out.print("Experience Required (years): ");
//        job.setExperienceRequired(Double.parseDouble(sc.nextLine()));
//
//        System.out.print("Education: ");
//        job.setEducation(sc.nextLine());
//
//        System.out.print("Location: ");
//        job.setLocation(sc.nextLine());
//
//        System.out.print("Salary Min: ");
//        job.setSalaryMin(Double.parseDouble(sc.nextLine()));
//
//        System.out.print("Salary Max: ");
//        job.setSalaryMax(Double.parseDouble(sc.nextLine()));
//
//        System.out.print("Job Type (FULL_TIME / PART_TIME / INTERNSHIP / CONTRACT): ");
//        job.setJobType(sc.nextLine().toUpperCase());
//
//        System.out.print("Deadline (YYYY-MM-DD): ");
//        job.setDeadline(Date.valueOf(sc.nextLine()));
//
//        boolean success = jobService.postJob(job);
//
//        if (success) {
//            System.out.println("Job posted successfully!");
//        } else {
//            System.out.println("Failed to post job. Try again.");
//        }
//    }
    
    private static void postJob(int userId) {
        Scanner sc = new Scanner(System.in);

        JobListing job = new JobListing();

        EmployerProfile profile = service.getProfile(userId);
        if (profile == null) {
            System.out.println("Please create Company Profile before posting a job.");
            return;
        }

        int employerId = profile.getEmployerId();
        job.setEmployerId(employerId);

        System.out.println("\n====== Post a Job ======");

        // Job Title
        System.out.print("Job Title: ");
        String title = sc.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("Job Title cannot be empty.");
            return;
        }
        job.setTitle(title);

        // Description
        System.out.print("Job Description: ");
        String desc = sc.nextLine().trim();
        if (desc.isEmpty()) {
            System.out.println("Job Description cannot be empty.");
            return;
        }
        job.setDescription(desc);

        // Skills
        System.out.print("Skills Required (comma separated): ");
        String skills = sc.nextLine().trim();
        if (skills.isEmpty()) {
            System.out.println("Skills cannot be empty.");
            return;
        }
        job.setSkillsRequired(skills);

        // Experience
        System.out.print("Experience Required (years): ");
        String expInput = sc.nextLine().trim();
        double exp;
        try {
            exp = Double.parseDouble(expInput);
            if (exp < 0) {
                System.out.println("Experience cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Experience. Please enter a number (e.g., 0, 1, 2.5).");
            return;
        }
        job.setExperienceRequired(exp);

        // Education
        System.out.print("Education: ");
        String edu = sc.nextLine().trim();
        if (edu.isEmpty()) {
            System.out.println("Education cannot be empty.");
            return;
        }
        job.setEducation(edu);

        // Location
        System.out.print("Location: ");
        String location = sc.nextLine().trim();
        if (location.isEmpty()) {
            System.out.println("Location cannot be empty.");
            return;
        }
        if (location.matches("\\d+")) {
            System.out.println("Location must be a valid city name (not numbers).");
            return;
        }
        job.setLocation(location);

        // Salary Min
        System.out.print("Salary Min: ");
        String minInput = sc.nextLine().trim();
        double minSalary;
        try {
            minSalary = Double.parseDouble(minInput);
            if (minSalary < 0) {
                System.out.println("Salary Min cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Salary Min. Please enter a valid number.");
            return;
        }

        // Salary Max
        System.out.print("Salary Max: ");
        String maxInput = sc.nextLine().trim();
        double maxSalary;
        try {
            maxSalary = Double.parseDouble(maxInput);
            if (maxSalary < 0) {
                System.out.println("Salary Max cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Salary Max. Please enter a valid number.");
            return;
        }

        if (minSalary > maxSalary) {
            System.out.println("Salary Min cannot be greater than Salary Max.");
            return;
        }

        job.setSalaryMin(minSalary);
        job.setSalaryMax(maxSalary);

        // Job Type
        System.out.print("Job Type (FULL_TIME / PART_TIME / INTERNSHIP / CONTRACT): ");
        String type = sc.nextLine().trim().toUpperCase();

        if (!type.equals("FULL_TIME") && !type.equals("PART_TIME")
                && !type.equals("INTERNSHIP") && !type.equals("CONTRACT")) {
            System.out.println("Invalid Job Type. Choose from FULL_TIME, PART_TIME, INTERNSHIP, CONTRACT.");
            return;
        }
        job.setJobType(type);

        // Deadline
        System.out.print("Deadline (YYYY-MM-DD): ");
        String deadlineInput = sc.nextLine().trim();
        try {
            Date deadline = Date.valueOf(deadlineInput);

            if (deadline.before(new java.util.Date())) {
                System.out.println("Deadline must be a future date.");
                return;
            }

            job.setDeadline(deadline);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return;
        }

        boolean success = jobService.postJob(job);

        if (success) {
            System.out.println("Job posted successfully!");
        } else {
            System.out.println("Failed to post job. Try again.");
        }
    }

    
    private static void showManageJobsMenu(int userId) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n====== Manage Job Postings ======");
            System.out.println("1. View My Job Listings");
            System.out.println("2. Edit Job Posting");
            System.out.println("3. Close / Reopen Job Posting");
            System.out.println("4. Delete Job Posting");
            System.out.println("5. View Job Statistics");
            System.out.println("0. Back");
            System.out.print("Choose an option: ");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1:
                    viewMyJobs(userId);
                    break;
                case 2: // Manage Job Postings
                	editJobPosting(userId,sc);
                    break;
                case 3:
                    closeOrReopenJob(userId, sc);
                    break;
                case 4:
                    deleteJobPosting(userId);
                    break;
                case 5:
                    viewJobStatistics(userId);
                    break;


                case 0:
                    return;
                default:
                    System.out.println("Feature coming soon...");
            }
        }
    }
    
    private static void viewApplicants(int userId) {
        Scanner sc = new Scanner(System.in);

        EmployerProfile profile = service.getProfile(userId);
        if (profile == null) {
            System.out.println("Please create Company Profile first.");
            return;
        }

        int employerId = profile.getEmployerId();

        List<JobListing> jobs = jobService.getMyJobs(employerId);
        if (jobs.isEmpty()) {
            System.out.println("No job postings found.");
            return;
        }

        System.out.println("\n--- My Job Listings ---");
        for (JobListing job : jobs) {
            System.out.println(job.getJobId() + " - " + job.getTitle());
        }

        System.out.print("Enter Job ID: ");
        int jobId = sc.nextInt();
        sc.nextLine();

        while (true) {
            System.out.println("\n====== Manage Applicants ======");
            System.out.println("1. View Applicants");
            System.out.println("2. Filter Applicants");
            System.out.println("3. Shortlist Applicant");
            System.out.println("4. Reject Applicant");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1:
                    List<Applicant> list = applicantService.viewApplicants(jobId);
                    if (list.isEmpty()) {
                        System.out.println("No applicants found.");
                    } else {
                        for (Applicant a : list) {
                            System.out.println(
                                    a.getApplicationId() + " | " +
                                    a.getName() + " | " +
                                    a.getEmail() + " | " +
                                    a.getStatus()
                            );
                        }
                    }
                    break;

                case 2:
                    System.out.print("Skill: ");
                    String skill = sc.nextLine();
                    System.out.print("Min Experience: ");
                    double exp = sc.nextDouble();
                    sc.nextLine();

                    List<Applicant> filtered = applicantService.filterApplicants(jobId, skill, exp);
                    if (filtered.isEmpty()) {
                        System.out.println("No applicants match the filter criteria.");
                    } else {
                        for (Applicant a : filtered) {
                            System.out.println(
                                    a.getApplicationId() + " | " +
                                    a.getName() + " | " +
                                    a.getEmail() + " | " +
                                    a.getStatus()
                            );
                        }
                    }
                    break;

                case 3:
                    System.out.print("Application ID: ");
                    int shortlistId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Comment: ");
                    String shortlistComment = sc.nextLine();

                    boolean shortlisted = applicantService.shortlistApplicant(shortlistId, shortlistComment);
                    if (shortlisted) {
                        System.out.println("Applicant shortlisted successfully!");
                    } else {
                        System.out.println("Failed to shortlist applicant.");
                    }
                    break;

                case 4:
                    System.out.print("Application ID: ");
                    int rejectId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Comment: ");
                    String rejectComment = sc.nextLine();

                    boolean rejected = applicantService.rejectApplicant(rejectId, rejectComment);
                    if (rejected) {
                        System.out.println("Applicant rejected successfully!");
                    } else {
                        System.out.println("Failed to reject applicant.");
                    }
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }


    // in the above code for the case 1
    private static void viewMyJobs(int userId) {

        EmployerProfile profile = service.getProfile(userId);
        if (profile == null) {
            System.out.println("Please create Company Profile first.");
            return;
        }

        int employerId = profile.getEmployerId();  // Correct ID

        List<JobListing> jobs = jobService.getMyJobs(employerId);  // Pass employerId

        if (jobs.isEmpty()) {
            System.out.println("No job postings found.");
            return;
        }

        System.out.println("\n------ My Job Listings ------");
        System.out.printf("%-6s | %-25s | %-10s | %-6s | %-10s%n",
                "Job ID", "Title", "Location", "Status", "Deadline");
        System.out.println("---------------------------------------------------------------");

        for (JobListing job : jobs) {
            System.out.printf("%-6d | %-25s | %-10s | %-6s | %-10s%n",
                    job.getJobId(),
                    job.getTitle(),
                    job.getLocation(),
                    job.getStatus(),
                    job.getDeadline());
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("\nOptions:");
        System.out.println("1. View Job Details");
        System.out.println("0. Back");
        System.out.print("Choose: ");

        int opt = sc.nextInt();
        sc.nextLine();

        if (opt == 1) {
            System.out.print("Enter Job ID: ");
            int jobId = sc.nextInt();
            sc.nextLine();
            viewJobDetails(jobId);
        }
    }

    
    private static void viewJobDetails(int jobId) {
        JobListing job = jobService.getJobDetails(jobId);

        if (job == null) {
            System.out.println("Job not found.");
            return;
        }

        System.out.println("\n--- Job Details ---");
        System.out.println("Title        : " + job.getTitle());
        System.out.println("Location     : " + job.getLocation());
        System.out.println("Experience   : " + job.getExperienceRequired() + " years");
        System.out.println("Salary       : INR" + job.getSalaryMin() + " - INR" + job.getSalaryMax());
        System.out.println("Job Type     : " + job.getJobType());
        System.out.println("Status       : " + job.getStatus());
        System.out.println("Posted On    : " + job.getCreatedAt().toLocalDateTime().toLocalDate());
        System.out.println("Deadline     : " + job.getDeadline());
    }
// end of case 1

// for the case 2 Manage Job Postings
    private static void editJobPosting(int userId, Scanner sc) {

        EmployerProfile profile = service.getProfile(userId);
        if (profile == null) {
            System.out.println("Please create Company Profile first.");
            return;
        }

        int employerId = profile.getEmployerId();

        System.out.print("Enter Job ID to Edit: ");
        int jobId = Integer.parseInt(sc.nextLine());

        JobListing job = jobService.getJobById(jobId, employerId);

        if (job == null) {
            System.out.println("Job not found or you are not authorized to edit this job.");
            return;
        }

        System.out.println("\n--- Edit Job Posting (Leave blank to keep old value) ---");

        System.out.print("Title (" + job.getTitle() + "): ");
        String title = sc.nextLine();
        if (!title.isBlank()) job.setTitle(title);

        System.out.print("Description (" + job.getDescription() + "): ");
        String desc = sc.nextLine();
        if (!desc.isBlank()) job.setDescription(desc);

        System.out.print("Skills (" + job.getSkillsRequired() + "): ");
        String skills = sc.nextLine();
        if (!skills.isBlank()) job.setSkillsRequired(skills);

        System.out.print("Experience (" + job.getExperienceRequired() + "): ");
        String exp = sc.nextLine();
        if (!exp.isBlank()) job.setExperienceRequired(Double.parseDouble(exp));

        System.out.print("Education (" + job.getEducation() + "): ");
        String edu = sc.nextLine();
        if (!edu.isBlank()) job.setEducation(edu);

        System.out.print("Location (" + job.getLocation() + "): ");
        String loc = sc.nextLine();
        if (!loc.isBlank()) job.setLocation(loc);

        System.out.print("Salary Min (" + job.getSalaryMin() + "): ");
        String sMin = sc.nextLine();
        if (!sMin.isBlank()) job.setSalaryMin(Double.parseDouble(sMin));

        System.out.print("Salary Max (" + job.getSalaryMax() + "): ");
        String sMax = sc.nextLine();
        if (!sMax.isBlank()) job.setSalaryMax(Double.parseDouble(sMax));

        System.out.print("Job Type (" + job.getJobType() + "): ");
        String type = sc.nextLine();
        if (!type.isBlank()) job.setJobType(type.toUpperCase());

        System.out.print("Deadline (" + job.getDeadline() + "): ");
        String dl = sc.nextLine();
        if (!dl.isBlank()) job.setDeadline(java.sql.Date.valueOf(dl));

        boolean updated = jobService.updateJob(job);

        if (updated) {
            System.out.println("Job updated successfully!");
        } else {
            System.out.println("Failed to update job.");
        }
    }

    // for the case 3 in the Manage Job Postings
    private static void closeOrReopenJob(int userId, Scanner sc) {

        EmployerProfile profile = service.getProfile(userId);
        if (profile == null) {
            System.out.println("Please create Company Profile first.");
            return;
        }

        int employerId = profile.getEmployerId();

        System.out.print("Enter Job ID: ");
        int jobId = Integer.parseInt(sc.nextLine());

        JobListing job = jobService.getJobByIdAndEmployer(jobId, employerId);

        if (job == null) {
            System.out.println("Job not found or you are not authorized to update this job.");
            return;
        }

        System.out.println("Current Status: " + job.getStatus());

        String newStatus = job.getStatus().equalsIgnoreCase("OPEN") ? "CLOSED" : "OPEN";

        boolean updated = jobService.toggleJobStatus(jobId, employerId, newStatus);

        if (updated) {
            System.out.println("Job status updated successfully! New Status: " + newStatus);
        } else {
            System.out.println("Failed to update job status.");
        }
    }
    
    // for the case 4 Manage Job Postings
    private static void deleteJobPosting(int userId) {
        Scanner sc = new Scanner(System.in);

        EmployerProfile profile = service.getProfile(userId);
        if (profile == null) {
            System.out.println("Please create Company Profile first.");
            return;
        }

        int employerId = profile.getEmployerId();

        System.out.print("Enter Job ID to Delete: ");
        int jobId = Integer.parseInt(sc.nextLine());

        System.out.print("Are you sure you want to delete this job? (yes/no): ");
        String confirm = sc.nextLine();

        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Delete operation cancelled.");
            return;
        }

        boolean deleted = jobService.deleteJob(jobId, employerId);

        if (deleted) {
            System.out.println("Job deleted successfully!");
        } else {
            System.out.println("Failed to delete job. Either Job ID not found or you are not authorized.");
        }
    }

// for the case 5 Manage Job Postings
    private static void viewJobStatistics(int userId) {

        EmployerProfile profile = service.getProfile(userId);
        if (profile == null) {
            System.out.println("Please create Company Profile first.");
            return;
        }

        int employerId = profile.getEmployerId();

        int totalJobs = jobService.getTotalJobs(employerId);
        int openJobs = jobService.getOpenJobs(employerId);
        int closedJobs = jobService.getClosedJobs(employerId);
        int totalApplications = jobService.getTotalApplications(employerId);

        System.out.println("\n====== Job Statistics ======");
        System.out.println("Total Jobs Posted     : " + totalJobs);
        System.out.println("Open Jobs             : " + openJobs);
        System.out.println("Closed Jobs           : " + closedJobs);
        System.out.println("Total Applications    : " + totalApplications);

        System.out.println("\n------ Applications Per Job ------");
        System.out.printf("%-6s | %-25s | %-12s%n", "Job ID", "Title", "Applications");
        System.out.println("----------------------------------------------");

        List<Object[]> stats = jobService.getApplicationsPerJob(employerId);

        for (Object[] row : stats) {
            System.out.printf("%-6d | %-25s | %-12d%n",
                    (int) row[0],
                    (String) row[1],
                    (int) row[2]);
        }
    }
    
 // ================= Notifications UI =================

    private static void showNotificationsMenu(int userId) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n====== Notifications Menu ======");
            System.out.println("1. View Notifications");
            System.out.println("2. Mark Notification as Read");
            System.out.println("3. Delete Notification");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    viewNotifications(userId);
                    break;
                case 2:
                    markNotificationAsRead(sc);
                    break;
                case 3:
                    deleteNotification(sc);
                    break;
                case 0:
                    return;
            }
        }
    }

    private static void viewNotifications(int userId) {
        List<Notification> list = notificationService.viewNotifications(userId);

        System.out.println("\n--- Your Notifications ---");

        if (list.isEmpty()) {
            System.out.println("No notifications found.");
            return;
        }

        System.out.printf("%-5s | %-60s | %-6s | %-20s%n", "ID", "Message", "Read", "Date");
        System.out.println("----------------------------------------------------------------------------------");

        for (Notification n : list) {
            System.out.printf("%-5d | %-60s | %-6s | %-20s%n",
                    n.getNotificationId(),
                    n.getMessage(),
                    n.isRead() ? "Yes" : "No",
                    n.getCreatedAt());
        }
    }

    private static void markNotificationAsRead(Scanner sc) {
        System.out.print("Enter Notification ID to mark as read: ");
        int id = sc.nextInt();
        sc.nextLine();

        boolean updated = notificationService.markNotificationRead(id);

        if (updated) {
            System.out.println("Notification marked as read.");
        } else {
            System.out.println("Notification ID not found.");
        }
    }
    
    private static void deleteNotification(Scanner sc) {
        System.out.print("Enter Notification ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        boolean deleted = notificationService.removeNotification(id);

        if (deleted) {
            System.out.println("Notification deleted.");
        } else {
            System.out.println("Notification ID not found.");
        }
    }



} 



//package com.revhire.ui;   // this code for separate ui
//
//import com.revhire.model.*;
//import com.revhire.service.*;
//
//import java.sql.Date;
//import java.util.List;
//import java.util.Scanner;
//
//public class EmployerUI {
//
//    private static EmployerProfileService employerProfileService = new EmployerProfileService();
//    private static AuthService authService = new AuthService();
//    private static JobListingService jobService = new JobListingService();
//    private static ApplicantService applicantService = new ApplicantService();
//    private static NotificationService notificationService = new NotificationService();
//
//    public static void showEmployerDashboard(int userId) {
//        Scanner sc = new Scanner(System.in);
//
//        while (true) {
//            System.out.println("\n====== Employer Dashboard ======");
//            System.out.println("1. Post a Job");
//            System.out.println("2. Manage Job Postings");
//            System.out.println("3. Manage Applicants");
//            System.out.println("4. Manage Company Profile");
//            System.out.println("5. Manage Notifications");
//            System.out.println("6. Change Password");
//            System.out.println("0. Logout");
//            System.out.print("Choose: ");
//
//            int choice = sc.nextInt();
//            sc.nextLine();
//
//            switch (choice) {
//                case 1:
//                	postJob(userId, sc);
//                case 2:
//                	showManageJobsMenu(userId, sc);
//                case 3:
//                	viewApplicants(userId, sc);
//                case 4:
//                	showCompanyProfileMenu(userId, sc);
//                case 5:
//                	showNotificationsMenu(userId, sc);
//                case 6:
//                	changePassword(userId, sc);
//                case 0:
//                	{ return; }
//                default:
//                	System.out.println("Invalid choice.");
//            }
//        }
//    }
//
//    private static void showCompanyProfileMenu(int userId, Scanner sc) {
//        System.out.println("1. Add Profile");
//        System.out.println("2. Update Profile");
//        System.out.println("3. View Profile");
//
//        int ch = sc.nextInt();
//        sc.nextLine();
//
//        if (ch == 1) employerProfileService.addProfileUI(userId, sc);
//        else if (ch == 2) employerProfileService.updateProfileUI(userId, sc);
//        else if (ch == 3) employerProfileService.viewProfile(userId);
//    }
//
//    private static void postJob(int userId, Scanner sc) {
//        jobService.postJobUI(userId, sc);
//    }
//
//    private static void showManageJobsMenu(int userId, Scanner sc) {
//        System.out.println("1. View My Jobs");
//        System.out.println("2. Edit Job");
//        System.out.println("3. Close/Reopen Job");
//        System.out.println("4. Delete Job");
//        System.out.println("5. Job Statistics");
//
//        int ch = sc.nextInt();
//        sc.nextLine();
//
//        switch (ch) {
//            case 1:
//            	 jobService.viewMyJobs(userId);
//            case 2:
//            	jobService.editJob(userId, sc);
//            case 3:
//            	jobService.toggleJobStatus(userId, sc);
//            case 4:
//            	jobService.deleteJob(userId, sc);
//            case 5:
//            	jobService.viewJobStatistics(userId);
//        }
//    }
//
//    private static void viewApplicants(int userId, Scanner sc) {
//        applicantService.manageApplicantsUI(userId, sc);
//    }
//
//    private static void showNotificationsMenu(int userId, Scanner sc) {
//        System.out.println("1. View Notifications");
//        System.out.println("2. Mark as Read");
//        System.out.println("3. Delete Notification");
//
//        int ch = sc.nextInt();
//        sc.nextLine();
//
//        if (ch == 1) notificationService.viewNotifications(userId);
//        else if (ch == 2) notificationService.markAsReadUI(sc);
//        else if (ch == 3) notificationService.deleteNotificationUI(sc);
//    }
//
//    private static void changePassword(int userId, Scanner sc) {
//        System.out.print("Old Password: ");
//        String oldPwd = sc.nextLine();
//        System.out.print("New Password: ");
//        String newPwd = sc.nextLine();
//        System.out.print("Confirm Password: ");
//        String confirmPwd = sc.nextLine();
//
//        if (!newPwd.equals(confirmPwd)) {
//            System.out.println("Passwords do not match.");
//            return;
//        }
//
//        boolean success = authService.changePassword(userId, oldPwd, newPwd);
//        System.out.println(success ? "Password updated!" : "Old password incorrect.");
//    }
//}

