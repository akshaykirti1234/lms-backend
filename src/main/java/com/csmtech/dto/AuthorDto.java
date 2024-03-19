package com.csmtech.dto;

import java.util.Date;

import lombok.Data;

@Data
public class AuthorDto {
	private Integer authId;

	private String authName;

	private String email;

	private String phone;

	private Integer createdBy;

	private Date createdOn;

	private Integer updatedBy;

	private Date updatedOn;

	private Boolean deletedFlag;
}
