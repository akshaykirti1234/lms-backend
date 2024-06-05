package com.csmtech.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.csmtech.entity.SessionResultStatus;
import com.csmtech.service.SessionResultStatusService;

public class SessionResultStatusControllerTest {

    @Mock
    private SessionResultStatusService sessionResultStatusService;

    @InjectMocks
    private SessionResultStatusController sessionResultStatusController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testGetSessionResult() {
        // Arrange
        Integer userId = 1;
        List<SessionResultStatus> sessionResultList = new ArrayList<>();
        when(sessionResultStatusService.getSessionResultStatus(userId)).thenReturn(sessionResultList);

        // Act
        ResponseEntity<?> responseEntity = sessionResultStatusController.getSessionResult(userId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(sessionResultList, responseEntity.getBody());
    }

    @Test
    public void testGetSessionResultBySessionIdUserId() {
        // Arrange
        Integer sessionId = 1;
        Integer userId = 1;
        List<SessionResultStatus> sessionResultList = new ArrayList<>();
        when(sessionResultStatusService.getSessionResultBySessionIdUserId(sessionId, userId)).thenReturn(sessionResultList);

        // Act
        ResponseEntity<?> responseEntity = sessionResultStatusController.getSessionResultBySessionIdUserId(sessionId, userId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(sessionResultList, responseEntity.getBody());
    }
}
