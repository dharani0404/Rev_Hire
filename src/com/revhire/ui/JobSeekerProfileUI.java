package com.revhire.ui;

import com.revhire.model.User;
import com.revhire.model.Application;
import com.revhire.model.JobSeekerProfile;
import com.revhire.model.Notification;
import com.revhire.service.ApplicationService;
import com.revhire.service.AuthService;
import com.revhire.service.JobSeekerService;
import com.revhire.service.NotificationService;
import com.revhire.model.Resume;
import com.revhire.ui.JobSearchUI;
import com.revhire.service.ResumeService;

import java.util.List;
import java.util.Scanner;

public class JobSeekerProfileUI {

    private final JobSeekerService jobSeekerService = new JobSeekerService();
    private final ResumeService resumeService = new ResumeService();
    private final Scanner sc = new Scanner(System.in);

    public void showDashboard(User loggedInUser) {
        while (true) {
        	JobSeekerProfile profile = null;

            try {
                profile = jobSeekerService.viewProfile(loggedInUser.getUserId());
            } catch (Exception e) {
                System.out.println("Error loading profile.");
                e.printStackTrace();
            }
            System.out.println("====== Job Seeker Dashboard ======");
            
            if (profile != null) {
                if (profile.isProfileComplete()) {
                    System.out.println("Profile Status: Completed");
                } else {
                    System.out.println("Profile Status:  Incomplete (Complete your resume sections)");
                }
            } else {
                System.out.println("Profile Status:  Not Created");
            }
            System.out.println("1. Manage Profile");
            System.out.println("2. Create or Update Resume");
            System.out.println("3. Manage Job Search"); 
            System.out.println("4. View My Applications"); 
            System.out.println("5. View Notifications");
            System.out.println("6. Change Password");
            System.out.println("0. Logout");
            System.out.print("Choose: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    manageProfile(loggedInUser);   // EXACT CALL
                    break;
                    
                case 2:
                    try {
                        JobSeekerProfile seekerProfile = jobSeekerService.viewProfile(loggedInUser.getUserId());

                        if (seekerProfile == null) {
                            System.out.println("Please create your profile before creating a resume.");
                        } else {
                            ResumeUI resumeUI = new ResumeUI();
                            resumeUI.show(loggedInUser, seekerProfile.getSeekerId());
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        JobSeekerProfile seekerProfile = jobSeekerService.viewProfile(loggedInUser.getUserId());

                        if (seekerProfile == null) {
                            System.out.println("Please create your profile before searching for jobs.");
                            break;
                        }

                        Resume resume = resumeService.getResumeBySeekerId(seekerProfile.getSeekerId());

                        if (resume == null) {
                            System.out.println("Please create your resume before applying for jobs.");
                            break;
                        }

                        JobSearchUI jobSearchUI = new JobSearchUI(seekerProfile.getSeekerId(), resume.getResumeId());
                        jobSearchUI.showJobSearchMenu();

                    } catch (Exception e) {
                        System.out.println("Error opening Job Search: " + e.getMessage());
                    }
                    break;

                case 4:
                    viewMyApplications(loggedInUser);
                    break;

                case 5:
                    viewNotifications(loggedInUser.getUserId());
                    break;
                case 6:
                    changePassword(loggedInUser.getUserId());
                    break;


                case 0:
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void manageProfile(User user) {
        try {
            JobSeekerProfile profile = jobSeekerService.viewProfile(user.getUserId());

            if (profile == null) {
                System.out.println("No profile found. Create new profile.");
                JobSeekerProfile newProfile = takeInput(user.getUserId());
                jobSeekerService.createOrUpdateProfile(newProfile);
                System.out.println("Profile created successfully.");
            } else {
                displayProfile(profile);
                System.out.print("Do you want to update profile? (yes/no): ");
                String ch = sc.nextLine();
                if (ch.equalsIgnoreCase("yes")) {
                	System.out.println("All fields are mandatory for update. Please enter all details.");
                    JobSeekerProfile updated = takeInput(user.getUserId());
                    jobSeekerService.createOrUpdateProfile(updated);
                    System.out.println("Profile updated successfully.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private JobSeekerProfile takeInput(int userId) {
        JobSeekerProfile p = new JobSeekerProfile();
        p.setUserId(userId);

        System.out.print("Full Name: ");
        p.setFullName(sc.nextLine());

        System.out.print("Phone (10 digits): ");
        p.setPhone(sc.nextLine());

        System.out.print("Location: ");
        p.setLocation(sc.nextLine());

        System.out.print("Experience Years: ");
        p.setExperienceYears(Double.parseDouble(sc.nextLine()));

        return p;
    }

    private void displayProfile(JobSeekerProfile p) {
        System.out.println("Profile Details");
        System.out.println("Name: " + p.getFullName());
        System.out.println("Phone: " + p.getPhone());
        System.out.println("Location: " + p.getLocation());
        System.out.println("Experience Years: " + p.getExperienceYears());
    }
    
    private void viewMyApplications(User loggedInUser) {

        Scanner sc = new Scanner(System.in);

        try {
            JobSeekerProfile profile = jobSeekerService.viewProfile(loggedInUser.getUserId());

            if (profile == null) {
                System.out.println("Please create your job seeker profile first.");
                return;
            }

            int seekerId = profile.getSeekerId();

            ApplicationService applicationService = new ApplicationService();
            List<Application> list = applicationService.getMyApplications(seekerId);

            System.out.println("\n====== My Applications ======");

            if (list.isEmpty()) {
                System.out.println("You have not applied for any jobs yet.");
                System.out.println("Press Enter to continue...");
                sc.nextLine();
                return;
            }

            System.out.printf("%-7s | %-20s | %-15s | %-12s | %-12s%n",
                    "APP_ID", "Job Title", "Company", "Location", "Status");
            System.out.println("----------------------------------------------------------------------------");

            for (Application app : list) {
                System.out.printf("%-7d | %-20s | %-15s | %-12s | %-12s%n",
                        app.getApplicationId(),
                        app.getJobTitle(),
                        app.getCompanyName(),
                        app.getLocation(),
                        app.getStatus());
            }

            System.out.println("\nEnter Application ID to withdraw or 0 to go back:");
            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 0) return;

            // Withdraw flow
            System.out.print("Are you sure you want to withdraw this application? (yes/no): ");
            String confirm = sc.nextLine();

            if (!confirm.equalsIgnoreCase("yes")) {
                System.out.println("Withdrawal cancelled.");
                System.out.println("Press Enter to continue...");
                sc.nextLine();
                return;
            }

            System.out.print("Optional: Enter reason for withdrawal (or press Enter to skip): ");
            String reason = sc.nextLine();

            boolean success = applicationService.withdrawMyApplication(choice, reason);

            if (success) {
                System.out.println(" Application withdrawn successfully.");
            } else {
                System.out.println(" Failed to withdraw application.Please try again. ");
            }

            System.out.println("Press Enter to continue...");
            sc.nextLine();

        } catch (Exception e) {
            System.out.println("Error while fetching or withdrawing applications.");
            e.printStackTrace();
        }
    }
    
    private void viewNotifications(int userId) {

        NotificationService notificationService = new NotificationService();
        List<Notification> list = notificationService.viewNotifications(userId);

        System.out.println("\n====== Notifications ======");

        if (list.isEmpty()) {
            System.out.println("No notifications found.");
            return;
        }

        for (Notification n : list) {
            String status = n.isRead() ? "READ" : "UNREAD";
            System.out.println(n.getNotificationId() + ". " + n.getMessage() + " [" + status + "]");
        }

        System.out.println("\nEnter notification ID to mark as read or 0 to go back:");
        int choice = Integer.parseInt(new Scanner(System.in).nextLine());

        if (choice != 0) {
            notificationService.markNotificationRead(choice);
            System.out.println("Notification marked as read.");
        }

        System.out.println("\nPress Enter to continue...");
        new Scanner(System.in).nextLine();
    }
    
    private void changePassword(int userId) {
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

        AuthService authService = new AuthService();
        boolean updated = authService.changePassword(userId, oldPwd, newPwd);

        if (updated) {
            System.out.println("Password changed successfully!");
        } else {
            System.out.println("Old password is incorrect. Password not changed.");
        }

        System.out.println("\nPress Enter to continue...");
        sc.nextLine();
    }


}
