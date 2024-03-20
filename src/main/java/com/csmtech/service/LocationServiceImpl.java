package com.csmtech.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.csmtech.dto.LocationDto;
import com.csmtech.entity.Location;
import com.csmtech.repository.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationRepository locationRepository;

	@Override
	public Map<String, Object> saveLocation(LocationDto locationDto) {
		Map<String, Object> response = new HashMap<>();
		try {

			Location entity = new Location();
			if (locationDto.getLocationId() != null) {
				entity.setLocationId(locationDto.getLocationId());
				entity.setUpdatedBy(1);
			}
			entity.setLocationName(locationDto.getLocationName());
			entity.setCreatedBy(1);
			locationRepository.save(entity);
			response.put("status", HttpStatus.CREATED);
			response.put("message", "Location Master data saved successfully");

		} catch (Exception e) {
			e.printStackTrace();
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
			response.put("message", "Something went wrong");
		}
		return response;
	}

	@Override
	public void deleteLocation(Integer id) {
		locationRepository.deleteLocation(id);
	}

	@Override
	public List<Map<String, Object>> getAllLocation() {
		return locationRepository.getAllLocation();
	}

	@Override
	public Map<String, Object> geteditById(Integer id) {
		return locationRepository.updateLocation(id);
	}
}
