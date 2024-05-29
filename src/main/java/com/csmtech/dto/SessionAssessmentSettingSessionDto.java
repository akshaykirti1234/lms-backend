package com.csmtech.dto;

import lombok.Data;

@Data
public class SessionAssessmentSettingSessionDto {
	
	public SessionAssessmentSettingSessionDto(int i, int j, double d) {
	}

	private Integer sessionId;

	private Integer numberOfQuestions;
	
	private Double passingPercentage;


}
