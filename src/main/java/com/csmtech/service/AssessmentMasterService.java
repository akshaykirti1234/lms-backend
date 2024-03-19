package com.csmtech.service;

import java.util.List;
import java.util.Map;

import com.csmtech.dto.AssessmentMasterDto;
import com.csmtech.entity.AssessmentMaster;

public interface AssessmentMasterService {
	AssessmentMaster saveAssessment(AssessmentMasterDto assessmentDto);

	List<Map<String, Object>> viewAssessmentData();

	void deleteAssessment(Integer id);

	Map<String, Object> getAssessmentById(Integer id);
}
