package com.revhire.service;

import com.revhire.config.DBConnection;
import com.revhire.model.Education;
import com.revhire.model.Experience;
import com.revhire.model.Project;
import com.revhire.model.Skill;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResumeServiceTest {

    private static ResumeService resumeService;
    private static int testSeekerId = 1;  // Make sure this user exists in DB

    @BeforeAll
    static void setup() {
        resumeService = new ResumeService();
    }
    
    // ========================= EDUCATION  =========================
    
    @Test
    @Order(1)
    void testAddEducation() {
        resumeService.addEducation(testSeekerId, "B.Tech", "NIT", "2020-01-01", "2024-01-01");

        List<Education> list = resumeService.getEducationList(testSeekerId);

        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    @Order(2)
    void testUpdateEducation() {
        List<Education> list = resumeService.getEducationList(testSeekerId);
        assertFalse(list.isEmpty());

        Education edu = list.get(0);

        resumeService.updateEducation(
                edu.getEducationId(),
                "B.Tech CSE",
                edu.getInstitution(),
                edu.getStartDate(),
                edu.getEndDate()
        );

        List<Education> updatedList = resumeService.getEducationList(testSeekerId);
        assertEquals("B.Tech CSE", updatedList.get(0).getDegree());
    }

    @Test
    @Order(3)
    void testDeleteEducation() {
        List<Education> list = resumeService.getEducationList(testSeekerId);
        assertFalse(list.isEmpty());

        int sizeBefore = list.size();
        resumeService.deleteEducation(list.get(0).getEducationId());

        List<Education> afterDelete = resumeService.getEducationList(testSeekerId);
        assertEquals(sizeBefore - 1, afterDelete.size());
    }
 // ========================= EXPERIENCE =========================

    @Test
    @Order(4)
    void testAddExperience() {
        resumeService.addExperience(testSeekerId, "Java Developer", "TCS", "2023-01-01", "2024-01-01");

        List<Experience> list = resumeService.getExperienceList(testSeekerId);

        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    @Order(5)
    void testUpdateExperience() {
        List<Experience> list = resumeService.getExperienceList(testSeekerId);
        Experience exp = list.get(0);

        resumeService.updateExperience(
                exp.getExperienceId(),
                "Senior Java Developer",
                exp.getCompany(),
                exp.getStartDate(),
                exp.getEndDate()
        );

        List<Experience> updated = resumeService.getExperienceList(testSeekerId);
        assertEquals("Senior Java Developer", updated.get(0).getJobTitle());
    }

    @Test
    @Order(6)
    void testDeleteExperience() {
        List<Experience> list = resumeService.getExperienceList(testSeekerId);
        int sizeBefore = list.size();

        resumeService.deleteExperience(list.get(0).getExperienceId());

        List<Experience> after = resumeService.getExperienceList(testSeekerId);
        assertEquals(sizeBefore - 1, after.size());
    }

    // ========================= SKILLS =========================
    
    @BeforeEach
    void cleanSkills() throws Exception {
        Connection con = DBConnection.getConnection();
        con.prepareStatement("DELETE FROM skills").executeUpdate();
        con.close();
    }

    @Test
    @Order(7)
    void testAddSkill() {
        resumeService.addSkill(testSeekerId, "Java", "ADVANCED");

        List<Skill> list = resumeService.getSkillsList(testSeekerId);

        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    @Order(8)
    void testUpdateSkill() {

        // Arrange: Ensure a skill exists before updating
        resumeService.addSkill(testSeekerId, "Java", "INTERMEDIATE");

        List<Skill> list = resumeService.getSkillsList(testSeekerId);

        assertNotNull(list);
        assertFalse(list.isEmpty(), "No skills found to update. Add a skill before updating.");

        Skill s = list.get(0);

        // Act: Update proficiency
        resumeService.updateSkill(
                s.getSkillId(),
                s.getSkillName(),
                "EXPERT"
        );

        // Assert: Fetch again and verify update
        List<Skill> updated = resumeService.getSkillsList(testSeekerId);

        Skill updatedSkill = updated.stream()
                .filter(skill -> skill.getSkillId() == s.getSkillId())
                .findFirst()
                .orElse(null);

        assertNotNull(updatedSkill, "Updated skill not found");
        assertEquals("EXPERT", updatedSkill.getProficiency());
    }



    @Test
    @Order(9)
    void testDeleteSkill() {
        // Arrange: add a skill first (important!)
        resumeService.addSkill(testSeekerId, "Spring Boot", "INTERMEDIATE");

        List<Skill> list = resumeService.getSkillsList(testSeekerId);

        // Safety checks
        assertNotNull(list);
        assertFalse(list.isEmpty(), "No skills found to delete. Add a skill before deleting.");

        int sizeBefore = list.size();
        int skillIdToDelete = list.get(0).getSkillId();

        // Act: delete the skill
        resumeService.deleteSkill(skillIdToDelete);

        // Assert: verify size decreased by 1
        List<Skill> after = resumeService.getSkillsList(testSeekerId);
        assertEquals(sizeBefore - 1, after.size());
    }


 // ========================= PROJECTS =========================

    @Test
    @Order(10)
    void testAddProject() {
        resumeService.addProject(
                testSeekerId,
                "RevHire",
                "Job Portal App",
                "Java, MySQL",
                "2024-01-01",
                "2024-06-01"
        );

        List<Project> list = resumeService.getProjectsList(testSeekerId);

        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    @Order(11)
    void testUpdateProject() {
        List<Project> list = resumeService.getProjectsList(testSeekerId);
        assertFalse(list.isEmpty());

        Project p = list.get(0);

        resumeService.updateProject(
                p.getProjectId(),
                "RevHire Pro",
                p.getDescription(),
                p.getTechnologies(),
                p.getStartDate(),
                p.getEndDate()
        );

        List<Project> updated = resumeService.getProjectsList(testSeekerId);
        assertEquals("RevHire Pro", updated.get(0).getTitle());
    }

    @Test
    @Order(12)
    void testDeleteProject() {
        List<Project> list = resumeService.getProjectsList(testSeekerId);
        assertFalse(list.isEmpty());

        int sizeBefore = list.size();

        resumeService.deleteProject(list.get(0).getProjectId());

        List<Project> after = resumeService.getProjectsList(testSeekerId);
        assertEquals(sizeBefore - 1, after.size());
    }

}
