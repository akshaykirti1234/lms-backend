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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.dto.SessionAssessmentMasterDto;
import com.csmtech.entity.SessionAssessmentMaster;
import com.csmtech.service.SessionAssessmentMasterService;

@CrossOrigin
@RestController
@RequestMapping("/api/sessionAssessment")
public class SessionAssessmentMasterController {
	private static final Logger logger = LoggerFactory.getLogger(SessionAssessmentMasterController.class);

	@Autowired
	private SessionAssessmentMasterService sessionAssessmentMasterService;

	@GetMapping("/getApi")
	public ResponseEntity<String> getApi() {
		return ResponseEntity.ok().body("Hii Assessment Setting is working");
	}

	@GetMapping("/getQuestionarBySessionId/{sessionId}")
	public ResponseEntity<?> getQuestionarBySessionId(@PathVariable Integer sessionId) {
		logger.info("getQuestionarBySessionId method of SessionAssessmentMasterController is executed");
		try {
			return sessionAssessmentMasterService.getQuestionarBySessionId(sessionId);
		} catch (Exception e) {
			logger.error("Error occurred while fetching questions by session ID {}", sessionId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing your request");
		}
	}

	@PostMapping(value = "/assessmentSessionSave")
	public ResponseEntity<?> saveAssessmentSession(@RequestBody SessionAssessmentMasterDto assessmentSessionDto) {
		logger.info("saveAssessmentSession method of SessionAssessmentMasterController is executed");
		try {
			SessionAssessmentMaster assessmentSesM = sessionAssessmentMasterService
					.saveAssessmentSession(assessmentSessionDto);
			return ResponseEntity.ok().body(assessmentSesM);
		} catch (Exception e) {
			logger.error("Exception occurred in saveAssessmentSession method of SessionAssessmentMasterController: {}",
					e.getMessage(), e);
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("status", "error");
			errorResponse.put("message", "An error occurred while saving the assessment session");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	@GetMapping("/viewAssessmentForSession")
	public ResponseEntity<?> viewAssessmentForSessionData() {
		logger.info("viewAssessmentForSessionData method of AssessmentMasterController is executed");
		try {
			List<Map<String, Object>> assessmentSessData = sessionAssessmentMasterService
					.viewAssessmentForSessionData();
			return ResponseEntity.ok().body(assessmentSessData);
		} catch (Exception e) {
			logger.error("Error occurred while fetching assessment data", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing your request");
		}
	}

	@DeleteMapping("deleteAssSession/{id}")
	public ResponseEntity<?> deleteAssessmentSession(@PathVariable("id") Integer id) {
		logger.info("deleteAssessmentSession method of AssessmentMasterController is executed");
		try {
			sessionAssessmentMasterService.deleteAssessmentSession(id);
			Map<String, Object> response = new HashMap<>();
			response.put("status", "deleted");
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			logger.error("Error occurred while deleting assessment session with ID {}", id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing your request");
		}
	}

	@GetMapping("editAssessmentSession/{id}")
	public ResponseEntity<?> getAssessmentSessionById(@PathVariable("id") Integer id) {
		logger.info("getAssessmentById method of AssessmentMasterController is executed");
		try {
			Map<String, Object> updateSes = sessionAssessmentMasterService.getAssessmentSessionById(id);
			return ResponseEntity.ok().body(updateSes);
		} catch (Exception e) {
			logger.error("Error occurred while fetching assessment session by ID {}", id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing your request");
		}
	}

	@GetMapping("/check-session-questions/{id}")
	public ResponseEntity<?> checkIfSessionQsnPreparedForScheduleId(@PathVariable("id") Integer id) {
		logger.info("checkIfSessionQsnPreparedForScheduleId method of AssessmentMasterController is executed");
		try {
			Long result = sessionAssessmentMasterService.checkIfSessionQsnPreparedForScheduleId(id);
			System.err.println("**************" + result);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			logger.error("Error occurred while fetching assessment session by ID {}", id, e);
			System.err.println("**************");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing your request");
		}
	}
}
