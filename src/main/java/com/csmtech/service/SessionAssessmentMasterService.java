package com.csmtech.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.csmtech.dto.SessionAssessmentMasterDto;
import com.csmtech.entity.SessionAssessmentMaster;

public interface SessionAssessmentMasterService {

	SessionAssessmentMaster saveAssessmentSession(SessionAssessmentMasterDto assessmentSessionDto);

	List<Map<String, Object>> viewAssessmentForSessionData();

	void deleteAssessmentSession(Integer id);

	Map<String, Object> getAssessmentSessionById(Integer id);

	ResponseEntity<?> getQuestionarBySessionId(Integer sessionId);

	// For upload excel

	List<Map<String, Object>> retriveModuleTypeList();

	List<Map<String, Object>> retriveSubModuleList();

	List<Map<String, Object>> retriveScheduleForList();

	List<Map<String, Object>> retriveSessionList();

	ResponseEntity<Map<String, Object>> uploadSessionExcelData(MultipartFile file);

	Map<String, Object> checkIfSessionQsnPreparedForScheduleId(Integer id);

}
