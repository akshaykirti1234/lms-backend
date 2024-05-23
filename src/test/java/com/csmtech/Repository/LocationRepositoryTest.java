package com.csmtech.Repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.csmtech.entity.Location;
import com.csmtech.repository.LocationRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LocationRepositoryTest {

	 @Autowired
	    private LocationRepository locationRepository;

	    private Location location;

	    @BeforeEach
	    public void setUp() {
	        location = new Location();
	        location.setLocationName("Test Location");
	        location.setCreatedBy(1);
	        location.setUpdatedBy(1);
	        location = locationRepository.save(location);
	    }

	    @Test
	    public void testGetAllLocation() {
	        List<Map<String, Object>> locations = locationRepository.getAllLocation();
	        assertThat(locations).isNotNull().isNotEmpty();
	    }

	    @Test
	    @Transactional
	    @Rollback
	    public void testDeleteLocation() {
	        // Check that the location exists before deletion
	        assertNotNull(locationRepository.findById(location.getLocationId()).orElse(null));

	        // Perform delete operation
	        locationRepository.deleteLocation(location.getLocationId());

	        // Check that the location has been marked as deleted
	        List<Map<String, Object>> locations = locationRepository.getAllLocation();
	        boolean isDeleted = locations.stream().noneMatch(l -> l.get("LOCATIONID").equals(location.getLocationId()));
	        assertThat(isDeleted).isTrue();
	    }

	    @Test
	    public void testUpdateLocation() {
	        Map<String, Object> locationMap = locationRepository.updateLocation(location.getLocationId());
	        assertThat(locationMap).isNotNull();
	        assertThat(locationMap.get("LOCATIONID")).isEqualTo(location.getLocationId());
	    }
}
