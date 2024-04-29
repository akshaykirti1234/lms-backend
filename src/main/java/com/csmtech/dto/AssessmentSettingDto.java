package com.csmtech.dto;

import java.util.List;

import lombok.Data;

@Data
public class AssessmentSettingDto {
	
	
	private Integer assessmentSettingId;

    private Integer module;

    private Integer subModule;

    private List<AssessmentSettingScheduleDto> scheduleWiseList;
    
	

}
