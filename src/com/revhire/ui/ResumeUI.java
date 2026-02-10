package com.revhire.ui;

import com.revhire.model.Education;
import com.revhire.model.Experience;
import com.revhire.model.Project;
import com.revhire.model.Resume;
import com.revhire.model.Skill;
import com.revhire.model.User;
import com.revhire.service.ResumeService;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ResumeUI {

    private final ResumeService resumeService = new ResumeService();
    private final Scanner sc = new Scanner(System.in);

    public void show(User user, int seekerId) {
        while (true) {
            System.out.println("====== Resume Menu ======");
            System.out.println("1. Create or Update Objective");
            System.out.println("2. Manage Education");
            System.out.println("3. Manage Experience");
            System.out.println("4. Manage Skills");
            System.out.println("5. Manage Projects");
            System.out.println("6. View Resume");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    createOrUpdateObjective(seekerId);
                    break;
                case 2:
                    addEducation(seekerId);
                    break;
                case 3:
                    addExperience(seekerId);
                    break;
                case 4:
                    addSkill(seekerId);
                    break;
                case 5:
                    addProject(seekerId);
                    break;
                case 6:
                    viewResume(seekerId);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void createOrUpdateObjective(int seekerId) {
        System.out.print("Enter resume objective: ");
        String obj = sc.nextLine();

        resumeService.saveOrUpdateObjective(seekerId, obj);
        System.out.println("Resume objective saved successfully.");
    }
    /**
     the commented code for only add
     */
  // ============================ to add education ============================================
    private void addEducation(int seekerId) {
        while (true) {
            System.out.println("=== Education Menu ===");
            System.out.println("1. Add Education");
            System.out.println("2. Update Education");
            System.out.println("3. Delete Education");
            System.out.println("4. Back to Resume Menu");
            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
            case 1:
                addEducationEntry(seekerId);
                break;
            case 2:
                updateEducationEntry(seekerId);
                break;
            case 3:
                deleteEducationEntry(seekerId);
                break;
            case 4:
                return; // go back to Resume Menu
            default:
                System.out.println("Invalid choice");
        }
        }
    }

    // ------------------------ Add Education
    private void addEducationEntry(int seekerId) {
        System.out.print("Degree: ");
        String degree = sc.nextLine().trim();
        System.out.print("Institution: ");
        String institution = sc.nextLine().trim();
        System.out.print("Start Date (YYYY-MM-DD): ");
        String start = sc.nextLine().trim();
        System.out.print("End Date (YYYY-MM-DD): ");
        String end = sc.nextLine().trim();

        // Validate input
        if (degree.isEmpty() || institution.isEmpty() || start.isEmpty() || end.isEmpty()) {
            System.out.println("All fields are mandatory. Please enter valid details.");
            return; // stop execution, do not call service
        }

        // Optional: you can also validate date format here using regex
        if (!start.matches("\\d{4}-\\d{2}-\\d{2}") || !end.matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return;
        }

        // Call service to add education
        resumeService.addEducation(seekerId, degree, institution, start, end);
        System.out.println("Education added successfully.");
    }


    // ------------------------------ Update Education
    
    private void updateEducationEntry(int seekerId) {
        // Get all education entries
        java.util.List<Education> eduList = resumeService.getEducationList(seekerId);
        if (eduList.isEmpty()) {
            System.out.println("No education entries found.");
            return;
        }

        // Display all entries
        for (int i = 0; i < eduList.size(); i++) {
            Education e = eduList.get(i);
            System.out.println((i + 1) + ". " + e.getDegree() + " at " + e.getInstitution());
        }

        System.out.print("Select entry to update: ");
        int index = Integer.parseInt(sc.nextLine()) - 1;
        if (index < 0 || index >= eduList.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Education edu = eduList.get(index);

        // Prompt for new values, keep old values if input is empty
        System.out.print("New Degree (" + edu.getDegree() + "): ");
        String degree = sc.nextLine();
        if (degree.isEmpty()) degree = edu.getDegree();

        System.out.print("New Institution (" + edu.getInstitution() + "): ");
        String institution = sc.nextLine();
        if (institution.isEmpty()) institution = edu.getInstitution();

        System.out.print("New Start Date (" + edu.getStartDate() + "): ");
        String start = sc.nextLine();
        if (start.isEmpty()) start = edu.getStartDate();

        System.out.print("New End Date (" + edu.getEndDate() + "): ");
        String end = sc.nextLine();
        if (end.isEmpty()) end = edu.getEndDate();

        // Call service to update
        resumeService.updateEducation(edu.getEducationId(), degree, institution, start, end);
        System.out.println("Education updated successfully.");
    }


    // --------------------------------- Delete Education
    private void deleteEducationEntry(int seekerId) {
        java.util.List<Education> eduList = resumeService.getEducationList(seekerId);
        if (eduList.isEmpty()) {
            System.out.println("No education entries found.");
            return;
        }

        for (int i = 0; i < eduList.size(); i++) {
            Education e = eduList.get(i);
            System.out.println((i + 1) + ". " + e.getDegree() + " at " + e.getInstitution());
        }

        System.out.print("Select entry to delete: ");
        int index = Integer.parseInt(sc.nextLine()) - 1;
        if (index < 0 || index >= eduList.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        resumeService.deleteEducation(eduList.get(index).getEducationId());
        System.out.println("Education deleted successfully.");
    }

    //Experience for only to add
//    private void addExperience(int seekerId) {
//        System.out.print("Job Title: ");
//        String title = sc.nextLine();
//        System.out.print("Company: ");
//        String company = sc.nextLine();
//        System.out.print("Start Date (YYYY-MM-DD): ");
//        String start = sc.nextLine();
//        System.out.print("End Date (YYYY-MM-DD): ");
//        String end = sc.nextLine();
//
//        resumeService.addExperience(seekerId, title, company, start, end);
//        System.out.println("Experience added successfully.");
//    }
    // =============================================== EXPERIENCE MENU ==========================================================================
    private void addExperience(int seekerId) {
        while (true) {
            System.out.println("=== Experience Menu ===");
            System.out.println("1. Add Experience");
            System.out.println("2. Update Experience");
            System.out.println("3. Delete Experience");
            System.out.println("4. Back to Resume Menu");
            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1: 
                	addExperienceEntry(seekerId); 
                	break;
                case 2: 
                	updateExperienceEntry(seekerId); 
                	break;
                case 3: 
                	deleteExperienceEntry(seekerId); 
                	break;
                case 4: 
                	return;
                default: 
                	System.out.println("Invalid choice");
            }
        }
    }
    
    // --------------- add experience 
    private void addExperienceEntry(int seekerId) {
        System.out.print("Job Title: ");
        String title = sc.nextLine().trim();
        System.out.print("Company: ");
        String company = sc.nextLine().trim();
        System.out.print("Start Date (YYYY-MM-DD): ");
        String start = sc.nextLine().trim();
        System.out.print("End Date (YYYY-MM-DD): ");
        String end = sc.nextLine().trim();

        // Validate input
        if (title.isEmpty() || company.isEmpty() || start.isEmpty() || end.isEmpty()) {
            System.out.println("All fields are mandatory. Please enter valid details.");
            return; // Stop execution if any field is empty
        }

        // Validate date format
        if (!start.matches("\\d{4}-\\d{2}-\\d{2}") || !end.matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return;
        }

        // Call service to add experience
        resumeService.addExperience(seekerId, title, company, start, end);
        System.out.println("Experience added successfully.");
    }


   // ---------------------- update experience
    private void updateExperienceEntry(int seekerId) {
        List<Experience> expList = resumeService.getExperienceList(seekerId);
        if (expList.isEmpty()) {
            System.out.println("No experience entries found.");
            return;
        }

        // Display all experiences
        for (int i = 0; i < expList.size(); i++) {
            Experience ex = expList.get(i);
            System.out.println((i + 1) + ". " + ex.getJobTitle() + " at " + ex.getCompany());
        }

        System.out.print("Select entry to update: ");
        int index = Integer.parseInt(sc.nextLine()) - 1;
        if (index < 0 || index >= expList.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Experience ex = expList.get(index);

        // Prompt for new values, keep old values if input is empty
        System.out.print("New Job Title (" + ex.getJobTitle() + "): ");
        String title = sc.nextLine();
        if (title.isEmpty()) title = ex.getJobTitle();

        System.out.print("New Company (" + ex.getCompany() + "): ");
        String company = sc.nextLine();
        if (company.isEmpty()) company = ex.getCompany();

        System.out.print("New Start Date (" + ex.getStartDate() + "): ");
        String start = sc.nextLine();
        if (start.isEmpty()) start = ex.getStartDate();

        System.out.print("New End Date (" + ex.getEndDate() + "): ");
        String end = sc.nextLine();
        if (end.isEmpty()) end = ex.getEndDate();

        // Call service to update
        resumeService.updateExperience(ex.getExperienceId(), title, company, start, end);
        System.out.println("Experience updated successfully.");
    }


    private void deleteExperienceEntry(int seekerId) {
        List<Experience> expList = resumeService.getExperienceList(seekerId);
        if (expList.isEmpty()) return;
        for (int i = 0; i < expList.size(); i++) {
            Experience ex = expList.get(i);
            System.out.println((i + 1) + ". " + ex.getJobTitle() + " at " + ex.getCompany());
        }
        System.out.print("Select entry to delete: ");
        int index = Integer.parseInt(sc.nextLine()) - 1;
        if (index < 0 || index >= expList.size()) return;
        resumeService.deleteExperience(expList.get(index).getExperienceId());
        System.out.println("Experience deleted successfully.");
    }
    
    
 // ====================== SKILLS MENU ======================
    private void addSkill(int seekerId) {
        while (true) {
            System.out.println("=== Skills Menu ===");
            System.out.println("1. Add Skill");
            System.out.println("2. Update Skill");
            System.out.println("3. Delete Skill");
            System.out.println("4. Back to Resume Menu");
            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1: 
                	addSkillEntry(seekerId); 
                	break;
                case 2: 
                	updateSkillEntry(seekerId); 
                	break;
                case 3: 
                	deleteSkillEntry(seekerId); 
                	break;
                case 4: 
                	return;
                default: 
                	System.out.println("Invalid choice");
            }
        }
    }
    // ------------------------ add skill
    private void addSkillEntry(int seekerId) {
        System.out.print("Skill Name: ");
        String skill = sc.nextLine().trim();
        System.out.print("Proficiency(Beginner, Intermediate, or Expert.): ");
        String level = sc.nextLine().trim();

        // Validate input
        if (skill.isEmpty() || level.isEmpty()) {
            System.out.println("All fields are mandatory. Please enter valid details.");
            return; // Stop execution if any field is empty
        }

        // validate proficiency levels Beginner, Intermediate, Expert
        
         List<String> validLevels = Arrays.asList("Beginner", "Intermediate", "Expert");
         if (!validLevels.contains(level)) {
             System.out.println("Invalid proficiency level. Choose Beginner, Intermediate, or Expert.");
             return;
         }

        // Call service to add skill
        resumeService.addSkill(seekerId, skill, level);
        System.out.println("Skill added successfully.");
    }

    // --------------------- update skill
    private void updateSkillEntry(int seekerId) {
        List<Skill> skills = resumeService.getSkillsList(seekerId);
        if (skills.isEmpty()) {
            System.out.println("No skills found.");
            return;
        }

        // Display all skills
        for (int i = 0; i < skills.size(); i++) {
            Skill s = skills.get(i);
            System.out.println((i + 1) + ". " + s.getSkillName() + " - " + s.getProficiency());
        }

        System.out.print("Select entry to update: ");
        int index = Integer.parseInt(sc.nextLine()) - 1;
        if (index < 0 || index >= skills.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Skill s = skills.get(index);

        // Prompt for new values, keep old values if input is empty
        System.out.print("New Skill Name (" + s.getSkillName() + "): ");
        String skillName = sc.nextLine().trim();
        if (skillName.isEmpty()) skillName = s.getSkillName();

        System.out.print("New Proficiency (" + s.getProficiency() + "): ");
        String prof = sc.nextLine().trim();
        if (prof.isEmpty()) prof = s.getProficiency();

        // Validate proficiency levels if needed
         List<String> validLevels = Arrays.asList("Beginner", "Intermediate", "Expert");
         if (!validLevels.contains(prof)) {
             System.out.println("Invalid proficiency level. Please enter Beginner, Intermediate, or Expert.");
             return;
         }

        // Call service to update skill
        resumeService.updateSkill(s.getSkillId(), skillName, prof);
        System.out.println("Skill updated successfully.");
    }


    private void deleteSkillEntry(int seekerId) {
        List<Skill> skills = resumeService.getSkillsList(seekerId);
        if (skills.isEmpty()) return;
        for (int i = 0; i < skills.size(); i++) {
            Skill s = skills.get(i);
            System.out.println((i + 1) + ". " + s.getSkillName() + " - " + s.getProficiency());
        }
        System.out.print("Select entry to delete: ");
        int index = Integer.parseInt(sc.nextLine()) - 1;
        if (index < 0 || index >= skills.size()) return;
        resumeService.deleteSkill(skills.get(index).getSkillId());
        System.out.println("Skill deleted successfully.");
    }
    
    // ====================== PROJECTS MENU ======================
    private void addProject(int seekerId) {
        while (true) {
            System.out.println("=== Projects Menu ===");
            System.out.println("1. Add Project");
            System.out.println("2. Update Project");
            System.out.println("3. Delete Project");
            System.out.println("4. Back to Resume Menu");
            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1: 
                	addProjectEntry(seekerId); 
                	break;
                case 2: 
                	updateProjectEntry(seekerId); 
                	break;
                case 3: 
                	deleteProjectEntry(seekerId); 
                	break;
                case 4: 
                	return;
                default: 
                	System.out.println("Invalid choice");
            }
        }
    }
    // -------------------------------- add project
    private void addProjectEntry(int seekerId) {
        System.out.print("Project Title: ");
        String title = sc.nextLine().trim();
        System.out.print("Description: ");
        String desc = sc.nextLine().trim();
        System.out.print("Technologies: ");
        String tech = sc.nextLine().trim();
        System.out.print("Start Date (YYYY-MM-DD): ");
        String start = sc.nextLine().trim();
        System.out.print("End Date (YYYY-MM-DD): ");
        String end = sc.nextLine().trim();

        // Validate input
        if (title.isEmpty() || desc.isEmpty() || tech.isEmpty() || start.isEmpty() || end.isEmpty()) {
            System.out.println("All fields are mandatory. Please enter valid details.");
            return; // stop execution if any field is empty
        }

        // Validate date format
        if (!start.matches("\\d{4}-\\d{2}-\\d{2}") || !end.matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return;
        }

        // Call service to add project
        resumeService.addProject(seekerId, title, desc, tech, start, end);
        System.out.println("Project added successfully.");
    }

    // ---------------- update project
    private void updateProjectEntry(int seekerId) {
        List<Project> projects = resumeService.getProjectsList(seekerId);
        if (projects.isEmpty()) {
            System.out.println("No projects found.");
            return;
        }

        // Display all projects
        for (int i = 0; i < projects.size(); i++) {
            Project p = projects.get(i);
            System.out.println((i + 1) + ". " + p.getTitle() + " | " + p.getTechnologies());
        }

        System.out.print("Select entry to update: ");
        int index = Integer.parseInt(sc.nextLine()) - 1;
        if (index < 0 || index >= projects.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Project p = projects.get(index);

        // Prompt for new values, keep old values if input is empty
        System.out.print("New Title (" + p.getTitle() + "): ");
        String title = sc.nextLine().trim();
        if (title.isEmpty()) title = p.getTitle();

        System.out.print("New Description (" + p.getDescription() + "): ");
        String desc = sc.nextLine().trim();
        if (desc.isEmpty()) desc = p.getDescription();

        System.out.print("New Technologies (" + p.getTechnologies() + "): ");
        String tech = sc.nextLine().trim();
        if (tech.isEmpty()) tech = p.getTechnologies();

        System.out.print("New Start Date (" + p.getStartDate() + "): ");
        String start = sc.nextLine().trim();
        if (start.isEmpty()) start = p.getStartDate();
        else if (!start.matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return;
        }

        System.out.print("New End Date (" + p.getEndDate() + "): ");
        String end = sc.nextLine().trim();
        if (end.isEmpty()) end = p.getEndDate();
        else if (!end.matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return;
        }

        // Call service to update project
        resumeService.updateProject(p.getProjectId(), title, desc, tech, start, end);
        System.out.println("Project updated successfully.");
    }


    private void deleteProjectEntry(int seekerId) {
        List<Project> projects = resumeService.getProjectsList(seekerId);
        if (projects.isEmpty()) return;
        for (int i = 0; i < projects.size(); i++) {
            Project p = projects.get(i);
            System.out.println((i + 1) + ". " + p.getTitle() + " | " + p.getTechnologies());
        }
        System.out.print("Select entry to delete: ");
        int index = Integer.parseInt(sc.nextLine()) - 1;
        if (index < 0 || index >= projects.size()) return;
        resumeService.deleteProject(projects.get(index).getProjectId());
        System.out.println("Project deleted successfully.");
    }

    // View Resume
//    private void viewResume(int seekerId) {
//        Resume resume = resumeService.getFullResume(seekerId);
//
//        if (resume == null) {
//            System.out.println("No resume found.");
//            return;
//        }
//        System.out.println("====================== Text Resume ==========================");
//        System.out.println("      ");
//        System.out.println("Resume Objective:");
//        System.out.println("----------------");
//        System.out.println(resume.getObjective());
//
//        resume.getEducationList().forEach(e ->
//                System.out.println("Education: " + e.getDegree() + " at " + e.getInstitution())
//              
//        );
//        System.out.println("      ");
//        resume.getExperienceList().forEach(ex ->
//                System.out.println("Experience: " + ex.getJobTitle() + " at " + ex.getCompany())
//        );
//        System.out.println("      ");
//        resume.getSkills().forEach(s ->
//                System.out.println("Skill: " + s.getSkillName() + " - " + s.getProficiency())
//        );
//        System.out.println("      ");
//        resume.getProjects().forEach(p ->
//                System.out.println("Project: " + p.getTitle() + " | " + p.getTechnologies())
//        );
//        System.out.println(" -------------------------------------------------------------     ");
//    }
 // ====================== VIEW RESUME ======================
    private void viewResume(int seekerId) {
        Resume resume = resumeService.getFullResume(seekerId);
        if (resume == null) {
            System.out.println("No resume found.");
            return;
        }

        System.out.println("====================== Text Resume ==========================");
        System.out.println("Resume Objective: ");
        System.out.println("----------------");
      System.out.println(resume.getObjective());
        resume.getEducationList().forEach(e ->
                System.out.println("Education: " + e.getDegree() + " at " + e.getInstitution())
                
        		);
        System.out.println("     ");
        resume.getExperienceList().forEach(ex ->
                System.out.println("Experience: " + ex.getJobTitle() + " at " + ex.getCompany())
                
        		);
        System.out.println("     ");
        resume.getSkills().forEach(s ->
                System.out.println("Skill: " + s.getSkillName() + " - " + s.getProficiency())
                
        		);
        System.out.println("     ");
        resume.getProjects().forEach(p ->
                System.out.println("Project: " + p.getTitle() + " | " + p.getTechnologies())
                
        		);
        System.out.println("-------------------------------------------------------------");
    }
}
