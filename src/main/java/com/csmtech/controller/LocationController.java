package com.csmtech.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	private static final Logger logger=LoggerFactory.getLogger(LocationController.class);

	@Autowired
	private LocationService locationService;

	@PostMapping("saveLocation")
	public ResponseEntity<Map<String, Object>> saveLocationMaster(@RequestBody LocationDto locationDto) {
		logger.info("saveLocationMaster method of LocationController is executed");
		 Map<String, Object> response = new HashMap<>();
		try {
		Map<String, Object> result = locationService.saveLocation(locationDto);
		return ResponseEntity.ok(result);
		}catch (Exception e) {
			logger.error("Exception caught while saving location: ", e);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		
	}

	@GetMapping("viewLocation")
	public ResponseEntity<List<Map<String, Object>>> getAllLocation() {
		logger.info("getAllLocation method of LocationController is executed");
	      Map<String, Object> response = new HashMap<>();
		try {
		List<Map<String, Object>> locationList = locationService.getAllLocation();
		return ResponseEntity.ok().body(locationList);
		}catch (Exception e) {
			logger.error("Exception caught while retrieving all locations: ", e);
			response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonList(response));
		}
	}

	@GetMapping("deleteLocation/{id}")
	public ResponseEntity<Map<String, Object>> deleteLocation(@PathVariable("id") Integer id) {
		logger.info("deleteLocation method of LocationController is executed");
		Map<String, Object> response = new HashMap<>();
		try {
		locationService.deleteLocation(id);
		response.put("status", "deleted");
		return ResponseEntity.ok().body(response);
		}catch (Exception e) {
			logger.error("Exception caught while deleting location: ", e);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			
		}
	}

	@GetMapping("editLocation/{id}")
	public ResponseEntity<Map<String, Object>> geteditById(@PathVariable("id") Integer id) {
		logger.info("geteditById method of LocationController is executed");
        Map<String, Object> errorResponse = new HashMap<>();

		try {
		Map<String, Object> update = locationService.geteditById(id);
		return ResponseEntity.ok().body(update);
		}catch (Exception e) {
			 logger.error("Exception caught while editing location: ", e);
	            errorResponse.put("error", e.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
		
	}
}
