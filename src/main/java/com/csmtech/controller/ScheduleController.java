package com.csmtech.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.csmtech.exceptions.IsLastSessionException;
import com.csmtech.service.AuthorService;
import com.csmtech.service.ScheduleForMasterService;
import com.csmtech.service.SubModuleService;
import com.csmtech.service.TechnologyService;

@CrossOrigin
@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

	private static final  Logger logger = LoggerFactory.getLogger(ScheduleController.class);

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
        logger.info("getAllScheduleForm method of ScheduleController is executed");
        try {
            List<ScheduleForMaster> scheduleForMasters = scheduleForMasterService.getAllScheduleForm();
            return new ResponseEntity<>(scheduleForMasters, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception occurred in getAllScheduleForm method: {}", e.getMessage(), e);
            return new ResponseEntity<>("An error occurred while fetching schedule forms", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllTechnologies")
    public ResponseEntity<?> getAllTechnologies() {
        logger.info("getAllTechnologies method of ScheduleController is executed");
        try {
            List<Technology> technologyList = technologyService.getAllTechnologies();
            return new ResponseEntity<>(technologyList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception occurred in getAllTechnologies method: {}", e.getMessage(), e);
            return new ResponseEntity<>("An error occurred while fetching technologies", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllSubModules")
    public ResponseEntity<?> getAllSubModules() {
        logger.info("getAllSubModules method of ScheduleController is executed");
        try {
            List<SubModule> subModulesList = subModuleService.getAllSubModules();
            return new ResponseEntity<>(subModulesList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception occurred in getAllSubModules method: {}", e.getMessage(), e);
            return new ResponseEntity<>("An error occurred while fetching sub-modules", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllAutohors")
    public ResponseEntity<?> getAllAutohors() {
        logger.info("getAllAutohors method of ScheduleController is executed");
        try {
            List<Author> authorsList = authorService.getAllAuthors();
            return new ResponseEntity<>(authorsList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception occurred in getAllAutohors method: {}", e.getMessage(), e);
            return new ResponseEntity<>("An error occurred while fetching authors", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    
}

	@SuppressWarnings("unused")
	@PostMapping("/saveScheduleForm")
	public ResponseEntity<?> saveScheduleForm(@RequestBody ScheduleForMasterDto scheduleForMasterDto) {
		logger.info("saveScheduleForm method of ScheduleController is executed");
		ResponseEntity<?> response = null;
		if (scheduleForMasterDto.getScheduleForId() != null) {
			try {
				response = scheduleForMasterService.updateScheduleForm(scheduleForMasterDto);
			} catch (IsLastSessionException e) {
				return ResponseEntity.badRequest().build();
			}
		} else {
			response = scheduleForMasterService.saveScheduleForm(scheduleForMasterDto);
		}
		return response;
	}

	@PutMapping("updateScheduleFor/{scheduleForId}")
    public ResponseEntity<?> updateScheduleFor(@PathVariable Integer scheduleForId) {
        logger.info("updateScheduleFor method of ScheduleController is executed");
        try {
            ResponseEntity<?> response = scheduleForMasterService.updateScheduleFor(scheduleForId);
            return response;
        } catch (Exception e) {
            logger.error("Exception occurred in updateScheduleFor method: {}", e.getMessage(), e);
            return new ResponseEntity<>("An error occurred while updating schedule", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteScheduleFor/{scheduleForId}")
    public ResponseEntity<?> deleteScheduleFor(@PathVariable Integer scheduleForId) {
        logger.info("deleteScheduleFor method of ScheduleController is executed");
        try {
            ResponseEntity<?> response = scheduleForMasterService.deleteScheduleFor(scheduleForId);
            System.err.println(response.getBody());
            return response;
        } catch (Exception e) {
            logger.error("Exception occurred in deleteScheduleFor method: {}", e.getMessage(), e);
            return new ResponseEntity<>("An error occurred while deleting schedule", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
