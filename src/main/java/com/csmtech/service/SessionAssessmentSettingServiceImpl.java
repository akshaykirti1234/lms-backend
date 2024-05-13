package com.csmtech.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.dto.SessionAssessmentSettingDto;
import com.csmtech.dto.SessionAssessmentSettingSessionDto;
import com.csmtech.entity.ModuleMaster;
import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.entity.SessionAssessmentSetting;
import com.csmtech.entity.SessionMaster;
import com.csmtech.entity.SubModule;
import com.csmtech.repository.SessionAssessmentSettingRepository;

@Service
public class SessionAssessmentSettingServiceImpl implements SessionAssessmentSettingService {

	private static final Logger logger=LoggerFactory.getLogger(SessionAssessmentSettingServiceImpl.class);
	@Autowired
	private SessionAssessmentSettingRepository sessionAssessmentSettingRepository;
	
	@Override
	public SessionAssessmentSetting saveSessionAssessmentSetting(SessionAssessmentSettingDto sessionAssessmentSettingDto) {
		logger.info("saveSessionAssessmentSetting method of SessionAssessmentSettingServiceImpl is executed");
		
		SessionAssessmentSetting sessionAssessmentSetting=null;
		
		ModuleMaster moduleMaster=new ModuleMaster();
		moduleMaster.setModuleId(sessionAssessmentSettingDto.getModule());
		
		SubModule subModule=new SubModule();
		subModule.setSubmoduleId(sessionAssessmentSettingDto.getSubModule());
		
		ScheduleForMaster scheduleForMaster=new ScheduleForMaster();
		scheduleForMaster.setScheduleForId(sessionAssessmentSettingDto.getSchedule());
		
		SessionMaster sessionMaster=new SessionMaster();
		
		
		for(SessionAssessmentSettingSessionDto SessionDto:sessionAssessmentSettingDto.getSessionWiseList()) {
			sessionAssessmentSetting=new SessionAssessmentSetting();
		
			sessionAssessmentSetting.setModuleMaster(moduleMaster);
			
			sessionAssessmentSetting.setSubModule(subModule);
			
			sessionAssessmentSetting.setScheduleForMaster(scheduleForMaster);
			
			sessionMaster.setSessionId(SessionDto.getSessionId());
			sessionAssessmentSetting.setSessionMaster(sessionMaster);
			
			sessionAssessmentSetting.setNumberOfQuestion(SessionDto.getNumberOfQuestions());
			sessionAssessmentSetting.setPassingPercentage(SessionDto.getPassingPercentage());
			sessionAssessmentSettingRepository.save(sessionAssessmentSetting);
		}
		
		return  sessionAssessmentSetting;
	}

	@Override
	public List<Map<String, Object>> getSessionAssessmentSetting() {
		logger.info("getSessionAssessmentSetting method of SessionAssessmentSettingServiceImpl is executed");
		return sessionAssessmentSettingRepository.getSessionAssessmentSetting();
	}

	@Override
	public Map<String, Object> getSessionAssessmentSettingById(Integer sessionAssessmentSettingId) {
		logger.info("getSessionAssessmentSettingById method of SessionAssessmentSettingServiceImpl is executed");
		return sessionAssessmentSettingRepository.getSessionAssessmentSettingById(sessionAssessmentSettingId) ;
	}

	@Override
	public void deleteSessionAssessmentSetting(Integer sessionAssessmentSettingId) {
		logger.info("deleteSessionAssessmentSetting method of SessionAssessmentSettingServiceImpl is executed");
		sessionAssessmentSettingRepository.deleteSessionAssessmentSetting(sessionAssessmentSettingId);
		
	}

	@Override
	public void updateSessionAssessmentSetting(Integer assessmentSettingId, Integer noOfQuestions , Double passingPercentage) {
		logger.info("updateSessionAssessmentSetting method of SessionAssessmentSettingServiceImpl is executed");
		sessionAssessmentSettingRepository.updateSessionAssessmentSetting(assessmentSettingId, noOfQuestions ,passingPercentage);
	}

	@Override
	public List<Map<String, Object>> getSessionforAssessmentSetting(Integer scheduleForId) {
		logger.info("getSessionforAssessmentSetting method of SessionAssessmentSettingServiceImpl is executed");
		return sessionAssessmentSettingRepository.getSessionforAssessmentSetting(scheduleForId);
	}

}
