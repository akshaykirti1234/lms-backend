package com.csmtech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.dto.ScheduleForMasterDto;
import com.csmtech.entity.Author;
import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.entity.SubModule;
import com.csmtech.entity.Technology;
import com.csmtech.service.AuthorService;
import com.csmtech.service.ScheduleForMasterService;
import com.csmtech.service.SubModuleService;
import com.csmtech.service.TechnologyService;

@CrossOrigin
@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

	@Autowired
	private ScheduleForMasterService scheduleForMasterService;
	@Autowired
	private SubModuleService subModuleService;
	@Autowired
	private TechnologyService technologyService;
	@Autowired
	private AuthorService authorService;

	@GetMapping("/getAllScheduleForm")
	public ResponseEntity<?> getAllScheduleForm() {
		List<ScheduleForMaster> scheduleForMasters = scheduleForMasterService.getAllScheduleForm();
		return new ResponseEntity<>(scheduleForMasters, HttpStatus.OK);
	}

	@GetMapping("/getAllTechnologies")
	public ResponseEntity<?> getAllTechnologies() {
		List<Technology> technologyList = technologyService.getAllTechnologies();
		return new ResponseEntity<>(technologyList, HttpStatus.OK);
	}

	@GetMapping("/getAllSubModules")
	public ResponseEntity<?> getAllSubModules() {
		List<SubModule> subModulesList = subModuleService.getAllSubModules();
		return new ResponseEntity<>(subModulesList, HttpStatus.OK);
	}

	@GetMapping("/getAllAutohors")
	public ResponseEntity<?> getAllAutohors() {
		List<Author> authorsList = authorService.getAllAuthors();
		return new ResponseEntity<>(authorsList, HttpStatus.OK);
	}

	@PostMapping("/saveScheduleForm")
	public ResponseEntity<?> saveScheduleForm(@RequestBody ScheduleForMasterDto scheduleForMasterDto) {
		ResponseEntity<?> response = scheduleForMasterService.saveScheduleForm(scheduleForMasterDto);
		return response;
	}

	@PutMapping("updateScheduleFor/{scheduleForId}")
	public ResponseEntity<?> updateScheduleFor(@PathVariable Integer scheduleForId) {
		ResponseEntity<?> response = scheduleForMasterService.updateScheduleFor(scheduleForId);
		return response;
	}

	@DeleteMapping("deleteScheduleFor/{scheduleForId}")
	public ResponseEntity<?> deleteScheduleFor(@PathVariable Integer scheduleForId) {
		ResponseEntity<?> response = scheduleForMasterService.deleteScheduleFor(scheduleForId);
		System.err.println(response.getBody());
		return response;
	}

}
