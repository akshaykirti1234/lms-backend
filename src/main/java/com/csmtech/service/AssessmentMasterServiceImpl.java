package com.csmtech.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.dto.AssessmentMasterDto;
import com.csmtech.entity.AssessmentMaster;
import com.csmtech.entity.SessionAssessmentMaster;
import com.csmtech.repository.AssessmentMasterRepository;
import com.csmtech.repository.ScheduleForMasterRepository;
import com.csmtech.repository.SessionAssessmentMasterRepository;

@Service
public class AssessmentMasterServiceImpl implements AssessmentMasterService {
	
	Logger logger=LoggerFactory.getLogger(AssessmentMasterServiceImpl.class);
	
	@Autowired
	private AssessmentMasterRepository assessmentMasterRepository;

	@Autowired
	ScheduleForMasterRepository scheduleForMasterRepository;
	
	@Autowired
	SessionAssessmentMasterRepository sessionAssessmentMasterRepository;

	@Override
	public AssessmentMaster saveAssessment(AssessmentMasterDto assessmentDto) {
	    try {
	        logger.info("saveAssessment method of AssessmentMasterServiceImpl is executed");
	        if (assessmentDto.getSessionId() != null) {
	            // If sessionId is present, save data in SessionAssessmentMaster
	            SessionAssessmentMaster sessionSave = new SessionAssessmentMaster();
	            sessionSave.setSessionAssesmentMasterId(assessmentDto.getAssessmentId());
	            sessionSave.setModuleId(assessmentDto.getModuleId());
	            sessionSave.setSubmoduleId(assessmentDto.getSubmoduleId());
	            sessionSave.setScheduleForId(assessmentDto.getScheduleForId());
	            sessionSave.setSessionId(assessmentDto.getSessionId());
	            sessionSave.setQuestion(assessmentDto.getQuestion());
	            sessionSave.setOption1(assessmentDto.getOption1());
	            sessionSave.setOption2(assessmentDto.getOption2());
	            sessionSave.setOption3(assessmentDto.getOption3());
	            sessionSave.setOption4(assessmentDto.getOption4());
	            sessionSave.setAnswer(assessmentDto.getAnswer());
	            sessionSave.setCreatedBy(1);
	            sessionSave.setUpdatedBy(1);
	            SessionAssessmentMaster sessionMasterSave = sessionAssessmentMasterRepository.save(sessionSave);
	            // Check if assessment is saved successfully
	            if (sessionMasterSave != null) {
	                // Update flag in another table
	                int updatedRows = scheduleForMasterRepository.updateFlagByScheduleId(assessmentDto.getScheduleForId());
	                // Handle the update status if needed
	            }
	            return null; // Return null as SessionAssessmentMaster is being saved
	        } else {
	            // If sessionId is not present, save data in AssessmentMaster
	            AssessmentMaster assessmentMaster = new AssessmentMaster();
	            assessmentMaster.setAssessmentMasterId(assessmentDto.getAssessmentId());
	            assessmentMaster.setModuleId(assessmentDto.getModuleId());
	            assessmentMaster.setSubmoduleId(assessmentDto.getSubmoduleId());
	            assessmentMaster.setScheduleForId(assessmentDto.getScheduleForId());
	            assessmentMaster.setQuestion(assessmentDto.getQuestion());
	            assessmentMaster.setOption1(assessmentDto.getOption1());
	            assessmentMaster.setOption2(assessmentDto.getOption2());
	            assessmentMaster.setOption3(assessmentDto.getOption3());
	            assessmentMaster.setOption4(assessmentDto.getOption4());
	            assessmentMaster.setAnswer(assessmentDto.getAnswer());
	            assessmentMaster.setCreatedBy(1);
	            assessmentMaster.setUpdatedBy(1);
	            AssessmentMaster save = assessmentMasterRepository.save(assessmentMaster);
	            // Check if assessment is saved successfully
	            if (save != null) {
	                // Update flag in another table
	                int updatedRows = scheduleForMasterRepository.updateFlagByScheduleId(assessmentDto.getScheduleForId());
	                // Handle the update status if needed
	            }
	            return save;
	        }
	    } catch (Exception e) {
	        logger.info("Error occurred in saveAssessment method of AssessmentMasterServiceImpl: " + e.getMessage());
	        e.printStackTrace();
	        return null;
	    }
	}

	@Override
	public List<Map<String, Object>> viewAssessmentData() {
		logger.info("viewAssessmentData method of AssessmentMasterServiceImpl is executed");
		return assessmentMasterRepository.getAllAssesmentdata();

	}

	@Override
	public void deleteAssessment(Integer id) {
		logger.info("deleteAssessment method of AssessmentMasterServiceImpl is executed");
		assessmentMasterRepository.deleteAssessment(id);

	}

	@Override
	public Map<String, Object> getAssessmentById(Integer id) {
		logger.info("getAssessmentById method of AssessmentMasterServiceImpl is executed");
		return assessmentMasterRepository.updateAssessment(id);
	}

	@Override
	public List<Map<String, Object>> viewAssessmentForSessionData() {
		return assessmentMasterRepository.viewAssessmentForSessionData();
	}

	@Override
	public void deleteAssessmentSession(Integer id) {
		assessmentMasterRepository.deleteAssessmentSession(id);
	}

	@Override
	public Map<String, Object> getAssessmentSessionById(Integer id) {
		return assessmentMasterRepository.getAssessmentSessionById(id);
	}
}
