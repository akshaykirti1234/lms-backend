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
import com.csmtech.entity.SessionResultMaster;
import com.csmtech.entity.UserMaster;
import com.csmtech.repository.SessionResultMasterRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SessionResultMasterTest {
	
	@Autowired
    private SessionResultMasterRepository sessionResultMasterRepository;

    @Autowired
    private EntityManager entityManager;

    private SessionMaster sessionMaster;
    private UserMaster userMaster;

    @BeforeEach
    public void setUp() {
        // Initialize and persist necessary entities to the in-memory database
        sessionMaster = new SessionMaster();
        // Set necessary fields for sessionMaster
        entityManager.persist(sessionMaster);

        userMaster = new UserMaster();
        // Set necessary fields for userMaster
        entityManager.persist(userMaster);

        SessionResultMaster srm1 = new SessionResultMaster();
        srm1.setSessionMaster(sessionMaster);
        srm1.setUserMaster(userMaster);
        // Set other fields for srm1
        entityManager.persist(srm1);

        SessionResultMaster srm2 = new SessionResultMaster();
        srm2.setSessionMaster(sessionMaster);
        srm2.setUserMaster(userMaster);
        // Set other fields for srm2
        entityManager.persist(srm2);
    }

    @Test
    public void testGetResultBySessionIdAndUserID() {
        // Act
        List<SessionResultMaster> results = sessionResultMasterRepository.getResultBySessionIdAndUserID(sessionMaster.getSessionId(), userMaster.getUserId());

        // Assert
        assertThat(results).isNotNull().hasSize(2);
    }

    @Test
    @Transactional
    public void testDeleteResultBySessionIdAndUserId() {
        // Act
        sessionResultMasterRepository.deleteResultBySessionIdAndUserId(sessionMaster.getSessionId(), userMaster.getUserId());

        // Assert
        List<SessionResultMaster> results = sessionResultMasterRepository.getResultBySessionIdAndUserID(sessionMaster.getSessionId(), userMaster.getUserId());
        assertThat(results).isEmpty();
    }

}
