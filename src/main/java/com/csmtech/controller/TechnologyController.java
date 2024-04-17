package com.csmtech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.dto.TechnologyDto;
import com.csmtech.entity.Technology;
import com.csmtech.service.TechnologyService;

@RestController
@CrossOrigin("*")
public class TechnologyController {
	
	Logger logger=LoggerFactory.getLogger(TechnologyController.class);
	
	@Autowired
	private TechnologyService technologyService;

	@PostMapping("technology")
	public ResponseEntity<Technology> saveTechnology(@RequestBody TechnologyDto technologyDto) {
		logger.info("saveTechnology method of TechnologyController is executed");
		Technology tech = technologyService.saveTechnology(technologyDto);
		return ResponseEntity.ok().body(tech);

	}

	@GetMapping("/alltechnology")
	public ResponseEntity<List<Technology>> getAllTechnology() {
		logger.info("getAllTechnology method of TechnologyController is executed");
		List<Technology> techList = technologyService.getAllTechnologies();
		return ResponseEntity.ok().body(techList);

	}

	@DeleteMapping("deleteTech/{tecId}")
	public ResponseEntity<Map<String, Object>> deleteTechnology(@PathVariable("tecId") Integer tecId) {
		logger.info("deleteTechnology method of TechnologyController is executed");
		Map<String, Object> response = new HashMap<>();
		technologyService.deleteTechnology(tecId);
		response.put("status", "Deleted");
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("editTech/{tecId}")
	public ResponseEntity<Technology> editTechnology(@PathVariable("tecId") Integer tecId) {
		logger.info("editTechnology method of TechnologyController is executed");
		Technology update = technologyService.editTechnology(tecId);
		return ResponseEntity.ok().body(update);
	}

}
