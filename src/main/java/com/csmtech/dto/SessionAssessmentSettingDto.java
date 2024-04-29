package com.csmtech.dto;

import java.util.List;

import lombok.Data;

@Data
public class SessionAssessmentSettingDto {
	
	
	private Integer sessionAssessmentSettingId;

	
	private Integer module;

	
	private Integer subModule;

	
	private Integer schedule;

    private List<SessionAssessmentSettingSessionDto> sessionWiseList;
	

}
