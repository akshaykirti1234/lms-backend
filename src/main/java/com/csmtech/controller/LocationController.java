package com.csmtech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.dto.LocationDto;
import com.csmtech.service.LocationService;

@CrossOrigin
@RestController
public class LocationController {

	@Autowired
	private LocationService locationService;

	@PostMapping("saveLocation")
	public ResponseEntity<Map<String, Object>> saveLocationMaster(@RequestBody LocationDto locationDto) {
		Map<String, Object> result = locationService.saveLocation(locationDto);
		return ResponseEntity.ok(result);
	}

	@GetMapping("viewLocation")
	public ResponseEntity<List<Map<String, Object>>> getAllLocation() {
		List<Map<String, Object>> locationList = locationService.getAllLocation();
		return ResponseEntity.ok().body(locationList);
	}

	@GetMapping("deleteLocation/{id}")
	public ResponseEntity<Map<String, Object>> deleteLocation(@PathVariable("id") Integer id) {
		Map<String, Object> response = new HashMap<>();
		locationService.deleteLocation(id);
		response.put("status", "deleted");
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("editLocation/{id}")
	public ResponseEntity<Map<String, Object>> geteditById(@PathVariable("id") Integer id) {
		Map<String, Object> update = locationService.geteditById(id);
		return ResponseEntity.ok().body(update);
	}
}
