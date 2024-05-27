package com.csmtech.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;

import com.csmtech.dto.LocationDto;
import com.csmtech.entity.Location;
import com.csmtech.repository.LocationRepository;
import com.csmtech.service.LocationServiceImpl;

import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
public class LocationServiceImplTest {

	@InjectMocks
    private LocationServiceImpl locationService;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private Logger logger;

    private LocationDto locationDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        locationDto = new LocationDto();
        locationDto.setLocationId(1);
        locationDto.setLocationName("Test Location");
    }

    @Test
    public void testSaveLocation_Success() {
        Map<String, Object> response = locationService.saveLocation(locationDto);
        assertEquals(HttpStatus.CREATED, response.get("status"));
        assertEquals("Location Master data saved successfully", response.get("message"));
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    public void testSaveLocation_Failure() {
        doThrow(new RuntimeException("Database Error")).when(locationRepository).save(any(Location.class));
        
        Map<String, Object> response = locationService.saveLocation(locationDto);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.get("status"));
        assertEquals("Something went wrong", response.get("message"));
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    public void testDeleteLocation() {
        Integer locationId = 1;
        locationService.deleteLocation(locationId);
        verify(locationRepository, times(1)).deleteLocation(locationId);
    }

    @Test
    public void testGetAllLocation() {
        List<Map<String, Object>> locationList = new ArrayList<>();
        Map<String, Object> location = new HashMap<>();
        location.put("locationId", 1);
        location.put("locationName", "Test Location");
        locationList.add(location);
        
        when(locationRepository.getAllLocation()).thenReturn(locationList);

        List<Map<String, Object>> result = locationService.getAllLocation();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Location", result.get(0).get("locationName"));
        verify(locationRepository, times(1)).getAllLocation();
    }

    @Test
    public void testGeteditById() {
        Integer locationId = 1;
        Map<String, Object> location = new HashMap<>();
        location.put("locationId", locationId);
        location.put("locationName", "Test Location");
        
        when(locationRepository.updateLocation(locationId)).thenReturn(location);

        Map<String, Object> result = locationService.geteditById(locationId);
        assertNotNull(result);
        assertEquals(locationId, result.get("locationId"));
        assertEquals("Test Location", result.get("locationName"));
        verify(locationRepository, times(1)).updateLocation(locationId);
    }
}
