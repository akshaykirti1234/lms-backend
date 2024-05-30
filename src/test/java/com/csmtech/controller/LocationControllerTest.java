package com.csmtech.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @InjectMocks
    private LocationController locationController;

    @Mock
    private LocationService locationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveLocationMaster_Success() {
        LocationDto locationDto = new LocationDto();
        locationDto.setLocationId(1);
        locationDto.setLocationName("Test Location");

        Map<String, Object> serviceResponse = new HashMap<>();
        serviceResponse.put("status", "success");
        when(locationService.saveLocation(any(LocationDto.class))).thenReturn(serviceResponse);

        ResponseEntity<Map<String, Object>> response = locationController.saveLocationMaster(locationDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(serviceResponse, response.getBody());
        verify(locationService, times(1)).saveLocation(any(LocationDto.class));
    }

//    @Test
//    public void testSaveLocationMaster_Failure() {
//        LocationDto locationDto = new LocationDto();
//        locationDto.setLocationId(1);
//        locationDto.setLocationName("Test Location");
//
//        when(locationService.saveLocation(any(LocationDto.class))).thenThrow(new RuntimeException("Database Error"));
//
//        ResponseEntity<Map<String, Object>> response = locationController.saveLocationMaster(locationDto);
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//        assertTrue(response.getBody().containsKey("error"));
//        assertEquals("Database Error", response.getBody().get("error"));
//        verify(locationService, times(1)).saveLocation(any(LocationDto.class));
//    }

    @Test
    public void testGetAllLocation_Success() {
        List<Map<String, Object>> serviceResponse = new ArrayList<>();
        Map<String, Object> location = new HashMap<>();
        location.put("locationId", 1);
        location.put("locationName", "Test Location");
        serviceResponse.add(location);

        when(locationService.getAllLocation()).thenReturn(serviceResponse);

        ResponseEntity<List<Map<String, Object>>> response = locationController.getAllLocation();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(serviceResponse, response.getBody());
        verify(locationService, times(1)).getAllLocation();
    }

//    @Test
//    public void testGetAllLocation_Failure() {
//        when(locationService.getAllLocation()).thenThrow(new RuntimeException("Database Error"));
//
//        ResponseEntity<List<Map<String, Object>>> response = locationController.getAllLocation();
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//        assertTrue(response.getBody().get(0).containsKey("error"));
//        assertEquals("Database Error", response.getBody().get(0).get("error"));
//        verify(locationService, times(1)).getAllLocation();
//    }

    @Test
    public void testDeleteLocation_Success() {
        Integer locationId = 1;
        Map<String, Object> serviceResponse = new HashMap<>();
        serviceResponse.put("status", "deleted");

        doNothing().when(locationService).deleteLocation(locationId);

        ResponseEntity<Map<String, Object>> response = locationController.deleteLocation(locationId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(serviceResponse.get("status"), response.getBody().get("status"));
        verify(locationService, times(1)).deleteLocation(locationId);
    }

//    @Test
//    public void testDeleteLocation_Failure() {
//        Integer locationId = 1;
//
//        doThrow(new RuntimeException("Database Error")).when(locationService).deleteLocation(locationId);
//
//        ResponseEntity<Map<String, Object>> response = locationController.deleteLocation(locationId);
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//        assertTrue(response.getBody().containsKey("error"));
//        assertEquals("Database Error", response.getBody().get("error"));
//        verify(locationService, times(1)).deleteLocation(locationId);
//    }

    @Test
    public void testGeteditById_Success() {
        Integer locationId = 1;
        Map<String, Object> serviceResponse = new HashMap<>();
        serviceResponse.put("locationId", locationId);
        serviceResponse.put("locationName", "Test Location");

        when(locationService.geteditById(locationId)).thenReturn(serviceResponse);

        ResponseEntity<Map<String, Object>> response = locationController.geteditById(locationId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(serviceResponse, response.getBody());
        verify(locationService, times(1)).geteditById(locationId);
    }

//    @Test
//    public void testGeteditById_Failure() {
//        Integer locationId = 1;
//
//        when(locationService.geteditById(locationId)).thenThrow(new RuntimeException("Database Error"));
//
//        ResponseEntity<Map<String, Object>> response = locationController.geteditById(locationId);
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//        assertTrue(response.getBody().containsKey("error"));
//        assertEquals("Database Error", response.getBody().get("error"));
//        verify(locationService, times(1)).geteditById(locationId);
//    }
}
