package com.csmtech.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.csmtech.dto.TechnologyDto;
import com.csmtech.entity.Technology;
import com.csmtech.service.TechnologyService;

	public class TechnologyControllerTest {

	    @Mock
	    private TechnologyService technologyService;

	    @InjectMocks
	    private TechnologyController technologyController;

	    @BeforeEach
	    public void setup() {
	        MockitoAnnotations.initMocks(this); // Initialize mocks
	    }

	    @Test
	    public void testSaveTechnology() {
	        // Arrange
	        TechnologyDto technologyDto = new TechnologyDto();
	        // Set up technologyDto if needed
	        
	        Technology savedTech = new Technology();
	        // Set up savedTech if needed
	        
	        when(technologyService.saveTechnology(any(TechnologyDto.class))).thenReturn(savedTech);

	        // Act
	        ResponseEntity<?> responseEntity = technologyController.saveTechnology(technologyDto);

	        // Assert
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
	        assertEquals(savedTech, responseEntity.getBody()); // Check body content
	    }

	    @Test
	    public void testGetAllTechnology() {
	        // Arrange
	        List<Technology> mockTechList = new ArrayList<>();
	        // Add mock technology data if needed
	        mockTechList.add(new Technology());

	        when(technologyService.getAllTechnologies()).thenReturn(mockTechList);

	        // Act
	        ResponseEntity<?> responseEntity = technologyController.getAllTechnology();

	        // Assert
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
	        assertEquals(mockTechList, responseEntity.getBody()); // Check body content
	    }

	    @Test
	    public void testDeleteTechnology() {
	        // Arrange
	        Integer tecId = 1;
	        doNothing().when(technologyService).deleteTechnology(anyInt());

	        // Act
	        ResponseEntity<?> responseEntity = technologyController.deleteTechnology(tecId);

	        // Assert
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
	        assertEquals("Deleted", ((Map<String, Object>) responseEntity.getBody()).get("status")); // Check body content
	    }

	    @Test
	    public void testEditTechnology() {
	        // Arrange
	        Integer tecId = 1;
	        Technology updatedTech = new Technology();
	        // Set up updatedTech if needed
	        
	        when(technologyService.editTechnology(anyInt())).thenReturn(updatedTech);

	        // Act
	        ResponseEntity<?> responseEntity = technologyController.editTechnology(tecId);

	        // Assert
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
	        assertEquals(updatedTech, responseEntity.getBody()); // Check body content
	    }
	}
