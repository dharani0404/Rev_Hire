package com.revhire.model;

import java.time.LocalDate;

public class JobApplication {
    private int id;
    private int jobId;
    private int seekerId;
    private String resumePath;
    private String coverLetter;
    private LocalDate appliedDate;

    // Constructors
    public JobApplication() {}

    public JobApplication(int id, int jobId, int seekerId, String resumePath, String coverLetter, LocalDate appliedDate) {
        this.id = id;
        this.jobId = jobId;
        this.seekerId = seekerId;
        this.resumePath = resumePath;
        this.coverLetter = coverLetter;
        this.appliedDate = appliedDate;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getJobId() { return jobId; }
    public void setJobId(int jobId) { this.jobId = jobId; }

    public int getSeekerId() { return seekerId; }
    public void setSeekerId(int seekerId) { this.seekerId = seekerId; }

    public String getResumePath() { return resumePath; }
    public void setResumePath(String resumePath) { this.resumePath = resumePath; }

    public String getCoverLetter() { return coverLetter; }
    public void setCoverLetter(String coverLetter) { this.coverLetter = coverLetter; }

    public LocalDate getAppliedDate() { return appliedDate; }
    public void setAppliedDate(LocalDate appliedDate) { this.appliedDate = appliedDate; }

    @Override
    public String toString() {
        return "JobApplication [id=" + id + ", jobId=" + jobId + ", seekerId=" + seekerId
                + ", resumePath=" + resumePath + ", coverLetter=" + coverLetter
                + ", appliedDate=" + appliedDate + "]";
    }
}
