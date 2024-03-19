package com.csmtech.dto;

import java.util.Date;

import lombok.Data;

@Data
public class AssessmentMasterDto {
	private Integer assessmentId;

	private Integer scheduleForId;

	private String question;

	private String option1;

	private String option2;

	private String option3;

	private String option4;

	private String answer;

	private Integer createdBy;

	private Date createdOn;

	private Integer updatedBy;

	private Date updatedOn;

	private Boolean deletedFlag;
}
