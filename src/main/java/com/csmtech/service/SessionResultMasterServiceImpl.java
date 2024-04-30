package com.csmtech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csmtech.dto.SessionResultDto;
import com.csmtech.entity.SessionAssessmentMaster;
import com.csmtech.entity.SessionResultMaster;
import com.csmtech.entity.UserMaster;
import com.csmtech.repository.SessionAssessmentMasterRepository;
import com.csmtech.repository.SessionResultMasterRepository;

@Service
public class SessionResultMasterServiceImpl implements SessionResultMasterService {

	@Autowired
	private SessionResultMasterRepository sessionResultMasterRepository;
	@Autowired
	private SessionAssessmentMasterRepository sessionAssessmentMasterRepository;

	@Override
	@Transactional
	public ResponseEntity<?> saveSessionResult(List<SessionResultDto> responsePayload) {

		Integer userId = 0;
		Integer sessionId = 0;

		for (SessionResultDto value : responsePayload) {
			// Retrieve sessionAssessmentMaster from repository
			SessionAssessmentMaster sessionAssessmentMaster = sessionAssessmentMasterRepository
					.findById(value.getSessionAssessmentMasterId())
					.orElseThrow(() -> new RuntimeException("Session Assessment Master not found"));

			// get userId and sessionId
			userId = value.getUserId();
			sessionId = sessionAssessmentMaster.getSessionMaster().getSessionId();

//			Delete sessionResultMaster by userId and sessionId
			sessionResultMasterRepository.deleteResultBySessionIdAndUserId(sessionId, userId);
		}

		for (SessionResultDto value : responsePayload) {
			// Retrieve sessionAssessmentMaster from repository
			SessionAssessmentMaster sessionAssessmentMaster = sessionAssessmentMasterRepository
					.findById(value.getSessionAssessmentMasterId())
					.orElseThrow(() -> new RuntimeException("Session Assessment Master not found"));

			// Create UserMaster object with userId
			UserMaster userMaster = new UserMaster();
			userMaster.setUserId(value.getUserId());

			// get userId and sessionId
			userId = value.getUserId();
			sessionId = sessionAssessmentMaster.getSessionMaster().getSessionId();

			// Create SessionResultMaster object
			SessionResultMaster sessionResultMaster = new SessionResultMaster();
			sessionResultMaster.setUserMaster(userMaster);
			sessionResultMaster.setSessionAssessmentMaster(sessionAssessmentMaster);
			sessionResultMaster.setModuleMaster(sessionAssessmentMaster.getModuleMaster());
			sessionResultMaster.setSubModule(sessionAssessmentMaster.getSubModule());
			sessionResultMaster.setScheduleForMaster(sessionAssessmentMaster.getScheduleForMaster());
			sessionResultMaster.setSessionMaster(sessionAssessmentMaster.getSessionMaster());
			sessionResultMaster.setActualAnswer(sessionAssessmentMaster.getAnswer());
			sessionResultMaster.setGivenAnswer(value.getOption());
			if (value.getOption() != null) {
				sessionResultMaster.setResultStatus(value.getOption().equals(sessionAssessmentMaster.getAnswer()));
			} else {
				sessionResultMaster.setResultStatus(false);

			}

			// Save sessionResultMaster to database
			SessionResultMaster save = sessionResultMasterRepository.save(sessionResultMaster);
		}

		List<SessionResultMaster> results = sessionResultMasterRepository.getResultBySessionIdAndUserID(sessionId,
				userId);
		int s = 0;
		for (SessionResultMaster sessionResultMaster : results) {
			if (sessionResultMaster.getResultStatus() == true) {
				s += 1;
			}
		}

		Double resultCal = (double) (s * 100 / results.size());
		System.out.println(resultCal);

		return ResponseEntity.ok(resultCal);
	}

}
