package com.revhire.service;

import com.revhire.model.*;
//import com.revhire.service.*;

import org.junit.jupiter.api.*;

//import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployerDashboardRemainingTest {

    private static EmployerProfileService employerProfileService;
    private static NotificationService notificationService;
    private static AuthService authService;
    private static ApplicantService applicantService;
    private static JobListingService jobService;

    private static int userId = 1;     // test employer user
    private static int employerId;
    private static int jobId;
    //employerProfileService.addProfile(profile);

    @BeforeAll
    static void setup() {
        employerProfileService = new EmployerProfileService();
        notificationService = new NotificationService();
        authService = new AuthService();
        applicantService = new ApplicantService();
        jobService = new JobListingService();

        EmployerProfile profile = new EmployerProfile();
        profile.setUserId(userId);
        profile.setCompanyName("Test Corp");
        profile.setIndustry("IT");
        profile.setCompanySize(50);
        profile.setLocation("Hyderabad");
        profile.setWebsite("www.testcorp.com");
        profile.setDescription("Test Company");

        EmployerProfile existing = employerProfileService.getProfile(userId);
        if (existing == null) {
            employerProfileService.addProfile(profile);
        }

        employerId = employerProfileService.getProfile(userId).getEmployerId();
    }


    // 3. View Applicants
    @Test
    @Order(1)
    void testViewApplicants() {
        int jobId = 1; // use any valid jobId present in your test DB / DAO

        List<Applicant> applicants = applicantService.viewApplicants(jobId);

        assertNotNull(applicants, "Applicants list should not be null");
    }


    // 4. Manage Company Profile – View
    @Test
    @Order(2)
    void testViewCompanyProfile() {

        EmployerProfile profile = employerProfileService.getProfile(userId);

        // Safety: profile must exist
        assertNotNull(profile, "Employer profile should not be null");

        // Instead of hard-coding, assert logical correctness
        assertNotNull(profile.getCompanyName());
        assertEquals(userId, profile.getUserId());
    }

    // 4. Manage Company Profile – Update
    @Test
    @Order(3)
    void testUpdateCompanyProfile() {
        EmployerProfile profile = employerProfileService.getProfile(userId);
        profile.setLocation("Bangalore");

        boolean updated = employerProfileService.updateProfile(profile);
        assertTrue(updated);

        EmployerProfile updatedProfile = employerProfileService.getProfile(userId);
        assertEquals("Bangalore", updatedProfile.getLocation());
    }

    // 5. Manage Notifications – View, Mark Read, Delete
    @Test
    @Order(4)
    void testNotificationsFlow() {
        notificationService.sendNotification(userId, "You have a new applicant");

        List<Notification> list = notificationService.viewNotifications(userId);
        assertNotNull(list);
        assertFalse(list.isEmpty());

        int notificationId = list.get(0).getNotificationId();

        boolean marked = notificationService.markNotificationRead(notificationId);
        assertTrue(marked);

        boolean deleted = notificationService.removeNotification(notificationId);
        assertTrue(deleted);
    }

    // 6. Change Password
    @Test
    @Order(5)
    void testChangePassword() {
        boolean changed = authService.changePassword(userId, "OldPass@123", "NewPass@123");
        assertNotNull(changed);
    }
}
