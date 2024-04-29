package com.csmtech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.service.SessionAssessmentMasterService;

@CrossOrigin
@RestController
@RequestMapping("/api/sessionAssessment")
public class SessionAssessmentMasterController {

	@Autowired
	private SessionAssessmentMasterService sessionAssessmentMasterService;

	@GetMapping("/getQuestionarBySessionId/{sessionId}")
	public ResponseEntity<?> getQuestionarBySessionId(@PathVariable Integer sessionId) {
		System.out.println(sessionId);
		return sessionAssessmentMasterService.getQuestionarBySessionId(sessionId);
	}

}
