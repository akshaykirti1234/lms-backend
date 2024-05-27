package com.csmtech.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.csmtech.entity.SessionMaster;
import com.csmtech.entity.SessionResultStatus;
import com.csmtech.entity.UserMaster;
import com.csmtech.repository.SessionResultStatusRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SessionResultStatusTest {
	@Autowired
    private SessionResultStatusRepository sessionResultStatusRepository;

    @Autowired
    private EntityManager entityManager;

    private SessionMaster sessionMaster;
    private UserMaster userMaster;

    @BeforeEach
    public void setUp() {
        sessionMaster = new SessionMaster();
        // Set necessary fields for sessionMaster
        entityManager.persist(sessionMaster);

        userMaster = new UserMaster();
        // Set necessary fields for userMaster
        entityManager.persist(userMaster);

        SessionResultStatus srs1 = new SessionResultStatus();
        srs1.setSessionMaster(sessionMaster);
        srs1.setUserMaster(userMaster);
        // Set other fields for srs1
        entityManager.persist(srs1);

        SessionResultStatus srs2 = new SessionResultStatus();
        srs2.setSessionMaster(sessionMaster);
        srs2.setUserMaster(userMaster);
        // Set other fields for srs2
        entityManager.persist(srs2);
    }

    @Test
    public void testGetSessionResultStatus() {
        List<SessionResultStatus> results = sessionResultStatusRepository.getSessionResultStatus(userMaster.getUserId());
        assertThat(results).isNotNull().hasSizeGreaterThan(0);
    }

    @Test
    public void testGetSessionResultBySessionIdUserId() {
        List<SessionResultStatus> results = sessionResultStatusRepository.getSessionResultBySessionIdUserId(sessionMaster.getSessionId(), userMaster.getUserId());
        assertThat(results).isNotNull().hasSizeGreaterThan(0);
    }

    @Test
    public void testFindSessionMasterBySessionIdAndUserId() {
        SessionResultStatus result = sessionResultStatusRepository.findSessionMasterBySessionIdAndUserId(sessionMaster.getSessionId(), userMaster.getUserId());
        assertThat(result).isNotNull();
    }

    @Test
    @Transactional
    public void testDeleteResultStatusBySessionIdAndUserId() {
        sessionResultStatusRepository.deleteResultStatusBySessionIdAndUserId(sessionMaster.getSessionId(), userMaster.getUserId());
        List<SessionResultStatus> results = sessionResultStatusRepository.getSessionResultBySessionIdUserId(sessionMaster.getSessionId(), userMaster.getUserId());
        assertThat(results).isEmpty();
    }

}
