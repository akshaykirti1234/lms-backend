package com.csmtech.dto;

import com.csmtech.entity.ModuleMaster;
import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.entity.SessionMaster;
import com.csmtech.entity.SubModule;

import lombok.Data;

@Data
public class SessionAssessmentMasterDto {
	private Integer assessmentId;

	private Integer  moduleId;

	
	private Integer submoduleId;

	private Integer scheduleForId;

	
	private Integer sessionId;

	
	private String question;

	private String option1;

	private String option2;

	private String option3;

	private String option4;

	private String answer;

}
