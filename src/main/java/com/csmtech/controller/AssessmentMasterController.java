package com.csmtech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.dto.AssessmentMasterDto;
import com.csmtech.entity.AssessmentMaster;
import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.service.AssessmentMasterService;
import com.csmtech.service.ScheduleForMasterService;

@CrossOrigin
@RestController
public class AssessmentMasterController {
	
	private static final Logger logger = LoggerFactory.getLogger(AssesmentExcelUploadController.class);

	@Autowired
	private AssessmentMasterService assessmentMasterService;
	@Autowired
	private ScheduleForMasterService scheduleForMasterService;

	@GetMapping("/getAllScheduleNames")
	public ResponseEntity<?> getAllScheduleNames() {
		logger.info("getAllScheduleNames method of AssessmentMasterController is executed");
		try {
		List<ScheduleForMaster> scheduleForMasters = scheduleForMasterService.getAllScheduleForm();
		return new ResponseEntity<>(scheduleForMasters, HttpStatus.OK);
	} catch (Exception e) {
        logger.error("Unexpected error occurred while retrieving schedule names", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
	}

	@PostMapping(value = "/assessmentSave")
	public ResponseEntity<AssessmentMaster> saveAssessment(@RequestBody AssessmentMasterDto assessmentDto) {
		logger.info("saveAssessment method of AssessmentMasterController is executed");
		try {
			AssessmentMaster assesmentM = assessmentMasterService.saveAssessment(assessmentDto);
			return ResponseEntity.ok().body(assesmentM);
		} catch (Exception e) {
			logger.info("Exception occured in saveAssessment method of AssessmentMasterController:"+e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@GetMapping("/viewAssessment")
	public ResponseEntity<List<Map<String, Object>>> viewAssessmentData() {
		logger.info("viewAssessmentData method of AssessmentMasterController is executed");
		try {
		List<Map<String, Object>> assessmentData = assessmentMasterService.viewAssessmentData();
		return ResponseEntity.ok().body(assessmentData);
		} catch (Exception e) {
			logger.info("Exception occured in viewAssessmentData method of AssessmentMasterController:"+e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<Map<String, Object>> deleteAssessment(@PathVariable("id") Integer id) {
		logger.info("deleteAssessment method of AssessmentMasterController is executed");
		Map<String, Object> response = new HashMap<>();
		try {
		assessmentMasterService.deleteAssessment(id);
		response.put("status", "deleted");
		return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.error("Error occurred while deleting assessment with ID " + id, ex);
	        response.put("error", "Internal Server Error");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("edit/{id}")
	public ResponseEntity<Map<String, Object>> getAssessmentById(@PathVariable("id") Integer id) {
		logger.info("getAssessmentById method of AssessmentMasterController is executed");
		try {
		Map<String, Object> update = assessmentMasterService.getAssessmentById(id);
		return ResponseEntity.ok().body(update);
		} catch (Exception e) {
			logger.info("Exception occured in getAssessmentById method of AssessmentMasterController:"+e.getMessage());
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	//Getting Questionar for Schedule
	@GetMapping("/getQuestionarByScheduleId/{scheduleId}")
	public ResponseEntity<?> getQuestionarByScheduleId(@PathVariable(name="scheduleId") Integer scheduleId) {
		logger.info("getQuestionarByScheduleId method of AssessmentMasterController is executed");
		System.out.println(scheduleId);
		return assessmentMasterService.getQuestionarByScheduleId(scheduleId);
	}
	
	
}
