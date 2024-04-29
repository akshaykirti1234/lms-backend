package com.csmtech.service;

import org.springframework.http.ResponseEntity;

public interface SessionAssessmentMasterService {

	ResponseEntity<?> getQuestionarBySessionId(Integer sessionId);

}
