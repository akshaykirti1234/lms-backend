package com.csmtech.service;

import java.util.List;
import java.util.Map;

import com.csmtech.dto.AssessmentSettingDto;
import com.csmtech.entity.AssessmentSetting;

public interface AssessmentSettingService {
	

	AssessmentSetting saveAssessmentSetting(AssessmentSettingDto assessmentSettingDto);

	List<Map<String, Object>> getAssessmentSetting();

	Map<String, Object> getAssessmentSettingById(Integer assessmentSettingId);

	void deleteAssessmentSetting(Integer assessmentSettingId);

	void updateAssessmentSetting(Integer assessmentSettingId, Integer noOfQuestions);

}
