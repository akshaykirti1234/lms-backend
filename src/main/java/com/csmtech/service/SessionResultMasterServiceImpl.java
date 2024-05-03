package com.csmtech.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csmtech.dto.SessionResultDto;
import com.csmtech.entity.ModuleMaster;
import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.entity.SessionAssessmentMaster;
import com.csmtech.entity.SessionAssessmentSetting;
import com.csmtech.entity.SessionMaster;
import com.csmtech.entity.SessionResultMaster;
import com.csmtech.entity.SessionResultStatus;
import com.csmtech.entity.SubModule;
import com.csmtech.entity.UserMaster;
import com.csmtech.repository.SessionAssessmentMasterRepository;
import com.csmtech.repository.SessionAssessmentSettingRepository;
import com.csmtech.repository.SessionResultMasterRepository;
import com.csmtech.repository.SessionResultStatusRepository;

@Service
public class SessionResultMasterServiceImpl implements SessionResultMasterService {

	@Autowired
	private SessionResultMasterRepository sessionResultMasterRepository;
	@Autowired
	private SessionAssessmentMasterRepository sessionAssessmentMasterRepository;
	@Autowired
	private SessionResultStatusRepository sessionResultStatusRepository;
	@Autowired
	private SessionAssessmentSettingRepository sessionAssessmentSettingRepository;

	@Override
	@Transactional
	public ResponseEntity<?> saveSessionResult(List<SessionResultDto> responsePayload) {

		Integer userId = 0;
		Integer sessionId = 0;
		Integer moduleId = 0;
		Integer subModuleId = 0;
		Integer scheduleForId = 0;

		for (SessionResultDto value : responsePayload) {
			// Retrieve sessionAssessmentMaster from repository
			SessionAssessmentMaster sessionAssessmentMaster = sessionAssessmentMasterRepository
					.findById(value.getSessionAssessmentMasterId())
					.orElseThrow(() -> new RuntimeException("Session Assessment Master not found"));

			// get userId and sessionId
			userId = value.getUserId();
			sessionId = sessionAssessmentMaster.getSessionMaster().getSessionId();
			moduleId = sessionAssessmentMaster.getModuleMaster().getModuleId();
			subModuleId = sessionAssessmentMaster.getSubModule().getSubmoduleId();
			scheduleForId = sessionAssessmentMaster.getScheduleForMaster().getScheduleForId();

//			Delete sessionResultMaster by userId and sessionId
			sessionResultMasterRepository.deleteResultBySessionIdAndUserId(sessionId, userId);

		}

//		Delete sessionResultStatusMaster by userId and sessionId
//		sessionResultStatusRepository.deleteResultStatusBySessionIdAndUserId(sessionId, userId);

		for (SessionResultDto value : responsePayload) {
			// Retrieve sessionAssessmentMaster from repository
			SessionAssessmentMaster sessionAssessmentMaster = sessionAssessmentMasterRepository
					.findById(value.getSessionAssessmentMasterId())
					.orElseThrow(() -> new RuntimeException("Session Assessment Master not found"));

			// Create UserMaster object with userId
			UserMaster userMaster = new UserMaster();
			userMaster.setUserId(value.getUserId());

			// get userId,moduleId,subModuleid,schedukeForId,sessionId
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

		// ************************************************
		// Save SessionResultStatus
		// ************************************************
		int sum = 0;
		for (SessionResultMaster sessionResultMaster : results) {
			if (sessionResultMaster.getResultStatus() == true) {
				sum += 1;
			}
		}

		Double percentage = (double) (sum * 100 / results.size());

		SessionResultStatus sessionResultStatus = new SessionResultStatus();

		ModuleMaster moduleMaster = new ModuleMaster();
		moduleMaster.setModuleId(moduleId);
		SubModule subModule = new SubModule();
		subModule.setSubmoduleId(subModuleId);
		ScheduleForMaster scheduleForMaster = new ScheduleForMaster();
		scheduleForMaster.setScheduleForId(scheduleForId);
		SessionMaster sessionMaster = new SessionMaster();
		sessionMaster.setSessionId(sessionId);
		UserMaster userMaster = new UserMaster();
		userMaster.setUserId(userId);

		sessionResultStatus.setModuleMaster(moduleMaster);
		sessionResultStatus.setSubModule(subModule);
		sessionResultStatus.setScheduleForMaster(scheduleForMaster);
		sessionResultStatus.setSessionMaster(sessionMaster);
		sessionResultStatus.setUserMaster(userMaster);
		sessionResultStatus.setPercentage(percentage);

		SessionAssessmentSetting sessionAssessmentSetting = sessionAssessmentSettingRepository
				.findBySessionId(sessionId);

		if (percentage >= sessionAssessmentSetting.getPassingPercentage()) {
			sessionResultStatus.setStatusOfResult(true);
		} else {
			sessionResultStatus.setStatusOfResult(false);
		}

		SessionResultStatus save = sessionResultStatusRepository.save(sessionResultStatus);

		Map<String, Object> response = new HashMap<>();
		response.put("percentage", percentage);
		response.put("passingPercentage", sessionAssessmentSetting.getPassingPercentage());

		return ResponseEntity.ok(response);
	}

}
