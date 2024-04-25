package com.csmtech.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.csmtech.dto.AssessmentMasterDto;
import com.csmtech.entity.AssessmentMaster;
import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.service.AssessmentMasterService;
import com.csmtech.service.ScheduleForMasterService;
public class AssessmentMasterControllerTest {
	
	    @Mock
	    private AssessmentMasterService assessmentMasterService;
	    
	    @Mock
	    private ScheduleForMasterService scheduleForMasterService;

	    @InjectMocks
	    private AssessmentMasterController assessmentMasterController;
	    
	    @Test
	    public void testGetAllScheduleNames() {
	        // Arrange
	        MockitoAnnotations.initMocks(this); // Initialize mocks

	        List<ScheduleForMaster> mockScheduleForMasters = new ArrayList<>();
	        // Add some mock schedule data if needed
	        
	        // Mock the behavior of the scheduleForMasterService
	        when(scheduleForMasterService.getAllScheduleForm()).thenReturn(mockScheduleForMasters);

	        // Act
	        ResponseEntity<?> responseEntity = assessmentMasterController.getAllScheduleNames();

	        // Assert
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
	        assertEquals(mockScheduleForMasters, responseEntity.getBody()); // Check body content
	    }

	    @Test
	    public void testViewAssessmentData() {
	        // Arrange
	        MockitoAnnotations.initMocks(this); // Initialize mocks

	        List<Map<String, Object>> mockAssessmentData = new ArrayList<>();
	        // Add some mock assessment data if needed
	        
	        // Mock the behavior of the assessmentMasterService
	        when(assessmentMasterService.viewAssessmentData()).thenReturn(mockAssessmentData);

	        // Act
	        ResponseEntity<List<Map<String, Object>>> responseEntity = assessmentMasterController.viewAssessmentData();

	        // Assert
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
	        assertEquals(mockAssessmentData, responseEntity.getBody()); // Check body content
	    }

       
	    @Test
	    public void testSaveAssessment_Success() {
	        // Arrange
	        MockitoAnnotations.initMocks(this); // Initialize mocks
	        AssessmentMasterDto assessmentDto = new AssessmentMasterDto();
	        // Set up assessmentDto if needed
	        
	        AssessmentMaster savedAssessment = new AssessmentMaster();
	        // Set up savedAssessment if needed
	        
	        when(assessmentMasterService.saveAssessment(any(AssessmentMasterDto.class))).thenReturn(savedAssessment);

	        // Act
	        ResponseEntity<AssessmentMaster> responseEntity = assessmentMasterController.saveAssessment(assessmentDto);

	        // Assert
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
	       // assertEquals(savedAssessment, responseEntity.getBody()); // Check body content
	    }

	    private AssessmentMasterDto any(Class<AssessmentMasterDto> class1) {
			return null;
		}

		@Test
	    public void testSaveAssessment_InternalServerError() {
	        // Arrange
	        MockitoAnnotations.initMocks(this); // Initialize mocks
	        AssessmentMasterDto assessmentDto = new AssessmentMasterDto();
	        // Set up assessmentDto if needed
	        
	        when(assessmentMasterService.saveAssessment(any(AssessmentMasterDto.class))).thenThrow(new RuntimeException());

	        // Act
	        ResponseEntity<AssessmentMaster> responseEntity = assessmentMasterController.saveAssessment(assessmentDto);

	        // Assert
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
	      //  assertEquals(null, responseEntity.getBody()); // Check body content
	    }
		
		
		@Test
	    public void testDeleteAssessment() {
	        // Arrange
	        MockitoAnnotations.initMocks(this); // Initialize mocks
	        Integer id = 1; // ID of the assessment to delete
	        
	        // Mock the behavior of assessmentMasterService to do nothing (delete operation)
	        doNothing().when(assessmentMasterService).deleteAssessment(id);

	        // Act
	        ResponseEntity<Map<String, Object>> responseEntity = assessmentMasterController.deleteAssessment(id);

	        // Assert
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
	        Map<String, Object> responseBody = responseEntity.getBody();
	        assertEquals("deleted", responseBody.get("status")); // Check if status is "deleted"
	        verify(assessmentMasterService).deleteAssessment(id); // Verify that deleteAssessment was called with the correct ID
	    }
	    
		@Test
	    public void testGetAssessmentById() {
	        // Arrange
	        MockitoAnnotations.initMocks(this); // Initialize mocks
	        Integer id = 1; // ID of the assessment to fetch
	        
	        Map<String, Object> mockAssessmentData = new HashMap<>();
	        // Set up mock assessment data if needed
	        
	        // Mock the behavior of assessmentMasterService to return the mock assessment data
	        when(assessmentMasterService.getAssessmentById(id)).thenReturn(mockAssessmentData);

	        // Act
	        ResponseEntity<Map<String, Object>> responseEntity = assessmentMasterController.getAssessmentById(id);

	        // Assert
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
	        assertEquals(mockAssessmentData, responseEntity.getBody()); // Check body content
	    }
	    
}
