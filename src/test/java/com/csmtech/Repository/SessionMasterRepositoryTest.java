package com.csmtech.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import com.csmtech.entity.SessionMaster;
import com.csmtech.repository.SessionMasterRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SessionMasterRepositoryTest {

    @Autowired
    private SessionMasterRepository sessionMasterRepository;

    @Test
    public void testGetAllSessionMaster() {
        List<Map<String, Object>> sessions = sessionMasterRepository.getAllSessionMaster();
        assertThat(sessions).isNotNull().isNotEmpty();
    }

    @Test
    @Transactional
    public void testDeleteSession() {
        Integer sessionId = 1; // Assuming a session with this ID exists
        sessionMasterRepository.deleteSession(sessionId);
        List<Map<String, Object>> sessions = sessionMasterRepository.getAllSessionMaster();
        boolean isDeleted = sessions.stream().noneMatch(s -> (Integer) s.get("sessionid") == sessionId);
        assertThat(isDeleted).isTrue();
    }

    @Test
    @Transactional
    public void testUpdateIsLastSession() {
        Integer scheduleForId = 1; // Assuming a schedule with this ID exists
        sessionMasterRepository.updateIsLastSession(scheduleForId);
        List<SessionMaster> sessions = sessionMasterRepository.getSessionByScheduleId(scheduleForId);
        sessions.forEach(session -> assertThat(session.getIsLastSession()).isFalse());
    }

    @Test
    public void testCheckIsLastSession() {
        Integer scheduleForId = 1; // Assuming a schedule with this ID exists
        Boolean isLastSession = sessionMasterRepository.checkIsLastSession(scheduleForId);
        assertThat(isLastSession).isNotNull();
    }

    @Test
    public void testCheckBoxValidation() {
        Integer scheduleForId = 1; // Assuming a schedule with this ID exists
        String status = sessionMasterRepository.checkBoxValidation(scheduleForId);
        assertThat(status).isIn("true", "false");
    }

    @Test
    public void testGetSessionByScheduleId() {
        Integer scheduleForId = 1; // Assuming a schedule with this ID exists
        List<SessionMaster> sessions = sessionMasterRepository.getSessionByScheduleId(scheduleForId);
        assertThat(sessions).isNotNull().isNotEmpty();
        sessions.forEach(session -> assertThat(session.getScheduleFor().getScheduleForId()).isEqualTo(scheduleForId));
    }
}
