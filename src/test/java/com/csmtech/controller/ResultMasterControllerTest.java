package com.csmtech.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.csmtech.dto.SessionResultDto;
import com.csmtech.service.ResultMasterService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ResultMasterControllerTest {

    @Mock
    private ResultMasterService resultMasterService;

    @InjectMocks
    private ResultMasterController resultMasterController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this); // Initialize mocks
    }

    @Test
    public void testSaveScheduleResult_Success() {
        // Arrange
        List<SessionResultDto> responsePayload = new ArrayList<>();
        // Add sample data to responsePayload if needed
        
        ResponseEntity<?> mockResponse = ResponseEntity.ok().build();
        when(resultMasterService.saveScheduleResult(any(List.class))).thenReturn(mockResponse);

        // Act
        ResponseEntity<?> responseEntity = resultMasterController.saveScheduleResult(responsePayload);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
    }

    @Test
    public void testSaveScheduleResult_Error() {
        // Arrange
        List<SessionResultDto> responsePayload = new ArrayList<>();
        // Add sample data to responsePayload if needed

        when(resultMasterService.saveScheduleResult(any(List.class))).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<?> responseEntity = resultMasterController.saveScheduleResult(responsePayload);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode()); // Check status code
        assertEquals("An error occurred while processing your request", responseEntity.getBody()); // Check error message
    }
}

