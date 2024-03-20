package com.csmtech.service;

import java.util.List;
import java.util.Map;

import com.csmtech.dto.LocationDto;

public interface LocationService {
	Map<String, Object> saveLocation(LocationDto locationDto);

	void deleteLocation(Integer id);

	List<Map<String, Object>> getAllLocation();

	Map<String, Object> geteditById(Integer id);
}
