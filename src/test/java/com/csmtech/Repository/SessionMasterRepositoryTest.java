package com.csmtech.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.entity.SessionMaster;
import com.csmtech.entity.SubModule;

import com.csmtech.entity.SessionMaster;
import com.csmtech.repository.SessionMasterRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SessionMasterRepositoryTest {
	
	@Autowired
    private SessionMasterRepository sessionMasterRepository;

    @Autowired
    private EntityManager entityManager;

    private SessionMaster sessionMaster;
    private SubModule subModuleMaster;
    private ScheduleForMaster scheduleForMaster;

    @BeforeEach
    public void setUp() {
        subModuleMaster = new SubModule();
        // Set necessary fields for subModuleMaster
        entityManager.persist(subModuleMaster);

        scheduleForMaster = new ScheduleForMaster();
        // Set necessary fields for scheduleForMaster
        entityManager.persist(scheduleForMaster);

        sessionMaster = new SessionMaster();
//        sessionMaster.setSubModuleMaster(subModuleMaster);
//        sessionMaster.setScheduleForMaster(scheduleForMaster);

        sessionMaster.setSubModule(subModuleMaster);
        sessionMaster.setScheduleFor(scheduleForMaster);
//        sessionMaster.setDeletedFlag(0);
        // Set other fields for sessionMaster
        entityManager.persist(sessionMaster);
    }

    @Test
    public void testGetAllSessionMaster() {
        List<Map<String, Object>> sessions = sessionMasterRepository.getAllSessionMaster();
        assertThat(sessions).isNotNull().hasSizeGreaterThan(0);
    }

//    @Test
//    @Transactional
//    public void testDeleteSession() {
//        sessionMasterRepository.deleteSession(sessionMaster.getSessionId());
//        SessionMaster deletedSession = entityManager.find(SessionMaster.class, sessionMaster.getSessionId());
//        assertThat(deletedSession.getDeletedFlag()).isEqualTo(1);
//    }
    //     assertThat(sessions).isNotNull().isNotEmpty();
    // }

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
