package com.csmtech.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserMasterDTO {
	private Integer userId;
	private String password;
	private String fullName;
	private Integer designId;
	private String contactNo;
	private String emailId;
	private String normalPass;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	private boolean deletedFlag;
	private Integer userTypeId;
	private Integer projectId;
	private String location;
}
