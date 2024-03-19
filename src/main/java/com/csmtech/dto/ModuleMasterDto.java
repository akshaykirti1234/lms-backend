package com.csmtech.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ModuleMasterDto {
	private Integer moduleId;
	private String moduleName;
	private String moduleDescription;
	private String logo;
	private Integer createdBy;
	private Date createdOn;
	private Integer updatedBy;
	private Date updatedOn;
	private boolean deletedFlag;
}
