package com.revhire.model;

public class Education {
    private int educationId;
    private int resumeId;      // link to Resume
    private String degree;
    private String institution;
    private String startDate;
    private String endDate;

    // Getters and setters
    public int getEducationId() { return educationId; }
    public void setEducationId(int educationId) { this.educationId = educationId; }

    public int getResumeId() { return resumeId; }
    public void setResumeId(int resumeId) { this.resumeId = resumeId; }

    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }

    public String getInstitution() { return institution; }
    public void setInstitution(String institution) { this.institution = institution; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
}
