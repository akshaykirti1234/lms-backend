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

    @Test
    @Transactional
    public void testUpdateIsLastSession() {
        sessionMasterRepository.updateIsLastSession(scheduleForMaster.getScheduleForId());
        List<SessionMaster> sessions = sessionMasterRepository.getSessionByScheduleId(scheduleForMaster.getScheduleForId());
        assertThat(sessions).hasSizeGreaterThan(0);
//        for (SessionMaster session : sessions) {
//            assertThat(session.getIsLastSession()).isEqualTo(0);
//        }
    }

    @Test
    public void testCheckIsLastSession() {
        Boolean isLastSession = sessionMasterRepository.checkIsLastSession(scheduleForMaster.getScheduleForId());
        assertThat(isLastSession).isNotNull();
    }

    @Test
    public void testCheckBoxValidation() {
        String status = sessionMasterRepository.checkBoxValidation(scheduleForMaster.getScheduleForId());
        assertThat(status).isEqualTo("false");
    }

    @Test
    public void testGetSessionByScheduleId() {
        List<SessionMaster> sessions = sessionMasterRepository.getSessionByScheduleId(scheduleForMaster.getScheduleForId());
        assertThat(sessions).isNotNull().hasSizeGreaterThan(0);
    }

}
