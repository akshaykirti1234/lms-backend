package com.csmtech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	Logger logger=LoggerFactory.getLogger(LocationController.class);

	@Autowired
	private LocationService locationService;

	@PostMapping("saveLocation")
	public ResponseEntity<Map<String, Object>> saveLocationMaster(@RequestBody LocationDto locationDto) {
		logger.info("saveLocationMaster method of LocationController is executed");
		Map<String, Object> result = locationService.saveLocation(locationDto);
		return ResponseEntity.ok(result);
	}

	@GetMapping("viewLocation")
	public ResponseEntity<List<Map<String, Object>>> getAllLocation() {
		logger.info("getAllLocation method of LocationController is executed");
		List<Map<String, Object>> locationList = locationService.getAllLocation();
		return ResponseEntity.ok().body(locationList);
	}

	@GetMapping("deleteLocation/{id}")
	public ResponseEntity<Map<String, Object>> deleteLocation(@PathVariable("id") Integer id) {
		logger.info("deleteLocation method of LocationController is executed");
		Map<String, Object> response = new HashMap<>();
		locationService.deleteLocation(id);
		response.put("status", "deleted");
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("editLocation/{id}")
	public ResponseEntity<Map<String, Object>> geteditById(@PathVariable("id") Integer id) {
		logger.info("geteditById method of LocationController is executed");
		Map<String, Object> update = locationService.geteditById(id);
		return ResponseEntity.ok().body(update);
	}
}
