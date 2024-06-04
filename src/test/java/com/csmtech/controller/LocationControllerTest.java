 package com.csmtech.controller;
 import static org.junit.jupiter.api.Assertions.assertEquals;
 import static org.mockito.Mockito.when;
 import static org.mockito.ArgumentMatchers.anyInt;
 import static org.mockito.ArgumentMatchers.any;
 import static org.mockito.Mockito.doNothing;

 import java.util.*;

 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 import org.mockito.InjectMocks;
 import org.mockito.Mock;
 import org.mockito.MockitoAnnotations;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;

import com.csmtech.dto.LocationDto;
import com.csmtech.service.LocationService;

 public class LocationControllerTest {

     @Mock
     private LocationService locationService;

     @InjectMocks
     private LocationController locationController;

     @BeforeEach
     public void setup() {
         MockitoAnnotations.initMocks(this); // Initialize mocks
     }

     @Test
     public void testSaveLocationMaster() {
         // Arrange
         LocationDto locationDto = new LocationDto();
         // Set up locationDto if needed
         
         Map<String, Object> result = new HashMap<>();
         result.put("status", "success");
         
         when(locationService.saveLocation(any(LocationDto.class))).thenReturn(result);

         // Act
         ResponseEntity<Map<String, Object>> responseEntity = locationController.saveLocationMaster(locationDto);

         // Assert
         assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
         assertEquals(result, responseEntity.getBody()); // Check body content
     }

     @Test
     public void testGetAllLocation() {
         // Arrange
         List<Map<String, Object>> mockLocationList = new ArrayList<>();
         Map<String, Object> location = new HashMap<>();
         location.put("id", 1);
         location.put("name", "Test Location");
         mockLocationList.add(location);
         
         when(locationService.getAllLocation()).thenReturn(mockLocationList);

         // Act
         ResponseEntity<List<Map<String, Object>>> responseEntity = locationController.getAllLocation();

         // Assert
         assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
         assertEquals(mockLocationList, responseEntity.getBody()); // Check body content
     }

     @Test
     public void testDeleteLocation() {
         // Arrange
         Integer id = 1;
         doNothing().when(locationService).deleteLocation(anyInt());

         // Act
         ResponseEntity<Map<String, Object>> responseEntity = locationController.deleteLocation(id);

         // Assert
         assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
         assertEquals("deleted", responseEntity.getBody().get("status")); // Check body content
     }

     @Test
     public void testGetEditById() {
         // Arrange
         Integer id = 1;
         Map<String, Object> update = new HashMap<>();
         update.put("id", id);
         update.put("name", "Updated Location");
         
         when(locationService.geteditById(anyInt())).thenReturn(update);

         // Act
         ResponseEntity<Map<String, Object>> responseEntity = locationController.geteditById(id);

         // Assert
         assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
         assertEquals(update, responseEntity.getBody()); // Check body content
     }
 }















