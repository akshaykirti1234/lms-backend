package com.csmtech.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.csmtech.entity.SessionResultStatus;
import com.csmtech.repository.SessionResultStatusRepository;
import com.csmtech.service.SessionResultStatusServiceImpl;

@SpringBootTest
public class SessionResultStatusServiceImplTest {

	@Mock
    private SessionResultStatusRepository sessionResultStatusRepository;

    @InjectMocks
    private SessionResultStatusServiceImpl sessionResultStatusService;

    private SessionResultStatus sessionResultStatus;
    private List<SessionResultStatus> sessionResultStatusList;

    @BeforeEach
    void setUp() {
        sessionResultStatus = new SessionResultStatus();
        sessionResultStatus.setSessionResultStatusId(1);
        sessionResultStatusList = Arrays.asList(sessionResultStatus);
    }

    @Test
    void testGetSessionResultStatus() {
        Integer userId = 1;

        when(sessionResultStatusRepository.getSessionResultStatus(userId)).thenReturn(sessionResultStatusList);

        List<SessionResultStatus> result = sessionResultStatusService.getSessionResultStatus(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(sessionResultStatusRepository, times(1)).getSessionResultStatus(userId);
    }

    @Test
    void testGetSessionResultBySessionIdUserId() {
        Integer sessionId = 1;
        Integer userId = 1;

        when(sessionResultStatusRepository.getSessionResultBySessionIdUserId(sessionId, userId)).thenReturn(sessionResultStatusList);

        List<SessionResultStatus> result = sessionResultStatusService.getSessionResultBySessionIdUserId(sessionId, userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(sessionResultStatusRepository, times(1)).getSessionResultBySessionIdUserId(sessionId, userId);
    }
}
