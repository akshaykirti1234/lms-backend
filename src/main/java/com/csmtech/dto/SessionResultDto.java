package com.csmtech.dto;

import lombok.Data;

@Data
public class SessionResultDto {
	private Integer userId;
	private Integer questionId;
	private String option;
}
