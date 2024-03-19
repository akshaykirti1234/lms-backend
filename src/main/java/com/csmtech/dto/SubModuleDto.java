package com.csmtech.dto;

import java.util.Date;

import lombok.Data;

@Data
public class SubModuleDto {
	private Integer submoduleId;
	private Integer moduleId;
	private String submoduleName;
	private Integer createdBy;
	private Date createdOn;
	private Integer updatedBy;
	private Date updatedOn;
	private boolean deletedFlag;
}
