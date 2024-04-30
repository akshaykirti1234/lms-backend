package com.csmtech.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "sessionresultstatus")
public class SessionResultStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SESSIONRESULTSTATUSID")
	private Integer sessionResultStatusId;

	@ManyToOne
	@JoinColumn(name = "MODULEID")
	private ModuleMaster moduleMaster;

	@ManyToOne
	@JoinColumn(name = "SUBMODULEID")
	private SubModule subModule;

	@ManyToOne
	@JoinColumn(name = "SCHEDULEFORID")
	private ScheduleForMaster scheduleForMaster;

	@ManyToOne
	@JoinColumn(name = "SESSIONID")
	private SessionMaster sessionMaster;

	@ManyToOne
	@JoinColumn(name = "USERID")
	private UserMaster userMaster;

	@Column(name = "PERCENTAGE")
	private Double percentage;

	@Column(name = "STATUSOFRESULT")
	private Boolean statusOfResult;

}
