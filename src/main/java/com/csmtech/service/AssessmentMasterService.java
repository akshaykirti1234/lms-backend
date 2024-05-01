package com.csmtech.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.csmtech.dto.AssessmentMasterDto;
import com.csmtech.entity.AssessmentMaster;

public interface AssessmentMasterService {
	AssessmentMaster saveAssessment(AssessmentMasterDto assessmentDto);

	List<Map<String, Object>> viewAssessmentData();

	void deleteAssessment(Integer id);

	Map<String, Object> getAssessmentById(Integer id);

	
	
	
	// For upload excel
	
	List<Map<String, Object>> retriveModuleTypeList();

	List<Map<String, Object>> retriveSubModuleList();

	List<Map<String, Object>> retriveScheduleForList();

	ResponseEntity<Map<String, Object>> uploadExcelData(MultipartFile file);

	//List<Map<String, Object>> retriveSessionList();

	
}
