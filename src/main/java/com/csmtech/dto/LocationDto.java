package com.csmtech.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class LocationDto {

	private Integer locationId;

	private String locationName;

	private Integer createdBy;

	private Date createdOn;

	private Integer updatedBy;

	private Date updatedOn;

	private boolean deletedFlag;
}
