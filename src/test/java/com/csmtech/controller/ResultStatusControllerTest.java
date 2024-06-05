package com.csmtech.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.csmtech.entity.ResultStatus;
import com.csmtech.service.ResultStatusService;

public class ResultStatusControllerTest {

    @Mock
    private ResultStatusService resultStatusService;

    @InjectMocks
    private ResultStatusController resultStatusController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetFinalResultByScheduleIdUserId_Success() {
        // Arrange
        Integer scheduleForId = 1;
        Integer userId = 1;
        List<ResultStatus> mockResultStatusList = new ArrayList<>();
        when(resultStatusService.getFinalResultByScheduleIdUserId(scheduleForId, userId)).thenReturn(mockResultStatusList);

        // Act
        ResponseEntity<?> responseEntity = resultStatusController.getFinalResultByScheduleIdUserId(scheduleForId, userId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockResultStatusList, responseEntity.getBody());
    }

    @Test
    public void testGetFinalResultByScheduleIdUserId_InternalServerError() {
        // Arrange
        Integer scheduleForId = 1;
        Integer userId = 1;
        when(resultStatusService.getFinalResultByScheduleIdUserId(scheduleForId, userId)).thenThrow(new RuntimeException("Error"));

        // Act
        ResponseEntity<?> responseEntity = resultStatusController.getFinalResultByScheduleIdUserId(scheduleForId, userId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("An error occurred while processing your request", responseEntity.getBody());
    }

    @Test
    public void testGetResultStatusByScheduleIdUserId_Success() {
        // Arrange
        Integer scheduleForId = 1;
        Integer userId = 1;
        ResultStatus mockResultStatus = new ResultStatus();
        when(resultStatusService.getResultStatusByScheduleIdUserId(scheduleForId, userId)).thenReturn(mockResultStatus);

        // Act
        ResponseEntity<?> responseEntity = resultStatusController.getResultStatusByScheduleIdUserId(scheduleForId, userId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockResultStatus, responseEntity.getBody());
    }

    @Test
    public void testGetResultStatusByScheduleIdUserId_InternalServerError() {
        // Arrange
        Integer scheduleForId = 1;
        Integer userId = 1;
        when(resultStatusService.getResultStatusByScheduleIdUserId(scheduleForId, userId)).thenThrow(new RuntimeException("Error"));

        // Act
        ResponseEntity<?> responseEntity = resultStatusController.getResultStatusByScheduleIdUserId(scheduleForId, userId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("An error occurred while processing your request", responseEntity.getBody());
    }
}
