package com.csmtech.dto;

import java.util.Date;

import com.csmtech.entity.Author;
import com.csmtech.entity.SubModule;
import com.csmtech.entity.Technology;

import lombok.Data;

@Data
public class ScheduleForMasterDto {
	private Integer scheduleForId;
	private String scheduleForName;
	private SubModule subModule;
	private Author author;
	private Technology technology;
	private Integer noOfSession;
	private Integer noOfHours;
	private Boolean isAssessmentPrepared;
	private Integer createdBy;
	private Date createdOn;
	private Integer updatedBy;
	private Date updatedOn;
	private Boolean deletedFlag;
}
