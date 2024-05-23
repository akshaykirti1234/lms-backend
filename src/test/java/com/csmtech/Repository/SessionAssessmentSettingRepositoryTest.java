package com.csmtech.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import com.csmtech.entity.SessionAssessmentSetting;
import com.csmtech.repository.SessionAssessmentSettingRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SessionAssessmentSettingRepositoryTest {

    @Autowired
    private SessionAssessmentSettingRepository sessionAssessmentSettingRepository;

    @Test
    public void testGetSessionAssessmentSetting() {
        List<Map<String, Object>> settings = sessionAssessmentSettingRepository.getSessionAssessmentSetting();
        assertThat(settings).isNotNull().isNotEmpty();
    }

    @Test
    public void testGetSessionAssessmentSettingById() {
        Integer settingId = 1; // Assuming a setting with this ID exists
        Map<String, Object> setting = sessionAssessmentSettingRepository.getSessionAssessmentSettingById(settingId);
        assertThat(setting).isNotNull();
        assertThat(setting.get("SESSIONASSESSMENTSETTINGID")).isEqualTo(settingId);
    }

    @Test
    @Transactional
    public void testDeleteSessionAssessmentSetting() {
        Integer settingId = 1; // Assuming a setting with this ID exists
        sessionAssessmentSettingRepository.deleteSessionAssessmentSetting(settingId);
        Map<String, Object> setting = sessionAssessmentSettingRepository.getSessionAssessmentSettingById(settingId);
        assertThat(setting).isNotNull();
    }

    @Test
    @Transactional
    public void testUpdateSessionAssessmentSetting() {
        Integer settingId = 2; // Assuming a setting with this ID exists
        Integer noOfQuestions = 1;
        Double passingPercentage = 75.00;
        sessionAssessmentSettingRepository.updateSessionAssessmentSetting(settingId, noOfQuestions, passingPercentage);
        Map<String, Object> setting = sessionAssessmentSettingRepository.getSessionAssessmentSettingById(settingId);
        assertThat(setting).isNotNull();
        assertThat(setting.get("NOOFQUESTION")).isEqualTo(noOfQuestions);
    }

    @Test
    public void testFindFirst1BySessionMaster_SessionId() {
        Integer sessionId = 3; // Assuming a session with this ID exists
        SessionAssessmentSetting setting = sessionAssessmentSettingRepository.findFirst1BySessionMaster_SessionId(sessionId);
        assertThat(setting).isNotNull();
        assertThat(setting.getSessionMaster().getSessionId()).isEqualTo(sessionId);
    }

    @Test
    public void testFindBySessionId() {
        Integer sessionId = 3; // Assuming a session with this ID exists
        SessionAssessmentSetting setting = sessionAssessmentSettingRepository.findBySessionId(sessionId);
        assertThat(setting).isNotNull();
        assertThat(setting.getSessionMaster().getSessionId()).isEqualTo(sessionId);
    }

    @Test
    public void testGetSessionforAssessmentSetting() {
        Integer scheduleForId = 8; // Assuming a schedule with this ID exists
        List<Map<String, Object>> sessions = sessionAssessmentSettingRepository.getSessionforAssessmentSetting(scheduleForId);
        assertThat(sessions).isNotNull().isNotEmpty();
    }
}
