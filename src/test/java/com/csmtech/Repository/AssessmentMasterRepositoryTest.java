package com.csmtech.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.csmtech.repository.AssessmentMasterRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AssessmentMasterRepositoryTest {

	@Autowired
    private AssessmentMasterRepository assessmentMasterRepository;


    @Test
    public void testGetAllAssesmentdata() {
        List<Map<String, Object>> assessments = assessmentMasterRepository.getAllAssesmentdata();
        assertThat(assessments).isNotNull().isNotEmpty();
    }

    @Test
    @Transactional
    public void testDeleteAssessment() {
        assessmentMasterRepository.deleteAssessment(1);
        List<Map<String, Object>> assessments = assessmentMasterRepository.getAllAssesmentdata();
        boolean isDeleted = assessments.stream().anyMatch(a -> (Integer) a.get("ASSESSMENTMASTERID") == 1 && (Boolean) a.get("DELETEDFLAG"));
        assertThat(isDeleted).isFalse();
    }

    @Test
    public void testUpdateAssessment() {
        Map<String, Object> assessment = assessmentMasterRepository.updateAssessment(1);
        assertThat(assessment).isNotNull();
        assertThat(assessment.get("ASSESSMENTMASTERID")).isEqualTo(1);
    }

    @Test
    public void testRetriveModuleTypeList() {
        List<Map<String, Object>> modules = assessmentMasterRepository.retriveModuleTypeList();
        assertThat(modules).isNotNull().isNotEmpty();
    }

    @Test
    public void testRetriveSubModuleList() {
        List<Map<String, Object>> subModules = assessmentMasterRepository.retriveSubModuleList();
        assertThat(subModules).isNotNull().isNotEmpty();
    }

    @Test
    public void testRetriveScheduleForList() {
        List<Map<String, Object>> schedules = assessmentMasterRepository.retriveScheduleForList();
        assertThat(schedules).isNotNull().isNotEmpty();
    }

    @Test
    public void testRetriveSessionList() {
        List<Map<String, Object>> sessions = assessmentMasterRepository.retriveSessionList();
        assertThat(sessions).isNotNull().isNotEmpty();
    }
}
