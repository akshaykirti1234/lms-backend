package com.csmtech.service;

import java.util.List;
import java.util.Map;

import com.csmtech.dto.SessionAssessmentSettingDto;
import com.csmtech.entity.SessionAssessmentSetting;

public interface SessionAssessmentSettingService {

	SessionAssessmentSetting saveSessionAssessmentSetting(SessionAssessmentSettingDto sessionAssessmentSettingDto);

	List<Map<String, Object>> getSessionAssessmentSetting();

	Map<String, Object> getSessionAssessmentSettingById(Integer sessionAssessmentSettingId);

	void deleteSessionAssessmentSetting(Integer sessionAssessmentSettingId);

	void updateSessionAssessmentSetting(Integer assessmentSettingId, Integer noOfQuestions , Double passingPercentage);

	List<Map<String, Object>> getSessionforAssessmentSetting(Integer scheduleForId);

}
