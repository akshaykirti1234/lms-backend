package com.csmtech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.csmtech.entity.SessionAssessmentMaster;
import com.csmtech.entity.SessionAssessmentSetting;
import com.csmtech.repository.SessionAssessmentMasterRepository;
import com.csmtech.repository.SessionAssessmentSettingRepository;

@Service
public class SessionAssessmentMasterServiceImpl implements SessionAssessmentMasterService {

	@Autowired
	private SessionAssessmentMasterRepository sessionAssessmentMasterRepository;
	@Autowired
	private SessionAssessmentSettingRepository assessmentSettingRepository;

	@Override
	public ResponseEntity<?> getQuestionarBySessionId(Integer sessionId) {
		SessionAssessmentSetting sessionSessionAssessmentSetting = assessmentSettingRepository
				.findFirst1BySessionMaster_SessionId(sessionId);

		Integer noOfQuestion = sessionSessionAssessmentSetting.getNumberOfQuestion();
		System.err.println(sessionId + " " + noOfQuestion);
		List<SessionAssessmentMaster> assessmentMastersList = sessionAssessmentMasterRepository
				.getQuestionarBySessionId(sessionId, noOfQuestion);

		if (assessmentMastersList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No questions found for session ID: " + sessionId);
		} else {
			return ResponseEntity.ok(assessmentMastersList);
		}
	}
}
