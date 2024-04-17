package com.csmtech.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.entity.SubModule;
import com.csmtech.service.ScheduleForMasterService;
import com.csmtech.service.SubModuleService;

@CrossOrigin
@RestController
@RequestMapping("/assign")
public class AssignControl {
	
	Logger logger=LoggerFactory.getLogger(AssignControl.class);

	@Autowired
	private SubModuleService subModuleService;
	@Autowired
	private ScheduleForMasterService scheduleForMasterService;

	@GetMapping("/getAllSubModules")
	public ResponseEntity<?> getAllSubModules() {
		logger.info("getAllSubModules method of AssignControl is executed");
		List<SubModule> subModulesList = subModuleService.getAllSubModules();
		return new ResponseEntity<>(subModulesList, HttpStatus.OK);
	}

	@GetMapping("/getAllScheduleForm")
	public ResponseEntity<?> getAllScheduleForm() {
		logger.info("getAllScheduleForm method of AssignControl is executed");
		List<ScheduleForMaster> scheduleForMasters = scheduleForMasterService.getAllScheduleForm();
		return new ResponseEntity<>(scheduleForMasters, HttpStatus.OK);
	}
}
