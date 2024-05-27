package com.csmtech.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import com.csmtech.entity.SessionAssessmentMaster;
import com.csmtech.entity.SessionMaster;
import com.csmtech.repository.SessionAssessmentMasterRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SessionAssessmentMasterRepositoryTest {

    @Autowired
    private SessionAssessmentMasterRepository sessionAssessmentMasterRepository;

    @Test
    public void testGetQuestionarBySessionId() {
        Integer sessionId = 1; // Assuming this session ID exists
        Integer noOfQuestion = 1;
        List<SessionAssessmentMaster> questions = sessionAssessmentMasterRepository.getQuestionarBySessionId(sessionId, noOfQuestion);
        assertThat(questions).isNotNull().isNotEmpty();
        assertThat(questions.size()).isEqualTo(noOfQuestion);
        questions.forEach(question -> assertThat(question.getSessionMaster().getSessionId()).isEqualTo(sessionId));
    }

    @Test
    public void testGetAllSessionMaster() {
        List<Map<String, Object>> sessions = sessionAssessmentMasterRepository.getAllSessionMaster();
        assertThat(sessions).isNotNull().isNotEmpty();
    }

    @Test
    @Transactional
    public void testDeleteSession() {
        Integer sessionId = 5; // Assuming this session ID exists
        sessionAssessmentMasterRepository.deleteSession(sessionId);
        List<Map<String, Object>> sessions = sessionAssessmentMasterRepository.getAllSessionMaster();
        boolean isDeleted = sessions.stream().anyMatch(session -> (Integer) session.get("sessionid") == sessionId && (Boolean) session.get("deletedflag"));
        assertThat(isDeleted).isFalse();
    }

    @Test
    public void testCheckIsLastSession() {
        Integer scheduleId = 8; // Assuming this schedule ID exists
        Boolean isLastSession = sessionAssessmentMasterRepository.checkIsLastSession(scheduleId);
        assertThat(isLastSession).isNotNull();
    }

    @Test
    public void testCheckBoxValidation() {
        Integer scheduleId = 8; // Assuming this schedule ID exists
        String status = sessionAssessmentMasterRepository.checkBoxValidation(scheduleId);
        assertThat(status).isNotNull().isIn("true", "false");
    }

    @Test
    public void testGetSessionByScheduleId() {
        Integer scheduleId = 8; // Assuming this schedule ID exists
        List<SessionMaster> sessions = sessionAssessmentMasterRepository.getSessionByScheduleId(scheduleId);
        assertThat(sessions).isNotNull().isNotEmpty();
        sessions.forEach(session -> assertThat(session.getScheduleFor().getScheduleForId()).isEqualTo(scheduleId));
    }

    @Test
    public void testViewAssessmentForSessionData() {
        List<Map<String, Object>> assessments = sessionAssessmentMasterRepository.viewAssessmentForSessionData();
        assertThat(assessments).isNotNull().isNotEmpty();
    }

    @Test
    @Transactional
    public void testDeleteAssessmentSession() {
        Integer assessmentSessionId = 5; // Assuming this assessment session ID exists
        sessionAssessmentMasterRepository.deleteAssessmentSession(assessmentSessionId);
        Map<String, Object> assessmentSession = sessionAssessmentMasterRepository.getAssessmentSessionById(assessmentSessionId);
        assertThat(assessmentSession).isNull();
    }

    @Test
    public void testGetAssessmentSessionById() {
        Integer assessmentSessionId = 1; // Assuming this assessment session ID exists
        Map<String, Object> assessmentSession = sessionAssessmentMasterRepository.getAssessmentSessionById(assessmentSessionId);
        assertThat(assessmentSession).isNotNull();
        assertThat(assessmentSession.get("SESSIONASSESSMENTMASTERID")).isEqualTo(assessmentSessionId);
    }

    @Test
    public void testRetriveModuleTypeList() {
        List<Map<String, Object>> modules = sessionAssessmentMasterRepository.retriveModuleTypeList();
        assertThat(modules).isNotNull().isNotEmpty();
    }

    @Test
    public void testRetriveSubModuleList() {
        List<Map<String, Object>> subModules = sessionAssessmentMasterRepository.retriveSubModuleList();
        assertThat(subModules).isNotNull().isNotEmpty();
    }

    @Test
    public void testRetriveScheduleForList() {
        List<Map<String, Object>> schedules = sessionAssessmentMasterRepository.retriveScheduleForList();
        assertThat(schedules).isNotNull().isNotEmpty();
    }

    @Test
    public void testRetriveSessionList() {
        List<Map<String, Object>> sessions = sessionAssessmentMasterRepository.retriveSessionList();
        assertThat(sessions).isNotNull().isNotEmpty();
    }
}

