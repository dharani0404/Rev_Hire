package com.revhire.service;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.*;

import com.revhire.model.JobListing;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JobListingServiceTest {

    private static JobListingService jobService;
    private static final int TEST_EMPLOYER_ID = 4;  // must exist in employer_profiles
    private static int CREATED_JOB_ID;

    @BeforeAll
    static void setup() {
        jobService = new JobListingService();
    }

    // 1. Create Job
    @Test
    @Order(1)
    void testPostJob() {
        JobListing job = new JobListing();
        job.setEmployerId(TEST_EMPLOYER_ID);
        job.setTitle("JUnit Test Developer");
        job.setDescription("Testing job posting module");
        job.setSkillsRequired("Java,JUnit");
        job.setExperienceRequired(1);
        job.setEducation("B.Tech");
        job.setLocation("Hyderabad");
        job.setSalaryMin(300000);
        job.setSalaryMax(500000);
        job.setJobType("FULL_TIME");
        job.setDeadline(Date.valueOf("2026-12-31"));

        boolean result = jobService.postJob(job);
        assertTrue(result, "Job creation should succeed");
    }

    // 2. Fetch My Jobs (get created jobId)
    @Test
    @Order(2)
    void testGetMyJobs() {
        List<JobListing> jobs = jobService.getMyJobs(TEST_EMPLOYER_ID);
        assertNotNull(jobs);
        assertFalse(jobs.isEmpty(), "Employer should have jobs");

        CREATED_JOB_ID = jobs.get(0).getJobId();
        assertTrue(CREATED_JOB_ID > 0);
    }

    // 3. Get Job Details
    @Test
    @Order(3)
    void testGetJobDetails() {
        JobListing job = jobService.getJobDetails(CREATED_JOB_ID);
        assertNotNull(job);
        assertEquals(CREATED_JOB_ID, job.getJobId());
    }

    // 4. Update Job
    @Test
    @Order(4)
    void testUpdateJob() {
        JobListing job = jobService.getJobByIdAndEmployer(CREATED_JOB_ID, TEST_EMPLOYER_ID);
        assertNotNull(job, "Job should belong to employer");

        job.setTitle("Updated JUnit Developer");
        boolean updated = jobService.updateJob(job);
        assertTrue(updated, "Job update should succeed");
    }

    // 5. Close Job
    @Test
    @Order(5)
    void testCloseJob() {
        boolean closed = jobService.toggleJobStatus(CREATED_JOB_ID, TEST_EMPLOYER_ID, "CLOSED");
        assertTrue(closed);
    }

    // 6. Reopen Job
    @Test
    @Order(6)
    void testReopenJob() {
        boolean reopened = jobService.toggleJobStatus(CREATED_JOB_ID, TEST_EMPLOYER_ID, "OPEN");
        assertTrue(reopened);

        JobListing job = jobService.getJobDetails(CREATED_JOB_ID);
        assertEquals("OPEN", job.getStatus());
    }

    // 7. Job Statistics
    @Test
    @Order(7)
    void testJobStatistics() {
        int totalJobs = jobService.getTotalJobs(TEST_EMPLOYER_ID);
        int openJobs = jobService.getOpenJobs(TEST_EMPLOYER_ID);
        int closedJobs = jobService.getClosedJobs(TEST_EMPLOYER_ID);

        assertTrue(totalJobs >= 1);
        assertTrue(openJobs >= 0);
        assertTrue(closedJobs >= 0);
    }

    // 8. Delete Job
    @Test
    @Order(8)
    void testDeleteJob() {
        boolean deleted = jobService.deleteJob(CREATED_JOB_ID, TEST_EMPLOYER_ID);
        assertTrue(deleted);

        JobListing job = jobService.getJobDetails(CREATED_JOB_ID);
        assertNull(job, "Job should be deleted");
    }
}
