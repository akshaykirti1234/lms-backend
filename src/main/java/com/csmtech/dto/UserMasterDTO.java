package com.csmtech.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserMasterDTO {
	private Integer userId;
	private String normalPassword;
	private String password;
	private String fullName;
	private String contactNo;
	private String emailId;
	private String designation;
	private String department;
	private Integer location;
	private Integer userType;
	private Integer createdBy;
	private Date createdOn;
	private Integer updatedBy;
	private Date updatedOn;
	private boolean deletedFlag;
}
