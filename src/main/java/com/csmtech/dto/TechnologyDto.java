package com.csmtech.dto;

import java.util.Date;

import lombok.Data;

@Data
public class TechnologyDto {

	private Integer techId;

	private String techName;

	private Integer createdBy;

	private Date createdOn;

	private Integer updatedBy;

	private Date updatedOn;

	private Boolean deletedFlag;
}
