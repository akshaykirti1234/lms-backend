package com.csmtech.dto;

import lombok.Data;

@Data
public class SessionResultDto {
	private Integer userId;
	private Integer sessionAssessmentMasterId;
	private String option;
}
