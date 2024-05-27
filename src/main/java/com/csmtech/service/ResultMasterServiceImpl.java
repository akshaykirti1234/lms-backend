package com.csmtech.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csmtech.dto.SessionResultDto;
import com.csmtech.entity.AssessmentMaster;
import com.csmtech.entity.AssessmentSetting;
import com.csmtech.entity.ModuleMaster;
import com.csmtech.entity.ResultMaster;
import com.csmtech.entity.ResultStatus;
import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.entity.SubModule;
import com.csmtech.entity.UserMaster;
import com.csmtech.repository.AssessmentMasterRepository;
import com.csmtech.repository.AssessmentSettingRespository;
import com.csmtech.repository.ResultMasterRepository;
import com.csmtech.repository.ResultStatusRepository;

@Service
public class ResultMasterServiceImpl implements ResultMasterService {
	
	@Autowired
	ResultMasterRepository resultMasterRepository;
	
	@Autowired
	AssessmentSettingRespository assessmentSettingRespository;
	
	@Autowired
	AssessmentMasterRepository assessmentMasterRepository;
	
	@Autowired
	ResultStatusRepository resultStatusRepository;
	
	private static final Logger logger=LoggerFactory.getLogger(ResultMasterServiceImpl.class);

	
	@Transactional
	@Override
	public ResponseEntity<?> saveScheduleResult(List<SessionResultDto> responsePayload) {
		logger.info("saveScheduleResult method of ResultMasterServiceImpl is executed");

		Integer userId = 0;
		Integer moduleId = 0;
		Integer subModuleId = 0;
		Integer scheduleForId = 0;

		for (SessionResultDto value : responsePayload) {
			// Retrieve AssessmentMaster from repository
			AssessmentMaster assessmentMaster = assessmentMasterRepository
					.findById(value.getQuestionId())
					.orElseThrow(() -> new RuntimeException("Session Assessment Master not found"));

			// get userId and sessionId
			userId = value.getUserId();
			moduleId = assessmentMaster.getModuleId();
			subModuleId = assessmentMaster.getSubmoduleId();
			scheduleForId = assessmentMaster.getScheduleForId();

//			Delete sessionResultMaster by userId and sessionId
			resultMasterRepository.deleteResultByScheduleIdAndUserId(scheduleForId, userId);

		}

//		Delete sessionResultStatusMaster by userId and sessionId
//		sessionResultStatusRepository.deleteResultStatusBySessionIdAndUserId(sessionId, userId);

		for (SessionResultDto value : responsePayload) {
			// Retrieve sessionAssessmentMaster from repository
			AssessmentMaster assessmentMaster = assessmentMasterRepository
					.findById(value.getQuestionId())
					.orElseThrow(() -> new RuntimeException("Session Assessment Master not found"));

			// Create UserMaster object with userId
			UserMaster userMaster = new UserMaster();
			userMaster.setUserId(value.getUserId());

			// get userId,moduleId,subModuleid,schedukeForId,sessionId
			userId = value.getUserId();
			

			// Create SessionResultMaster object
			ResultMaster resultMaster = new ResultMaster();
			resultMaster.setUserMaster(userMaster);
			resultMaster.setAssessmentMaster(assessmentMaster);
			
			ModuleMaster moduleMaster = new ModuleMaster();
			moduleMaster.setModuleId(assessmentMaster.getModuleId());
			resultMaster.setModuleMaster(moduleMaster);
			
			SubModule subModule=new SubModule();
			subModule.setSubmoduleId(assessmentMaster.getSubmoduleId());
			
			ScheduleForMaster scheduleForMaster =new ScheduleForMaster();
			scheduleForMaster.setScheduleForId(assessmentMaster.getScheduleForId());
			resultMaster.setScheduleForMaster(scheduleForMaster);
			resultMaster.setSubModule(subModule);
			
		    resultMaster.setActualAnswer(assessmentMaster.getAnswer());
			resultMaster.setGivenAnswer(value.getOption());
			if (value.getOption() != null) {
				resultMaster.setResultStatus(value.getOption().equals(assessmentMaster.getAnswer()));
			} else {
				resultMaster.setResultStatus(false);

			}

			// Save sessionResultMaster to database
			ResultMaster save = resultMasterRepository.save(resultMaster);
		}

		List<ResultMaster> results =resultMasterRepository.getResultByScheduleIdAndUserID(scheduleForId,
				userId);

		// ************************************************
		// Save SessionResultStatus
		// ************************************************
		int sum = 0;
		for (ResultMaster resultMaster : results) {
			if (resultMaster.getResultStatus() == true) {
				sum += 1;
			}
		}

		Double percentage = (double) (sum * 100 / results.size());

		ResultStatus resultStatus = new ResultStatus();

		ModuleMaster moduleMaster = new ModuleMaster();
		moduleMaster.setModuleId(moduleId);
		SubModule subModule = new SubModule();
		subModule.setSubmoduleId(subModuleId);
		ScheduleForMaster scheduleForMaster = new ScheduleForMaster();
		scheduleForMaster.setScheduleForId(scheduleForId);
		
	    UserMaster userMaster = new UserMaster();
		userMaster.setUserId(userId);

		resultStatus.setModuleMaster(moduleMaster);
		resultStatus.setSubModule(subModule);
		resultStatus.setScheduleForMaster(scheduleForMaster);
		resultStatus.setUserMaster(userMaster);
		resultStatus.setPercentage(percentage);

		AssessmentSetting assessmentSetting = assessmentSettingRespository
				.findByScheduleForId(scheduleForId);

		if (assessmentSetting.getPassingPercentage() != null) {
			if (percentage >= assessmentSetting.getPassingPercentage()) {
				resultStatus.setStatusOfResult(true);
			} else {
				resultStatus.setStatusOfResult(false);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		ResultStatus save = resultStatusRepository.save(resultStatus);

		Map<String, Object> response = new HashMap<>();
		response.put("percentage", percentage);
		response.put("passingPercentage", assessmentSetting.getPassingPercentage());

		return ResponseEntity.ok(response);
	}


}
