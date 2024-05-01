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
	Logger logger = LoggerFactory.getLogger(SessionAssessmentMasterController.class);

	@Autowired
	private SessionAssessmentMasterService sessionAssessmentMasterService;

	@GetMapping("/getApi")
	public ResponseEntity<String> getApi() {
		return ResponseEntity.ok().body("Hii Assessment Setting is working");

	}

	@GetMapping("/getQuestionarBySessionId/{sessionId}")
	public ResponseEntity<?> getQuestionarBySessionId(@PathVariable Integer sessionId) {
		System.out.println(sessionId);
		return sessionAssessmentMasterService.getQuestionarBySessionId(sessionId);
	}

	@PostMapping(value = "/assessmentSessionSave")
	public ResponseEntity<SessionAssessmentMaster> saveAssessmentSession(
			@RequestBody SessionAssessmentMasterDto assessmentSessionDto) {
		logger.info("saveAssessmentSession method of SessionAssessmentMasterController is executed");
		try {
			SessionAssessmentMaster assesmentSesM = sessionAssessmentMasterService
					.saveAssessmentSession(assessmentSessionDto);
			// System.out.println(assessmentSessionDto);

			return ResponseEntity.ok().body(assesmentSesM);
		} catch (Exception e) {
			logger.info("Exception occured in saveAssessmentSession method of SessionAssessmentMasterController:"
					+ e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@GetMapping("/viewAssessmentForSession")
	public ResponseEntity<List<Map<String, Object>>> viewAssessmentForSessionData() {
		logger.info("viewAssessmentForSessionData method of AssessmentMasterController is executed");
		List<Map<String, Object>> assessmentSessData = sessionAssessmentMasterService.viewAssessmentForSessionData();
		return ResponseEntity.ok().body(assessmentSessData);

	}

	@DeleteMapping("deleteAssSession/{id}")
	public ResponseEntity<Map<String, Object>> deleteAssessmentSession(@PathVariable("id") Integer id) {
		logger.info("deleteAssessmentSession method of AssessmentMasterController is executed");
		Map<String, Object> responseSe = new HashMap<>();
		sessionAssessmentMasterService.deleteAssessmentSession(id);
		responseSe.put("status", "deleted");
		return ResponseEntity.ok().body(responseSe);
	}

	@GetMapping("editAssessmentSession/{id}")
	public ResponseEntity<Map<String, Object>> getAssessmentSessionById(@PathVariable("id") Integer id) {
		logger.info("getAssessmentById method of AssessmentMasterController is executed");
		Map<String, Object> updateSes = sessionAssessmentMasterService.getAssessmentSessionById(id);
		System.err.println(updateSes);
		return ResponseEntity.ok().body(updateSes);
	}

}
